package mx.schooladmin.studentservices.service;

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
        return students.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public StudentDTO getStudentByIdFromDatabase(Long id){
        Optional<Student> optionalStudent = studentRepository.findById(id);
        if(optionalStudent.isEmpty())
            return null;

        return convertToDto(optionalStudent.get());
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
        return modelMapper.map(post, StudentDTO.class);
    }

    private Student convertToEntity(StudentDTO studentDTO){
        return modelMapper.map(studentDTO, Student.class);
    }
}
