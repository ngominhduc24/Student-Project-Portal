package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.IssueUpdate;
import swp.studentprojectportal.repository.IIssueUpdateRepository;
import swp.studentprojectportal.service.IIssueSettingService;
import swp.studentprojectportal.service.IIssueUpdateService;

import java.util.List;

@Service
public class IssueUpdateService implements IIssueUpdateService{
    @Autowired
    IIssueUpdateRepository issueUpdateRepository;
    @Override
    public List<IssueUpdate> findAllByIssue_Id(Integer issueId){
        return issueUpdateRepository.findAllByIssue_Id(issueId);
    }

    @Override public IssueUpdate findById(int id){
        return issueUpdateRepository.findById(id).get();
    }

    @Override public IssueUpdate findByIssueAndTitle(int issueId,String title){
        return issueUpdateRepository.findIssueUpdateByIssueAndTitle(issueId,title).orElse(null);
    }
    @Override
    public Page<IssueUpdate> filterWork(Integer issueId, String search, Integer pageNo, Integer pageSize,
                                                     String sortBy, Integer sortType){
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return issueUpdateRepository.filterWork(issueId,search, PageRequest.of(pageNo, pageSize, sort));

    }
    @Override
    public IssueUpdate saveIssueUpdate(IssueUpdate issueUpdate){
        return issueUpdateRepository.save(issueUpdate);
    }
}
