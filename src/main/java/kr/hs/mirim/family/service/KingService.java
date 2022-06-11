package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.response.*;
import kr.hs.mirim.family.entity.chore.ChoreCategory;
import kr.hs.mirim.family.entity.chore.repository.ChoreRepository;
import kr.hs.mirim.family.entity.group.repository.GroupRepository;
import kr.hs.mirim.family.entity.quest.repository.QuestRepository;
import kr.hs.mirim.family.exception.BadRequestException;
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
    private final QuestRepository questRepository;

    public MonthKingResponse userKing(long groupId, String date){

        existGroup(groupId);
        MonthQuestKingResponse questKing = questRepository.monthQuestKing(groupId, setFormatDate(date));
        List<MonthChoreKingResponse> choreKing = choreRepository.monthChoreKing(groupId, setFormatDate(date));
        HashMap<ChoreCategory, MonthChoreKingResponse> hashMap = new HashMap<>();

        for (MonthChoreKingResponse response : choreKing) {
            if (hashMap.containsKey(response.getCategory())) {
                if (hashMap.get(response.getCategory()).getCount() < response.getCount()){
                    System.out.println("put됨");
                    hashMap.put(response.getCategory(), response);
                }
                else continue;
            }
            else {
                hashMap.put(response.getCategory(), response);
            }
            if(hashMap.size() == 3) break;
        }

        return MonthKingResponse.builder()
                .choreKingResponse(new ArrayList<>(hashMap.values()))
                .questKingResponse(questKing)
                .build();
    }

    private YearMonth setFormatDate(String date){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM");
        YearMonth yearMonth;

        try{
            yearMonth = YearMonth.parse(date, formatter);
        }catch(Exception e){
            throw new BadRequestException("잘못된 날짜 형식입니다.");
        }
        return yearMonth;
    }

    private void existGroup(long groupId){
        if(!groupRepository.existsById(groupId)){
            throw new DataNotFoundException("존재하지 않는 그룹입니다.");
        }
    }
}
