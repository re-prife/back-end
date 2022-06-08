package kr.hs.mirim.family.controller;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
public class NotificationController {

    public static Map<Long, SseEmitter> sseEmitters = new ConcurrentHashMap<>();

    @CrossOrigin
    @GetMapping(value = "/subscribe/{groupId}", consumes = MediaType.ALL_VALUE)
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