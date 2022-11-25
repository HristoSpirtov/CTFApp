package com.ctf.ctfserver.service.challenge;

import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.entities.Flag;
import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.enums.ChallengeState;
import com.ctf.ctfserver.domain.models.mapper.ChallengeMapper;
import com.ctf.ctfserver.domain.models.mapper.FlagMapper;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.domain.models.service.FlagServiceModel;
import com.ctf.ctfserver.repository.ChallengeRepository;
import com.ctf.ctfserver.repository.FlagRepository;
import com.ctf.ctfserver.repository.UserRepository;
import com.ctf.ctfserver.service.challenge.interceptor.ChallengeWithCorrectSubmissionInterceptor;
import com.ctf.ctfserver.service.challenge.interceptor.ChallengesForUserInterceptor;
import com.ctf.ctfserver.service.challenge.interceptor.SubmissionsForUserOnlyInterceptor;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import javax.transaction.Transactional;
import java.sql.Array;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeServiceImpl implements ChallengeService {


    private final ChallengeRepository challengeRepository;
    private final FlagRepository flagRepository;

    private final UserRepository userRepository;

    @Override
    public void createChallenge(ChallengeServiceModel challengeServiceModel) {

        //persist challenge
        Challenge challenge = ChallengeMapper.INSTANCE.challengeServiceModelToChallenge(challengeServiceModel);
        Challenge created = challengeRepository.save(challenge);

        challengeServiceModel.getParticipants().forEach(ch -> {
            User user = this.userRepository.findById(ch.getId()).get();
            created.getParticipants().add(user);
        });

        //persist flag
        Flag flag = new Flag();
        flag.setFlag(challengeServiceModel.getFlag());
        flag.setChallenge(created);
        flagRepository.save(flag);
    }

    @Override
    public void createFlag(FlagServiceModel flagServiceModel, String challengeId) {
        Challenge challenge = this.challengeRepository.findById(challengeId).get();
        Flag flag = new Flag();
        flag.setFlag(flagServiceModel.getFlag());
        flag.setChallenge(challenge);
        flagRepository.save(flag);
    }

    @Override
    public void editFlag(FlagServiceModel flagServiceModel) {
        Flag flag = flagRepository.findById(flagServiceModel.getId()).get();
        flag.setFlag(flagServiceModel.getFlag());
    }

    @Override
    public List<ChallengeServiceModel> getAllMissingChallengesForUser(String id) {
        User user = this.userRepository.findById(id).get();

        List<ChallengeServiceModel> collect = this.challengeRepository.findAll().stream()
                .map(ChallengeMapper.INSTANCE::ChallengeToChallengeServiceModel)
                .map(challenge -> ChallengesForUserInterceptor.getInstance().process(challenge, user.getId()))
                .filter(Objects::nonNull)
                .map(challenge -> SubmissionsForUserOnlyInterceptor.getInstance().process(challenge, user.getName()))
                .filter(Objects::nonNull)
                .map(challenge -> ChallengeWithCorrectSubmissionInterceptor.getInstance().process(challenge, user.getName()))
                .filter(Objects::nonNull)
                .filter(challenge -> challenge.getSubmissions().size() == 0)
                .collect(Collectors.toList());
        return collect;
    }

    @Override
    public List<ChallengeServiceModel> getAllChallengesForUser(String id) {
        User user = this.userRepository.findById(id).get();
        if(user.getRoles().size() == 2) {
            return this.challengeRepository.findAll().stream()
                    .map(ChallengeMapper.INSTANCE::ChallengeToChallengeServiceModel)
                    .collect(Collectors.toList());
        }
        return this.challengeRepository.findAll().stream()
                .filter(ch -> ch.getParticipants().stream().filter(p -> p.getId().equals(id)).count() == 1)
                .filter(ch -> ch.getState() == ChallengeState.VISIBLE)
                .map(ChallengeMapper.INSTANCE::ChallengeToChallengeServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public List<ChallengeServiceModel> getAllChallenges() {
        return this.challengeRepository.findAll().stream()
                .map(ChallengeMapper.INSTANCE::ChallengeToChallengeServiceModel)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteChallenges(List<ChallengeServiceModel> challengeServiceModels) {
        challengeServiceModels.forEach(challenge -> this.challengeRepository.deleteById(challenge.getId()));
    }

    @Override
    public void editChallenges(List<ChallengeServiceModel> challengeServiceModels) {
        challengeServiceModels.forEach(challenge -> {
            this.challengeRepository.findById(challenge.getId()).
                    ifPresent(found -> {
                        found.setValue(challenge.getValue());
                        found.setState(ChallengeState.valueOf(challenge.getState().toUpperCase()));
                    });
        });
    }

    @Override
    public ChallengeServiceModel getChallengeById(String id) {
        return ChallengeMapper.INSTANCE.ChallengeToChallengeServiceModel(challengeRepository.findById(id).get());
    }

    @Override
    public List<FlagServiceModel> getAllFlagsForChallenge(String challengeId) {
        return FlagMapper.INSTANCE.listFlagsToListFlagServiceModels(this.flagRepository.findAllByChallengeId(challengeId));
    }

    @Override
    public ChallengeServiceModel editChallenge(ChallengeServiceModel challengeServiceModel, String challengeId) {
        Challenge edited = this.challengeRepository.findById(challengeId).get();
        edited.setState(ChallengeState.valueOf(challengeServiceModel.getState().toUpperCase()));
        edited.setValue(challengeServiceModel.getValue());
        edited.setName(challengeServiceModel.getName());
        edited.setDescription(challengeServiceModel.getDescription());

        edited.getParticipants().clear();
        challengeServiceModel.getParticipants().forEach(ch -> {
            User user = this.userRepository.findById(ch.getId()).get();
            edited.getParticipants().add(user);
        });
        return ChallengeMapper.INSTANCE.challengeToChallengeServiceModel(edited);
    }



    @Override
    public void deleteFlag(String flagId) {
        this.flagRepository.deleteById(flagId);
    }


}
