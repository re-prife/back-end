package kr.hs.mirim.family.controller;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
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

    @Operation(tags = "NOTIFICATION", summary = "심부름 생성 알림 구독", description = "심부름 생성 시 그룹원이 알림을 받는 API")
    @CrossOrigin
    @GetMapping(value = "/quest/{groupId}", consumes = MediaType.ALL_VALUE)
    public SseEmitter subscribe(@PathVariable Long groupId) {
        SseEmitter sseEmitter = new SseEmitter(Long.MAX_VALUE);
        try {
            sseEmitter.send(SseEmitter.event().name("connect"));
        } catch (IOException e) {
            e.printStackTrace();
        }

        sseEmitters.put(groupId, sseEmitter);

        sseEmitter.onCompletion(() -> sseEmitters.remove(groupId));
        sseEmitter.onTimeout(() -> sseEmitters.remove(groupId));
        sseEmitter.onError((e) -> sseEmitters.remove(groupId));

        return sseEmitter;
    }
}