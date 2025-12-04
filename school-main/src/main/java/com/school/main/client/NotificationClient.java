package com.school.main.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "notification-service", url = "${notification-service.url}")
public interface NotificationClient {

    @PostMapping("/notifications")
    void createNotification(@RequestBody NotificationDTO notification);

    @GetMapping("/notifications")
    List<NotificationDTO> getAllNotifications();

    @DeleteMapping("/notifications/{id}")
    void deleteNotification(@PathVariable("id") UUID id);
}
