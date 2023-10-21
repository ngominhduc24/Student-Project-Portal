package swp.studentprojectportal.controller.subject_manager.sclass;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp.studentprojectportal.model.*;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.*;
import swp.studentprojectportal.utils.Validate;

import java.util.List;
@Controller
public class ClassHomeController {
    @Autowired
    ClassService classService;
    @Autowired
    SubjectService subjectService;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @Autowired
    MilestoneService milestoneService;
    @Autowired
    IssueSettingService issueSettingService;
    @GetMapping("/subject-manager/class")
    public String classPage(@RequestParam(defaultValue = "0") Integer pageNo,
            @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
            @RequestParam(defaultValue = "-1") Integer subjectId, @RequestParam(defaultValue = "-1") Integer semesterId,
            @RequestParam(defaultValue = "-1") Integer teacherId, @RequestParam(defaultValue = "-1") Integer status,
            @RequestParam(defaultValue = "id") String sortBy, @RequestParam(defaultValue = "1") Integer sortType,
                            Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Class classA = new Class();
        classA.setSubject(new Subject());
        classA.setSemester(new Setting());
        classA.setUser(new User());
        classA.setDescription("");
        Page<Class> classList = classService.findAllBySubjectManagerId(user.getId(), search, pageNo, pageSize, sortBy, sortType, subjectId, semesterId, teacherId, status);
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        List<User> teacherList = userService.findTeacherBySubjectManagerId(user.getId());
        List<Setting> semesterList = settingService.findSemesterBySubjectManagerId(user.getId());
        model.addAttribute("subjectList", subjectService.findAllSubjectByUserAndStatus(user, true));
        model.addAttribute("classList", classList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("semesterId", semesterId);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", classList.getTotalPages());
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        model.addAttribute("class", classA);

        List<Subject> subjectListAdd = subjectService.findAllSubjectByUserAndStatus(user, true);
        List<Setting> semesterListAdd = settingService.findSemesterByStatus(3, true);
        List<User> teacherListAdd = userService.findTeacherByRoleIdAndStatus(4, true);
        model.addAttribute("subjectListAdd",subjectListAdd);
        model.addAttribute("teacherListAdd",teacherListAdd);
        model.addAttribute("semesterListAdd",semesterListAdd);
        return "subject_manager/class/classList";
    }

    @PostMapping("/subject-manager/class")
    public String classAddPage(@RequestParam Integer pageNo,
                            @RequestParam Integer pageSize, @RequestParam String search,
                            @RequestParam Integer subjectId, @RequestParam Integer semesterId,
                            @RequestParam Integer teacherId, @RequestParam Integer status,
                            @RequestParam String sortBy, @RequestParam Integer sortType,
                               @RequestParam String description,
                               @RequestParam String newClassName, @RequestParam Integer newSubjectId,
                               @RequestParam Integer newSemesterId, @RequestParam Integer newClassManagerId,
                               WebRequest request, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Class classA = new Class();
        classA.setClassName(newClassName);
        classA.setDescription(description);
        classA.setSubject(subjectService.getSubjectById(newSubjectId));
        classA.setSemester(settingService.getSettingByID(newSemesterId));
        classA.setUser(userService.getUserById(newClassManagerId));
        if (!newClassName.isEmpty()) {
            if (classService.checkExistedClassName(newClassName, newSubjectId, null))
                model.addAttribute("errmsg", "This class name has already existed in this subject!");
            else {
                classA = classService.saveClass(classA);
                milestoneService.addClassAssignment(classA);
                classA= new Class();
                classA.setSubject(new Subject());
                classA.setSemester(new Setting());
                classA.setUser(new User());
                classA.setDescription("");
                model.addAttribute("toastMessage", "Add new class successfully");
            }
        }
        if(subjectId!=-1 || semesterId !=-1 || teacherId!=-1 || status!=-1 || !search.isEmpty()) pageNo=0;

        Page<Class> classList = classService.findAllBySubjectManagerId(user.getId(), search, pageNo, pageSize, sortBy, sortType, subjectId, semesterId, teacherId, status);
        List<Subject> subjectList = subjectService.findAllSubjectByUser(user);
        List<User> teacherList = userService.findTeacherBySubjectManagerId(user.getId());
        List<Setting> semesterList = settingService.findSemesterBySubjectManagerId(user.getId());
        model.addAttribute("subjectList", subjectService.findAllSubjectByUserAndStatus(user, true));
        model.addAttribute("classList", classList);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("subjectId", subjectId);
        model.addAttribute("semesterId", semesterId);
        model.addAttribute("teacherId", teacherId);
        model.addAttribute("status", status);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("totalPage", classList.getTotalPages());
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        model.addAttribute("class", classA);

        List<Subject> subjectListAdd = subjectService.findAllSubjectByUserAndStatus(user, true);
        List<Setting> semesterListAdd = settingService.findSemesterByStatus(3, true);
        List<User> teacherListAdd = userService.findTeacherByRoleIdAndStatus(4, true);
        model.addAttribute("subjectListAdd",subjectListAdd);
        model.addAttribute("teacherListAdd",teacherListAdd);
        model.addAttribute("semesterListAdd",semesterListAdd);
        return "subject_manager/class/classList";
    }

    @GetMapping("/subject-manager/class/updateStatus")
    public String updateSubjectSettingStatus(@RequestParam int id,
            @RequestParam Integer status, RedirectAttributes attributes) {
        Class classA = classService.findById(id);
        if(status==-1){
            classService.delete(classA);
            attributes.addFlashAttribute("toastMessage", "Delete a class successfully");
            return "redirect:/subject-manager/class";
        }
        classA.setStatus(status);
        if(status==1) attributes.addFlashAttribute("toastMessage", "Cancel a class successfully");
        if(status==2) attributes.addFlashAttribute("toastMessage", "Start a class successfully");
        if(status==3) attributes.addFlashAttribute("toastMessage", "Close a class successfully");
        classService.saveClass(classA);
        return "redirect:/subject-manager/class";
    }

    @GetMapping("/subject-manager/classDetail")
    public String classDetail(@RequestParam("id") Integer id, Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Class classA = classService.findById(id);
        List<Subject> subjectList = subjectService.findAllSubjectByUserAndStatus(user, true);
        List<Setting> semesterList = settingService.findSemesterByStatus(3, true);
        List<User> teacherList = userService.findTeacherByRoleIdAndStatus(4, true);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        model.addAttribute("class", classA);
        return "subject_manager/class/classDetail";
    }

    @GetMapping("/class/issue-setting")
    public String issueSettingPage(@RequestParam("id") Integer classId,@RequestParam(defaultValue = "0") Integer pageNo,
                                @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") String settingGroup,
                                Model model, HttpSession session) {
        Class classA = classService.findById(classId);
        Page<IssueSetting> issueSettingList= issueSettingService.filterClassIssueSetting(classA.getSubject().getId(), classId,search, pageNo, pageSize, sortBy, sortType, settingGroup, status);
        List<String> settingGroupList = issueSettingService.findAllDistinctClassSettingGroup(classA.getSubject().getId(), classId);

        IssueSetting setting = new IssueSetting();
        setting.setAclass(classA);
        setting.setSettingGroup("Not Empty");
        setting.setSettingTitle("");
        setting.setDescription("");
        model.addAttribute("setting",setting);

        User user = (User) session.getAttribute("user");
        model.addAttribute("subjectList", subjectService.findAllSubjectByUserAndStatus(user, true));
        model.addAttribute("personalToken", user.getPersonalTokenGitlab());
        model.addAttribute("class", classA);
        model.addAttribute("pageSize", pageSize);
        model.addAttribute("pageNo", pageNo);
        model.addAttribute("search", search);
        model.addAttribute("classId", classId);
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortType", sortType);
        model.addAttribute("status", status);
        model.addAttribute("totalPage", issueSettingList.getTotalPages());
        model.addAttribute("settingGroup", settingGroup);

        model.addAttribute("issueSettingList", issueSettingList);
        model.addAttribute("settingGroupList", settingGroupList);

        return "class_manager/class/issueSettingList";
    }

        @PostMapping("/class/issue-setting")
        public String issueSettingAdd(@RequestParam("id") Integer classId,@RequestParam(defaultValue = "0") Integer pageNo,
                                       @RequestParam(defaultValue = "10") Integer pageSize, @RequestParam(defaultValue = "") String search,
                                       @RequestParam(defaultValue = "-1") Integer status, @RequestParam(defaultValue = "id") String sortBy,
                                       @RequestParam(defaultValue = "1") Integer sortType, @RequestParam(defaultValue = "") String settingGroup,
                                       @RequestParam String description,
                                       @RequestParam("newSettingGroup") String newSettingGroup,
                                       @RequestParam("newSettingTitle") String newSettingTitle,
                                       Model model, HttpSession session) {
            Class classA = classService.findById(classId);
            Page<IssueSetting> issueSettingList= issueSettingService.filterClassIssueSetting(classA.getSubject().getId(), classId,search, pageNo, pageSize, sortBy, sortType, settingGroup, status);
            List<String> settingGroupList = issueSettingService.findAllDistinctClassSettingGroup(classA.getSubject().getId(), classId);

            IssueSetting setting = new IssueSetting();
            setting.setAclass(classA);
            setting.setSettingGroup(newSettingGroup);
            setting.setSettingTitle(newSettingTitle);
            setting.setDescription(description);
                if (Validate.validNotempty(newSettingGroup) == false) {
                    String errmsg = "Group can't empty. Add failed!";
                    model.addAttribute("errmsg", errmsg);
                } else {
                    if (issueSettingService.findByClassAndGroupAndTitle(classId, newSettingGroup, newSettingTitle) != null) {
                        String errmsg = "Issue setting existed. Add failed!";
                        model.addAttribute("errmsg", errmsg);
                    } else {
                        issueSettingService.saveSubjectSetting(setting);
                        setting = new IssueSetting();
                        setting.setAclass(classA);
                        setting.setSettingGroup("Not Empty");
                        setting.setSettingTitle("");
                        setting.setDescription("");
                        model.addAttribute("toastMessage", "Add new class successfully");
                    }
                }

            model.addAttribute("setting",setting);



            User user = (User) session.getAttribute("user");
            model.addAttribute("subjectList", subjectService.findAllSubjectByUserAndStatus(user, true));
            model.addAttribute("personalToken", user.getPersonalTokenGitlab());
            model.addAttribute("class", classA);
            model.addAttribute("pageSize", pageSize);
            model.addAttribute("pageNo", pageNo);
            model.addAttribute("search", search);
            model.addAttribute("classId", classId);
            model.addAttribute("sortBy", sortBy);
            model.addAttribute("sortType", sortType);
            model.addAttribute("status", status);
            model.addAttribute("totalPage", issueSettingList.getTotalPages());
            model.addAttribute("settingGroup", settingGroup);

            model.addAttribute("issueSettingList", issueSettingList);
            model.addAttribute("settingGroupList", settingGroupList);

            return "class_manager/class/issueSettingList";
        }

    @PostMapping("/subject-manager/class/update")
    public String updateClass(@RequestParam("id") Integer classId,@RequestParam String description,
            @RequestParam String className, @RequestParam Integer subjectId,
            @RequestParam Integer semesterId, @RequestParam Integer classManagerId, @RequestParam Integer status,
            WebRequest request, Model model, HttpSession session, RedirectAttributes attributes) {
        Class classA = classService.findById(classId);
        classA.setClassName(className);
        classA.setDescription(description);
        classA.setStatus(status);
        classA.setSubject(subjectService.getSubjectById(subjectId));
        classA.setSemester(settingService.getSettingByID(semesterId));
        classA.setUser(userService.getUserById(classManagerId));
        model.addAttribute("class", classA);
        if(classService.checkExistedClassName(className, subjectId, classId))
            model.addAttribute("errmsg", "This class name has already existed!");
        else {
            classService.saveClass(classA);
            attributes.addFlashAttribute("toastMessage", "Update class details successfully");
            return "redirect:/subject-manager/class";
        }

        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUserAndStatus(user, true);
        List<Setting> semesterList = settingService.findSemesterByStatus(3, true);
        List<User> teacherList = userService.findTeacherByRoleIdAndStatus(4, true);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        return "subject_manager/classDetail";
    }

    @GetMapping("/subject-manager/class/add")
    public String classDetail(Model model, HttpSession session) {
        User user = (User) session.getAttribute("user");
        Class classA = new Class();
        classA.setSubject(new Subject());
        classA.setSemester(new Setting());
        classA.setUser(new User());
        classA.setDescription("");
        List<Subject> subjectList = subjectService.findAllSubjectByUserAndStatus(user, true);
        List<Setting> semesterList = settingService.findSemesterByStatus(3, true);
        List<User> teacherList = userService.findTeacherByRoleIdAndStatus(4, true);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        model.addAttribute("class",classA);
        return "subject_manager/class/classAdd";
    }

    @PostMapping("/subject-manager/class/add")
    public String addClass(@RequestParam String description,
                              @RequestParam String className, @RequestParam Integer subjectId,
                              @RequestParam Integer semesterId, @RequestParam Integer classManagerId,
                              WebRequest request, Model model, HttpSession session, RedirectAttributes attributes) {
        Class classA = new Class();
        classA.setClassName(className);
        classA.setDescription(description);
        classA.setSubject(subjectService.getSubjectById(subjectId));
        classA.setSemester(settingService.getSettingByID(semesterId));
        classA.setUser(userService.getUserById(classManagerId));
        model.addAttribute("class", classA);
        if(classService.checkExistedClassName(className, subjectId, null))
            model.addAttribute("errmsg", "This class name has already existed in this subject!");
        else {
            classA = classService.saveClass(classA);
            milestoneService.addClassAssignment(classA);
            attributes.addFlashAttribute("toastMessage", "Add new class successfully");
            return "redirect:/subject-manager/class";
        }
        User user = (User) session.getAttribute("user");
        List<Subject> subjectList = subjectService.findAllSubjectByUserAndStatus(user, true);
        List<Setting> semesterList = settingService.findSemesterByStatus(3, true);
        List<User> teacherList = userService.findTeacherByRoleIdAndStatus(4, true);
        model.addAttribute("subjectList",subjectList);
        model.addAttribute("teacherList",teacherList);
        model.addAttribute("semesterList",semesterList);
        return "subject_manager/class/classAdd";
    }
}
