import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.*;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class TestHelper {

    static WebDriver driver;
    final int waitForResposeTime = 4;
	
	// here write a link to your admin website (e.g. http://my-app.herokuapp.com/admin)
    String baseUrlAdmin = "http://localhost:3000/admin";
	
	// here write a link to your website (e.g. http://my-app.herokuapp.com/)
    String baseUrl = "http://localhost:3000/";

    @Before
    public void setUp(){
        // if you use Chrome:
        System.setProperty("webdriver.chrome.driver", "C:\\Users\\radsin\\Desktop\\kooliasjad\\4 semester\\tarkvaratestimine\\praks 6\\chromedriver-win64\\chromedriver.exe");
        driver = new ChromeDriver();

        // if you use Firefox:
        //System.setProperty("webdriver.gecko.driver", "C:\\Users\\johan\\kool\\swt\\geckodriver.exe");
        //driver = new FirefoxDriver();

        driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
        driver.get(baseUrl);

    }

    void goToPage(String page){
        driver.get(baseUrl + page);
    }

    void waitForElementById(String id){
        new WebDriverWait(driver, waitForResposeTime).until(ExpectedConditions.presenceOfElementLocated(By.id(id)));
    }

    public boolean isElementPresent(By by) {
        try {
            driver.findElement(by);
            return true;
        }
        catch (NoSuchElementException e) {
            return false;
        }
    }

    public void createUser(String username, String password){
        goToPage("admin");

        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("user_name")).click();
        driver.findElement(By.id("user_name")).sendKeys(username);
        driver.findElement(By.id("user_password")).sendKeys(password);
        driver.findElement(By.id("user_password_confirmation")).sendKeys(password);
        driver.findElement(By.name("commit")).click();
    }

    public void deleteUser(String username){
        goToPage("admin");
        driver.findElement(By.xpath("//p[@id='" + username + "']/a[2]")).click();
    }

    void login(String username, String password){

        driver.get(baseUrlAdmin);

        driver.findElement(By.linkText("Login")).click();

        driver.findElement(By.id("name")).sendKeys(username);
        driver.findElement(By.id("password")).sendKeys(password);

        By loginButtonXpath = By.xpath("//input[@value='Login']");
        driver.findElement(loginButtonXpath).click();
    }

    void logout(){
        goToPage("admin");
        WebElement logout = driver.findElement(By.linkText("Logout"));
        logout.click();
        waitForElementById("Admin");
    }

    @After
    public void tearDown(){
        driver.quit();
    }

}