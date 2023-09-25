package swp.studentprojectportal.service;

public interface IEmailService {
    public void sendEmail(String to, String subject, String body);
}
