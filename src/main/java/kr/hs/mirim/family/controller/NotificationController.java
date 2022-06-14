package kr.hs.mirim.family.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import kr.hs.mirim.family.exception.InternalServerException;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Tag(name = "NOTIFICATION", description = "알림 API")
@RestController
@RequestMapping(value = "/subscribe")
public class NotificationController {

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @Operation(tags = "NOTIFICATION", summary = "그룹 내, 실시간 알림 구독", description = "그룹에서 실시간으로 심부름 생성 및 집안일 인증 알림을 받는 API")
    @CrossOrigin
    @GetMapping(value = "/{groupId}", consumes = MediaType.ALL_VALUE,  produces="application/text; charset=utf8")
    public SseEmitter subscribe(@PathVariable Long groupId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            throw new InternalServerException(e.getMessage());
        }

        sseEmitters.put(groupId, sseEmitter);

        sseEmitter.onCompletion(() -> sseEmitters.remove(groupId));
        sseEmitter.onTimeout(() -> sseEmitters.remove(groupId));
        sseEmitter.onError((e) -> sseEmitters.remove(groupId));

        return sseEmitter;
    }
}