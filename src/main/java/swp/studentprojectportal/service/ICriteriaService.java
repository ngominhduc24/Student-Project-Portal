package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.Criteria;
import swp.studentprojectportal.model.Setting;

import java.util.List;

public interface ICriteriaService {
    Page<Criteria> filter(Integer assignmentId, String search, Integer pageNo, Integer pageSize,
                          String sortBy, Integer sortType, Integer status);
    Criteria findById(int id);
    Criteria save(Criteria criteria);
    Boolean checkExistedName(String name, Integer assignmentId, Integer id);

    List<Criteria> getAllCriteriaByAssignmentId(int assignmentId);
}
