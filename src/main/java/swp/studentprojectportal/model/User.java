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
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email;

    @Column(name = "phone")
    private String phone;

    @Column(name = "password")
    private String password;

    @Column(name = "status")
    private boolean status;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "gender")
    private boolean gender;

    @Column(name = "date_of_birth")
    private Date dateOfBirth;

    @Column(name = "avatar_url")
    private String avatarUrl;

    @Column(name = "create_by")
    private int createBy;

    @Column(name = "create_at")
    private Timestamp createAt;

    @Column(name = "update_by")
    private int updateBy;

    @Column(name = "update_at")
    private Timestamp updateAt;

    @OneToOne
    @JoinColumn(name = "role_id")
    private Setting setting;
}
