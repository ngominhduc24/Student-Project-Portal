package swp.studentprojectportal.service;

import org.springframework.data.domain.Page;
import swp.studentprojectportal.model.ClassIssueSetting;

import java.util.List;

public interface IClassIssueSettingService {

//    Page<ClassIssueSetting> filter(int teacherId,String search, Integer pageNo, Integer pageSize,
//                                   String sortBy, Integer sortType, Integer status) ;

    List<ClassIssueSetting> getALL(int teacherId) ;
}
