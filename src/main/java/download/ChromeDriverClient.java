/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package download;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

/**
 *
 * @author Viet Bac
 */
public class ChromeDriverClient {

    private static WebDriver chromeDriver = null;

    private ChromeDriverClient() {
    }

    public static WebDriver getChromeDriver() {
        DesiredCapabilities caps = DesiredCapabilities.chrome();
        caps.setJavascriptEnabled(true);
        System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
        chromeDriver = new ChromeDriver(caps);
        return chromeDriver;
    }
    
    public static void quitChrome(){
        chromeDriver.quit();
    }
}
