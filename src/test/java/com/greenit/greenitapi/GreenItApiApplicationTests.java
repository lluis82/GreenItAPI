package com.greenit.greenitapi;

import com.greenit.greenitapi.Controller.*;
import com.greenit.greenitapi.Entities.User;
import com.greenit.greenitapi.Util.Config;
import com.greenit.greenitapi.Util.mariadbConnect;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import static org.springframework.test.util.AssertionErrors.assertEquals;
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
                .andExpect(jsonPath("$[0].displayName").value("GreenIt enjoyer"));

    }

    @Test
    public void testFollowersUser() throws Exception {
        mockMvc.perform(get("http://localhost:8080/followersUser?userId=18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$[0].displayName").value("hector"));

    }

    @Test
    public void testBackendCache() throws Exception{
        mockMvc.perform(get("http://localhost:8080/purgecache")).andExpect(status().isOk()).andExpect(content().string(new Config().getSrvName() + " OK"));

        resetDatabase();

        //region server
        ServerController.meetServer("192.168.1.1","Eruruu");
        var a = ServerController.getServers();
        var b = ServerController.getServers();
        assertEquals("diferentes",a,b);
        var c = ServerController.getServer();
        var d = ServerController.getServer();
        assertEquals("diferentes",c,d);
        //endregion
        //region user
        UserController.register("g@h.es","pass","Aruruu","","");
        UserController.updateUser(UserController.getUserByName("Aruruu").getId(),"aruruu@uta.es","pass","Aruruu","","Uta FTW");
        var e = UserController.getUser("a@b.es");
        var f = UserController.getUser("a@b.es");
        assertEquals("diferentes",e,f);

        var g = UserController.getUserByName("jrber");
        var h = UserController.getUserByName("jrber");
        assertEquals("diferentes",g,h);

        var i = UserController.getUserById(13);
        var j = UserController.getUserById(13);
        assertEquals("diferentes",i,j);
        //endregion
        //region post
        PostController.publishPost("Aruruu","","");
        var k = PostController.getPostById(12);
        var l = PostController.getPostById(12);
        assertEquals("diferentes",k,l);

        //GET POST BY USER TIENE LA CACHE DESACTIVADA POR PROBLEMAS
        var nnn = PostController.getPostByUser("hector");
        //var o = PostController.getPostByUser("hector");
        //assertEquals("diferentes",o,n);

        var n = PostController.getAllPosts(1);
        var o = PostController.getAllPosts(1);
        assertEquals("diferentes",o,n);

        n = PostController.getAllPosts(2);
        o = PostController.getAllPosts(2);
        assertEquals("diferentes",o,n);

        n = PostController.getAllPosts(3);
        o = PostController.getAllPosts(3);
        assertEquals("diferentes",o,n);

        n = PostController.getAllPosts(4);
        o = PostController.getAllPosts(4);
        assertEquals("diferentes",o,n);

        var p = PostController.getCountOfUserPosts("hector");
        var q = PostController.getCountOfUserPosts("hector");
        assertEquals("diferentes",p,q);
        //endregion

        //region reduceduser
        ReducedUserController.newFollower(13,UserController.getUserByName("Aruruu").getId());
        ReducedUserController.deleteFollower(13,UserController.getUserByName("Aruruu").getId());
        var r = ReducedUserController.getFollowedbyUser(13);
        var s = ReducedUserController.getFollowedbyUser(13);
        assertEquals("diferentes",r,s);

        var t = ReducedUserController.getUserFollowers(14);
        var u = ReducedUserController.getUserFollowers(14);
        assertEquals("diferentes",t,u);

        p = ReducedUserController.getFollowedCount(13);
        q = ReducedUserController.getFollowedCount(13);
        assertEquals("diferentes",p,q);

        p = ReducedUserController.getFollowersCount(14);
        q = ReducedUserController.getFollowersCount(14);
        assertEquals("diferentes",p,q);

        var w = ReducedUserController.checkFollows(13,14);
        var x = ReducedUserController.checkFollows(13,14);
        assertEquals("diferentes",w,x);
        //endregion
        //region like
        LikeController.like("Aruruu",12);
        LikeController.unlike("Aruruu",12);
        p = LikeController.howmanylikes(12);
        q = LikeController.howmanylikes(12);
        assertEquals("diferentes",p,q);

        var y = LikeController.checklike(12,"hector");
        var z = LikeController.checklike(12,"hector");
        assertEquals("diferentes",y,z);
        //endregion
        //region step
        StepController.publishStep(21,false,"",12,"");
        var ee = StepController.getStepByid(21);
        var ff = StepController.getStepByid(21);
        assertEquals("diferentes",ee,ff);

        //STEP BY PREVIOUS ID TIENE LA CACHE MAL, DESACTIVADA
        var ggg = StepController.getStepByPrevId(21);
        //var hh = StepController.getStepByPrevId(21);
        //assertEquals("diferentes",gg,hh);

        //endregion
        //region comments
        CommentController.commentOnPostOrComment(0,"",12,"Aruruu");
        //CACHE DE GET COMMENTS FROM POST TIENE LA CACHE MAL, DESACTIVADA
        var aaa = CommentController.getCommentsfromPost(12);
        //var bb = CommentController.getCommentsfromPost(12);
        //assertEquals("diferentes",aa,bb);

        var cc = CommentController.getRepliesFromCommentID(4);
        var dd = CommentController.getRepliesFromCommentID(4);
        assertEquals("diferentes",cc,dd);
        //endregion
        //region rrss
        mockMvc.perform(get("http://localhost:8080/rrssprofile?username=jrber23"));
        mockMvc.perform(get("http://localhost:8080/rrsspost?postid=49"));
        mockMvc.perform(get("http://localhost:8080/rrssstep?stepid=26"));
        //endregion
    }

    @Test
    public void testconnectiondb(){
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

    @Test
    public void test() throws Exception {
        WebDriverManager.edgedriver().setup();
        webdriver=new EdgeDriver();
        webdriver.manage().window().maximize();
        webdriver.manage().timeouts().implicitlyWait(Duration.ofMillis(10000));

        webdriver.get("http://16.170.159.93/purgecache");
        Thread.sleep(100);
        JavascriptExecutor js = (JavascriptExecutor) webdriver;
        WebElement w2;
        webdriver.get("http://16.170.159.93/rrssprofile?username=jrber23");

        w2 = webdriver.findElement(By.xpath("/html/body/main/div[2]/div[3]/button"));
        w2.click();
        assertEquals("Adonde me esta llevando mi ubeeeeeer","http://16.170.159.93/rrsspost?postid=12", webdriver.getCurrentUrl());
        Thread.sleep(2000);

        w2 = webdriver.findElement(By.xpath("/html/body/article/button"));
        w2.click();
        assertEquals("Adonde me esta llevando mi ubeeeeeer","http://16.170.159.93/rrssstep?stepid=15", webdriver.getCurrentUrl());
        Thread.sleep(2000);

        js.executeScript("javascript:window.scrollBy(750,950)");

        w2 = webdriver.findElement(By.xpath("/html/body/article/main/div/div/button"));
        w2.click();
        assertEquals("Adonde me esta llevando mi ubeeeeeer","http://16.170.159.93/rrssstep?stepid=20", webdriver.getCurrentUrl());
        Thread.sleep(2000);

        js.executeScript("javascript:window.scrollBy(750,950)");

        w2 = webdriver.findElement(By.xpath("/html/body/article/main/div/div/button"));
        w2.click();
        assertEquals("Adonde me esta llevando mi ubeeeeeer","http://16.170.159.93/rrssstep?stepid=25", webdriver.getCurrentUrl());
        Thread.sleep(2000);

        w2 = webdriver.findElement(By.xpath("/html/body/article/button"));
        w2.click();
        assertEquals("Adonde me esta llevando mi ubeeeeeer","http://16.170.159.93/rrssstep?stepid=20", webdriver.getCurrentUrl());
        Thread.sleep(2000);

        webdriver.close();
    }

    public static void resetDatabase() throws SQLException {
        String s;
        StringBuffer sb = new StringBuffer();

        try {
            FileReader fr = new FileReader(new File(Config.getResourcesLocation() + "awsFinal.sql"));
            // be sure to not have line starting with "--" or "/*" or any other non aplhabetical character

            BufferedReader br = new BufferedReader(fr);

            while ((s = br.readLine()) != null) {
                sb.append(s);
            }
            br.close();

            // here is our splitter ! We use ";" as a delimiter for each request
            // then we are sure to have well formed statements
            String[] inst = sb.toString().split(";");

            Connection c = mariadbConnect.mdbconn();
            Statement st = c.createStatement();

            for (int i = 0; i < inst.length; i++) {
                // we ensure that there is no spaces before or after the request string
                // in order to not execute empty statements
                if (!inst[i].trim().equals("")) {
                    st.executeUpdate(inst[i] + ";");
                    System.out.println(">>" + inst[i] + ";");
                }
            }

        } catch (Exception e) {
            System.out.println("*** Error : " + e);
            System.out.println("*** ");
            System.out.println("*** Error : ");
            e.printStackTrace();
            System.out.println("################################################");
            System.out.println(sb);
        }
    }

}

