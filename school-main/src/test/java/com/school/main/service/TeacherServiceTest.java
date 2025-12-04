package com.school.main.service;

import com.school.main.entity.Teacher;
import com.school.main.repository.TeacherRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TeacherServiceTest {

    @Mock
    private TeacherRepository teacherRepository;

    @InjectMocks
    private TeacherService teacherService;

    @Test
    void getAllTeachers_ShouldReturnList() {
        when(teacherRepository.findAll()).thenReturn(List.of(new Teacher()));

        List<Teacher> teachers = teacherService.getAllTeachers();

        assertFalse(teachers.isEmpty());
        verify(teacherRepository, times(1)).findAll();
    }

    @Test
    void getTeacherById_ShouldReturnTeacher() {
        UUID id = UUID.randomUUID();
        Teacher teacher = Teacher.builder().id(id).name("Test Teacher").build();

        when(teacherRepository.findById(id)).thenReturn(Optional.of(teacher));

        Teacher found = teacherService.getTeacherById(id);

        assertEquals(id, found.getId());
    }
}
