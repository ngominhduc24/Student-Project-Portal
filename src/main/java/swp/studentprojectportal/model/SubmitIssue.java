package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "submit_issue")
public class SubmitIssue {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "issue_id")
    private Issue issue;

    @ManyToOne
    @JoinColumn(name = "submit_id")
    private Submission submit;

    @Column(name = "is_final")
    private Boolean isFinal = true;

    @ManyToOne
    @JoinColumn(name = "quality_id")
    private SubjectSetting quality;

    @ManyToOne
    @JoinColumn(name = "complexity_id")
    private SubjectSetting complexity;

    @Column(name = "function_loc")
    private Integer functionLoc;

    @Column(name = "is_rejected")
    private Boolean isRejected;

    @Column(name = "create_by")
    private Integer createBy = 0;

    @Column(name = "create_at")
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "update_by")
    private Integer updateBy = 0;

    @Column(name = "update_at")
    private Timestamp updateAt = Timestamp.valueOf(LocalDateTime.now());
}
