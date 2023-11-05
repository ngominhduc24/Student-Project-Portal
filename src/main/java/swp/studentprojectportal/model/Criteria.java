package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "criteria")
public class Criteria {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String name;

    private Integer weight;

    private Boolean status;

    @ManyToOne
    @JoinColumn(name = "assignment_id")
    private Assignment assignment;
}
