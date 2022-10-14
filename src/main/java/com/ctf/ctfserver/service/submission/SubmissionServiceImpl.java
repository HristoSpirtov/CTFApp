package com.ctf.ctfserver.service.submission;

import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.entities.Flag;
import com.ctf.ctfserver.domain.entities.Submission;
import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.enums.SubmissionType;
import com.ctf.ctfserver.domain.models.mapper.SubmissionMapper;
import com.ctf.ctfserver.domain.models.service.SubmissionServiceModel;
import com.ctf.ctfserver.repository.ChallengeRepository;
import com.ctf.ctfserver.repository.FlagRepository;
import com.ctf.ctfserver.repository.SubmissionRepository;
import com.ctf.ctfserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final SubmissionRepository submissionRepository;
    private final ChallengeRepository challengeRepository;
    private final UserRepository userRepository;
    private final FlagRepository flagRepository;


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
        submission.setProvided(submissionServiceModel.getProvided());
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

}
