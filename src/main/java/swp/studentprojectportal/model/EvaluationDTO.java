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

    private float bonusAndPenalty;

    private String commentGroup;

    private String groupname;

    private Integer workPoint = 0;

    private Float workGrade = 0f;

    private String CommentPersonal;

    private List<CriteriaDTO> criteriaGradeList;

    public Float getGradeSubmission() {
        Float grade = 0f;
        for (CriteriaDTO criteriaGrade : criteriaGradeList) {
            grade += criteriaGrade.getGrade()*criteriaGrade.getWeight()/100;
        }
        return grade;
    }
}
