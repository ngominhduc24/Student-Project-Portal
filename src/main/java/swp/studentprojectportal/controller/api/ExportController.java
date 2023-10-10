package swp.studentprojectportal.controller.api;

import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import swp.studentprojectportal.service.servicesimpl.UserService;
import swp.studentprojectportal.utils.SheetHandle;

import java.io.IOException;

@RestController
public class ExportController {
    @Autowired
    UserService userService;

    @GetMapping("/exportUser")
    public void exportExcel(HttpServletResponse response) throws IOException {

        XSSFWorkbook workbook = new SheetHandle().exportSheetUser(userService.findAllUser());

        // Write workbook to response
        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment; filename=data.xlsx");
        workbook.write(response.getOutputStream());
        workbook.close();
    }

}
