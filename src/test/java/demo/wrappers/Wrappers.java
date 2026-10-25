package demo.wrappers;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.stream.Collectors;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.How;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.text.NumberFormat;
import java.time.Duration;

public class Wrappers {
    /*
     * Write your selenium wrappers here
     */
    ChromeDriver driver;

    public Wrappers(ChromeDriver driver) {
        this.driver = driver;
    }

    public void navigateToPage(String url) throws InterruptedException {
        driver.get(url);
        Thread.sleep(10000);
    }

    public void sendText(String text, WebElement element) throws InterruptedException {
        element.sendKeys(text);
        Thread.sleep(1000);
    }

    public void btnClick(WebElement element) {
        element.click();
    }

    public int CountBelow4star(By ratingLocator) {
        int count = 0;

        List<WebElement> elements = driver.findElements(ratingLocator);

        for (int i = 0; i < elements.size(); i++) {
            try {
                // Re-fetch elements because the page may refresh
                elements = driver.findElements(ratingLocator);

                String ratingText = elements.get(i).getText().trim();
                double rating = Double.parseDouble(ratingText);
                System.out.println(rating);

                if (rating <= 4.0) {
                    count++;
                }
            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                // Skip an element that becomes stale
            }
        }

        return count;
    }

    public void printTitleByDiscount(By ratingLocator, By titleLocator) {
        List<WebElement> elements = driver.findElements(ratingLocator);

        for (int i = 0; i < elements.size(); i++) {
            try {
                WebElement rating = elements.get(i);

                String ratingText = rating.getText()
                        .replace("% off", "")
                        .trim();

                int eachRating = Integer.parseInt(ratingText);

                if (eachRating > 17) {

                    WebElement productTitle = rating.findElement(
                            By.xpath(
                                    "./ancestor::div[contains(@class,'ZFwe0M')]"
                                            + "//div[@class='RG5Slk']"));

                    System.out.println(
                            productTitle.getText() + " - " + eachRating + "% off");
                }

            } catch (org.openqa.selenium.StaleElementReferenceException e) {
                System.out.println("Product element became stale; skipping it.");
            } catch (NumberFormatException e) {
                System.out.println("Could not parse discount text.");
            }
        }
    }

    public void scrollToElement(By elementLacator) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        WebElement element = driver.findElement(elementLacator);

        js.executeScript(
                "arguments[0].scrollIntoView({block: 'center', inline: 'nearest'});",
                element);
    }

    public List<String> getAllUserRatedCount(By Locator) {
        List<Long> Ratings = new ArrayList<>();
        // TODO Auto-generated method stub
        List<WebElement> userRatingCount = driver.findElements(Locator);
        for (WebElement userRatingCountEach : userRatingCount) {
            String number = userRatingCountEach.getText().replaceAll("[^0-9]", "");

            long value = Long.parseLong(number);
            Ratings.add(value);

        }
        // System.out.println(Ratings);
        Collections.sort(Ratings, Collections.reverseOrder());
        // System.out.println(Ratings);
        NumberFormat formatter = NumberFormat.getInstance(Locale.US);

        
List<String> stringRatings = Ratings.stream().map(rating -> "(" + formatter.format(rating) + ")").collect(Collectors.toList());
       // System.out.println(stringRatings);
        return stringRatings;

    }
   public List<String> returnTitleFromUserRating(List<String> Ratings){
     List<String>Title=new ArrayList<>();
    for (String rating : Ratings) {
        String xpath = String.format(
            "//span[text()='%s']//ancestor::div[@class='RGLWAk']//a[@class='pIpigb']",
            rating);

        WebElement title = driver.findElement(By.xpath(xpath));
        Title.add(title.getText()) ;
    }
    return Title;

    }

}
