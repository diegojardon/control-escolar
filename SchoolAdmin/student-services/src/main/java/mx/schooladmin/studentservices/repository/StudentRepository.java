package mx.schooladmin.studentservices.repository;

import mx.schooladmin.studentservices.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
