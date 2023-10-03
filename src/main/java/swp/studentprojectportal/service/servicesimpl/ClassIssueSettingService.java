package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.repository.IClassIssueSettingRepository;
import swp.studentprojectportal.service.IClassIssueSettingService;
@Service
public class ClassIssueSettingService implements IClassIssueSettingService {
    @Autowired
    IClassIssueSettingRepository classIssueSettingRepository;
}
