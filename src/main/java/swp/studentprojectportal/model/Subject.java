package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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
    private String status;

    @Column(name = "description")
    private String description;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_at")
    private String updateAt;

    @OneToOne
    @JoinColumn(name = "subject_manager_id")
    private User user;
}
