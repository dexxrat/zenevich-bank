package com.zenevich.bank.tests.utils;

import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

public class XlsReader {
    private final XSSFWorkbook book;
    private final XSSFSheet sheet;

    public XlsReader(File xlsxFile) throws IOException {
        try (FileInputStream fs = new FileInputStream(xlsxFile)) {
            book = new XSSFWorkbook(fs);
            sheet = book.getSheetAt(0);
        }
    }

    public XlsReader(File xlsxFile, String sheetName) throws IOException {
        try (FileInputStream fs = new FileInputStream(xlsxFile)) {
            book = new XSSFWorkbook(fs);
            sheet = book.getSheet(sheetName);
        }
    }

    public String[][] getSheetData() throws Exception {
        int numberOfColumn = sheet.getRow(0).getLastCellNum();
        int numberOfRows = sheet.getLastRowNum() + 1;
        String[][] data = new String[numberOfRows - 1][numberOfColumn];

        for (int i = 1; i < numberOfRows; i++) {
            XSSFRow row = sheet.getRow(i);
            if (row == null) continue;
            for (int j = 0; j < numberOfColumn; j++) {
                XSSFCell cell = row.getCell(j);
                if (cell == null) continue;
                data[i - 1][j] = cell.toString();
            }
        }

        return Arrays.stream(data)
                .filter(row -> Arrays.stream(row).anyMatch(Objects::nonNull))
                .filter(row -> Arrays.stream(row).anyMatch(x -> x != null && !x.isEmpty()))
                .toArray(String[][]::new);
    }
}