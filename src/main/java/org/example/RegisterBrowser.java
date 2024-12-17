package org.example;

import org.openqa.selenium.WebDriver;

public class RegisterBrowser {
    public static WebDriver restartBrowser(WebDriver driver, String url) {
        System.out.println("브라우저를 다시 시작합니다.");
        driver.quit();
        driver = ChromeDriverSetup.createDriver();
        driver.get(url);
        return driver;
    }
}
