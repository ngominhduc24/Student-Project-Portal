package swp.studentprojectportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IUserRepository;

@SpringBootApplication
public class StudentProjectPortalApplication {

    public static void main(String[] args) {
        SpringApplication.run(StudentProjectPortalApplication.class, args);
    }

}
