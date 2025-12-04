package com.school.notification.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.school.notification.dto.NotificationRequest;
import com.school.notification.entity.Notification;
import com.school.notification.service.NotificationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(NotificationController.class)
class NotificationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private NotificationService notificationService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void createNotification_ShouldReturnCreated() throws Exception {
        NotificationRequest request = new NotificationRequest();
        request.setTitle("Test");
        request.setMessage("Message");
        request.setType("INFO");

        Notification notification = Notification.builder()
                .id(UUID.randomUUID())
                .title("Test")
                .message("Message")
                .type("INFO")
                .build();

        when(notificationService.createNotification(any(NotificationRequest.class))).thenReturn(notification);

        mockMvc.perform(post("/notifications")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Test"));
    }

    @Test
    void getAllNotifications_ShouldReturnList() throws Exception {
        when(notificationService.getAllNotifications()).thenReturn(List.of());

        mockMvc.perform(get("/notifications"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }
}
