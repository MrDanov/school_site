package com.school.main.service;

import com.school.main.client.NotificationClient;
import com.school.main.entity.Event;
import com.school.main.repository.EventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventServiceTest {

    @Mock
    private EventRepository eventRepository;

    @Mock
    private NotificationClient notificationClient;

    @InjectMocks
    private EventService eventService;

    @Test
    void createEvent_ShouldSaveAndNotify() {
        Event event = Event.builder()
                .title("Test Event")
                .description("Description")
                .eventDate(LocalDateTime.now().plusDays(1))
                .location("Location")
                .build();

        when(eventRepository.save(any(Event.class))).thenReturn(event);

        Event created = eventService.createEvent(event);

        assertNotNull(created);
        assertEquals("Test Event", created.getTitle());
        verify(eventRepository, times(1)).save(any(Event.class));
        verify(notificationClient, times(1)).createNotification(any());
    }

    @Test
    void getUpcomingEvents_ShouldReturnList() {
        when(eventRepository.findAllByEventDateAfterOrderByEventDateAsc(any(LocalDateTime.class)))
                .thenReturn(List.of(new Event()));

        List<Event> events = eventService.getUpcomingEvents();

        assertFalse(events.isEmpty());
        verify(eventRepository, times(1)).findAllByEventDateAfterOrderByEventDateAsc(any(LocalDateTime.class));
    }
}
