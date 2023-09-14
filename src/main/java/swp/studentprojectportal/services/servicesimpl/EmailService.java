package swp.studentprojectportal.services.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.services.IEmailService;
@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;
    @Override
    public void sendEmail(String name, String to, String token) {
        String body = "\n" +
                "Hi " + name + "\n" +
                "\n" +
                "You registered an account on SPP-G3, before being able to use your account you need to verify that this is your email address by clicking here: " + token;
        String subject = "Activate your SPP-G3 account";
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("G3@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(body);

        mailSender.send(message);
    }

}
