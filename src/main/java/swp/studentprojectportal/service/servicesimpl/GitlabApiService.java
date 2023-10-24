package swp.studentprojectportal.service.servicesimpl;

import org.gitlab4j.api.GitLabApi;
import org.gitlab4j.api.GitLabApiException;
import org.gitlab4j.api.Pager;
import org.gitlab4j.api.models.Label;
import org.gitlab4j.api.models.Milestone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GitlabApiService {
    private GitLabApi gitLabApi;

    // -------------------------- CLASS --------------------------
    public  List<Milestone> getClassMilestoneGitlab(String projectIdOrPath, String personToken) throws GitLabApiException {
        // Get all milestones from Gitlab
        // Get all milestones from database
        // Compare and sync
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Pager<Milestone> milestones = gitLabApi.getMilestonesApi().getGroupMilestones(projectIdOrPath, 100);
        List<Milestone> milestoneList = milestones.all();
        return milestoneList;
    }

    // update class milestone
    public boolean updateClassMilestone(String projectIdOrPath, String personToken, Milestone milestone) throws GitLabApiException {
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Milestone newMilestone = gitLabApi.getMilestonesApi().updateGroupMilestone(projectIdOrPath, milestone.getId(), milestone.getTitle(), milestone.getDescription(), milestone.getDueDate(), milestone.getStartDate(), null);
        return newMilestone != null;
    }

    public List<Label> getClassLabelGitlab(String projectIdOrPath, String personToken) throws GitLabApiException {
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Pager<Label> labels = gitLabApi.getLabelsApi().getGroupLabels(projectIdOrPath, 100);
        List<Label> labelList = labels.all();
        return labelList;
    }


    public boolean createClassLabel(String projectIdOrPath, String personToken, Label label) throws GitLabApiException {
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Label newLabel = gitLabApi.getLabelsApi().createGroupLabel(projectIdOrPath, label);
        return newLabel != null;
    }

    // -------------------------- PROJECT --------------------------
    public boolean createGrouptMilestone(String groupIdOrPath, String personToken, Milestone milestone) throws GitLabApiException {
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Milestone newMilestone = gitLabApi.getMilestonesApi().createGroupMilestone(groupIdOrPath, milestone.getTitle(), milestone.getDescription(), milestone.getDueDate(), milestone.getStartDate());
        return newMilestone != null;
    }

    public List<Milestone> getProjectMilestoneGitlab(String projectIdOrPath, String personToken) throws GitLabApiException {
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Pager<Milestone> milestones = gitLabApi.getMilestonesApi().getMilestones(projectIdOrPath, 100);
        List<Milestone> milestoneList = milestones.all();
        return milestoneList;
    }


    public List<Label> getProjectLabelGitlab(String projectIdOrPath, String personToken) throws GitLabApiException {
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Pager<Label> labels = gitLabApi.getLabelsApi().getLabels(projectIdOrPath, 100);
        List<Label> labelList = labels.all();
        return labelList;
    }

    public boolean createProjectLabel(String projectIdOrPath, String personToken, Label label) throws GitLabApiException {
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Label newLabel = gitLabApi.getLabelsApi().createProjectLabel(projectIdOrPath, label);
        return newLabel != null;
    }
}
