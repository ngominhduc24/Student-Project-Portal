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

}
