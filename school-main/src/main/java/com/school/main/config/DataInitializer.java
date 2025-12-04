package com.school.main.config;

import com.school.main.entity.*;
import com.school.main.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

        private final UserRepository userRepository;
        private final NewsRepository newsRepository;
        private final EventRepository eventRepository;
        private final PasswordEncoder passwordEncoder;
        private final TeacherRepository teacherRepository;
        private final ClassInfoRepository classInfoRepository;

        @Override
        public void run(String... args) throws Exception {
                if (userRepository.count() == 0) {
                        User admin = User.builder()
                                        .username("admin")
                                        .password(passwordEncoder.encode("admin123"))
                                        .fullName("Administrator")
                                        .role(Role.ADMIN)
                                        .build();
                        userRepository.save(admin);

                        User user = User.builder()
                                        .username("user")
                                        .password(passwordEncoder.encode("user123"))
                                        .fullName("Normal User")
                                        .role(Role.USER)
                                        .build();
                        userRepository.save(user);

                        // Sample News
                        newsRepository.save(News.builder()
                                        .title("Празничен концерт за будителите")
                                        .content(
                                                        "Учениците от клуб \"Приказките оживяват\" с ръководител г-жа Анна Дюлгерова взеха участие в традиционния празничен концерт за Деня на народните будители.")
                                        .date(LocalDate.now().minusDays(2))
                                        .imageUrl("https://images.unsplash.com/photo-1511671782779-c97d3d27a1d4?auto=format&fit=crop&w=800&q=80")
                                        .author(admin)
                                        .build());

                        newsRepository.save(News.builder()
                                        .title("Наследник на Ботев вдъхновява учениците")
                                        .content("Вълнуваща среща с наследник на Христо Ботев се проведе днес в аулата на училището.")
                                        .date(LocalDate.now().minusDays(5))
                                        .imageUrl("https://images.unsplash.com/photo-1524178232363-1fb2b075b655?auto=format&fit=crop&w=800&q=80")
                                        .author(admin)
                                        .build());

                        newsRepository.save(News.builder()
                                        .title("Среща с писателката Здравка Евтимова")
                                        .content("Известната писателка гостува на нашите ученици и сподели своя опит.")
                                        .date(LocalDate.now().minusDays(10))
                                        .imageUrl("https://images.unsplash.com/photo-1544716278-ca5e3f4abd8c?auto=format&fit=crop&w=800&q=80")
                                        .author(admin)
                                        .author(admin)
                                        .build());

                        newsRepository.save(News.builder()
                                        .title("Успех на олимпиадата по математика")
                                        .content("Нашите ученици завоюваха златни медали на областното първенство.")
                                        .date(LocalDate.now().minusDays(12))
                                        .imageUrl("https://images.unsplash.com/photo-1596495578065-6e0763fa1178?auto=format&fit=crop&w=800&q=80")
                                        .author(admin)
                                        .build());

                        newsRepository.save(News.builder()
                                        .title("Нова компютърна зала")
                                        .content("Училището откри модерна компютърна зала за обучение по информационни технологии.")
                                        .date(LocalDate.now().minusDays(15))
                                        .imageUrl("https://images.unsplash.com/photo-1517694712202-14dd9538aa97?auto=format&fit=crop&w=800&q=80")
                                        .author(admin)
                                        .build());

                        newsRepository.save(News.builder()
                                        .title("Спортен празник")
                                        .content("Проведе се ежегодният спортен празник с участието на всички класове.")
                                        .date(LocalDate.now().minusDays(18))
                                        .imageUrl("https://images.unsplash.com/photo-1576678927484-cc907957088c?auto=format&fit=crop&w=800&q=80")
                                        .author(admin)
                                        .build());

                        newsRepository.save(News.builder()
                                        .title("Екскурзия до Рилския манастир")
                                        .content("Учениците от 10-ти клас посетиха Рилския манастир и се запознаха с неговата история.")
                                        .date(LocalDate.now().minusDays(20))
                                        .imageUrl("https://images.unsplash.com/photo-1565060169194-126e298a275b?auto=format&fit=crop&w=800&q=80")
                                        .author(admin)
                                        .build());

                        // Sample Events
                        eventRepository.save(Event.builder()
                                        .title("Коледен концерт")
                                        .description("Годишен коледен концерт на учениците.")
                                        .eventDate(LocalDateTime.now().plusDays(20))
                                        .location("Актова зала")
                                        .build());

                        eventRepository.save(Event.builder()
                                        .title("Родителска среща")
                                        .description("Редовна родителска среща за всички класове.")
                                        .eventDate(LocalDateTime.now().plusDays(5))
                                        .location("Класни стаи")
                                        .build());

                        // Sample Teachers
                        Teacher t1 = teacherRepository.save(Teacher.builder()
                                        .name("Иван Петров")
                                        .subject("Математика")
                                        .bio("Старши учител с 20 години опит.")
                                        .build());

                        Teacher t2 = teacherRepository.save(Teacher.builder()
                                        .name("Мария Иванова")
                                        .subject("Български език и литература")
                                        .bio("Главен учител.")
                                        .build());

                        // Sample Classes
                        classInfoRepository.save(ClassInfo.builder()
                                        .name("1 А")
                                        .profile("Математика")
                                        .classTeacher(t1)
                                        .build());

                        classInfoRepository.save(ClassInfo.builder()
                                        .name("1 Б")
                                        .profile("Изкуства")
                                        .classTeacher(t2)
                                        .build());
                }
        }
}
