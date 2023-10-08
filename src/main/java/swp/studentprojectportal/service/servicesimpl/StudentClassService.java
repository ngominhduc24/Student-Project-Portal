package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.repository.IClassRepository;
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

    @Override
    public List<StudentClass> findAllByProjectId(int projectId) {
        return studentClassRepository.findAllByProjectId(projectId);
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

    public boolean checkStudentInClass(int classId, int studentId) {
        StudentClass studentClass = studentClassRepository.findStudentClassByStudent_IdAndAclass_Id(studentId, classId);
        if(studentClass == null) return false;
        return true;
    }
}
