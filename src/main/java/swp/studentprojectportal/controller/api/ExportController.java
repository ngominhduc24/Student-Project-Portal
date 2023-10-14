package swp.studentprojectportal.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.service.servicesimpl.ProjectService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.SheetHandle;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/export")
public class ExportController {
    @Autowired
    UserService userService;
    @Autowired
    StudentClassService studentClassService;
    @Autowired
    ProjectService projectService;

    @GetMapping("/user")
    public void exportExcel(HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new SheetHandle().exportSheetUser(userService.findAllUser());

        // Write workbook to response
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/studentClass/{classId}")
    public void exportStudentClass(HttpServletResponse response, @PathVariable int classId) throws IOException {

        XSSFWorkbook workbook = new SheetHandle().exportStudentClass(
                studentClassService.findAllByClassId(classId), projectService.findAllByClassId(classId));

        // Write workbook to response
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
