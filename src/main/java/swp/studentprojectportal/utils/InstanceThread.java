package swp.studentprojectportal.utils;

import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import swp.studentprojectportal.service.servicesimpl.EmailService;

@Component
public class InstanceThread extends Thread{
    @Autowired
    EmailService emailService;
    public void run() {
        // Thread running
    }

    public void emailSenderThread(JavaMailSender mailSender, MimeMessage message) {
        Thread thread1 = new Thread(() -> {
            mailSender.send(message);
        });
        thread1.start();
    }

}
