package kr.hs.mirim.family.controller;

import kr.hs.mirim.family.dto.response.KingResponse;
import kr.hs.mirim.family.service.KingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;


@RestController
@RequiredArgsConstructor
@RequestMapping("/groups/{groupId}")
public class KingController {
    private final KingService kingService;

    @GetMapping("/kings")
    public KingResponse getKingOfTheMonth(@PathVariable long groupId, @RequestParam String date){
        return kingService.kingOfTheMonth(groupId,date);
    }
}
