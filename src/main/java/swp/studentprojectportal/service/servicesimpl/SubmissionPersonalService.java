package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.SubmissionPersonal;
import swp.studentprojectportal.repository.ISubmissionPersonalReposiroty;
import swp.studentprojectportal.service.ISubmissionPersonalService;

import java.util.List;

@Service
public class SubmissionPersonalService implements ISubmissionPersonalService {
    @Autowired
    ISubmissionPersonalReposiroty submissionPersonalReposiroty;

    @Autowired
    SubmissionService submissionService;

    @Autowired
    UserService userService;

    public List<SubmissionPersonal> getAllSubmissionPersonalBySubmissionId(int submissionId){
        return submissionPersonalReposiroty.findAllBySubmissionId(submissionId);
    }

    public boolean updateSubmissionPersonal(Integer submissionId, Integer studentId, String bonus, String comment){
        try {
            SubmissionPersonal submissionPersonal = new SubmissionPersonal();
            if(submissionPersonalReposiroty.findBySubmissionIdAndStudentId(submissionId, studentId) != null) {
                submissionPersonal = submissionPersonalReposiroty.findBySubmissionIdAndStudentId(submissionId, studentId);
            } else {
                submissionPersonal.setSubmission(submissionService.findById(submissionId));
                submissionPersonal.setStudent(userService.findById(studentId));
            }
            float bonusParse = 0f;
            try {
                bonusParse = Float.parseFloat(bonus);
            } catch (Exception e) {
                return false;
            }
            submissionPersonal.setBonus(bonusParse);
            submissionPersonal.setComment(comment);
            submissionPersonalReposiroty.save(submissionPersonal);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
