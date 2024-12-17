package org.example;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Duration;
import java.util.Random;
import java.util.List;
import java.io.IOException;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import javax.lang.model.element.Element;

public class HairBearLogin {

    private WebDriver driver;
    private WebElement element;
    private String url;
    private boolean firstLoop = true;
    private List<String[]> dataArray;

    public HairBearLogin() {
        driver = ChromeDriverSetup.createDriver();
        url = "https://xn--9k3b19nba786n.com/";
        try {
            dataArray = LoginDataLoader.loadLoginData("C:/makeProject/login_data.csv");
        } catch (IOException e) {
            e.printStackTrace();
            dataArray = List.of();
        }
    }

    public void activateBot() {
        Random rand = new Random();
        var roofIndex = 0;

        try {
            if (dataArray.isEmpty()) {
                System.err.println("Login data array is empty. Please check the CSV file.");
                return;
            }

            while (true) {
                // 현재 시간 확인
                LocalDateTime currentDateTime = LocalDateTime.now();
                LocalTime currentTime = currentDateTime.toLocalTime();
                LocalTime startTime = LocalTime.of(8, 0);   // 오전 8시
                LocalTime endTime = LocalTime.of(2, 0);     // 다음날 새벽 2시

                // 현재 시간이 지정된 시간 범위 내에 있는지 확인 (오전 8시부터 다음날 새벽 2시까지)
                if (!(currentTime.isAfter(startTime) || currentTime.isBefore(endTime))) {

                    // 다음 실행 가능한 시간 계산 (오전 8시)
                    LocalDateTime nextStartDateTime = currentDateTime.withHour(8).withMinute(0).withSecond(0).withNano(0);
                    if (currentTime.isAfter(endTime)) {
                        nextStartDateTime = nextStartDateTime.plusDays(1);
                    }

                    long waitTime = Duration.between(currentDateTime, nextStartDateTime).toMillis();
                    long remainingTime = (waitTime / 1000 / 60);

                    if (remainingTime < 4) {
                        System.out.println("\r다음 실행까지 대기 시간: " + remainingTime + "분");
                    }

                    Thread.sleep(60000); // 1분 마다 확인
                    continue;
                }

                // 봇 활성화 로직 시작
                if (firstLoop) {
                    driver.get(url);
                    PageUtils.waitForPageLoad(driver);
                    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                    try {
                        WebElement randomElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"bh_li1\"]/div/div[1]/div[2]/div[1]/span")));
                        randomElement.click();
                    } catch (Exception e) {
                        driver = RegisterBrowser.restartBrowser(driver, url);
                        firstLoop = true;
                        continue;
                    }
                    firstLoop = false;
                }
                // 30초 ~ 3분 대기
                Thread.sleep(30000 + rand.nextInt(180000));

                roofIndex++;
                int index = rand.nextInt(dataArray.size());
                String email = dataArray.get(index)[0];
                String password = dataArray.get(index)[1];

                // 로그인 시도
                try {
                    element = driver.findElement(By.id("uid"));
                    element.clear();
                    element.sendKeys(email);

                    element = driver.findElement(By.id("upw"));
                    element.clear();
                    element.sendKeys(password);

                    WebElement loginButton = driver.findElement(By.cssSelector(".submit.bh_write_btn.hover.w-100.h-100.bh_bg_color_main.bh_bd_color_main"));
                    loginButton.click();

                    PageUtils.waitForPageLoad(driver);
                    if(roofIndex % 10 == 0){
                        System.out.println("\r반복수: " + roofIndex + "\n로그인 완료된 계정 이메일: " + email);
                    }
                } catch (Exception e) {
                    driver = RegisterBrowser.restartBrowser(driver, url);
                    firstLoop = true;
                    continue;
                }

                // 1~5분 대기
                Thread.sleep(60000 + rand.nextInt(300000));

                PageUtils.closeModalIfPresent(driver);

                WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
                try {
                    WebElement nextActionElement = wait.until(ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"section1\"]/div/div[1]/div/div/div/div[1]/div[2]/div[1]/div[2]/div/div[2]/div/div[2]/a[2]")));
                    nextActionElement.click();
                } catch (Exception e) {
                    driver.get(url);
                    PageUtils.waitForPageLoad(driver);
                    firstLoop = true;
                    continue;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (driver != null) {
                driver.quit();
            }
        }
    }

    public static void main(String[] args) {
        HairBearLogin bot1 = new HairBearLogin();
        bot1.activateBot();
    }
}

// 이 창을 닫지 마세요
