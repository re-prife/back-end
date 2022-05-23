package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.response.KingDataResponse;
import kr.hs.mirim.family.dto.response.KingResponse;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import kr.hs.mirim.family.entity.chore.repository.ChoreRepository;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.exception.DataNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KingService {

    private final ChoreRepository choreRepository;
    private final GroupRepository groupRepository;

    public KingDataResponse kingOfTheMonth(long groupId, String date){
        if(!groupRepository.existsById(groupId)){
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        }

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth = YearMonth.parse(date, formatter);

        List<KingResponse> list = choreRepository.monthKing(groupId, yearMonth);
        List<KingResponse> returnList = new ArrayList<>();

        ChoreCategory[] categories = new ChoreCategory[]{ChoreCategory.SHOPPING, ChoreCategory.DISH_WASHING, ChoreCategory.COOK};


        for(ChoreCategory category : categories){
            KingResponse res = categoryList(list, category);
            if(res!=null)
                returnList.add(res);
        }

        return KingDataResponse.builder()
                .data(returnList)
                .build();
    }

    private KingResponse categoryList(List<KingResponse> list, ChoreCategory category){
        for (KingResponse kingResponse : list) {
            if (kingResponse.getCategory() == category)
                return new KingResponse(category, kingResponse.getUserId(), kingResponse.getQuestCount());
        }
        return null;
    }
}
