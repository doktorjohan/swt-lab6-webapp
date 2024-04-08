import org.junit.Test;

import static junit.framework.TestCase.assertEquals;

public class BasicTest extends TestHelper {


    private String username = "";
    private String password = "";

    @Test
    public void titleExistsTest(){
        String expectedTitle = "ST Online Store";
        String actualTitle = driver.getTitle();

        assertEquals(expectedTitle, actualTitle);
    }


    /*
    In class Exercise

    Fill in loginLogoutTest() and login mehtod in TestHelper, so that the test passes correctly.

     */
    // @Test
    public void loginLogoutTest(){

        login(username, password);

        // assert that correct page appeared
        // WebElement adminHeader = driver.findElement...
        // ...

        logout();
    }

    /*
    In class Exercise

     Write a test case, where you make sure, that one can’t log in with a false password

     */
    // @Test
    public void loginFalsePassword() {

    }

}
