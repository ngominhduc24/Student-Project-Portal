package swp.studentprojectportal.service.servicesimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import swp.studentprojectportal.model.Criteria;
import swp.studentprojectportal.repository.ICriteriaRepository;
import swp.studentprojectportal.service.ICriteriaService;
@Service
public class CriteriaService implements ICriteriaService {
    @Autowired
    ICriteriaRepository criteriaRepository;
    @Override
    public Page<Criteria> filter(Integer assignmentId, String search, Integer pageNo, Integer pageSize, String sortBy, Integer sortType, Integer status) {
        Sort sort;
        if(sortType==1)
            sort = Sort.by(sortBy).ascending();
        else
            sort = Sort.by(sortBy).descending();
        return criteriaRepository.filter(assignmentId, search, status, PageRequest.of(pageNo, pageSize, sort));
    }

    @Override
    public Criteria findById(int id) {
        return criteriaRepository.getById(id);
    }

    @Override
    public Criteria save(Criteria criteria) {
        return criteriaRepository.save(criteria);
    }
}
