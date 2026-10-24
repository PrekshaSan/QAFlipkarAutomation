package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
     ChromeDriver driver;

    public Wrappers(ChromeDriver driver) {
        this.driver = driver;
    }

    public  void navigateToPage(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(10000);
    }
    public void sendText(String text,WebElement element) throws InterruptedException {
        element.sendKeys(text);
        Thread.sleep(1000);
    }

    public void btnClick(WebElement element){
    element.click();
    }

    public  int CountBelow4star(By ratingLocator){
        int count = 0;

    List<WebElement> elements = driver.findElements(ratingLocator);

    for (int i = 0; i < elements.size(); i++) {
        try {
            // Re-fetch elements because the page may refresh
            elements = driver.findElements(ratingLocator);

            String ratingText = elements.get(i).getText().trim();
            double rating = Double.parseDouble(ratingText);
            System.out.println(rating);

            if (rating <=4.0) {
                count++;
            }
        } catch (org.openqa.selenium.StaleElementReferenceException e) {
            // Skip an element that becomes stale
        }
    }

    return count;
        }
       

    

}
