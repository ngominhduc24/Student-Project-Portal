package swp.studentprojectportal.controller.admin;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp.studentprojectportal.model.Criteria;
import swp.studentprojectportal.model.Setting;
import swp.studentprojectportal.service.servicesimpl.SettingService;

import java.util.List;

@Controller
public class SettingController {
    @Autowired
    SettingService settingService;
    @GetMapping("/admin/setting")
    public String settingPage(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer typeId,
                @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(value = "settingId", defaultValue = "-1") Integer settingId,Model model) {
        if (settingId != -1) {
            model.addAttribute("setting", settingService.findById(settingId));
        } else {
            Setting setting = new Setting();
            setting.setId(-1);
            model.addAttribute("setting", setting);
        }
        Page<Setting> settingList = settingService.filter(search, pageNo, pageSize, sortBy, sortType, typeId, status);
        model.addAttribute("settingList", settingList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("typeId", typeId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", settingList.getTotalPages());
        return "admin/setting/settingList";
    }

    @PostMapping("/admin/setting")
    public String updateSetting(@RequestParam(defaultValue = "0") Integer pageNo, @RequestParam(defaultValue = "10") Integer pageSize,
                                @RequestParam(defaultValue = "") String search, @RequestParam(defaultValue = "-1") Integer typeId,
                                @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                @RequestParam(defaultValue = "1") Integer sortType, @RequestParam String newSettingTitle,
                                @RequestParam Integer newTypeId, @RequestParam Integer newDisplayOrder,
                                @RequestParam(defaultValue = "0") Boolean newStatus, @RequestParam String description,
                                @RequestParam(defaultValue = "-1") Integer updateId,Model model) {

        Setting setting = Setting.builder()
                .settingTitle(newSettingTitle)
                .typeId(newTypeId)
                .displayOrder(newDisplayOrder)
                .status(newStatus)
                .description(description)
                .build();

        if(updateId!=-1){
            setting.setId(updateId);
        }

        if (settingService.checkExistedSettingTitle(newSettingTitle, updateId)) {
            model.addAttribute("toastMessageRed", "Duplicate name");
        } else {
            if(updateId==-1) {
                model.addAttribute("toastMessage", "Add new setting successfully");
            } else {
                model.addAttribute("toastMessage", "Update setting details successfully");
            }
            settingService.saveSetting(setting);
        }

        setting = new Setting();
        setting.setId(-1);
        model.addAttribute("setting", setting);

        Page<Setting> settingList = settingService.filter(search, pageNo, pageSize, sortBy, sortType, typeId, status);
        model.addAttribute("settingList", settingList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("typeId", typeId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", settingList.getTotalPages());
        return "admin/setting/settingList";
    }

    @GetMapping("/admin/setting/checkExisted")
    public ResponseEntity<Boolean> checkExisted(
            @RequestParam Integer updateId, @RequestParam String newSettingTitle) {
        return ResponseEntity.ok(settingService.checkExistedSettingTitle(newSettingTitle, updateId));
    }

    @GetMapping("/admin/setting/updateStatus")
    public String updateSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        Setting setting = settingService.findById(id);
        setting.setStatus(status);
        settingService.saveSetting(setting);
        return "redirect:/";
    }
}
