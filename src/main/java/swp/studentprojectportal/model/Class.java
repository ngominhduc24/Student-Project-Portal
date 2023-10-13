package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Class")
public class Class {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "class_name")
    private String className;

    @Column(name = "description")
    private String description;

    @Column(name = "status")
    private Integer status = 0;

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    @ManyToOne
    @JoinColumn(name = "semester_id")
    private Setting semester;

    @ManyToOne
    @JoinColumn(name = "teacher_id")
    private User user;

    @Column(name = "create_by")
    private Integer createBy = 0;

    @Column(name = "create_at")
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "update_by")
    private Integer updateBy = 0;

    @Column(name = "update_at")
    private Timestamp updateAt = Timestamp.valueOf(LocalDateTime.now());

    @OneToMany(mappedBy = "aclass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentClass> students;

    @OneToMany(mappedBy = "aclass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Milestone> milestones;

    @OneToMany(mappedBy = "aClass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ClassIssueSetting> classIssueSettings;

    @OneToMany(mappedBy = "aclass", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Project> projects;
}
