package com.ctf.ctfserver.service.challenge;

import com.ctf.ctfserver.domain.entities.Challenge;
import com.ctf.ctfserver.domain.entities.Flag;
import com.ctf.ctfserver.domain.models.mapper.ChallengeMapper;
import com.ctf.ctfserver.domain.models.service.ChallengeServiceModel;
import com.ctf.ctfserver.repository.ChallengeRepository;
import com.ctf.ctfserver.repository.FlagRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class ChallengeServiceImpl implements ChallengeService {


    private final ChallengeRepository challengeRepository;
    private final FlagRepository flagRepository;

    @Override
    public void createChallenge(ChallengeServiceModel challengeServiceModel) {

        //persist challenge
        Challenge challenge = ChallengeMapper.INSTANCE.challengeServiceModelToChallenge(challengeServiceModel);
        Challenge created = challengeRepository.save(challenge);

        //persist flag
        Flag flag = new Flag();
        flag.setFlag(challengeServiceModel.getFlag());
        flag.setChallenge(created);
        flagRepository.save(flag);
    }

    @Override
    public List<ChallengeServiceModel> getAllChallenges() {
        return this.challengeRepository.findAll().stream()
                .map(ChallengeMapper.INSTANCE::ChallengeToChallengeServiceModel)
                .collect(Collectors.toList());
    }
}
