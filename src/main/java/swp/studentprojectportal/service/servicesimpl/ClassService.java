package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IClassRepository;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.IClassService;
import swp.studentprojectportal.service.IStudentClassService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService implements IClassService {
    @Autowired
    IClassRepository classRepository;

    @Autowired
    IStudentClassRepository studentClassRepository;

    @Override
    public List<User> getAllStudent(int classId) {
        List<StudentClass> data = studentClassRepository.findAllByAclass_Id(classId);
        List<User> listStudent = new ArrayList<>();
        for (StudentClass studentClass : data) {
            listStudent.add(studentClass.getStudent());
        }
        return listStudent;
    }

    public Class getClass(int classId) {
        return classRepository.findClassById(classId);
    }
}
