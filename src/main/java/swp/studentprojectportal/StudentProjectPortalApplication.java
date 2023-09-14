package swp.studentprojectportal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IUserRepository;

@SpringBootApplication
public class StudentProjectPortalApplication {

    public static void main(String[] args) {
        ApplicationContext context = SpringApplication.run(StudentProjectPortalApplication.class, args);
        IUserRepository userRepository = context.getBean(IUserRepository.class);

        // Lấy ra toàn bộ user trong db
        User u = userRepository.findUserByEmailAndPassword("gillianmorris@gmail.com", "123456");
        System.out.println(u);
    }

}
