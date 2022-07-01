package checkingregistration;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class RegistrationMozilla extends RegistrationChrome {
    //Запустив этот класс, он выполнит не только вход, но и остальные тесты из родительского класса
    @Test
    @Order(1)
    public void openBrowser(){
        System.setProperty("webdriver.gecko.driver", "D:/Itstep/Selenium/geckodriver.exe"); // указали путь к драйверу
        FirefoxOptions options = new FirefoxOptions();
        options.setBinary("C:\\Program Files\\Mozilla Firefox\\firefox.exe");
        driver = new FirefoxDriver(options);
        driver.get(baseUrl);//Переход по заданному адресу
        driver.manage().window().maximize(); //расширили окно
    }
}

