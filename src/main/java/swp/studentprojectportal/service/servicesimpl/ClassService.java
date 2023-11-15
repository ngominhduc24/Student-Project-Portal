package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.repository.IClassRepository;
import swp.studentprojectportal.repository.IStudentClassRepository;
import swp.studentprojectportal.service.IClassService;

import java.util.ArrayList;
import java.util.List;

@Service
public class ClassService implements IClassService {
    @Autowired
    IClassRepository classRepository;

    @Autowired
    IStudentClassRepository studentClassRepository;

    @Override
    public List<StudentClass> getAllStudent(int classId) {
        List<StudentClass> data = studentClassRepository.findAllByAclass_Id(classId);
        return data;
    }
    @Override
    public int classCount() {
        return classRepository.findAll().size();
    }

    @Override
    public int classCountBySemFall23() {return classRepository.findClassBySemesterFall23().size();}

    @Override
    public int classCountBySemSummer23() {return classRepository.findClassBySemesterSummer23().size();}

    @Override
    public Class getClass(int classId) {
        return classRepository.findClassById(classId);
    }

    @Override
    public List<Class> findAllByClassManagerId(int classManagerId) {
        List<Class> data = new ArrayList<>();

        data.addAll(classRepository.findAllByUserIdAndStatus(classManagerId, 2)); //started
        data.addAll(classRepository.findAllByUserIdAndStatus(classManagerId, 0)); //pending
        data.addAll(classRepository.findAllByUserIdAndStatus(classManagerId, 3)); //closed
        return data;
    }

    @Override
    public List<Class> findClassForIssue(int teacherId){
        return  classRepository.findClassForIssue(teacherId);
    }

    @Override
    public Page<Class> findAllBySemester(Integer semesterId, Integer teacherId, Integer pageNo, Integer pageSize){
        return classRepository.filterClassBySemester(semesterId, teacherId, PageRequest.of(pageNo, pageSize));
    }

    @Override
    public Page<Class> findAllBySubjectManagerId(int subjectManagerId, String search, Integer pageNo, Integer pageSize,
                                                 String sortBy, Integer sortType, Integer subjectId, Integer semesterId,
                                                 Integer teacherId, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return classRepository.filterClassBySubjectManager(subjectManagerId, search, subjectId, semesterId, teacherId, status,
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

    @Override public Class findByClassName(String className){
        return  classRepository.findClassByClassName(className);
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

    @Override
    public void delete(Class classA) {
        classRepository.delete(classA);
    }

    @Override
    public List<Class> findAllByStudentUserId(Integer userId) {
        List<StudentClass> studentClassList = studentClassRepository.findAllByStudentId(userId);
        List<Class> classList = new ArrayList<>();

        for (StudentClass studentClass : studentClassList)
            classList.add(studentClass.getAclass());

        return classList;
    }

    @Override
    public List<Class> findClassForProject(Integer projectMentorId) {
        return classRepository.findClassByProjectsProjectMentorId(projectMentorId);
    }


}
