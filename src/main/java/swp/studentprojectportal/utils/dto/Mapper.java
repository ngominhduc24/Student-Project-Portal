package swp.studentprojectportal.utils.dto;

import org.gitlab4j.api.models.Label;
import swp.studentprojectportal.model.CriteriaDTO;
import swp.studentprojectportal.model.Evaluation;
import swp.studentprojectportal.model.EvaluationDTO;
import swp.studentprojectportal.model.IssueSetting;

import java.sql.Date;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Mapper {
    // Convert from swp.studentprojectportal.model.IssueSetting to org.gitlab4j.api.models.Label
    public static Label labelConvert(IssueSetting issueSetting) {
        Label label = new Label();
        if(issueSetting.getSettingGroup() != null)
            label.setName(issueSetting.getSettingGroup() + "::" + issueSetting.getSettingTitle());
        else
            label.setName(issueSetting.getSettingTitle());
        label.setDescription(issueSetting.getDescription());
        label.setColor("#9e9e9e");
        return label;
    }

    // Convert from org.gitlab4j.api.models.Label to swp.studentprojectportal.model.IssueSetting
    public static IssueSetting labelConvert(Label label) {
        IssueSetting issueSetting = new IssueSetting();
        String[] settingGroupAndTitle = label.getName().split("::");
        if(settingGroupAndTitle.length == 2) {
            issueSetting.setSettingGroup(settingGroupAndTitle[0]);
            issueSetting.setSettingTitle(settingGroupAndTitle[1]);
        }
        else
            issueSetting.setSettingTitle(settingGroupAndTitle[0]);
        issueSetting.setDescription(label.getDescription());
        issueSetting.setUpdateAt(new Timestamp(System.currentTimeMillis()));
        return issueSetting;
    }

    // Compare two swp.studentprojectportal.model.IssueSetting objects
    public static boolean labelEquals(IssueSetting issueSetting, Label label) {
        if(issueSetting.getSettingGroup() == null)
            return issueSetting.getSettingTitle().equals(label.getName());
        String[] settingGroupAndTitle = label.getName().split("::");
        if(settingGroupAndTitle.length == 2) {
            return issueSetting.getSettingGroup().equals(settingGroupAndTitle[0]) &&
                    issueSetting.getSettingTitle().equals(settingGroupAndTitle[1]);
        }
        else
            return issueSetting.getSettingTitle().equals(settingGroupAndTitle[0]);
    }

    // Convert from swp.studentprojectportal.model.Milestone to org.gitlab4j.api.models.Milestone
    public static org.gitlab4j.api.models.Milestone milestoneConvert(swp.studentprojectportal.model.Milestone milestone) {
        org.gitlab4j.api.models.Milestone milestoneGitlab = new org.gitlab4j.api.models.Milestone();
        milestoneGitlab.setTitle(milestone.getTitle());
        milestoneGitlab.setDescription(milestone.getDescription());
        milestoneGitlab.setStartDate(milestone.getStartDate());
        milestoneGitlab.setDueDate(milestone.getEndDate());
        return milestoneGitlab;
    }

    // Convert from org.gitlab4j.api.models.Milestone to swp.studentprojectportal.model.Milestone
    public static swp.studentprojectportal.model.Milestone milestoneConvert(org.gitlab4j.api.models.Milestone milestoneGitlab) {
        swp.studentprojectportal.model.Milestone milestone = new swp.studentprojectportal.model.Milestone();
        milestone.setTitle(milestoneGitlab.getTitle());
        milestone.setDescription(milestoneGitlab.getDescription());
        milestone.setCreateAt(new Timestamp(System.currentTimeMillis()));
        milestone.setUpdateAt(new Timestamp(System.currentTimeMillis()));

        // Convert start and due dates to java.sql.Date
        if (milestoneGitlab.getStartDate() != null) {
            java.util.Date utilStartDate = milestoneGitlab.getStartDate();
            java.sql.Date sqlStartDate = new java.sql.Date(utilStartDate.getTime());
            milestone.setStartDate(sqlStartDate);
        }

        if (milestoneGitlab.getDueDate() != null) {
            java.util.Date utilDueDate = milestoneGitlab.getDueDate();
            java.sql.Date sqlDueDate = new java.sql.Date(utilDueDate.getTime());
            milestone.setEndDate(sqlDueDate);
        }
        return milestone;
    }

    // Compare two swp.studentprojectportal.model.Milestone objects
    public static boolean milestoneEquals(swp.studentprojectportal.model.Milestone milestone, org.gitlab4j.api.models.Milestone milestoneGitlab) {
        return milestone.getTitle().equals(milestoneGitlab.getTitle());
    }

    // Convert Evaluation to EvaluationDTO
    public static List<EvaluationDTO> evaluationMapper(List<Evaluation> evaluation) {
        if(evaluation == null || evaluation.size() == 0)
            return new ArrayList<>();

        List<EvaluationDTO> evaluationDTOList= new ArrayList<>();
        evaluation.forEach(item -> {
            if(evaluationDTOList.stream().noneMatch(evaluationDTO -> evaluationDTO.getStudentId().equals(item.getStudent().getId()))) {
                EvaluationDTO evaluationDTO = new EvaluationDTO();
                evaluationDTO.setStudentId(item.getStudent().getId());
                evaluationDTO.setSubmissionId(item.getSubmission().getId());
                evaluationDTO.setUsername(item.getStudent().getDisplayName());
                evaluationDTO.setFullname(item.getStudent().getFullName());
                evaluationDTO.setGroupname(item.getSubmission().getProject().getGroupName());
                evaluationDTO.setWeight(item.getWeight());
                evaluationDTO.setGrade(item.getGrade());
                evaluationDTO.setCommentGroup(item.getSubmission().getComment());

                // add criteria grade list
                List<CriteriaDTO> criteriaDTOList = new ArrayList<>();
                criteriaDTOList.add(new CriteriaDTO(item.getId(), item.getCriteria(), item.getGrade(), item.getComment()));
                evaluationDTO.setCriteriaGradeList(criteriaDTOList);
                evaluationDTOList.add(evaluationDTO);
            } else {
                evaluationDTOList.stream().filter(evaluationDTO -> evaluationDTO.getStudentId().equals(item.getStudent().getId())).forEach(evaluationDTO -> {
                     evaluationDTO.getCriteriaGradeList().add(new CriteriaDTO(item.getId(), item.getCriteria(), item.getGrade(), item.getComment()));
                });
            }
        });
        return evaluationDTOList;
    }
}
