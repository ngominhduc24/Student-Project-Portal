package swp.studentprojectportal.services;

public interface IEmailService {
    public void sendEmail(String to, String subject, String body);
}
