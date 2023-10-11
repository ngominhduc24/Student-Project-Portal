package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Setting;
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

    @Override
    public List<Class> findAllByClassManagerId(int classManagerId) {
        return classRepository.findAllByUserId(classManagerId);
    }

    @Override
    public List<Class> findClassForIssue(int teacherId){
        return  classRepository.findClassForIssue(teacherId);
    }

    @Override
    public Page<Class> findAllBySubjectManagerId(int subjecManagertId, String search, Integer pageNo, Integer pageSize,
                                                 String sortBy, Integer sortType, Integer subjectId, Integer semesterId,
                                                 Integer teacherId, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return classRepository.filterClassBySubjectManager(subjecManagertId, search, subjectId, semesterId, teacherId, status,
                PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public Page<Class> findAllByClassManagerId(int teacherId, String search, Integer pageNo, Integer pageSize, String sortBy, Integer sortType, Integer subjectId, Integer semesterId, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return classRepository.filterClassByClassManager(teacherId , search , subjectId, semesterId, status, PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public Class findById(int id) {
        return classRepository.findById(id).get();
    }

    @Override
    public Class saveClass(Class classA) {
        return classRepository.save(classA);
    }

    @Override
    public boolean checkExistedClassName(String className, Integer subjectId, Integer id) {
        Class classA = classRepository.findClassByClassNameAndSubjectId(className, subjectId);
        if(classA !=null)  {
            if(id==null) return true;
            if(classA.getId()!=id)  return true;
        }
        return false;
    }


}
