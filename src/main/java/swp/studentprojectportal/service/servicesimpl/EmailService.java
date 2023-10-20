package swp.studentprojectportal.service.servicesimpl;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.service.IEmailService;
import swp.studentprojectportal.utils.instance.InstanceThread;

import java.util.logging.Level;
import java.util.logging.Logger;

@Service
public class EmailService implements IEmailService {
    @Autowired
    private JavaMailSender mailSender;

    @Override
    public void sendEmail(String name, String to, String token) {
        String fromAddress = "Studix@sss";
        String senderName = "Studix";
        String subject = "Activate your Studix account";
        String content = "\n" +
                "<!DOCTYPE html>\n" +
                "<html lang=\"en\">\n" +
                "\n" +
                "    <head>\n" +
                "        <meta charset=\"UTF-8\">\n" +
                "        <meta http-equiv=\"X-UA-Compatible\" content=\"IE=edge\">\n" +
                "        <meta name=\"viewport\" content=\"width=device-width, initial-scale=1.0\">\n" +
                "        <title>Tailwind CSS Simple Email Template Example </title>\n" +
                "        <link href=\"https://unpkg.com/tailwindcss@^2/dist/tailwind.min.css\" rel=\"stylesheet\">\n" +
                "\n" +
                "    </head>\n" +
                "\n" +
                "    <body>\n" +
                "        <div class=\"flex items-center justify-center min-h-screen p-5 bg-gray-600 min-w-screen\">\n" +
                "            <div class=\"max-w-xl p-8 text-center text-gray-800 bg-white shadow-xl lg:max-w-3xl rounded-3xl lg:p-12\">\n" +
                "                <h3 class=\"text-2xl\">Thanks [[name]] for signing up for Studix!</h3>\n" +
                "                <div class=\"flex justify-center\">\n" +
                "                    <img style=\"height: 80px;\"\n" +
                "                    src=\"https://res.cloudinary.com/dxu6nsoye/image/upload/v1695704345/logog3-removebg-preview_ptkuxj.png\"\n" +
                "                    alt=\"\">\n" +
                "                </div>\n" +
                "                <p>We're happy you're here. Let's get your email address verified:</p>\n" +
                "                <div class=\"mt-4\">\n" +
                "                    <a href=\"[[URL]]\"> <button class=\"px-2 py-2 text-blue-200 bg-blue-600 rounded\">Click to Verify Email</button></a>\n" +
                "                    <p class=\"mt-4 text-sm\">If you’re having trouble clicking the \"Verify Email Address\" button, copy and paste the URL below into your web browser: <br>\n" +
                "                        <a href=\"\" class=\"text-blue-600\">[[URL]]</a>\n" +
                "                    </p>\n" +
                "                </div>\n" +
                "            </div>\n" +
                "        </div>\n" +
                "\n" +
                "    </body>\n" +
                "</html>";
        MimeMessage message = mailSender.createMimeMessage();
         try {
            content = content.replace("[[name]]", name);
            content = content.replace("[[URL]]", token);

            message.setFrom(fromAddress);
            message.setRecipients(MimeMessage.RecipientType.TO, to);
            message.setSubject(subject);
            message.setContent(content, "text/html;charset=UTF-8");
            InstanceThread emailThread = new InstanceThread();
            emailThread.emailSenderThread(mailSender, message);

        } catch (MessagingException ex) {
            Logger.getLogger(UserService.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}
