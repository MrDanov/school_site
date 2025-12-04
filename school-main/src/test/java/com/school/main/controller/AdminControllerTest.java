package com.school.main.controller;

import com.school.main.client.NotificationClient;
import com.school.main.service.EventService;
import com.school.main.service.NewsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(AdminController.class)
@AutoConfigureMockMvc(addFilters = false) // Disable security filters for simple testing
class AdminControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NewsService newsService;

    @MockBean
    private EventService eventService;

    @MockBean
    private NotificationClient notificationClient;

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void dashboard_ShouldReturnDashboardView() throws Exception {
        mockMvc.perform(get("/admin/dashboard"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/dashboard"));
    }

    @Test
    @WithMockUser(username = "admin", roles = { "ADMIN" })
    void manageNews_ShouldReturnNewsListView() throws Exception {
        mockMvc.perform(get("/admin/news"))
                .andExpect(status().isOk())
                .andExpect(view().name("admin/news/list"))
                .andExpect(model().attributeExists("newsList"));
    }
}
