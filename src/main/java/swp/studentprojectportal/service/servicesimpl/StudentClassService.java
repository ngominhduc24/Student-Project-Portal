package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.repository.IClassRepository;
import swp.studentprojectportal.repository.IProjectRepository;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.repository.IUserRepository;
import swp.studentprojectportal.service.IStudentClassService;

import java.util.List;
import java.util.Optional;

@Service
public class StudentClassService implements IStudentClassService {
    @Autowired
    IStudentClassRepository studentClassRepository;
    @Autowired
    IUserRepository userRepository;
    @Autowired
    IClassRepository classRepository;
    @Autowired
    IProjectRepository projectRepository;

    @Override
    public List<StudentClass> findAllByProjectId(int projectId) {
        return studentClassRepository.findAllByProjectId(projectId);
    }

    public List<StudentClass> findAllByClassId(int classId) {
        return studentClassRepository.findAllByAclass_Id(classId);
    }

    @Override
    public List<StudentClass> findAllNoGroupInClass(int classId) {
        return studentClassRepository.findAllByAclass_IdAndProjectId(classId, null);
    }

    @Override
    public boolean updateProjectId(Integer studentId, Integer projectId) {

        try {
            StudentClass studentClass = studentClassRepository.findById(studentId).get();

            //check team leader old project
            Project oldProject = studentClass.getProject();
            if(oldProject!=null && oldProject.getTeamLeader().getId() == studentId) {
                oldProject.setTeamLeader(null);
                projectRepository.save(oldProject);
            }

            //update new project
            if (projectId>0) studentClass.setProject(projectRepository.findById(projectId).get());
            else studentClass.setProject(null);

            studentClassRepository.save(studentClass);
            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }

    }

    public boolean addNewStudentToClass(int classId, int studentId) {
        StudentClass studentClass = new StudentClass();

        // get student
        User student = userRepository.findUserById(studentId);
        if(student == null) {
            return false;
        }

        // get class
        Class c = classRepository.findClassById(classId);
        if(c == null) {
            return false;
        }

        studentClass.setStudent(student);
        studentClass.setAclass(c);
        if(studentClassRepository.save(studentClass) == null) return false;
        
        return true;
    }
}
