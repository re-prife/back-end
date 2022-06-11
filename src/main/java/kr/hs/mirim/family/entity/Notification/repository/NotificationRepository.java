package kr.hs.mirim.family.entity.Notification.repository;

import kr.hs.mirim.family.entity.Notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NotificationRepository extends JpaRepository<Notification, Long> {
}