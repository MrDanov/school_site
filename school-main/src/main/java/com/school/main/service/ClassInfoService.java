package com.school.main.service;

import com.school.main.entity.ClassInfo;
import com.school.main.repository.ClassInfoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ClassInfoService {

    private final ClassInfoRepository classInfoRepository;

    public List<ClassInfo> getAllClasses() {
        return classInfoRepository.findAll();
    }

    @Transactional
    public ClassInfo saveClass(ClassInfo classInfo) {
        return classInfoRepository.save(classInfo);
    }

    @Transactional
    public void deleteClass(UUID id) {
        classInfoRepository.deleteById(id);
    }
}
