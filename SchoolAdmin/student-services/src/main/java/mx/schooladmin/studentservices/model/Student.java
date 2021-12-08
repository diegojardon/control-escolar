package mx.schooladmin.studentservices.model;

import lombok.*;
import mx.schooladmin.studentservices.config.Generated;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity(name = "Student")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Generated
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;

    @NonNull
    private Integer age;

}
