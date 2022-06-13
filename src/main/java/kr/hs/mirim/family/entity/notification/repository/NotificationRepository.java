package kr.hs.mirim.family.entity.notification.repository;

import kr.hs.mirim.family.entity.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}