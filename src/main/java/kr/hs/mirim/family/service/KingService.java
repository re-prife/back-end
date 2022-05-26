package kr.hs.mirim.family.service;

import kr.hs.mirim.family.dto.response.KingDataResponse;
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

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Service
@RequiredArgsConstructor
public class KingService {

    private final ChoreRepository choreRepository;
    private final GroupRepository groupRepository;
    private final QuestRepository questRepository;

    public KingDataResponse kingOfTheMonth(long groupId, String date){
        existGroup(groupId);

        List<KingResponse> list = choreRepository.monthKing(groupId, setFormatDate(date));
        HashMap<ChoreCategory, KingResponse> hashMap = new HashMap<>();

        for (KingResponse kingResponse : list) {
            if (hashMap.containsKey(kingResponse.getCategory())) {
                if (hashMap.get(kingResponse.getCategory()).getQuestCount() < kingResponse.getQuestCount())
                    hashMap.put(kingResponse.getCategory(), kingResponse);
            } else {
                hashMap.put(kingResponse.getCategory(), kingResponse);
            }
            if(hashMap.size() == 3) break;
        }

        return KingDataResponse.builder()
                .data(new ArrayList(hashMap.values()))
                .build();
    }

    public QuestKingResponse monthQuestKing(long groupId, String date){
        existGroup(groupId);
        return questRepository.questKingMonth(groupId, setFormatDate(date));
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
