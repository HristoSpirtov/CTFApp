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

        long count = flags.stream().map(f -> f.getFlag())
                .filter(f -> f.equals(submissionServiceModel.getProvided()))
                .count();

        if (count == 1) {
            submission.setType(SubmissionType.CORRECT);
        } else {
            submission.setType(SubmissionType.INCORRECT);
        }

//        SubmissionServiceModel saved = mapSavedSubmissionToServiceModel(this.submissionRepository.save(submission));



        return SubmissionMapper.INSTANCE.submissionToSubmissionServiceModel(this.submissionRepository.save(submission));

    }

//    private SubmissionServiceModel mapSavedSubmissionToServiceModel(Submission submission) {
//        SubmissionServiceModel submissionServiceModel = new SubmissionServiceModel();
//        submissionServiceModel.setUser(submission.getUser().getUsername());
//        submissionServiceModel.setChallenge(submission.getChallenge().getName());
//        submissionServiceModel.setType(submission.getType().name());
//        submissionServiceModel.setSchool(submission.getSchool());
//        submissionServiceModel.setProvided(submission.getProvided());
//        submissionServiceModel.setDate(submission.getDate());
//        return submissionServiceModel;
//    }
}
