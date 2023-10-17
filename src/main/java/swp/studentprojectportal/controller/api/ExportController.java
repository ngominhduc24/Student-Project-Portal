package swp.studentprojectportal.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import swp.studentprojectportal.service.servicesimpl.ClassService;
import swp.studentprojectportal.service.servicesimpl.ProjectService;
import swp.studentprojectportal.service.servicesimpl.StudentClassService;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.SheetHandle;

import java.io.ByteArrayOutputStream;
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
    @Autowired
    ClassService classService;

    @GetMapping("/user")
    public void exportExcel(HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new SheetHandle().exportSheetUser(userService.findAllUser());

        // Write workbook to response
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

    @GetMapping("/student/{classId}")
    public ResponseEntity<ByteArrayResource> exportStudent(@PathVariable Integer classId) {
        SheetHandle sheetHandle = new SheetHandle();
        XSSFWorkbook workbook = sheetHandle.exportSheetUser(classService.getAllStudent(classId).stream().map(studentClass -> studentClass.getStudent()).toList());

        // Convert the XSSFWorkbook to a byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {
            workbook.write(byteArrayOutputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Create a ByteArrayResource from the byte array
        ByteArrayResource resource = new ByteArrayResource(byteArrayOutputStream.toByteArray());

        // Set the headers for the download
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=exported_students.xlsx");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_OCTET_STREAM)
                .body(resource);
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
