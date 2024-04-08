import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import static org.junit.Assert.assertTrue;

public class AdminTest extends TestHelper{

    private String username = "johan";
    private String password = "password";

    @Test
    public void loginLogoutTest(){
        login(username, password);
        assertTrue(driver.getCurrentUrl().contains("products"));
        logout();
    }

    @Test
    public void loginFalsePassword() {
        login(username, "falsePassword");
        WebElement loginError = driver.findElement(By.id("notice"));
        assertTrue(loginError.isDisplayed());
    }

    @Test
    public void registerUser() {
        goToPage("admin");
        createUser("newUser", "password");
        WebElement successMessage = driver.findElement(By.id("notice"));
        assertTrue(successMessage.isDisplayed());

        deleteUser("newUser");
    }

    @Test
    public void registerPasswordConfirmDoesNotMatch() {
        goToPage("admin");

        driver.findElement(By.linkText("Register")).click();
        driver.findElement(By.id("user_name")).click();
        driver.findElement(By.id("user_name")).sendKeys("newUser");
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).sendKeys("asd");
        driver.findElement(By.id("user_password_confirmation")).click();
        driver.findElement(By.id("user_password_confirmation")).sendKeys("dsa");
        driver.findElement(By.name("commit")).click();
        WebElement element = driver.findElement(By.id("user_password_confirmation"));
        assertTrue(element.isDisplayed());
    }

    @Test
    public void editUserPasswordConfirmDoesNotMatch() {

        login(username, password);
        goToPage("admin");

        driver.findElement(By.linkText("Edit")).click();
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).sendKeys("asd");
        driver.findElement(By.id("user_password_confirmation")).click();
        driver.findElement(By.id("user_password_confirmation")).sendKeys("dsa");
        driver.findElement(By.name("commit")).click();
        WebElement element = driver.findElement(By.id("user_password_confirmation"));
        assertTrue(element.isDisplayed());

    }

    @Test
    public void editUserPasswordSuccess() {
        createUser("newUser3", "password");
        goToPage("admin");

        driver.findElement(By.linkText("Edit")).click();
        driver.findElement(By.id("user_password")).click();
        driver.findElement(By.id("user_password")).sendKeys("newPassword");
        driver.findElement(By.id("user_password_confirmation")).click();
        driver.findElement(By.id("user_password_confirmation")).sendKeys("newPassword");
        driver.findElement(By.name("commit")).click();
        WebElement successMessage = driver.findElement(By.id("notice"));
        assertTrue(successMessage.isDisplayed());

        deleteUser("newUser3");
    }

    @Test
    public void editProduct() {
        login(username, password);

        driver.findElement(By.xpath("(//a[contains(text(),'Edit')])[4]")).click();
        driver.findElement(By.id("product_description")).click();
        driver.findElement(By.id("product_description")).sendKeys("edited");
        driver.findElement(By.name("commit")).click();
        driver.findElement(By.id("notice")).click();

        logout();
    }

    @Test
    public void editProductPriceIsNotNumeric() {
        login(username, password);

        driver.findElement(By.xpath("(//a[contains(text(),'Edit')])[4]")).click();
        driver.findElement(By.id("product_price")).click();
        driver.findElement(By.id("product_price")).sendKeys("asd");
        driver.findElement(By.name("commit")).click();
        WebElement element = driver.findElement(By.id("product_price"));
        assertTrue(element.isDisplayed());

        logout();
    }

    @Test
    public void deleteProduct() {
        login(username, password);

        driver.findElement(By.linkText("New product")).click();
        driver.findElement(By.id("product_title")).click();
        driver.findElement(By.id("product_title")).sendKeys("asd");
        driver.findElement(By.id("product_description")).click();
        driver.findElement(By.id("product_description")).sendKeys("asd");
        driver.findElement(By.id("product_prod_type")).click();
        WebElement dropdown = driver.findElement(By.id("product_prod_type"));
        dropdown.findElement(By.xpath("//option[. = 'Books']")).click();
        driver.findElement(By.id("product_price")).click();
        driver.findElement(By.id("product_price")).sendKeys("56");
        driver.findElement(By.name("commit")).click();
        driver.findElement(By.xpath("(//a[contains(text(),'Delete')])[5]")).click();
        WebElement element = driver.findElement(By.id("notice"));
        assertTrue(element.isDisplayed());

        logout();
    }

    @Test
    public void editProductType() {
        login(username, password);

        driver.findElement(By.xpath("//a[contains(@href, '/products/4/edit')]")).click();
        driver.findElement(By.id("product_prod_type")).click();
        WebElement dropdown = driver.findElement(By.id("product_prod_type"));
        dropdown.findElement(By.xpath("//option[. = 'Other']")).click();
        driver.findElement(By.name("commit")).click();

        WebElement element = driver.findElement(By.xpath("//div[@id='main']/div/p[4]"));
        assertTrue(element.getText().contains("Other"));

        logout();
    }

}
