/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package download;

import java.io.File;
import java.io.IOException;
import jxl.Cell;
import jxl.Sheet;
import jxl.Workbook;
import jxl.read.biff.BiffException;

/**
 *
 * @author Administrator
 */
public class ExelUlti {
    public int maxindex;
    public int index;
    private static final String FILE = "Movies.xls";
    public ExelUlti() throws IOException, BiffException {
        Workbook wb = Workbook.getWorkbook(new File(FILE));
        Sheet sheet = wb.getSheet(0);
        maxindex = sheet.getRows();
        System.out.println(maxindex);
        wb.close();
    }
    
    public String getLink(int index) throws IOException, BiffException{
        Workbook wb = Workbook.getWorkbook(new File(FILE));
        Sheet sheet = wb.getSheet(0);
        Cell cell = sheet.getCell(3, index);
        String link = cell.getContents();
        wb.close();
        link = link.concat("watching.html");
        return link;
    }
    
    public String getTitle(int index) throws IOException, BiffException{
        Workbook wb = Workbook.getWorkbook(new File(FILE));
        Sheet sheet = wb.getSheet(0);
        Cell cell1 = sheet.getCell(0, index);
        String title = cell1.getContents();
        wb.close();
        title = title.replaceAll(" ", "");
        title = title.replaceAll(":", "");
        return title;
    }
//    public void writeToFile(String title, String changeName) throws IOException, BiffException, WriteException{
//        System.out.println(title);
//        Workbook wb = Workbook.getWorkbook(new File("Title.xls"));
//        WritableWorkbook wbWrite = Workbook.createWorkbook(new File(fileName), wb);
//        WritableSheet sheet = wbWrite.getSheet(0);
//        sheet.addCell(new Label(0,index, changeName));
//        sheet.addCell(new Label(1,index, title));
//        wbWrite.write();
//        wbWrite.close();
//        index++;
//    }
}
