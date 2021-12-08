package mx.schooladmin.studentservices.model;

import lombok.*;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

@Generated
@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentDTO {

    private Long id;

    private String name;

    @Min(6)
    @Max(80)
    private int age;

}
