package checkingregistration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.Select;
import java.util.Random;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationChrome {
    protected static WebDriver driver = null;
    /* Равно null значит, что объект изначально не проинициализирован. Инициализация
    происходит в тесте driver = new ChromeDriver();. Создаем здесь объект со значением null, т.к.
    в тесте может потребоваться дополнительные настройки, которые дописываются в скобках. */
    protected final String baseUrl = "https://unimall.by/";

    @Test
    @Order(1)
    public void openBrowser() {
        // Указать путь к драйверу.
        System.setProperty("webdriver.chrome.driver", "D:/Itstep/Selenium/chromedriver.exe");
        driver = new ChromeDriver();// Создать объект
        driver.get(baseUrl);// Перейти по заданному адресу
        driver.manage().window().maximize(); // Расширить окно
    }

    @Test    // Начало регистрации. Нажать на кнопку "Личный кабинет".
    @Order(2)
    public void pressButton() {
        WebElement button = driver.findElement(By.xpath("//*[@id='fm-account-dropdown']"));
        // Меняем слэш и двойные кавычки на одиночные
        button.click();
    }

    @Test    // Перейти по ссылке "Регистрация".
    @Order(3)
    public void goToLink() {
        driver.get(baseUrl + "index.php?route=account/simpleregister");
        // Проверить корректность перехода по ссылке.
        // Чтобы не было ошибок из-за регистра, дописать два раза toUpperCase().
        assertTrue(driver.getTitle().toUpperCase().contains("Быстрая регистрация".toUpperCase()));
    }

    @Test    // Ввести данные и нажать на кнопку "Продолжить".
    @Order(4)
    public void singUp(){
        WebElement email = driver.findElement(By.id("register_email"));
        // Заполнение строки адресом несуществующей почты, сгенерировнным случайным образом в тесте №9.
        email.sendKeys(generate());
        WebElement firstName = driver.findElement(By.id("register_firstname"));
        firstName.sendKeys("Jane");
        WebElement telephone = driver.findElement(By.id("register_telephone"));
        telephone.sendKeys("291234567");
        // Заполнить выпадающий список.
        Select zone = new Select(driver.findElement(By.id("register_zone_id")));
        zone.selectByValue("7");
        // Ввести адрес.
        WebElement address = driver.findElement(By.id("register_address_1"));
        address.sendKeys("пр-т Черняховского, 8");
        // Проставить галочки в двух чекбоксах.
        WebElement checkbox1 = driver.findElement
                (By.xpath("//*[@id='agreement_checkbox']/div[1]/label/input"));
        // В xpathExpression поменять слэш и двойные кавычки на одиночные.
        checkbox1.click();
        WebElement checkbox2 = driver.findElement
                (By.xpath("//*[@id='agreement_checkbox']/div[2]/label/input"));
        checkbox2.click();
        // Нажать кнопку "Продолжить"
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement button = driver.findElement(By
                .xpath("//*[@id='simpleregister_button_confirm']"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", button);
    }

    @Test    // Перейти на страницу смены пароля
    @Order(5)
    public void pressPassword(){
        WebElement button2 = driver.findElement(By
                .xpath("//*[@id='column-right']/div/ul/li[3]/a"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", button2);
        driver.get(baseUrl + "index.php?route=account/password");
        driver.navigate().refresh();
    }

    @Test    // Ввести пароль и нажать на кнопку "Продолжить"
    @Order(6)
    public void changePassword() {
        // Необходимо установить задержку, т.к. тест не срабатывает в Edge.
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement password = driver.findElement(By.id("input-password"));
        password.sendKeys("abcd1234");
        WebElement confirm = driver.findElement(By.id("input-confirm"));
        confirm.sendKeys("abcd1234");
        // Нажать кнопку "Продолжить".
        WebElement button3 = driver.findElement(By
                .xpath("//*[@id='content']/div/div/form/div/input"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", button3);
        /* Перестал работать этот вариант
        WebElement button = driver.findElement(By
                .xpath("//*[@id='content']/div/div/form/div/input"));
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        button.click();
         */
    }

    @Test    //Выход из аккаунта. Нажать на кнопку "Личный кабинет" (появится выпадающий список).
    @Order(7)
    // Поставить задержку, т.к. без нее не срабатывает предыдущий тест.
    public void pressButton3() {
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement button3 = driver.findElement(By
                        .xpath("//*[@id='fm-account-dropdown']/a"));
        button3.click();
    }

    @Test    // Выбрать элемент выпадающего списка "Выход".
    @Order(8)
    public void logOut(){
        driver.get(baseUrl + "logout");
        assertTrue(driver.getTitle().toUpperCase().contains("Выход".toUpperCase()));
    }

    @Test
    @Order(9)
    // Задать рандомно почту.
    public String generate () {
        // Сгенерировать случайным образом буквы.
        String s = "";
        Random random = new Random();
        for (int i = 0; i < 4; i++) {
            int n = random.nextInt(122 - 97 + 1) + 97;
            char c = (char) n;
            s += c;
        }

        // Сгенерировать случайным образом цифры.
        int number = random.nextInt(1000) + 1000;
        s += String.valueOf(number);
        s += "@gmail.com";
        return s;
    }
}

