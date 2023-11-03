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

            //check status current project
            Project oldProject = studentClass.getProject();
            if(oldProject!=null && oldProject.isStatus()) return false;

            //check team leader old project
            if (oldProject != null && oldProject.getTeamLeader()!=null &&
                    oldProject.getTeamLeader().getId() == studentClass.getStudent().getId()) {

                oldProject.setTeamLeader(null);
                projectRepository.save(oldProject);
            }

            //update new project
            if (projectId > 0) {
                Project project = projectRepository.findById(projectId).get();

                //check status
                if (project.isStatus()) return false;

                studentClass.setProject(project);
            }
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
        if (student == null) {
            return false;
        }
        // get class
        Class c = classRepository.findClassById(classId);
        if (c == null) {
            return false;
        }

        studentClass.setStudent(student);
        studentClass.setAclass(c);

        if (checkStudentInClass(classId, studentId)) return false;
        if (studentClassRepository.save(studentClass) == null) return false;

        return true;
    }

    // This function is accept add student to class by studentId (String) ex: HE191919
    public boolean addNewStudentToClass(int classId, String studentId) {
        StudentClass studentClass = new StudentClass();
        // get student
        User student = userRepository.findUserByEmailContainsIgnoreCase(studentId);
        if (student == null) {
            return false;
        }
        // get class
        Class c = classRepository.findClassById(classId);
        if (c == null) {
            return false;
        }

        studentClass.setStudent(student);
        studentClass.setAclass(c);

        if (checkStudentInClass(classId, student.getId())) return false;
        if (studentClassRepository.save(studentClass) == null) return false;

        return true;
    }


    public boolean checkStudentInClass(int classId, int studentId) {
        StudentClass studentClass = studentClassRepository.findStudentClassByStudent_IdAndAclass_Id(studentId, classId);
        if (studentClass == null) return false;
        return true;
    }

    @Override
    public List<StudentClass> findAllByClassManager(int classManagerId) {
        return studentClassRepository.findAllByAclassUserId(classManagerId);
    }

    @Override
    public boolean removeAllStudentFromClass(int classId) {
        try{
            List<StudentClass> studentClassList = studentClassRepository.findAllByAclass_Id(classId);

            studentClassRepository.deleteAll(studentClassList);

            return true;
        } catch (Exception e) {
            System.out.println(e);
            return false;
        }
    }

    @Override
    public StudentClass findByStudentIdAndAclassId(Integer userId, Integer classId) {
        return studentClassRepository.findByStudentIdAndAclassId(userId, classId);
    }

    @Override
    public List<StudentClass> findAllByStudentId(Integer studentId) {
        return studentClassRepository.findAllByStudentId(studentId);
    }

    public boolean removeStudentFromClass(int classId, int studentId) {
        StudentClass studentClass = studentClassRepository.findStudentClassByStudent_IdAndAclass_Id(studentId, classId);
        if (studentClass == null) return false;
        studentClassRepository.delete(studentClass);
        return true;
    }
}
