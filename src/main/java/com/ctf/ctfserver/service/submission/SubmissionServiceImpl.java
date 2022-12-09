package com.ctf.ctfserver.service.submission;

import com.ctf.ctfserver.domain.entities.*;
import com.ctf.ctfserver.domain.enums.SubmissionType;
import com.ctf.ctfserver.domain.models.mapper.SubmissionMapper;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.domain.models.service.ChartServiceModel;
import com.ctf.ctfserver.domain.models.service.SolvedSubmissionServiceModel;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import com.ctf.ctfserver.repository.*;
import com.ctf.ctfserver.service.submission.collector.ChartServiceModelCollector;
import com.ctf.ctfserver.service.submission.comparator.ChartDataDateComparator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final FlagRepository flagRepository;

    private final AwardRepository awardRepository;

    private static final String CHART_DATE_FORMAT = "yyyy-MM-24\nHH:mm:ss";

    private final Function<Submission, ChartServiceModel> mapSubmissionToChartServiceModel = (submission) ->
            new ChartServiceModel(new SimpleDateFormat(CHART_DATE_FORMAT).format(submission.getDate()), submission.getChallenge().getValue());

    private final Function<Award, ChartServiceModel> mapAwardToChartServiceModel = (award) ->
            new ChartServiceModel(new SimpleDateFormat(CHART_DATE_FORMAT).format(award.getDate()), award.getValue());


    @Override
    public SubmissionServiceModel createSubmission(SubmissionServiceModel submissionServiceModel) {
        Submission submission = new Submission();
        Challenge challenge = challengeRepository.findByName(submissionServiceModel.getChallenge());
        User user = userRepository.findByUsername(submissionServiceModel.getUser());
        submission.setChallenge(challenge);
        submission.setUser(user);
        submission.setProvided(submissionServiceModel.getProvided());
        submission.setDate(submissionServiceModel.getDate());
        submission.setSchool(submissionServiceModel.getSchool());
        List<Flag> flags = this.flagRepository.findAllByChallengeId(challenge.getId());

        long count = flags.stream().map(Flag::getFlag)
                .filter(f -> f.equals(submissionServiceModel.getProvided()))
                .count();

        if (count == 1) {
            submission.setType(SubmissionType.CORRECT);
        } else {
            submission.setType(SubmissionType.INCORRECT);
        }

        return SubmissionMapper.INSTANCE
                .submissionToSubmissionServiceModel(this.submissionRepository.save(submission));

    }

    @Override
    public List<SubmissionServiceModel> getSubmissions(String type, String id) {

        if(!id.equals("undefined")) {
            return this.submissionRepository
                .findAllByType(SubmissionType.valueOf(type)).stream()
                .filter(submission -> submission.getChallenge().getId().equals(id))
                .map(SubmissionMapper.INSTANCE::submissionToSubmissionServiceModel)
                .collect(Collectors.toList());
        }
        if(type.equals("UNDEFINED")) {
            return this.submissionRepository.findAll().stream()
                .map(SubmissionMapper.INSTANCE::submissionToSubmissionServiceModel)
                .collect(Collectors.toList());
        }
        return this.submissionRepository
            .findAllByType(SubmissionType.valueOf(type)).stream()
            .map(SubmissionMapper.INSTANCE::submissionToSubmissionServiceModel)
            .collect(Collectors.toList());
    }

    @Override
    public void deleteSubmissions(List<String> submissionServiceModels) {

        submissionServiceModels.forEach(this.submissionRepository::deleteSubmissions);
    }

    @Override
    public List<SolvedSubmissionServiceModel> getSolvesForUser(String userId) {


        return this.submissionRepository.findAll().stream()
            .filter(submission -> submission.getUser().getId().equals(userId))
            .filter(submission -> submission.getType().name().equals("CORRECT"))
            .map(SubmissionMapper.INSTANCE::submissionToSolvedSubmissionServiceModel)
            .collect(Collectors.toList());
    }

    @Override
    public List<SolvedSubmissionServiceModel> getFailsForUser(String userId) {
        return this.submissionRepository.findAll().stream()
                .filter(submission -> submission.getUser().getId().equals(userId))
                .filter(submission -> submission.getType().name().equals("INCORRECT"))
                .map(SubmissionMapper.INSTANCE::submissionToSolvedSubmissionServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public void solveSubmissions(List<String> submissionServiceModels, String userId) {
        for (String submissionServiceModel : submissionServiceModels) {
            Submission submission = new Submission();
            Challenge challenge = challengeRepository.findById(submissionServiceModel).get();
            User user = userRepository.findById(userId).get();
            submission.setChallenge(challenge);
            submission.setUser(user);
            submission.setProvided("MARKED AS SOLVED BY ADMIN");
            submission.setDate(new Timestamp(System.currentTimeMillis()));
            submission.setSchool(user.getSchool());
            submission.setType(SubmissionType.CORRECT);
            this.submissionRepository.save(submission);
        }
    }

    @Override
    public List<ChartServiceModel> getUserScoreOverTime(String userId) {
        List<ChartServiceModel> solvesModels = this.submissionRepository
            .findAllByTypeAndUserId(SubmissionType.CORRECT, userId).stream()
            .map(this.mapSubmissionToChartServiceModel)
            .collect(Collectors.toList());

        List<ChartServiceModel> awardsModels = this.awardRepository
            .findAllByUserId(userId).stream()
            .map(this.mapAwardToChartServiceModel)
            .collect(Collectors.toList());

        solvesModels.addAll(awardsModels);

        return solvesModels.stream()
                .sorted(ChartDataDateComparator.getInstance())
                .collect(ChartServiceModelCollector.toChartServiceModel());
    }

}
