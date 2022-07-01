package checkingregistration;

import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static org.junit.jupiter.api.Assertions.assertTrue;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class RegistrationChrome {
    protected static WebDriver driver = null; /*Равно null значит, что объект изначально не проинициализирован.
    Инициализация происходит в тесте driver = new ChromeDriver();. Создаем здесь объект со значением null, т.к.
    в тесте может потребоваться дополнительные настройки, которые дописываются в скобках. */
    protected final String baseUrl = "https://unimall.by/";


    @Test
    @Order(1)
    public void openBrowser() {
        System.setProperty("webdriver.chrome.driver", "D:/Itstep/Selenium/chromedriver.exe"); // указали путь к драйверу
        driver = new ChromeDriver();// Создаем объект
        driver.get(baseUrl);//Переход по заданному адресу
        driver.manage().window().maximize(); //расширили окно
    }

    @Test    //Начинаем регистрироваться. Нажимаем на кнопку "Личный кабинет"
    @Order(2)
    public void pressButton() {
        //*[@id="fm-account-dropdown"]/a
        WebElement button = driver.findElement(By.xpath("//*[@id='fm-account-dropdown']"));
        // Меняем слэш и двойные кавычки на одиночные
        button.click();
    }

    @Test    //Переходим по ссылке "Регистрация"
    @Order(3)
    public void goToLink() {
        driver.get(baseUrl + "index.php?route=account/simpleregister");
        assertTrue(driver.getTitle().toUpperCase().contains("Быстрая регистрация".toUpperCase()));
        //Проверяем переходим ли по нужной ссылке.
        // Чтобы не было ошибок из-за регистра дописываем два раза toUpperCase()
    }

    @Test    //ВВод данных и нажатие на кнопку "Продолжить"
    @Order(4)
    public void singUp(){
        // id ищем через Copy XPath
        //*[@id="register_email"]
        //*[@id="register_firstname"]
        //*[@id="register_telephone"]
        //*[@id="select2-register_zone_id-container"]
        //*[@id="register_address_1"]
        WebElement email = driver.findElement(By.id("register_email"));
        email.sendKeys(generate()); // вводим несуществующую почту
        WebElement firstName = driver.findElement(By.id("register_firstname"));
        firstName.sendKeys("Jane");
        WebElement telephone = driver.findElement(By.id("register_telephone"));
        telephone.sendKeys("291234567");
        // Заполняем выпадающий список
        //*[@id="register_zone_id"]
        Select zone = new Select(driver.findElement(By.id("register_zone_id")));
        /*
        try {
            Thread.sleep(240000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

         */
        zone.selectByValue("7");
        //ВВодим адрес
        //*[@id="register_address_1"]
        WebElement address = driver.findElement(By.id("register_address_1"));
        address.sendKeys("пр-т Черняховского, 8");
        //Проставляем галочки в двух checkboxes
        WebElement checkbox1 = driver.findElement
                (By.xpath("//*[@id='agreement_checkbox']/div[1]/label/input"));
        // В id меняем слэш и двойные кавычки на одиночные
        checkbox1.click();
        WebElement checkbox2 = driver.findElement
                (By.xpath("//*[@id='agreement_checkbox']/div[2]/label/input"));
        checkbox2.click();
        // Нажимаем кнопку "Продолжить"
        //*[@id="simpleregister_button_confirm"]
        //*[@id="simpleregister_button_confirm"]/span
        // Locating search button and click on it
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        WebElement button = driver.findElement(By
                .xpath("//*[@id='simpleregister_button_confirm']"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", button);

        // 1 вариант
        //Зададим паузу, т.к. выдает ошибку "element click intercepted"
        //Thread.sleep(180000);
        /* 2 вариант
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(240));
        WebElement button = driver.findElement
                (By.xpath("//*[@id='simpleregister_button_confirm']"));
        wait.until(ExpectedConditions.elementToBeClickable(button));
        button.click();

        */
    }

    //Зададим пароль
    @Test
    @Order(7)
    @Disabled
    public void pressPassword(){
        WebElement button2 = driver.findElement(By
                .xpath("//*[@id='column-right']/div/ul/li[3]/a"));
        JavascriptExecutor jse = (JavascriptExecutor)driver;
        jse.executeScript("arguments[0].click()", button2);
        /*
        <a class="fm-account-link d-flex align-items-center" href="https://unimall.by/index.php?route=account/password" rel="nofollow"><i class="fas fa-cogs"></i><span>Пароль</span></a>
        driver.get(baseUrl + "index.php?route=account/password");
        //assertTrue(driver.getTitle().toUpperCase().contains("Изменить пароль".toUpperCase()));

         */
    }

    @Test    //ВВод пароля и нажатие на кнопку "Продолжить"
    @Order(8)
    @Disabled
    public void changePassword() {
         //*[@id="input-password"]
         WebElement password = driver.findElement(By.id("input-password"));
        password.sendKeys("abcd1234");
        //*[@id="input-confirm"]
        WebElement confirm = driver.findElement(By.cssSelector("input[name='confirm']"));
        confirm.sendKeys("abcd1234");
        // Нажимаем кнопку "Продолжить"
        WebElement button = driver.findElement(By
                .xpath("//*[@id='content']/div/div/form/div/input"));
        try {
            Thread.sleep(30000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        button.click();
    }

    //Выход из аккаунта
    @Test
    @Order(9)
    @Disabled
    public void pressButton3() {
        WebElement button3 = driver.findElement(By
                        .xpath("//*[@id='fm-account-dropdown']/a"));
        button3.click();
    }

    @Test
    @Order(10)
    @Disabled
    public void logOut(){
        driver.get(baseUrl + "logout");
        assertTrue(driver.getTitle().toUpperCase().contains("Выход".toUpperCase()));
    }

    @Test
    @Order(11)
        //Задаем рандомно почту
        public String generate () {
            //Генерируем случайным образом буквы
            String s = "";
            Random random = new Random();
            for (int i = 0; i < 4; i++) {
                int n = random.nextInt(122 - 97 + 1) + 97;
                char c = (char) n;
                s += c;
            }

        /* Задаем 4 постоянные буквы
        String s = "abcd";
        Random random = new Random();
         */

            //Генерируем случайным образом цифры
            int number = random.nextInt(1000) + 1000;
            s += String.valueOf(number);
            s += "@gmail.com";
            return s;
    }
}
