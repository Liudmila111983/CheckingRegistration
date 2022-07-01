package checkingregistration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.edge.EdgeDriver;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)

public class RegistrationEdge extends RegistrationChrome {
    //Запустив этот класс, он выполнит не только вход, но и остальные тесты из родительского класса
    @Test
    @Order(1)
    public void openBrowser(){
        System.setProperty("webdriver.edge.driver", "D:/Itstep/Selenium/msedgedriver.exe"); // указали путь к драйверу
        driver = new EdgeDriver();// Создаем объект
        driver.get(baseUrl);//Переход по заданному адресу
        driver.manage().window().maximize(); //расширили окно
    }
}

