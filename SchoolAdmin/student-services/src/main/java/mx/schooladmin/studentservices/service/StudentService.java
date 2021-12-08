package mx.schooladmin.studentservices.service;

import mx.schooladmin.studentservices.model.StudentDTO;

import java.util.List;

public interface StudentService {

    List<StudentDTO>  getStudentsFromDatabase();
    StudentDTO getStudentByIdFromDatabase(Long id);
    StudentDTO addStudentToDatabase(StudentDTO studentDTO);
    void updateStudentAgeInDatabase(StudentDTO studentDTO, int age);
    void deleteStudentFromDatabase(Long id);
}
