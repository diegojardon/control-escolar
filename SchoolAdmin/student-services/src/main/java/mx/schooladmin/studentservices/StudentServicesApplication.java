package mx.schooladmin.studentservices;

import mx.schooladmin.studentservices.config.Generated;
import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class StudentServicesApplication {

	@Generated
	public static void main(String[] args) {
		SpringApplication.run(StudentServicesApplication.class, args);
	}

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
}
