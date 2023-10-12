package swp.studentprojectportal.controller.class_manager;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.apache.tomcat.util.json.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.service.servicesimpl.SettingService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;

import java.net.HttpURLConnection;
import java.util.Optional;

@Controller
@RequestMapping("/class-manager")
public class StudentController {
    @Autowired
    ClassService classService;
    @Autowired
    UserService userService;
    @Autowired
    SettingService settingService;
    @Autowired
    StudentClassService studentClassService;

    @GetMapping("/class")
    public String studentList(Model model,
                           @RequestParam(defaultValue = "-1") int classId) {
        final int roleId = 1;
        Class c = classService.getClass(classId);
        if(c == null) {
            return "redirect:/class";
        }
        model.addAttribute("classId", classId);
        model.addAttribute("className", c.getClassName());
        model.addAttribute("semester", c.getSemester().getSettingTitle());
        model.addAttribute("totalPage", userService.getTotalPage(10, roleId));
        model.addAttribute("studentList", classService.getAllStudent(classId));
        return "class_manager/student/studentList";
    }

    @GetMapping("/class/studentDetails")
    public String studentDetails(Model model,
                           @RequestParam(defaultValue = "-1") int studentId) {
        Optional<User> user = userService.findUserById(studentId);
        model.addAttribute("user", user.isPresent() ? user.get() : null);
        model.addAttribute("roleList", settingService.getAllRole());
        return "class_manager/student/studentDetails";
    }

    @GetMapping("/class/removeStudentFromClass")
    public String removeStudentFromClass(
            @RequestParam(name = "classId") Integer classId,
            @RequestParam(name = "studentId") Integer studentId) {
        boolean result =  studentClassService.removeStudentFromClass(classId, studentId);
        return "redirect:/class-manager/class?classId=" + classId;
    }

    @GetMapping("/class/syncStudent")
    public String removeStudentFromClass(
            @RequestParam(name = "classId") Integer classId) {
        Class myClass = classService.getClass(classId);
        String hrefApi = "http://localhost:3000/api/v1/students?subject=" + myClass.getSubject().getSubjectCode().toLowerCase() + "&class=" + myClass.getClassName().toLowerCase();

        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            // Create an HTTP GET request
            HttpGet httpGet = new HttpGet(hrefApi);

            // Execute the request and get the response
            CloseableHttpResponse response = httpClient.execute(httpGet);

            // Get the response entity as a string
            String responseBody = EntityUtils.toString(response.getEntity());
            // Print the response
            System.out.println("Response:\n" + responseBody);

            // Ensure the response is properly closed to release resources
            response.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "redirect:/class-manager/class?classId=" + classId;
    }
}
