package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.repository.IIssueRepository;
import swp.studentprojectportal.repository.IProjectRepository;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.IIssueService;

import java.util.ArrayList;
import java.util.List;

@Service
public class IssueService implements IIssueService {
    @Autowired
    IIssueRepository issueRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    ProjectService projectService;

    @Autowired
    IStudentClassRepository studentClassRepository;
    @Override
    public Issue getIssueById(Integer issueId) {
        Issue issue = new Issue();
        if(issueId == -1) {
            Milestone milestone = new Milestone();
            milestone.setId(-1);

            Project project = new Project();
            project.setId(-1);

            User user = new User();
            user.setId(-1);

            IssueSetting setting = new IssueSetting();
            setting.setId(-1);
            setting.setSettingTitle("");

            issue.setTitle(null);
            issue.setProject(project);
            issue.setMilestone(milestone);
            issue.setType(setting);
            issue.setStatus(setting);
            issue.setProcess(setting);
            issue.setAssignee(user);
        } else {
            issue =  issueRepository.findIssueById(issueId);
        }
        return issue;
    }

    @Override
    public Issue saveIssue(Issue issue){return issueRepository.save(issue);}
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
    @Override
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


    @Override
    public List<Issue> findAllByMilestoneId(Integer milestoneId) {
        return issueRepository.findAllByMilestoneId(milestoneId);
    }

    @Override
    public boolean updateIssueAssignee(Integer issueId, Integer assigneeId) {
        try {
            Issue issue = issueRepository.findById(issueId).get();

            issue.setAssignee(userRepository.findUserById(assigneeId));

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public Issue findById(int id){
        return issueRepository.findById(id).get();
    }

    @Override
    public List<Issue> findAllByStudentId(Integer studentId) {
        return issueRepository.findAllByProjectIn(projectService.findAllByStudentUserId(studentId));
    }


}
