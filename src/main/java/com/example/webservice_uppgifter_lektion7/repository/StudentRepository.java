package com.example.webservice_uppgifter_lektion7.repository;

import com.example.webservice_uppgifter_lektion7.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findStudentById(Long id);
}
