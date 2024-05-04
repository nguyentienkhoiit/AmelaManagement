package com.khoinguyen.amela.excel;

import com.khoinguyen.amela.model.dto.attendance.AttendanceDtoResponse;
import com.khoinguyen.amela.util.DateTimeHelper;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.IOException;
import java.util.List;

public class AttendanceExcel {
    private final XSSFWorkbook workbook;
    private final XSSFSheet sheet;
    private final List<AttendanceDtoResponse> attendanceDtoResponses;

    public AttendanceExcel(List<AttendanceDtoResponse> attendanceDtoResponses) {
        this.attendanceDtoResponses = attendanceDtoResponses;
        workbook = new XSSFWorkbook();
        sheet = workbook.createSheet("attendance sheet");
        sheet.setDefaultColumnWidth(20);
    }

    private void writeHeaderRow() {
        CellStyle cellStyle = workbook.createCellStyle();
        Font fontHeader = workbook.createFont();
        fontHeader.setBold(true);
        fontHeader.setFontHeightInPoints((short) 15);
        fontHeader.setColor(IndexedColors.BLACK.getIndex());
        cellStyle.setFont(fontHeader);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);

        Row rowHeader = sheet.createRow(0);
        Cell cellHeader = rowHeader.createCell(0);
        cellHeader.setCellValue("Personal timekeeping report");
        sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 8));
        cellHeader.setCellStyle(cellStyle);
        rowHeader.setHeightInPoints(30);

        Row row = sheet.createRow(1);

        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.ROYAL_BLUE.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Font font = workbook.createFont();
        font.setBold(true);
        font.setFontHeightInPoints((short) 12);
        font.setColor(IndexedColors.WHITE.getIndex());
        headerStyle.setFont(font);

        Cell cell = row.createCell(0);
        cell.setCellValue("Code");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(1);
        cell.setCellValue("Full name");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(2);
        cell.setCellValue("Department");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(3);
        cell.setCellValue("Date");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(4);
        cell.setCellValue("Day");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(5);
        cell.setCellValue("Check in");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(6);
        cell.setCellValue("Check out");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(7);
        cell.setCellValue("Work time");
        cell.setCellStyle(headerStyle);

        cell = row.createCell(8);
        cell.setCellValue("Note");
        cell.setCellStyle(headerStyle);
    }

    private void writeDataRows() {
        int rowCount = 2;
        for (AttendanceDtoResponse attendance : attendanceDtoResponses) {
            if (!attendance.isStatus()) continue;

            Row row = sheet.createRow(rowCount++);

            Cell cell = row.createCell(0);
            cell.setCellValue(attendance.getUser().getCode());

            cell = row.createCell(1);
            cell.setCellValue(attendance.getFullName());

            cell = row.createCell(2);
            cell.setCellValue(attendance.getUser().getDepartment().getName());

            cell = row.createCell(3);
            cell.setCellValue(attendance.getCheckDay());

            cell = row.createCell(4);
            cell.setCellValue(attendance.getDay());

            cell = row.createCell(5);
            cell.setCellValue(attendance.getCheckInTime());

            cell = row.createCell(6);
            cell.setCellValue(attendance.getCheckOutTime());

            cell = row.createCell(7);
            cell.setCellValue(attendance.getWorkTime());

            cell = row.createCell(8);
            cell.setCellValue(attendance.getNote());
        }


        CellStyle headerStyle = workbook.createCellStyle();
        headerStyle.setFillForegroundColor(IndexedColors.CORAL.getIndex());
        headerStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);

        Row row = sheet.createRow(rowCount);
        Cell cell = row.createCell(0);
        cell.setCellValue("Summary");
        cell.setCellStyle(headerStyle);
        sheet.addMergedRegion(new CellRangeAddress(rowCount, rowCount, 1, 6));

        int totalMinus = attendanceDtoResponses.stream()
                .mapToInt(a -> DateTimeHelper.getMinus(a.getWorkTime()))
                .sum();
        cell = row.createCell(7);
        cell.setCellValue(DateTimeHelper.getDateFromMinus(totalMinus));
        cell.setCellStyle(headerStyle);
    }

    public void export(HttpServletResponse response) throws IOException {
        writeHeaderRow();
        writeDataRows();

        ServletOutputStream outputStream = response.getOutputStream();
        workbook.write(outputStream);
        workbook.close();
        outputStream.close();
    }
}
