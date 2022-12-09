package com.ctf.ctfserver.service.award;

import com.ctf.ctfserver.domain.models.service.AwardServiceModel;
import java.util.List;

public interface AwardService {

    void createAward(AwardServiceModel awardServiceModel);
    List<AwardServiceModel> findAllAwardsForUser(String userId);

    void deleteSubmissions(List<String> awardServiceModels);
}
