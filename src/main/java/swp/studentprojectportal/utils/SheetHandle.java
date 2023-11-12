package swp.studentprojectportal.utils;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import swp.studentprojectportal.model.Project;
import swp.studentprojectportal.model.StudentClass;
import swp.studentprojectportal.model.User;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;

public class SheetHandle {

    public List<List<String>> importSheet(MultipartFile file) {

        try {
            List<List<String>> data = new ArrayList<>();

            XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
            XSSFSheet worksheet = workbook.getSheetAt(0); //get data from first row

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
            data.remove(0); //remove header in data
            return data;

        } catch (IOException e) {
            System.out.println("Import sheet: " + e);
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
//                user.setFullName(row.get(2));

                userList.add(user);
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
            headerRow.createCell(2).setCellValue("Full Name");
            headerRow.createCell(3).setCellValue("Status");

            // Write data
            for (User user : list) {
                Row row = sheet.createRow(sheet.getLastRowNum()+1);

                row.createCell(row.getLastCellNum()+1).setCellValue(writeCell(user.getEmail()));
                row.createCell(row.getLastCellNum()).setCellValue(writeCell(user.getPhone()));
                row.createCell(row.getLastCellNum()).setCellValue(writeCell(user.getFullName()));
                row.createCell(row.getLastCellNum()).setCellValue(writeCell(user.isStatus()+""));
            }

            return workbook;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public XSSFWorkbook exportStudentClass(List<StudentClass> list, List<Project> groupList) {

        try {
            XSSFWorkbook workbook = new XSSFWorkbook();
            XSSFSheet sheet = workbook.createSheet("Data");

            //set options group
            List<String> options = groupList.stream()
                    .map(Project::getGroupName)
                    .collect(Collectors.toList());
            options.add("No group");
            CellRangeAddressList addressList = new CellRangeAddressList(1, Math.max(list.size(), 1), 2, 2); // (startRow, endRow, startCol, endCol)

            // Create a data validation object
            DataValidationHelper dataValidationHelper = sheet.getDataValidationHelper();
            DataValidationConstraint dvConstraint = dataValidationHelper.createExplicitListConstraint(options.toArray(new String[options.size()]));
            DataValidation dataValidation = dataValidationHelper.createValidation(dvConstraint, addressList);

            // Add the data validation to the cell
            sheet.addValidationData(dataValidation);

            // Write header
            Row headerRow = sheet.createRow(0);
            headerRow.createCell(0).setCellValue("Email");
            headerRow.createCell(1).setCellValue("Phone");
            headerRow.createCell(2).setCellValue("Group name");

            // Write data
            for (StudentClass student : list) {
                Row row = sheet.createRow(sheet.getLastRowNum()+1);

                row.createCell(row.getLastCellNum()+1).setCellValue(writeCell(student.getStudent().getEmail()));
                row.createCell(row.getLastCellNum()).setCellValue(writeCell(student.getStudent().getPhone()));

                String groupName = student.getProject()==null ? "No group" : student.getProject().getGroupName();
                row.createCell(row.getLastCellNum()).setCellValue(groupName);
            }

            return workbook;
        } catch (Exception e) {
            System.out.println(e);
            return null;
        }
    }

    public String writeCell(String value) {
        return (value==null || value.isEmpty()) ? "none" : value;
    }

}
