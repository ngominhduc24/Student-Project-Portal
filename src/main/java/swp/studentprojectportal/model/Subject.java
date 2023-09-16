package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "subject")
public class Subject {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "subject_name")
    private String subjectName;

    @Column(name = "subject_code")
    private String subjectCode;

    @Column(name = "status")
    private boolean status;

    @Column(name = "create_by")
    private Integer createBy;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_by")
    private Integer updateBy;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @OneToOne
    @JoinColumn(name = "subject_manager_id")
    private User user;
}
