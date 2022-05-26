package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.response.ChoreKingResponse;
import kr.hs.mirim.family.dto.response.KingResponse;
import kr.hs.mirim.family.dto.response.QuestKingResponse;
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

    public KingResponse kingOfTheMonth(long groupId, String date){

        existGroup(groupId);
        List<QuestKingResponse> questKing = questRepository.questKingMonth(groupId, setFormatDate(date));
        List<ChoreKingResponse> choreList = choreRepository.monthKing(groupId, setFormatDate(date));
        HashMap<ChoreCategory, ChoreKingResponse> hashMap = new HashMap<>();

        for (ChoreKingResponse choreKingResponse : choreList) {
            if (hashMap.containsKey(choreKingResponse.getCategory())) {
                if (hashMap.get(choreKingResponse.getCategory()).getCount() < choreKingResponse.getCount())
                    hashMap.put(choreKingResponse.getCategory(), choreKingResponse);
            } else {
                hashMap.put(choreKingResponse.getCategory(), choreKingResponse);
            }
            if(hashMap.size() == 3) break;
        }

        return KingResponse.builder()
                .choreKing(new ArrayList(hashMap.values()))
                .questKing(questKing)
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
