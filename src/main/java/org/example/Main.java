package org.example;

import org.example.converter.Converter;

import java.io.File;
import java.io.IOException;

public class Main {
    public static void main(String[] args)  {

        File excelFile = new File("C:\\Users\\Kush\\OneDrive\\Expense Account\\2023\\January - Copy.xlsx");
        File sqlFile = new File("sql.sql");
        Converter converter = new Converter(excelFile, sqlFile);

        try{
            converter.convert();
        }catch(IOException ex){
            ex.printStackTrace();
        }

    }
}