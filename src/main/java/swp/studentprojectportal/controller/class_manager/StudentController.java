package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.modelmapper.internal.bytebuddy.utility.RandomString;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.*;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.utils.Utility;
import swp.studentprojectportal.utils.instance.InstanceSingleton;
import swp.studentprojectportal.utils.SheetHandle;

import java.util.List;
import java.util.Optional;

@Controller
public class StudentController {
    @Autowired
    ClassService classService;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @Autowired
    StudentClassService studentClassService;
    @Autowired
    EmailService emailservice;

    @GetMapping("class/student")
    public String studentList(Model model,
                              HttpSession session,
                              @RequestParam(defaultValue = "-1") int classId,
                              @RequestParam(defaultValue = "-1", required = false) int id) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("user", user.isPresent() ? user.get() : userService.findUserById(1).get());
        model.addAttribute("roleList", settingService.getAllRole());

        final int roleId = 1;
        Class c = classService.getClass(classId);
        if (c == null) {
            return "redirect:class/student";
        }
        if (session.getAttribute("numberStudentAdded") != null) {
            String mess = "Sync FAP successfully! " + session.getAttribute("numberStudentAdded") + " student added";
            model.addAttribute("smessage", mess);
            session.removeAttribute("numberStudentAdded");
        }
        model.addAttribute("classId", classId);
        model.addAttribute("className", c.getClassName());
        model.addAttribute("class", c);
        model.addAttribute("semester", c.getSemester().getSettingTitle());
        model.addAttribute("totalPage", userService.getTotalPage(10, roleId));
        model.addAttribute("studentList", classService.getAllStudent(classId));

        return "class_manager/student/studentList";
    }

    @PostMapping("class/updateStudent")
    public String updateUser(
            @RequestParam(defaultValue = "-1") int classId,
            @RequestParam int id,
            @RequestParam(defaultValue = "") String note,
            @RequestParam boolean status,
            Model model, RedirectAttributes attributes) {
        User userUpdate = userService.findUserById(id).get();
        model.addAttribute("user", userUpdate);
        model.addAttribute("roleList", settingService.getAllRole());

        //update
        boolean ans = userService.updateStudent(id, status, note);

        if (ans) model.addAttribute("msg", "Update success");
        else model.addAttribute("errorMsg", "Update failed");

        final int roleId = 1;
        Class c = classService.getClass(classId);

        if (c == null) {
            return "redirect:/class/student?classId=" + classId;
        }
        model.addAttribute("classId", classId);
        model.addAttribute("className", c.getClassName());
        model.addAttribute("class", c);
        model.addAttribute("semester", c.getSemester().getSettingTitle());
        model.addAttribute("totalPage", userService.getTotalPage(10, roleId));
        model.addAttribute("studentList", classService.getAllStudent(classId));

        attributes.addFlashAttribute("toastMessage", "Update student successfully");

        return "redirect:/class/student?classId=" + classId;
    }

    @GetMapping("/class/student/updateStatus")
    public String updateSubjectSettingStatus(
            @RequestParam int id,
            @RequestParam boolean status) {
        User student = userService.getUserById(id);
        student.setStatus(status);
        userService.saveUser(student);
        return "redirect:/";
    }


    @GetMapping("/class-manager/class/studentDetails")
    public String studentDetails(Model model,
                                 @RequestParam(defaultValue = "-1") int studentId) {
        Optional<User> user = userService.findUserById(studentId);
        model.addAttribute("user", user.isPresent() ? user.get() : null);
        model.addAttribute("roleList", settingService.getAllRole());
        return "class_manager/student/studentDetails";
    }

    @GetMapping("/class-manager/class/removeStudentFromClass")
    public String removeStudentFromClass(
            @RequestParam(name = "classId") Integer classId,
            @RequestParam(name = "studentId") Integer studentId) {
        studentClassService.removeStudentFromClass(classId, studentId);
        return "redirect:/class/student?classId=" + classId;
    }

    @GetMapping("/class-manager/class/syncStudent")
    public String syncStudentToClass(
            HttpSession session,
            @RequestParam(name = "classId") Integer classId) {
        Class myClass = classService.getClass(classId);
        String apiEndpoint = "http://localhost:3000/api/v1/students?subject=" + myClass.getSubject().getSubjectCode().toLowerCase() + "&class=" + myClass.getClassName().toLowerCase();
        String[] studentIdList = new String[0];
        int numberStudentAdded = 0;

        String responseBody = InstanceSingleton.getInstance().callApiFap(apiEndpoint);
        if (responseBody != null) {
            studentIdList = responseBody.split(",");

            for (String studentId : studentIdList) {
                if (studentClassService.addNewStudentToClass(classId, studentId)) {
                    numberStudentAdded++;
                }
            }
        }

        session.setAttribute("numberStudentAdded", numberStudentAdded);

        return "redirect:/class/student?classId=" + classId;
    }

    @PostMapping("/class/importStudent")
    public String importStudent(@RequestParam MultipartFile file,
                                @RequestParam int classId) {
        //read sheet data
        List<User> userList = new SheetHandle().importSheetUser(file);

        if (userList == null) return "redirect:./student?classId=" + classId;

        //remove all student in current class
        studentClassService.removeAllStudentFromClass(classId);

        for (User userData : userList) {
            try {
                //find by email
                User user = userService.findByEmail(userData.getEmail());

                //find by phone
                if (user == null) {
                    user = userService.findByPhone(userData.getPhone());
                }
                if(user != null) {
                //add student to class
                studentClassService.addNewStudentToClass(classId, user.getId());
                }

                if(user == null) {
                    //if add with email -> send mail
                    if(!userData.getEmail().isEmpty() && !userData.getFullName().isEmpty()) {
                        // gen token
                        String token = RandomString.make(30); // generate token
                        userData.setToken(token);

                        userData.setActive(false);
                        userData.setStatus(true);
                        userData.setSetting(settingService.findById(1));
                        //save new user
                        User newUser = userService.saveUser(userData);

                        // send mail
                        String href = "reset-password";
                        String tokenSender = Utility.getSiteURL() + "/" + href + "?key=" + token;

                        emailservice.sendEmail(newUser.getFullName(), newUser.getEmail(), tokenSender);

                        //add student to class
                        studentClassService.addNewStudentToClass(classId, newUser.getId());
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return "redirect:./student?classId=" + classId;
    }
}
