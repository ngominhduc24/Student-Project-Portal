package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "submission")
public class Submission {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "milestone_id")
    private Milestone milestone;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "submit_date")
    private Timestamp submitDate = Timestamp.valueOf(LocalDateTime.now());

    private String note;

    @Column(name = "file_location")
    private String fileLocation = "1"; // Default value "1"

    private Float evaluation;

    private String comment;

    @Column(name = "status")
    private Integer status = 1; // Default value 1

    @Column(name = "create_by")
    private Integer createBy = 0;

    @Column(name = "create_at")
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "update_by")
    private Integer updateBy = 0;

    @Column(name = "update_at")
    private Timestamp updateAt = Timestamp.valueOf(LocalDateTime.now());
}
