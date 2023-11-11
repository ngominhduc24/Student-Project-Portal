package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.*;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "milestone")
public class Milestone {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "title")
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "start_date")
    private Date startDate;

    @Column(name = "end_date")
    private Date endDate;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "gitlab_milestone_id")
    private Integer gitlabMilestoneId;

    @ManyToOne
    @JoinColumn(name = "subject_assignment_id")
    private Assignment subjectAssignment;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aclass;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    @Column(name = "create_by")
    private Integer createBy = 0;

    @Column(name = "create_at")
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "update_by")
    private Integer updateBy = 0;

    @Column(name = "update_at")
    private Timestamp updateAt = Timestamp.valueOf(LocalDateTime.now());

    @OneToMany(mappedBy = "milestone", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("createAt DESC")
    private List<Submission> submissionList = new ArrayList<>();
}
