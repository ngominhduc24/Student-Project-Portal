package swp.studentprojectportal.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.model.User;

import java.util.List;

@Repository
public interface IUserRepository extends JpaRepository<User, Integer> {
    User findUserByEmailAndPassword(String username,String password);
    User findUserByPhoneAndPassword(String username,String password);
    User findUserByToken(String token);
    User findUserByEmail(String email);
    User findUserByPhone(String phone);
    User findUserByEmailOrPhone(String email, String phone);
    List<User> findAllBySetting(Setting setting);
    Page<User> findUserByFullNameContainsIgnoreCaseOrEmailContainsIgnoreCaseOrPhoneContainsIgnoreCase(String fullName, String email, String phone, Pageable pageable);
    @Query(value = "SELECT * FROM user u " +
            "WHERE (LOWER(u.full_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND u.role_id = :roleId",
            nativeQuery = true)
    Page<User> searchUsersAndFilterByRole(@Param("searchTerm") String searchTerm, @Param("roleId") Integer roleId, Pageable pageable);

    @Query(value = "SELECT * FROM user u " +
            "WHERE (LOWER(u.full_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND u.status = :status",
            nativeQuery = true)
    Page<User> searchUsersAndFilterByStatus(@Param("searchTerm") String searchTerm, @Param("status") Integer status, Pageable pageable);

    @Query(value = "SELECT * FROM user u " +
            "WHERE (LOWER(u.full_name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.email) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
            "OR LOWER(u.phone) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
            "AND u.role_id = :roleId " +
            "AND u.status = :status",
            nativeQuery = true)
    Page<User> searchUsersAndFilterByRoleIdAndStatus(@Param("searchTerm") String searchTerm, @Param("roleId") Integer roleId,  @Param("status") Integer status, Pageable pageable);

    List<User> findAllBySettingIdOrSettingId(int roleId, int roleId2);
    int countAllBySettingId(int roleId);
    User findUserById(int id);

    @Query(value="SELECT distinct user.* FROM class c join subject s on c.subject_id = s.id join user on c.teacher_id = user.id\n" +
            "WHERE s.subject_manager_id = ?1", nativeQuery = true)
    List<User> findTeacherBySubjectManagerId(int subjectManagerId);
}


