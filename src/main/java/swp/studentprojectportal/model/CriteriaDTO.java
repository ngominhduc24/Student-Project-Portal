package swp.studentprojectportal.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CriteriaDTO {
    private Integer criteriaGradeId;

    private String criteriaName;

    private Float grade;

    private String comment;
}
