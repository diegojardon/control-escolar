package mx.schooladmin.studentservices.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import mx.schooladmin.studentservices.exception.StudentNotFoundException;
import mx.schooladmin.studentservices.model.StudentDTO;
import mx.schooladmin.studentservices.service.StudentService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.springframework.test.util.AssertionErrors.assertEquals;
import static org.springframework.test.util.AssertionErrors.assertTrue;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@WebMvcTest(StudentController.class)
class StudentControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @MockBean
    StudentService studentService;

    StudentDTO STUDENT_1 = new StudentDTO(1L, "Diego Alberto Jardon Ramirez", 34);
    StudentDTO STUDENT_2 = new StudentDTO(2L, "Liliana Manzanarez Ramirez", 43);
    StudentDTO STUDENT_3 = new StudentDTO(3L, "Juan Perez", 15);

    @Test
    void getAllStudents_success() throws Exception {
        List<StudentDTO> studentList = new ArrayList<>(Arrays.asList(STUDENT_1, STUDENT_2, STUDENT_3));
        Mockito.when(studentService.getStudentsFromDatabase()).thenReturn(studentList);

        mockMvc.perform(MockMvcRequestBuilders.get("/students").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[0].name", is("Diego Alberto Jardon Ramirez")));
    }

    @Test
    void getStudentById_success() throws Exception {
        Mockito.when(studentService.getStudentByIdFromDatabase(STUDENT_2.getId())).thenReturn(STUDENT_2);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/2").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Liliana Manzanarez Ramirez")));
    }

    @Test
    void getStudentById_studentNotFound() throws Exception{
        Long nonExistentStudentId = 5L;

        Mockito.when(studentService.getStudentByIdFromDatabase(nonExistentStudentId)).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.get("/student/5")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue("",
                                                result.getResolvedException() instanceof StudentNotFoundException))
                .andExpect(result -> assertEquals("", "Student with ID " + nonExistentStudentId + " does not exist.",
                                                  result.getResolvedException().getMessage()));
    }

    @Test
    void createStudent_success() throws Exception{
        StudentDTO student = StudentDTO.builder().id(4L).name("Panchito Lopez Perez").age(25).build();

        Mockito.when(studentService.addStudentToDatabase(student)).thenReturn(student);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.post("/student")
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(this.objectMapper.writeValueAsString(student));

        mockMvc.perform(mockRequest)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$", notNullValue()))
                .andExpect(jsonPath("$.name", is("Panchito Lopez Perez")));
    }

    @Test
    void updateStudentAge_success() throws Exception{
        final int mockAge = 25;

        Mockito.when(studentService.getStudentByIdFromDatabase(STUDENT_1.getId())).thenReturn(STUDENT_1);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/student/1/age/25")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest).andExpect(status().isNoContent());

        Mockito.verify(studentService, Mockito.times(1)).updateStudentAgeInDatabase(STUDENT_1, mockAge);
    }

    @Test
    void updateStudentAge_studentNotFound() throws Exception{
        Long nonExistentStudentId = 5L;

        Mockito.when(studentService.getStudentByIdFromDatabase(nonExistentStudentId)).thenReturn(null);

        MockHttpServletRequestBuilder mockRequest = MockMvcRequestBuilders.patch("/student/5/age/25")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(mockRequest)
                .andExpect(status().isNotFound())
                .andExpect(result -> assertTrue("",
                                                result.getResolvedException() instanceof StudentNotFoundException))
                .andExpect(result -> assertEquals("", "Student with ID " + nonExistentStudentId + " does not exist.",
                                                  result.getResolvedException().getMessage()));
    }

    @Test
    void deleteStudent_success() throws Exception{

        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());

        Mockito.verify(studentService).deleteStudentFromDatabase(STUDENT_1.getId());
    }

}