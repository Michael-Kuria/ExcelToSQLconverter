package org.example.converter;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.FormulaEvaluator;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.xmlbeans.impl.store.Locale;

import java.io.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.Iterator;

public class Converter {
    private File excelFile;
    private File sqlFile;

    public Converter(File excelFile, File sqlFile){
        this.excelFile = excelFile;
        this.sqlFile = sqlFile;
    }

    public void convert() throws IOException {

        FileInputStream inputStream = new FileInputStream(excelFile);
        XSSFWorkbook workbook = new XSSFWorkbook(inputStream);
        XSSFSheet sheet = workbook.getSheetAt(0);

        PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(sqlFile)));

        FormulaEvaluator evaluator = workbook.getCreationHelper().createFormulaEvaluator();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");

        Iterator<Row> rows = sheet.iterator();

        while(rows.hasNext()){
            Row row = rows.next();
            String id = row.getCell(0).getStringCellValue();
            Date date = row.getCell(1).getDateCellValue();
            String category = row.getCell(2).getStringCellValue();
            String description = row.getCell(3).getStringCellValue();
            int amount = (int) row.getCell(4).getNumericCellValue();


            String str = String.format("insert into transactions (id, amount,date,description, modification_date, " +
                    "category_id) values ('%s',%d,'%s','%s','%s',%d);", id,amount,format.format(date),description, LocalDateTime.now(), convertCategory(category));

            writer.println(str);

        }

        writer.flush();

    }


    public int convertCategory(String category){

        switch (category.toLowerCase()){
            case "transport":
                return 2;
            case "bills":
                return 1;
            case "clothes":
                return 5;
            case "electronics":
                return 4;
            case "enjoyment":
                return 8;
            case "furniture":
                return 4;
            case "groceries":
                return 3;
            case "handouts":
                return 10;
            case "internet":
                return 4;
            case "learning":
                return 12;
            case "loan":
                return 9;
            case "medical":
                return 6;
            case "monthly shopping":
                return 7;
            case "transactions cost":
                return 11;
            case "other":
                return 8;
            case "church":
                return 10;
            default:
                return 4;

        }
    }
}
