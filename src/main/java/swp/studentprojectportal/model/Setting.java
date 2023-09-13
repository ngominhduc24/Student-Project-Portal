package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Date;
import java.sql.Timestamp;

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
    private int typeId;

    @Column(name = "setting_title")
    private String settingTitle;

    @Column(name = "status")
    private boolean status;

    @Column(name = "create_by")
    private int createBy;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_by")
    private int updateBy;

    @Column(name = "update_at")
    private Timestamp updateAt;
}
