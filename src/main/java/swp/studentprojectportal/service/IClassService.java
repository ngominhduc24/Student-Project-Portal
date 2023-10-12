package swp.studentprojectportal.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.SubjectSetting;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.repository.IClassRepository;

import java.util.List;

public interface IClassService {
    List<User> getAllStudent(int classId);
    Class getClass(int classId);

    List<Class> findAllByClassManagerId(int classManagerId);

    Page<Class> findAllBySemester(Integer pageNo, Integer teacherId, Integer pageSize, Integer semesterId);


    List<Class> findClassForIssue(int teacherId);
    Page<Class> findAllBySubjectManagerId(int subjectManagerId, String search, Integer pageNo,
                                          Integer pageSize, String sortBy, Integer sortType, Integer subjectId, Integer semesterId,
                                          Integer teacherId, Integer status);
    Page<Class> findAllByClassManagerId(int teacherId, String search, Integer pageNo,
                                          Integer pageSize, String sortBy, Integer sortType, Integer subjectId, Integer semesterId,
                                          Integer status);
    Class findById(int id);

    Class findByClassName(String className);
    Class saveClass(Class classA);
    public boolean checkExistedClassName(String className, Integer subjectId, Integer id);
    void delete(Class classA);
}
