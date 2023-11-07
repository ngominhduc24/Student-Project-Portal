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

    public List<SubmissionPersonal> getAllSubmissionPersonalBySubmissionId(int submissionId){
        return submissionPersonalReposiroty.findAllBySubmissionId(submissionId);
    }

    public boolean updateSubmissionPersonal(Integer submissionId, Integer studentId, Integer bonus, String comment){
        try {
            SubmissionPersonal submissionPersonal = submissionPersonalReposiroty.findBySubmissionIdAndStudentId(submissionId, studentId);
            submissionPersonal.setBonus(bonus);
            submissionPersonal.setComment(comment);
            submissionPersonalReposiroty.save(submissionPersonal);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
