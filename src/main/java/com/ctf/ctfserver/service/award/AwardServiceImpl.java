package com.ctf.ctfserver.service.award;

import com.ctf.ctfserver.domain.entities.Award;
import com.ctf.ctfserver.domain.entities.User;
import com.ctf.ctfserver.domain.models.mapper.AwardMapper;
import com.ctf.ctfserver.domain.models.service.AwardServiceModel;
import com.ctf.ctfserver.repository.AwardRepository;
import com.ctf.ctfserver.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class AwardServiceImpl implements AwardService {

    private final AwardRepository awardRepository;
    private final UserRepository userRepository;

    @Override
    public void createAward(AwardServiceModel awardServiceModel) {
        Award award = AwardMapper.INSTANCE.awardServiceModelToAward(awardServiceModel);
        this.awardRepository.save(award);
        User user = this.userRepository.findById(awardServiceModel.getUserId()).get();
        user.getAwards().add(award);

    }

    @Override
    public List<AwardServiceModel> findAllAwardsForUser(String userId) {
        return AwardMapper.INSTANCE.awardToAwardServiceModels(this.awardRepository.findAllByUserId(userId));
    }

    @Override
    public void deleteSubmissions(List<String> awardServiceModels) {
        awardServiceModels.forEach(this.awardRepository::deleteAwards);
    }
}
