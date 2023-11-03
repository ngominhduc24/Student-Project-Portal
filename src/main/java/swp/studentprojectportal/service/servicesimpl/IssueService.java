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
    public List<Issue> getAllIssueByStudentId(Integer studentId){
        List<Issue> listIssue = new ArrayList<>();
        List<StudentClass> studentClassList = studentClassRepository.findAllByStudentId(studentId);
        List<Project> projectList = studentClassList.stream().map(StudentClass::getProject).toList();
        projectList.forEach(project -> {
            System.out.println(project.getId());
            listIssue.addAll(issueRepository.findAllByProjectId(project.getId()));
        });
        return listIssue;
    }
}
