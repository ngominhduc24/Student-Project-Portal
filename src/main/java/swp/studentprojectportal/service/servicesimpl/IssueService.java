package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Issue;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.repository.IIssueRepository;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.IIssueService;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService implements IIssueService {
    @Autowired
    IIssueRepository issueRepository;

    @Autowired
    IStudentClassRepository studentClassRepository;

    @Override
    public List<Issue> getAllIssueByStudentId(Integer studentId) {
        List<Issue> listIssue = new ArrayList<>();
        List<StudentClass> studentClassList = studentClassRepository.findAllByStudentId(studentId);
        List<Project> projectList = studentClassList.stream().map(StudentClass::getProject).toList();
        projectList.forEach(project -> {
            listIssue.addAll(issueRepository.findAllByProjectId(project.getId()));
        });
        return listIssue;
    }

    public List<Issue> filterIssue(List<Issue> listIssue, Integer projectId, Integer milestoneId, Integer assigneeId, String search) {

        // filter by project id
        if (projectId != -1) {
            listIssue.removeIf(issue -> !issue.getProject().getId().equals(projectId));
        }

        // filter by milestone id
        if (milestoneId != -1) {
            listIssue.removeIf(issue -> !issue.getMilestone().getId().equals(milestoneId));
        }

        // filter by assignee id
        if (assigneeId != -1) {
            listIssue.removeIf(issue -> !issue.getAssignee().getId().equals(assigneeId));
        }

        // filter by search
        if (!search.equals("")) {
            listIssue.removeIf(issue -> !issue.getTitle().toLowerCase().contains(search.toLowerCase())
                    && !issue.getMilestone().getTitle().toLowerCase().contains(search.toLowerCase())
                    && !issue.getAssignee().getDisplayName().toLowerCase().contains(search.toLowerCase())
                    && !issue.getTitle().toLowerCase().contains(search.toLowerCase())
                    && !issue.getStatus().getSettingTitle().toLowerCase().contains(search.toLowerCase())
                    && !issue.getProcess().getSettingTitle().toLowerCase().contains(search.toLowerCase())
            );
        }

        return listIssue;
    }

}
