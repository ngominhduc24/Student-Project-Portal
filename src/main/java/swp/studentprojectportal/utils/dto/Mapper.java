package swp.studentprojectportal.utils.dto;

import org.gitlab4j.api.models.Label;
import swp.studentprojectportal.model.IssueSetting;

public class Mapper {
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
        return issueSetting;
    }

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
}
