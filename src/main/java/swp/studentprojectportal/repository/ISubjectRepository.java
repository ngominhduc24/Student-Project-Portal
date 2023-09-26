package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Subject;
import swp.studentprojectportal.model.User;

import java.util.List;
import java.util.Optional;

@Repository
public interface ISubjectRepository extends JpaRepository<Subject, Integer> {


    Subject findSubjectBySubjectName(String subjectName);

    Subject findSubjectBySubjectCode(String subjectCode);
    List<Subject> findAllSubjectByUser(User user);

    //List<Subject> findSubjectPaging(Pageable pageable);
}
