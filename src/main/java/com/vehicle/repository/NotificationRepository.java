package com.vehicle.repository;

import com.vehicle.model.entity.Notification;
import com.vehicle.model.enums.NotificationType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {

    Page<Notification> findByStudentId(Long studentId, Pageable pageable);

    Page<Notification> findByStudentIdAndIsRead(Long studentId, boolean isRead, Pageable pageable);

    Page<Notification> findByIsRead(boolean isRead, Pageable pageable);

    Page<Notification> findByType(NotificationType type, Pageable pageable);

    List<Notification> findByStudentIdAndIsReadOrderByCreatedAtDesc(Long studentId, boolean isRead);

    @Query("SELECT n FROM Notification n WHERE n.type = :type AND n.createdAt >= :since")
    List<Notification> findByTypeAndCreatedAtAfter(@Param("type") NotificationType type,
                                                    @Param("since") LocalDateTime since);

    List<Notification> findByStudentIdOrderByCreatedAtDesc(Long studentId);
}
