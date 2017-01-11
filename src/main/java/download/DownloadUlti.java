/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package download;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author Viet Bac
 */
public class DownloadUlti {

    public static String redirect(String url) {
        try {
            URLConnection con = new URL(url).openConnection();
            con.connect();
            InputStream is = con.getInputStream();

            is.close();
            return con.getURL().toString();
        } catch (IOException ex) {
            Logger.getLogger(DownloadUlti.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static void download(String url, String title) {
        Random r = new Random();
        WebDriver driver = ChromeDriverClient.getChromeDriver();
        driver.get(url);
        try {
            Thread.sleep(12000 + r.nextInt(2000));
        } catch (InterruptedException ex) {
            Logger.getLogger(DownloadUlti.class.getName()).log(Level.SEVERE, null, ex);
        }
        WebElement findElement = driver.findElement(By.className("les-content"));
        List<WebElement> findElements = findElement.findElements(By.tagName("a"));
        for (WebElement findElement1 : findElements) {
            JavascriptExecutor js = (JavascriptExecutor) driver;
            js.executeScript("arguments[0].click();", findElement1);
            String ep = findElement1.getText();
            try {
                Thread.sleep(12000 + r.nextInt(2000));
            } catch (InterruptedException ex) {
                Logger.getLogger(DownloadUlti.class.getName()).log(Level.SEVERE, null, ex);
            }
            Document parse = Jsoup.parse(driver.getPageSource());
            Elements elementsByTag = parse.getElementsByTag("video");
            String link = elementsByTag.get(0).attr("src");
            try {
                getGoogleDownload(link, title + ep);
            } catch (IOException ex) {
                Logger.getLogger(DownloadUlti.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public static void getGoogleDownload(String link, String fileName) throws IOException {
        fileName = fileName.replaceAll(":", "");
        Runtime rt = Runtime.getRuntime();
        fileName = fileName.replaceAll(" ", "");
        link = redirect(link);
        System.out.println("Link: " + link);
        System.out.println("File Name: " + fileName);
        //rt.exec("C:\\Program Files (x86)\\Internet Download Manager\\idman.exe /n /d " + link + " /p C:\\Download /f " + fileName + ".mp4");
    }

    public static void main(String[] args) {
        System.out.println("123");
        download("http://123movies.ru/film/2-days-in-paris-2438/".concat("watching.html"), "2 Days In Paris");
        download("http://123movies.ru/film/8-simple-rules-season-3-15881/".concat("watching.html"), "8 Simple Rules - Season 3");
    }
}
