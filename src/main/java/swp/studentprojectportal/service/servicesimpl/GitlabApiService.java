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

    public  List<Milestone> getClassMilestoneGitlab(String projectIdOrPath, String personToken) throws GitLabApiException {
        // Get all milestones from Gitlab
        // Get all milestones from database
        // Compare and sync
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Pager<Milestone> milestones = gitLabApi.getMilestonesApi().getGroupMilestones(projectIdOrPath, 100);
        List<Milestone> milestoneList = milestones.all();
        return milestoneList;
    }

    public List<Label> getClassLabelGitlab(String projectIdOrPath, String personToken) throws GitLabApiException {
        gitLabApi = new GitLabApi("https://gitlab.com", personToken);
        Pager<Label> labels = gitLabApi.getLabelsApi().getGroupLabels(projectIdOrPath, 100);
        List<Label> labelList = labels.all();
        return labelList;
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
}
