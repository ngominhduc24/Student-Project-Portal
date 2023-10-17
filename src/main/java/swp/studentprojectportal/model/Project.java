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
@Table(name = "project")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "title")
    private String title;

    @OneToOne
    @JoinColumn(name = "project_mentor_id")
    private User projectMentor = null;

    @OneToOne
    @JoinColumn(name = "team_leader_id")
    private User teamLeader = null;

    @Column
    private String description;

    @Column(name = "group_name")
    private String groupName;

    @ManyToOne
    @JoinColumn(name = "class_id")
    private Class aclass;

    @Column(name = "create_by")
    private Integer createBy = 0;

    @Column(name = "create_at")
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "update_by")
    private Integer updateBy = 0;

    @Column(name = "update_at")
    private Timestamp updateAt = Timestamp.valueOf(LocalDateTime.now());

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<StudentClass> members;
}
