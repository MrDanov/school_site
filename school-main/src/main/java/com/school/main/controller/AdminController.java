package com.school.main.controller;

import com.school.main.client.NotificationClient;
import com.school.main.entity.*;
import com.school.main.service.*;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Controller
@RequestMapping("/admin")
@RequiredArgsConstructor
public class AdminController {

    private final NewsService newsService;
    private final EventService eventService;
    private final NotificationClient notificationClient;

    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        try {
            model.addAttribute("notifications", notificationClient.getAllNotifications());
        } catch (Exception e) {
            model.addAttribute("notificationsError", "Could not load notifications");
        }
        return "admin/dashboard";
    }

    // --- News Management ---
    @GetMapping("/news")
    public String manageNews(Model model) {
        model.addAttribute("newsList", newsService.getAllNews());
        return "admin/news/list";
    }

    @GetMapping("/news/create")
    public String createNewsForm(Model model) {
        model.addAttribute("news", new News());
        return "admin/news/form";
    }

    @PostMapping("/news/save")
    public String saveNews(@Valid @ModelAttribute News news, BindingResult result,
            @RequestParam(required = false) String additionalImagesText) {
        if (result.hasErrors()) {
            return "admin/news/form";
        }

        if (additionalImagesText != null && !additionalImagesText.isBlank()) {
            java.util.List<String> urls = java.util.Arrays.stream(additionalImagesText.split("\\r?\\n"))
                    .map(String::trim)
                    .filter(s -> !s.isEmpty())
                    .toList();
            news.setAdditionalImageUrls(urls);
        }

        newsService.createNews(news);
        return "redirect:/admin/news";
    }

    @GetMapping("/news/delete/{id}")
    public String deleteNews(@PathVariable UUID id) {
        newsService.deleteNews(id);
        return "redirect:/admin/news";
    }

    // --- Event Management ---
    @GetMapping("/events")
    public String manageEvents(Model model) {
        model.addAttribute("events", eventService.getUpcomingEvents());
        return "admin/events/list";
    }

    @GetMapping("/events/create")
    public String createEventForm(Model model) {
        model.addAttribute("event", new Event());
        return "admin/events/form";
    }

    @PostMapping("/events/save")
    public String saveEvent(@Valid @ModelAttribute Event event, BindingResult result) {
        if (result.hasErrors()) {
            return "admin/events/form";
        }
        eventService.createEvent(event);
        return "redirect:/admin/events";
    }

    @GetMapping("/events/delete/{id}")
    public String deleteEvent(@PathVariable UUID id) {
        eventService.deleteEvent(id);
        return "redirect:/admin/events";
    }
}
