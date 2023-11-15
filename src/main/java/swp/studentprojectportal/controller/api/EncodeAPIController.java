package swp.studentprojectportal.controller.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.model.Class;
import swp.studentprojectportal.model.User;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.utils.JwtTokenProvider;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/encode")
public class EncodeAPIController {

    @Autowired
    ClassService classService;

    private static final JwtTokenProvider jwtTokenProvider = new JwtTokenProvider();

    @PostMapping ("/data")
    public String encode(@RequestBody String data) {

        return jwtTokenProvider.encodeData(data);

    }

    @GetMapping("/getClassId")
    public String encode(HttpSession session) {

        User user = (User) session.getAttribute("user");
        List<Integer> classList = new ArrayList<>();

        //student
        if(user.getSetting().getId() == 1) {
            List<Class> listClass = classService.findAllByStudentUserId(user.getId());
            for (Class aclass : listClass)
                classList.add(aclass.getId());
        }

        //class manager
        if(user.getSetting().getId() == 4) {
            List<Class> listClass = classService.findAllByClassManagerId(user.getId());
            for (Class aclass : listClass)
                classList.add(aclass.getId());
        }

        return jwtTokenProvider.encodeData(convertListToJson(classList));

    }

    @GetMapping("/getStudentId")
    public String encodeStudentId(HttpSession session) {

        User user = (User) session.getAttribute("user");

        List<Integer>  userList = new ArrayList<>();
        userList.add(user.getId());

        return jwtTokenProvider.encodeData(convertListToJson(userList));

    }

    public static String convertListToJson(List<Integer> integerList) {
        ObjectMapper objectMapper = new ObjectMapper();
        String json = "";
        try {
            json = objectMapper.writeValueAsString(Collections.singletonMap("listClassId", integerList));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return json;
    }

}
