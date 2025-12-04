package com.school.main.service;

import com.school.main.client.NotificationClient;
import com.school.main.client.NotificationDTO;
import com.school.main.entity.Event;
import com.school.main.repository.EventRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class EventService {

    private final EventRepository eventRepository;
    private final NotificationClient notificationClient;

    public List<Event> getUpcomingEvents() {
        return eventRepository.findAllByEventDateAfterOrderByEventDateAsc(LocalDateTime.now());
    }

    public Event getEventById(UUID id) {
        return eventRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Event not found"));
    }

    @Transactional
    @CacheEvict(value = "newsList", allEntries = true) // Assuming events might appear on home page which is cached
    public Event createEvent(Event event) {
        log.info("Creating event: {}", event.getTitle());
        Event savedEvent = eventRepository.save(event);

        try {
            String username = "System";
            try {
                username = org.springframework.security.core.context.SecurityContextHolder.getContext()
                        .getAuthentication().getName();
            } catch (Exception e) {
                // ignore
            }

            notificationClient.createNotification(NotificationDTO.builder()
                    .title("New Event")
                    .message("Upcoming event: " + savedEvent.getTitle())
                    .type("EVENT_CREATED")
                    .username(username)
                    .build());
        } catch (Exception e) {
            log.error("Failed to send notification", e);
        }

        return savedEvent;
    }

    @Transactional
    public void deleteEvent(UUID id) {
        log.info("Deleting event: {}", id);
        eventRepository.deleteById(id);
    }
}
