package swp.studentprojectportal.utils;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import swp.studentprojectportal.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SheetHandle {

    public List<List<String>> importSheet(MultipartFile file) {

        try {
            List<List<String>> data = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(1); //get data from second row

            //loop through rows
            for (Row row : worksheet) {
                Iterator<Cell> cellIterator = row.cellIterator();
                List<String> dataRow = new ArrayList<>();

                //loop through cells
                while (cellIterator.hasNext()) {

                    Cell cell = cellIterator.next();
                    dataRow.add(cell.toString());
                }

                data.add(dataRow);
            }

            workbook.close();
            return data;

        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public List<User> importSheetUser(MultipartFile file) {
        try {

            List<User> userList = new ArrayList<>();
            List<List<String>> data = importSheet(file);

            for (List<String> row : data) {

                User user = new User();
                user.setEmail(row.get(0));
                user.setPhone(row.get(1));

            }

            return userList;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public XSSFWorkbook exportSheetUser(List<User> list) {

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Data");

            // Write header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Email");
            headerRow.createCell(1).setCellValue("Phone");

            // Write data
            for (User user : list) {
                Row row = sheet.createRow(sheet.getLastRowNum()+1);

                row.createCell(row.getLastCellNum()+1).setCellValue(user.getEmail());
                row.createCell(row.getLastCellNum()).setCellValue(user.getPhone());
                row.createCell(row.getLastCellNum()).setCellValue(user.getId());
            }

            return workbook;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

}
