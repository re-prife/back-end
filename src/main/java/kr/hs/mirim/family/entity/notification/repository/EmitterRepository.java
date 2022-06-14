package kr.hs.mirim.family.entity.notification.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository {
    SseEmitter save(String emitterId, SseEmitter sseEmitter);

    void saveEventCache(String emitterId, Object event);

    Map<String, SseEmitter> findAllEmitterStartWithByUserId(String userId);

    Map<String, Object> findAllEventCacheStartWithByUserId(String userId);

    void deleteById(String id);

    void deleteAllEmitterStartWithUserId(String UserId);

    void deleteAllEventCacheStartWithUserId(String userId);
}
