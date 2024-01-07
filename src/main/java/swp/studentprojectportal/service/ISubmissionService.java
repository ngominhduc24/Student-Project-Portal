package swp.studentprojectportal.service;

import swp.studentprojectportal.model.Submission;
import swp.studentprojectportal.model.User;

import java.util.List;

public interface ISubmissionService {

    public Submission insertSubmission(Integer milestoneId, String note, User user, Integer projectId);

    public boolean updateFileLocation(Integer submissionId, String fileLocation);

    public Submission findById(Integer submissionId);

    public List<Submission> findAllByProjectId(Integer projectId);

    boolean updateComment(Integer submissionId, String comment);
}
