package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "setting")
public class Setting {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type_id")
    private String subjectManagerId;

    @Column(name = "setting_title")
    private String subjectName;

    @Column(name = "status")
    private String subjectCode;

    @Column(name = "create_by")
    private String createBy;

    @Column(name = "create_at")
    private String createAt;

    @Column(name = "update_by")
    private String updateBy;

    @Column(name = "update_at")
    private String updateAt;
}
