package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.query.Param;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.ClassIssueSetting;

import java.util.List;

public interface IClassIssueSettingService {



    List<ClassIssueSetting> getALL(int teacherId) ;

    Page<ClassIssueSetting> findAllByClassManagerId(int teacherId, String search, Integer pageNo,
                                        Integer pageSize, String sortBy, Integer sortType, Integer classId,
                                        Integer status);
}
