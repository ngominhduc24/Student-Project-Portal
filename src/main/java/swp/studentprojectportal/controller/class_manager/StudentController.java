package swp.studentprojectportal.controller.class_manager;

import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.Validate;
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

    @GetMapping("class/student")
    public String studentList(Model model,
                              HttpSession session,
                              @RequestParam(defaultValue = "-1") int classId,
                              @RequestParam(defaultValue = "-1", required = false) int id) {
        Optional<User> user = userService.findUserById(id);
        model.addAttribute("user", user.isPresent() ? user.get() : userService.findUserById(1).get());

        final int roleId = 1;
        Class c = classService.getClass(classId);
        if (c == null) {
            return "redirect:class/student";
        }
        if (session.getAttribute("numberStudentAdded") != null) {
            model.addAttribute("numberStudentAdded", session.getAttribute("numberStudentAdded"));
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
            @RequestParam(name = "studentId", required = false) int id,
            @RequestParam String fullName,
            @RequestParam String email,
            @RequestParam String phone,
            @RequestParam int roleId,
            @RequestParam String note,
            @RequestParam boolean status,
            Model model) {
        User userUpdate = userService.findUserById(id).get();
        model.addAttribute("user", userUpdate);
        model.addAttribute("roleList", settingService.getAllRole());

        //check validate before update
        String msg = checkValidateUpdateUser(email, phone, userUpdate);
        if (msg != null) {
            model.addAttribute("errorMsg", msg);
        } else {
            //update
            boolean ans = userService.updateUser(id, fullName, email, phone, roleId, status, note);

            if (ans) model.addAttribute("msg", "Update success");
            else model.addAttribute("errorMsg", "Update failed");
        }

        return "class_manager/student/studentList";
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
        boolean result = studentClassService.removeStudentFromClass(classId, studentId);
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
                    user = userService.findByPhone(user.getPhone());

                    if (user == null) continue;
                }

                //add student to class
                studentClassService.addNewStudentToClass(classId, user.getId());

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        return "redirect:./student?classId=" + classId;
    }

    private String checkValidateUpdateUser(String email, String phone, User user) {
        email = email.trim();
        phone = phone.trim();

        if (email.isEmpty() && phone.isEmpty()) return "Please input email or phone number";
        if (!email.isEmpty() && !userService.checkEmailDomain(email)) return "Invalid email domain";

        if (!email.isEmpty() && !Validate.validEmail(email)) return "Invalid email";
        if (!phone.isEmpty() && !Validate.validPhoneNumber(phone)) return "Invalid phone number";

        if (!email.equals(user.getEmail()) && !email.isEmpty() && userService.checkExistMail(email))
            return "Email existed!";
        if (!phone.equals(user.getPhone()) && !phone.isEmpty() && userService.checkExistPhoneNumber(phone))
            return "Phone number existed!";

        return null;
    }
}
