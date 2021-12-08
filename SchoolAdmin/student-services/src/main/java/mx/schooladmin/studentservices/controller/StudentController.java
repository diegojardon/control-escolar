package mx.schooladmin.studentservices.controller;

import mx.schooladmin.studentservices.exception.StudentNotFoundException;
import mx.schooladmin.studentservices.model.StudentDTO;
import mx.schooladmin.studentservices.service.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.List;

@RestController
public class StudentController {

    @Autowired
    private StudentService studentService;

    @GetMapping("/students")
    ResponseEntity<List<StudentDTO>> getAllStudents(){
        List<StudentDTO> studentsDTO = studentService.getStudentsFromDatabase();
        return new ResponseEntity<>(studentsDTO, HttpStatus.OK);
    }

    @GetMapping("/student/{id}")
    ResponseEntity<StudentDTO> getStudentById(@PathVariable Long id) throws StudentNotFoundException{
        StudentDTO studentDTO = studentService.getStudentByIdFromDatabase(id);
        if(studentDTO == null)
            throw new StudentNotFoundException("Student with ID " + id + " does not exist.");
        return new ResponseEntity<>(studentDTO, HttpStatus.OK);
    }

    @PostMapping("/student")
    ResponseEntity<StudentDTO> createStudent(@Valid @RequestBody StudentDTO studentDTO){
        StudentDTO createdStudentDTO = studentService.addStudentToDatabase(studentDTO);
        return new ResponseEntity<>(createdStudentDTO, HttpStatus.CREATED);
    }

    @PatchMapping("/student/{id}/age/{age}")
    ResponseEntity<String> updateStudentAge(@PathVariable Long id, @PathVariable @Min(6) @Max(80) Integer age)
            throws StudentNotFoundException {
        StudentDTO studentDTO = studentService.getStudentByIdFromDatabase(id);
        if(studentDTO == null)
            throw new StudentNotFoundException("Student with ID " + id + " does not exist.");
        studentService.updateStudentAgeInDatabase(studentDTO, age);
        return new ResponseEntity<>("Student updated successfully", HttpStatus.NO_CONTENT);
    }

    @DeleteMapping("/student/{id}")
    ResponseEntity<String> deleteStudent(@PathVariable Long id){
        studentService.deleteStudentFromDatabase(id);
        return new ResponseEntity<>("Student deleted successfully", HttpStatus.NO_CONTENT);
    }

}