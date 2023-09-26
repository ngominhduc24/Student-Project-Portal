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
@Table(name = "subject_setting")
public class SubjectSetting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_id")
    private Integer typeId;

    @Column(name = "setting_title")
    private String settingTitle;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "display_order")
    private Integer displayOrder;

    @Column(name = "create_by")
    private Integer createBy = 0;

    @Column(name = "create_at")
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "update_by")
    private Integer updateBy = 0;

    @Column(name = "update_at")
    private Timestamp updateAt = Timestamp.valueOf(LocalDateTime.now());

    @ManyToOne
    @JoinColumn(name = "subject_id")
    private Subject subject;

    public void setSubject(Subject subject) {
        this.subject = subject;
    }
}