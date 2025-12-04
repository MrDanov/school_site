package com.school.notification.service;

import com.school.notification.dto.NotificationRequest;
import com.school.notification.entity.Notification;
import com.school.notification.exception.ResourceNotFoundException;
import com.school.notification.repository.NotificationRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class NotificationService {

    private final NotificationRepository notificationRepository;

    public Notification createNotification(NotificationRequest request) {
        log.info("Creating notification: {}", request);
        Notification notification = Notification.builder()
                .title(request.getTitle())
                .message(request.getMessage())
                .type(request.getType())
                .username(request.getUsername())
                .build();
        return notificationRepository.save(notification);
    }

    public List<Notification> getAllNotifications() {
        log.info("Fetching all notifications");
        return notificationRepository.findAllByOrderByCreatedAtDesc();
    }

    public void deleteNotification(UUID id) {
        log.info("Deleting notification with ID: {}", id);
        if (!notificationRepository.existsById(id)) {
            throw new ResourceNotFoundException("Notification not found with id: " + id);
        }
        notificationRepository.deleteById(id);
    }
}
