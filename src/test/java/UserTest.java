import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;
import java.util.concurrent.TimeUnit;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.Assert.assertTrue;

public class UserTest extends TestHelper {

    //Checking if this even works
    /*
    @Test
    public void titleExistsTest(){
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }
     */

    //Actual tests
    @Test
    public void addAndDeleteFromCart() {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"Sunglasses 2AR_entry\"]/div[2]/form/input[1]")).click();

        new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"current_item\"]/td[4]/a"))).click();

        new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[4]/a"))).click();

    }

    @Test
    public void deleteEntireCart() {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();
        driver.findElement(By.xpath("//*[@id=\"Sunglasses 2AR_entry\"]/div[2]/form/input[1]")).click();

        new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart\"]/form[1]/input[2]"))).click();

        WebElement deletedMessage = driver.findElement(By.id("notice"));
        assertTrue(deletedMessage.isDisplayed());
        assertEquals(deletedMessage.getText(), "Cart successfully deleted.");
    }

    @Test
    public void increaseAndDecrease() {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();

        //increasing
        for (int i = 0; i < 3; i++) {
            new WebDriverWait(driver, waitForResposeTime).ignoring(
                    StaleElementReferenceException.class).until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[5]/a"))).click();
        }
        String amount = new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"current_item\"]/td[1]"))).getText();
        assertEquals(amount, "4×");

        //decreasing
        for (int i = 0; i < 3; i++) {
            new WebDriverWait(driver, waitForResposeTime).ignoring(
                    StaleElementReferenceException.class).until(
                    ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart\"]/table/tbody/tr[1]/td[4]/a"))).click();
        }
        amount = new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"current_item\"]/td[1]"))).getText();
        assertEquals(amount, "1×");

        //deleting cart afterwards
        new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"cart\"]/form[1]/input[2]"))).click();

    }

    @Test
    public void increaseByAddingToCart() {
        for (int i = 0; i < 4; i++) {
            driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();
        }

        String amount = new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"current_item\"]/td[1]"))).getText();
        assertEquals(amount, "4×");
    }

    @Test
    public void searchProducts() {
        driver.findElement(By.id("search_input")).sendKeys("book");

        List<WebElement> items = driver.findElements(By.className("entry"));

        for (WebElement item : items) {
            String itemname = item.getAttribute("id");
            if (itemname.toLowerCase().contains("book")) {
                assertTrue(item.isDisplayed());
            } else {
                assertFalse(item.isDisplayed());
            }
        }
    }

    @Test
    public void filterSunglasses() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[2]/a")).click();
        TimeUnit.SECONDS.sleep(1); //I need a normal wait because everything breaks horribly if I try to do it differently
        List<WebElement> items = driver.findElements(By.cssSelector(".entry #category"));
        for (WebElement item : items) {
            String category = item.getText().split(" ")[1];
            assertEquals(category, "Sunglasses");
        }
    }

    @Test
    public void filterBooks() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[3]/a")).click();
        TimeUnit.SECONDS.sleep(1);
        List<WebElement> items = driver.findElements(By.cssSelector(".entry #category"));
        for (WebElement item : items) {
            String category = item.getText().split(" ")[1];
            assertEquals(category, "Books");
        }
    }

    @Test
    public void filterOther() throws InterruptedException {
        driver.findElement(By.xpath("//*[@id=\"menu\"]/ul/li[4]/a")).click();
        TimeUnit.SECONDS.sleep(1);
        List<WebElement> items = driver.findElements(By.cssSelector(".entry #category"));
        for (WebElement item : items) {
            String category = item.getText().split(" ")[1];
            assertEquals(category, "Other");
        }
    }

    @Test
    public void purchaseItem() {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();

        new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkout_button\"]/input"))).click();

        driver.findElement(By.id("order_name")).sendKeys("name");
        driver.findElement(By.id("order_address")).sendKeys("address");
        driver.findElement(By.id("order_email")).sendKeys("email");
        WebElement selectElement = driver.findElement(By.id("order_pay_type"));
        Select select = new Select(selectElement);
        select.selectByValue("Check");

        driver.findElement(By.xpath("//*[@id=\"place_order\"]/input")).click();

        WebElement receiptMessage = driver.findElement(By.id("order_receipt"));
        assertEquals(receiptMessage.isDisplayed(), true);
    }

    @Test
    public void purchaseItemDontFillInfo() {
        driver.findElement(By.xpath("//*[@id=\"B45593 Sunglasses_entry\"]/div[2]/form/input[1]")).click();

        new WebDriverWait(driver, waitForResposeTime).ignoring(
                StaleElementReferenceException.class).until(
                ExpectedConditions.elementToBeClickable(By.xpath("//*[@id=\"checkout_button\"]/input"))).click();

        driver.findElement(By.xpath("//*[@id=\"place_order\"]/input")).click();

        WebElement errorMessage = driver.findElement(By.id("error_explanation"));
        assertEquals(errorMessage.isDisplayed(), true);
    }
}
