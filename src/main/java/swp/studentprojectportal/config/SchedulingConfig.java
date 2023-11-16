//package swp.studentprojectportal.config;
//
//import jakarta.servlet.http.HttpSession;
//import org.gitlab4j.api.GitLabApiException;
//import org.gitlab4j.api.models.Milestone;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.scheduling.annotation.EnableScheduling;
//import org.springframework.scheduling.annotation.Scheduled;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//import swp.studentprojectportal.model.Assignment;
//import swp.studentprojectportal.model.Class;
//import swp.studentprojectportal.model.User;
//import swp.studentprojectportal.service.servicesimpl.*;
//import swp.studentprojectportal.utils.dto.Mapper;
//
//import java.util.List;
//
//@Configuration
//@EnableScheduling
//public class SchedulingConfig {
//    @Autowired
//    GitlabApiService gitlabApiService;
//
//    @Autowired
//    ClassService classService;
//
//    @Autowired
//    MilestoneService milestoneService;
//
//    @Autowired
//    AssignmentService assignmentService;
//
//    @Autowired
//    UserService userService;
//
//
//
//    @Scheduled(fixedRate = 1000)
//    public void syncGitlabConfig() {
//        System.out.println(
//                "Fixed rate task - " + System.currentTimeMillis() / 1000);
//    }
//
//    public void synchronizeGitlabMilestoneClass(
//            Integer classId,
//            String groupIdOrPath,
//            String personalToken
//    ) throws GitLabApiException {
//        List<Milestone> milestoneListGitlab = null;
//        List<swp.studentprojectportal.model.Milestone> milestoneListDB = milestoneService.findMilestoneByClassId(classId);
//        List<Assignment> milestoneSubjectListDB = assignmentService.getAssignmentBySubjectId(classService.findById(classId).getSubject().getId());
//
//        try {
//            milestoneListGitlab = gitlabApiService.getClassMilestoneGitlab(groupIdOrPath, personalToken);
//        } catch (GitLabApiException e) {
//            System.out.printf(e.getMessage());
//        }
//
//        // sync to db
//        for (org.gitlab4j.api.models.Milestone milestone : milestoneListGitlab) {
//            boolean isExist = false;
//            for (swp.studentprojectportal.model.Milestone milestoneDB : milestoneListDB) {
//                if (Mapper.milestoneEquals(milestoneDB, milestone)) {
//                    isExist = true;
//                    if(milestoneDB.getGitlabMilestoneId() != null) {
//                        milestoneDB.setGitlabMilestoneId(milestone.getId());
//                        milestoneService.save(milestoneDB);
//                    }
//                }
//
//                if(milestoneDB.getGitlabMilestoneId() == null) {
//                    continue;
//                }
//
//                // if milestone in db is updated then update
//
//
//                if (milestone.getId().longValue() == milestoneDB.getGitlabMilestoneId().longValue()) {
//                    isExist = true;
//                    if (milestoneDB.getUpdateAt().before(milestone.getUpdatedAt())) {
//                        swp.studentprojectportal.model.Milestone milestoneUpdated = Mapper.milestoneConvert(milestone);
//                        milestoneUpdated.setId(milestoneDB.getId());
//                        milestoneUpdated.setAclass(classService.findById(classId));
//                        milestoneService.save(milestoneUpdated);
//                    }
//                    break;
//                }
//
//            }
//
//            try {
//                if (!isExist) {
//                    swp.studentprojectportal.model.Milestone milestoneDB = Mapper.milestoneConvert(milestone);
//                    milestoneDB.setGitlabMilestoneId(milestone.getId());
//                    milestoneDB.setAclass(classService.findById(classId));
//                    milestoneService.save(milestoneDB);
//                }
//            } catch (Exception e) {
//                System.out.println(e.getMessage());
//            }
//        }
//
//        // sync to gitlab
//        for (swp.studentprojectportal.model.Milestone milestoneDB : milestoneListDB) {
//            boolean isExist = false;
//            for (org.gitlab4j.api.models.Milestone milestone : milestoneListGitlab) {
//                // Check exist title to create new milestone
//                if (Mapper.milestoneEquals(milestoneDB, milestone)) {
//                    isExist = true;
//                }
//
//                if(milestoneDB.getGitlabMilestoneId() == null) {
//                    continue;
//                }
//
//                // if exist milestone are sync then update
//                if (milestone.getId().longValue() == milestoneDB.getGitlabMilestoneId().longValue()) {
//                    isExist = true;
//                    if (milestoneDB.getUpdateAt().after(milestone.getUpdatedAt())) {
//                        org.gitlab4j.api.models.Milestone milestoneGitlab = Mapper.milestoneConvert(milestoneDB);
//                        milestoneGitlab.setId(milestoneDB.getGitlabMilestoneId());
//                        System.out.println("title: " + milestoneGitlab.getTitle() + "status " + milestoneDB.isStatus());
//                        gitlabApiService.updateClassMilestone(groupIdOrPath, personalToken, milestoneGitlab, milestoneDB.isStatus());
//                    }
//                    break;
//                }
//            }
//
//            if (!isExist) {
//                // create milestone in gitlab
//                org.gitlab4j.api.models.Milestone milestoneGitlab = gitlabApiService.createGrouptMilestone(groupIdOrPath, personalToken, Mapper.milestoneConvert(milestoneDB));
//                // update milestone id in db
//                milestoneDB.setGitlabMilestoneId(milestoneGitlab.getId());
//                milestoneService.save(milestoneDB);
//            }
//        }
//
//        // save gitlab group id
//        Class classA = classService.findById(classId);
//        classA.setGitlabGroupId(groupIdOrPath);
//        classService.saveClass(classA);
//    }
//}
