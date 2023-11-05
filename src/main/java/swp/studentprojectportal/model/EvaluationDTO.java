package swp.studentprojectportal.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class EvaluationDTO {
    private Integer submissionId;
    private Integer studentId;

    private String username;

    private String fullname;

    private Integer weight;

    private Integer bonusAndPenalty;

    private Float grade = 0.0f;

    private String commentGroup;

    private String groupname;

    private List<CriteriaDTO> criteriaGradeList;
}
