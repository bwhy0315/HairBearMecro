package org.example;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class ChromeDriverSetup {
    public static WebDriver createDriver() {
        System.setProperty("webdriver.chrome.driver", "C:/chromedriver-win64/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        options.addArguments("user-agent=Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.83 Safari/537.36");
        options.addArguments("--headless"); // Headless 모드 추가
        options.addArguments("disable-gpu"); // gpu(그래픽카드 가속)를 사용하지 않는 옵션
        options.addArguments("--window-size=1920,1080"); // 화면 크기 설정
        return new ChromeDriver(options);
    }
}

