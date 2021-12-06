package mx.schooladmin.studentservices.service;

import mx.schooladmin.studentservices.exception.StudentNotFoundException;
import mx.schooladmin.studentservices.model.Student;
import mx.schooladmin.studentservices.model.StudentDTO;
import mx.schooladmin.studentservices.repository.StudentRepository;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class StudentServiceImpl implements StudentService{

    @Autowired
    private StudentRepository studentRepository;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public List<StudentDTO> getStudentsFromDatabase() {
        List<Student> students = studentRepository.findAll();
        List<StudentDTO> studentsDTO = students.stream().map(student -> convertToDto(student)).collect(Collectors.toList());
        return studentsDTO;
    }

    @Override
    public StudentDTO getStudentByIdFromDatabase(Long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty())
            return null;

        StudentDTO studentDTO = convertToDto(optionalStudent.get());
        return studentDTO;
    }

    @Override
    public StudentDTO addStudentToDatabase(StudentDTO studentDTO) {
        Student student = studentRepository.save(convertToEntity(studentDTO));
        return convertToDto(student);
    }

    @Override
    public void updateStudentAgeInDatabase(StudentDTO studentDTO, int age){
        studentDTO.setAge(age);
        studentRepository.save(convertToEntity(studentDTO));
    }

    @Override
    public void deleteStudentFromDatabase(Long id) {
        studentRepository.deleteById(id);
    }

    private StudentDTO convertToDto(Student post) {
        StudentDTO studentDTO = modelMapper.map(post, StudentDTO.class);
        return studentDTO;
    }

    private Student convertToEntity(StudentDTO studentDTO){
        Student student = modelMapper.map(studentDTO, Student.class);
        return student;
    }
}
