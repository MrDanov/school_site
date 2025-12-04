package com.school.main.controller;

import com.school.main.service.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.UUID;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final NewsService newsService;
    private final EventService eventService;

    private final TeacherService teacherService;
    private final ClassInfoService classInfoService;

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("newsList", newsService.getTop6News());
        model.addAttribute("events", eventService.getUpcomingEvents());
        return "index";
    }

    @GetMapping("/news")
    public String news(Model model) {
        model.addAttribute("newsList", newsService.getAllNews());
        return "news/list";
    }

    @GetMapping("/news/{id}")
    public String newsDetail(@PathVariable UUID id, Model model) {
        model.addAttribute("news", newsService.getNewsById(id));
        return "news/detail";
    }

    @GetMapping("/events")
    public String events(Model model) {
        model.addAttribute("events", eventService.getUpcomingEvents());
        return "events/list";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/teachers")
    public String teachers(Model model) {
        model.addAttribute("teachers", teacherService.getAllTeachers());
        return "teachers";
    }

    @GetMapping("/classes")
    public String classes(Model model) {
        model.addAttribute("classes", classInfoService.getAllClasses());
        return "classes";
    }

}
