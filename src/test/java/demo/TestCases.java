package demo;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;


// import io.github.bonigarcia.wdm.WebDriverManager;
import demo.wrappers.Wrappers;

public class TestCases {
    ChromeDriver driver;
    Wrappers wrapper ;

    /*
     * TODO: Write your tests here with testng @Test annotation. 
     * Follow `testCase01` `testCase02`... format or what is provided in instructions
     */
    @Test
    public void testCase01(){
        try {

            wrapper.navigateToPage("https://www.flipkart.com/");
            WebElement searchBar=driver.findElement(By.xpath("//input[@name='q']"));
            wrapper.btnClick(searchBar);
            wrapper.sendText("Washing Machine", searchBar);
            searchBar.sendKeys(Keys.ENTER);
            WebElement popularityBtn=driver.findElement(By.xpath("//div[text()='Popularity']"));
            wrapper.btnClick(popularityBtn);
            int count = wrapper.CountBelow4star(By.xpath("//span[contains(@id,'productRating')]//div[@class='MKiFS6']"));
             System.out.println("Ratings  count equal to or below 4 is "+count);


        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }


    @Test
    public void testCase02(){
        try {

            wrapper.navigateToPage("https://www.flipkart.com/");
            WebElement searchBar=driver.findElement(By.xpath("//input[@name='q']"));
           // wrapper.btnClick(searchBar);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='q']")));

            wrapper.sendText("iPhone", searchBar);
            searchBar.sendKeys(Keys.ENTER);
            List<WebElement> starRatings=driver.findElements(By.xpath("//div[@class='HQe8jr']"));
            for(WebElement e: starRatings){
                String ratingText = e.getText().replace("% off", "").trim();

             int eachRating = Integer.parseInt(ratingText);
               // System.out.println((eachRating.replace("% off","")));
                if(eachRating>17){
                    WebElement iphoneTitle=driver.findElement(By.xpath("//div[@class='HQe8jr']//ancestor::div[@class='ZFwe0M row']//div[@class='RG5Slk']"));
                    String iphoneTitleString=iphoneTitle.getText();
                    System.out.println(iphoneTitleString);
                }
            }
            
           



        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }
 @Test
    public void testCase03(){
        try {

            wrapper.navigateToPage("https://www.flipkart.com/");
            WebElement searchBar=driver.findElement(By.xpath("//input[@name='q']"));
           // wrapper.btnClick(searchBar);
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));

            WebElement searchBox = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//input[@name='q']")));

            wrapper.sendText("Coffee Mug", searchBar);
            searchBar.sendKeys(Keys.ENTER);

            WebElement fourStarCheck = driver.findElement(By.xpath("//div[contains(text(),'4* & above']//preceding-sibling::input"));

            JavascriptExecutor js = (JavascriptExecutor) driver;

            js.executeScript("arguments[0].scrollIntoView(true);", fourStarCheck);

            fourStarCheck.click();

            
            
           Thread.sleep(1000);



        } catch (InterruptedException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

    }

     
    /*
     * Do not change the provided methods unless necessary, they will help in automation and assessment
     */
    @BeforeTest
    public void startBrowser()
    {
        System.setProperty("java.util.logging.config.file", "logging.properties");

        // NOT NEEDED FOR SELENIUM MANAGER
        // WebDriverManager.chromedriver().timeout(30).setup();

        ChromeOptions options = new ChromeOptions();
        LoggingPreferences logs = new LoggingPreferences();

        logs.enable(LogType.BROWSER, Level.ALL);
        logs.enable(LogType.DRIVER, Level.ALL);
        options.setCapability("goog:loggingPrefs", logs);
        options.addArguments("--remote-allow-origins=*");

        System.setProperty(ChromeDriverService.CHROME_DRIVER_LOG_PROPERTY, "build/chromedriver.log"); 

        driver = new ChromeDriver(options);

        driver.manage().window().maximize();
        wrapper = new Wrappers(driver);
    }

    @AfterTest
    public void endTest()
    {
        driver.close();
        driver.quit();

    }
}