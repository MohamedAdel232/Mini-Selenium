package MiniSelenium;

public class Main {
    public static void main(String[] args) throws Exception {
        MiniWebDriver driver = new MiniWebDriver();

        driver.navigate("https://www.saucedemo.com/");

        String username = driver.findElement("css selector", "#user-name");
        String password = driver.findElement("css selector", "#password");
        String loginButton = driver.findElement("css selector", "#login-button");

        driver.sendKeys(username, "standard_user");
        driver.sendKeys(password, "secret_sauce");
        driver.click(loginButton);

        driver.quit();
    }
}