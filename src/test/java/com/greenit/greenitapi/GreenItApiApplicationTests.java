package com.greenit.greenitapi;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Duration;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
class GreenItApiApplicationTests {

    @Autowired
    private MockMvc mockMvc;
    private static WebDriver webdriver;

    @Test
    public void testFollowedByUser() throws Exception {
        mockMvc.perform(get("http://localhost:8080/followedByUser?userId=13"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].displayName").value("jrber23"));

    }

    @Test
    public void testFollowersUser() throws Exception {
        mockMvc.perform(get("http://localhost:8080/followersUser?userId=14"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].displayName").value("rizna"));

    }

    @Test
    private void testconnectiondb(){
        try {
            mockMvc.perform(get("http://localhost:8080/servers"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$").isArray())
                    .andExpect(jsonPath("$[0].name").value("Touka"));
        } catch (Exception e){}
    }
    @Test
    public void testDBConnectionBuffer(){
        Thread t1 = new Thread(() -> {testconnectiondb();});
        Thread t2 = new Thread(() -> {testconnectiondb();});
        Thread t3 = new Thread(() -> {testconnectiondb();});
        Thread t4 = new Thread(() -> {testconnectiondb();});
        Thread t5 = new Thread(() -> {testconnectiondb();});
        Thread t6 = new Thread(() -> {testconnectiondb();});
        Thread t7 = new Thread(() -> {testconnectiondb();});
        Thread t8 = new Thread(() -> {testconnectiondb();});
        Thread t9 = new Thread(() -> {testconnectiondb();});
        Thread t10 = new Thread(() -> {testconnectiondb();});
        Thread t11 = new Thread(() -> {testconnectiondb();});
        Thread t12 = new Thread(() -> {testconnectiondb();});
        Thread t13 = new Thread(() -> {testconnectiondb();});
        Thread t14 = new Thread(() -> {testconnectiondb();});
        Thread t15 = new Thread(() -> {testconnectiondb();});
        Thread t16 = new Thread(() -> {testconnectiondb();});
        Thread t17 = new Thread(() -> {testconnectiondb();});
        Thread t18 = new Thread(() -> {testconnectiondb();});
        Thread t19 = new Thread(() -> {testconnectiondb();});
        Thread t20 = new Thread(() -> {testconnectiondb();});

        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t5.start();
        t6.start();
        t7.start();
        t8.start();
        t9.start();
        t10.start();
        t11.start();
        t12.start();
        t13.start();
        t14.start();
        t15.start();
        t16.start();
        t17.start();
        t18.start();
        t19.start();
        t20.start();
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }



//    @Test
//    public void selenium1() throws InterruptedException {
//        driver.get("https://www.google.com/");
//        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(500));
//        Thread.sleep(2000);
//        JavascriptExecutor js = (JavascriptExecutor) driver;
//        js.executeScript("javascript:window.scrollBy(750,950)");
//
//        driver.quit();
//    }


    @BeforeAll
    public static void beforeClass()
    {
        //WebDriverManager.chromedriver().setup();
        //WebDriverManager.firefoxdriver().setup();
        WebDriverManager.edgedriver().setup();
    }
    @BeforeEach
    public void beforeMethod()
    {
        webdriver=new EdgeDriver();
        webdriver.manage().window().maximize();
    }
    @AfterEach
    public void AfterMethod()
    {
        webdriver.close();
    }
    @Test
    public void test() throws InterruptedException {
        //webdriver.get("http://www.google.com/");
        webdriver.get("http://16.170.159.93/rrsspost?postid=12");
        Thread.sleep(2000);
    }

}

