package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Submission;
import swp.studentprojectportal.model.User;

public interface ISubmissionService {

    public Submission insertSubmission(Integer milestoneId, String note, User user);

    public boolean updateFileLocation(Integer submissionId, String fileLocation);

    public Submission findById(Integer submissionId);

    boolean updateComment(Integer submissionId, String comment);
}
