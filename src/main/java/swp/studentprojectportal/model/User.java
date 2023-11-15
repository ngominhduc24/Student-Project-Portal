package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.beans.factory.annotation.Autowired;
import swp.studentprojectportal.repository.ISettingRepository;
import swp.studentprojectportal.utils.Utility;

import java.security.NoSuchAlgorithmException;
import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "user")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "email")
    private String email = null;

    @Column(name = "phone")
    private String phone = null;

    @Column(name = "password")
    private String password = null;

    @Column(name = "status")
    private boolean status = true;

    @Column(name = "note")
    private String note;

    @Column(name = "full_name")
    private String fullName;

    @Column(name = "avatar_url")
    private String avatarUrl = "/images/user_icon.png";

    @Column(name = "token")
    private String token;

    @Column(name = "personal_token_gitlab")
    private String personalTokenGitlab;

    @Column(name = "active")
    private boolean active = false;

    @Column(name = "create_by")
    private Integer createBy = 0;

    @Column(name = "create_at")
    private Timestamp createAt = Timestamp.valueOf(LocalDateTime.now());

    @Column(name = "update_by")
    private Integer updateBy = 0;

    @Column(name = "update_at")
    private Timestamp updateAt = Timestamp.valueOf(LocalDateTime.now());

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Setting setting;

    public void setPhone(String phone) {
        this.phone = phone.replace("+84", "0").replace(" ", "");
    }

    public void setPassword(String password) throws NoSuchAlgorithmException {
        this.password = Utility.hash(password);
    }

    public String getDisplayName() {
        if(email == null) {
            return fullName;
        } else {
            return email.split("@")[0];
        }
    }

}
