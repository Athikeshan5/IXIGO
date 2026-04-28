package com.ixigo.testing.utilities;

import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ExcelDataProvider {

    // Try multiple paths — one will work depending on run config
    private static final String[] EXCEL_PATHS = {
        "./src/test/resources/Reader/TrainTestData.xlsx",
        "src/test/resources/Reader/TrainTestData.xlsx",
        "../src/test/resources/Reader/TrainTestData.xlsx",
        "IXIGO/src/test/resources/Reader/TrainTestData.xlsx"
    };

 
    public static List<Map<String, String>> getData(String sheetName) {

        List<Map<String, String>> dataList = new ArrayList<>();

        // Find the correct path
        String foundPath = null;
        for (String path : EXCEL_PATHS) {
            if (new File(path).exists()) {
                foundPath = path;
                break;
            }
        }

        if (foundPath == null) {
            System.out.println("Excel file not found. Tried paths:");
            for (String p : EXCEL_PATHS) {
                System.out.println("  " + new File(p).getAbsolutePath());
            }
            System.out.println("Please ensure TrainTestData.xlsx is in src/test/resources/Reader/");
            return dataList;
        }

        try (FileInputStream fis = new FileInputStream(foundPath);
             XSSFWorkbook workbook = new XSSFWorkbook(fis)) {

            XSSFSheet sheet = workbook.getSheet(sheetName);

            if (sheet == null) {
                System.out.println("Sheet not found: " + sheetName
                    + " in " + foundPath);
                System.out.println("Available sheets:");
                for (int i = 0; i < workbook.getNumberOfSheets(); i++) {
                    System.out.println("  - " + workbook.getSheetName(i));
                }
                return dataList;
            }

            XSSFRow headerRow = sheet.getRow(0);
            int colCount = headerRow.getLastCellNum();

            List<String> headers = new ArrayList<>();
            for (int col = 0; col < colCount; col++) {
                headers.add(headerRow.getCell(col).getStringCellValue().trim());
            }

            for (int row = 1; row <= sheet.getLastRowNum(); row++) {
                XSSFRow dataRow = sheet.getRow(row);
                if (dataRow == null) continue;

                Map<String, String> rowData = new LinkedHashMap<>();
                for (int col = 0; col < colCount; col++) {
                    String key   = headers.get(col);
                    String value = "";
                    try { value = dataRow.getCell(col).toString().trim(); }
                    catch (Exception ignored) {}
                    rowData.put(key, value);
                }
                dataList.add(rowData);
            }

            System.out.println("Excel data loaded: " + dataList.size()
                + " rows from sheet '" + sheetName + "' at " + foundPath);

        } catch (Exception e) {
            System.out.println("ExcelDataProvider error: " + e.getMessage());
        }

        return dataList;
    }

    public static List<String> getColumnData(String sheetName, String columnName) {
        List<String> result = new ArrayList<>();
        for (Map<String, String> row : getData(sheetName)) {
            String val = row.get(columnName);
            if (val != null && !val.isEmpty()) result.add(val);
        }
        return result;
    }
}