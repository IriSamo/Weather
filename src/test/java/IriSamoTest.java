import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.testng.Assert;
import org.testng.annotations.Ignore;
import org.testng.annotations.Test;

import java.nio.file.WatchEvent;
import java.util.List;

public class IriSamoTest {

    //    TC_1_1  - Тест кейс:
    //    //1. Открыть страницу https://openweathermap.org/
    //    //2. Набрать в строке поиска город Paris
    //    //3. Нажать пункт меню Search
    //    //4. Из выпадающего списка выбрать Paris, FR
    //    //5. Подтвердить, что заголовок изменился на "Paris, FR"
    @Test(priority = 1)
    public void testH2TagText_WhenSearchingCityCountry() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String cityName = "Paris";
        String expectedResult = "Paris, FR";

        driver.get(url);

        Thread.sleep(5000);

        WebElement searchCity = driver.findElement(
                By.xpath("//input[@placeholder='Search city']")
        );

        Thread.sleep(1000);
        searchCity.click();
        searchCity.sendKeys(cityName);
        Thread.sleep(1000);
        WebElement buttonSearch = driver.findElement(
                By.xpath("//button[@type='submit']")
        );
        buttonSearch.click();
        Thread.sleep(1000);
        WebElement parisFRChoiceInDropdownMenu = driver.findElement(
                By.xpath("//ul[@class='search-dropdown-menu']//span[text()='Paris, FR ']")
        );
        parisFRChoiceInDropdownMenu.click();
        WebElement h2CityCountryHeader = driver.findElement(
                By.xpath("//div[@id=\"weather-widget\"]//h2")
        );
        Thread.sleep(1000);

        String actualResult = h2CityCountryHeader.getText();

        Assert.assertEquals(actualResult, expectedResult);

        driver.quit();
    }

    /**
     * TC_11_01
     * 1.  Открыть базовую ссылку
     * 2.  Нажать на пункт меню Guide
     * 3.  Подтвердить, что вы перешли на страницу со ссылкой https://openweathermap.org/guide
     * и что title этой страницы OpenWeatherMap API guide - OpenWeatherMap
     */
    @Test(priority = 2)
    public void testTabGuideUrlTitle() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String expectedTitle = "OpenWeatherMap API guide - OpenWeatherMap";
        String expectedUrl = "https://openweathermap.org/guide";

        driver.get(url);

        Thread.sleep(5000);

        WebElement tabGuide = driver.findElement(
                By.xpath("//a[@href='/guide']")
        );

        Thread.sleep(2000);
        tabGuide.click();
        String actualTitle = driver.getTitle();
        String actualUrl = driver.getCurrentUrl();

        Assert.assertEquals(actualTitle, expectedTitle);
        Assert.assertEquals(actualUrl, expectedUrl);

        driver.quit();
    }

    /**
     * TC_11_02
     * 1.  Открыть базовую ссылку
     * 2.  Нажать на единицы измерения Imperial: °F, mph
     * 3.  Подтвердить, что температура для города показана в Фарингейтах
     */

    @Test(priority = 3)
    public void testUnitOfMeasurementFahrenheit() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String expectedMeasurement = "°F";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement areaMeasurementImperial = driver.findElement(
                By.xpath("//div[@class='switch-container']/div[text()='Imperial: °F, mph']")
        );
        areaMeasurementImperial.click();

        Thread.sleep(2000);

        String  currentTemp = driver.findElement(
                By.xpath("//div[@class='current-temp']//span")
        ).getText();
        String actualMeasurement = currentTemp.substring(currentTemp.length()-2);

        Assert.assertEquals(actualMeasurement, expectedMeasurement);

        driver.quit();
    }

    /**
     * TC_11_03
     * 1.  Открыть базовую ссылку
     * 2. Подтвердить, что внизу страницы есть панель с текстом “We use cookies which are
     * essential for the site to work. We also use non-essential cookies to help us
     * improve our services. Any data collected is anonymised. You can allow all cookies
     * or manage them individually.”
     * 3. Подтвердить, что на панели внизу страницы есть 2 кнопки “Allow all” и “Manage cookies”
     */

    @Test(priority = 4)
    public void testFootContent() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver=new ChromeDriver(capabilities);

        String url = "https://openweathermap.org/";
        String expectedMessage = "We use cookies which are essential for the site to work. "
                + "We also use non-essential cookies to help us improve our services. "
                + "Any data collected is anonymised. You can allow all cookies or manage "
                + "them individually.";
        int expectedQuantityButtons = 2;


        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement panelMessage = driver.findElement(
                By.xpath("//*[@id=\"stick-footer-panel\"]//p")
        );
        String actualText = panelMessage.getText();

//        List<WebElement> btn = driver.findElements(
//                By.xpath("//div/*[@class='stick-footer-panel__link']")
//        );
//        int actualQuantityButtons = btn.size();
        int btn = driver.findElements(
                By.xpath("//div/*[@class='stick-footer-panel__link']")
        ).size();


        Assert.assertEquals(btn, expectedQuantityButtons);
        Assert.assertEquals(actualText, expectedMessage);

        driver.quit();
    }

    /**
     * TC_11_04
     * 1.  Открыть базовую ссылку
     * 2.  Подтвердить, что в меню Support есть 3 подменю с названиями
     * “FAQ”, “How to start” и “Ask a question”
     */
    @Test(priority = 5)
    public void testMenuTabSupport() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        int expectedQuantityLines = 3;
        String expectedDropMenuFirstLine = "FAQ";
        String expectedDropMenuSecondLine = "How to start";
        String expectedDropMenuLastLine = "Ask a question";

        driver.get(url);

        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement tabSupport = driver.findElement(By.id("support-dropdown"));
        tabSupport.click();

//        List<WebElement> lines = driver.findElements(
//                By.xpath("//*[@id=\"support-dropdown-menu\"]/li/a")
//        );
//        int actualQuantityLines = lines.size();

        int lines = driver.findElements(
                By.xpath("//*[@id=\"support-dropdown-menu\"]/li/a")
        ).size();


        WebElement dropMenuFirstLine = driver.findElement(
                By.xpath("//div/ul/li/ul//li/a[@href='/faq']")
        );
        String actualDropMenuFirstLine = dropMenuFirstLine.getText();
        WebElement dropMenuSecondLine = driver.findElement(
                By.xpath("//div/ul/li/ul//li/a[@href='/appid']")
        );
        String actualDropMenuSecondLine = dropMenuSecondLine.getText();
        WebElement dropMenuLastLine = driver.findElement(
                By.xpath("//div/ul/li/ul//li/a[@href='https://home.openweathermap.org/questions']")
        );
        String actualDropMenuLastLine = dropMenuLastLine.getText();

        Assert.assertEquals(lines, expectedQuantityLines);
        Assert.assertEquals(actualDropMenuFirstLine, expectedDropMenuFirstLine);
        Assert.assertEquals(actualDropMenuSecondLine, expectedDropMenuSecondLine);
        Assert.assertEquals(actualDropMenuLastLine, expectedDropMenuLastLine);

        driver.quit();
    }

    @Test(priority = 6)
    public void testAskAQuestionWithoutCAPTCHA() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();
        String originalWindow = driver.getWindowHandle();
        String url = "https://openweathermap.org/";
        String expectedTextHelpBlock = "reCAPTCHA verification failed, please try again.";

        driver.get(url);

        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement tabSupport = driver.findElement(By.id("support-dropdown"));
        tabSupport.click();
        WebElement dropMenuLastLine = driver.findElement(
                By.xpath("//div/ul/li/ul//li/a[@href='https://home.openweathermap.org/questions']")
        );
        dropMenuLastLine.click();

        Thread.sleep(7000);

        for (String windowHandle : driver.getWindowHandles()) {
            if(!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        WebElement boxMail = driver.findElement(By.id("question_form_email"));
        boxMail.click();
        boxMail.clear();
        boxMail.sendKeys("test369852147IS@gmail.com");

        Thread.sleep(2000);

        WebElement boxSubject = driver.findElement(By.id("question_form_subject"));
        boxSubject.click();

        Thread.sleep(2000);

        WebElement subjectOptionOther = driver.findElement(
                By.xpath("//*[@id=\"question_form_subject\"]//option[@value='Other']")
        );
        subjectOptionOther.click();

        Thread.sleep(2000);

        WebElement boxMessage = driver.findElement(By.id("question_form_message"));
        boxMessage.click();
        boxMessage.clear();
        boxMessage.sendKeys("question message");

        Thread.sleep(2000);

        WebElement buttonSubmit = driver.findElement(By.name("commit"));
        buttonSubmit.click();

        Thread.sleep(2000);

        WebElement helpBlock = driver.findElement(
                By.xpath("//*[@id=\"new_question_form\"]/div[9]/div[1]/div")
        );
        String actualTextHelpBlock = helpBlock.getText();

        Assert.assertEquals(actualTextHelpBlock, expectedTextHelpBlock);

        driver.quit();
    }

    /**
     * TC_11_06
     * 1. Открыть базовую ссылку
     * 2. Нажать пункт меню Support → Ask a question
     * 3. Оставить значение по умолчанию в checkbox Are you an OpenWeather user?
     * 4. Оставить пустым поле Email
     * 5. Заполнить поля  Subject, Message
     * 6. Подтвердить CAPTCHA
     * 7. Нажать кнопку Submit
     * 8. Подтвердить, что в поле Email пользователю будет показана ошибка “can't be blank”
     */
    @Ignore
    @Test
    public void testAskAQuestionWithEmptyMailBox() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");
        DesiredCapabilities capabilities = DesiredCapabilities.chrome();
        capabilities.setCapability(ChromeOptions.CAPABILITY, options);
        WebDriver driver=new ChromeDriver(capabilities);
        String originalWindow = driver.getWindowHandle();
        String url = "https://openweathermap.org/";
        String expectedTextHelpBlock = "can't be blank";

        driver.get(url);

        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement tabSupport = driver.findElement(By.id("support-dropdown"));
        tabSupport.click();
        WebElement dropMenuLastLine = driver.findElement(
                By.xpath("//div/ul/li/ul//li/a[@href='https://home.openweathermap.org/questions']")
        );
        dropMenuLastLine.click();

        Thread.sleep(7000);

        for (String windowHandle : driver.getWindowHandles()) {
            if (!originalWindow.contentEquals(windowHandle)) {
                driver.switchTo().window(windowHandle);
                break;
            }
        }

        Thread.sleep(2000);

        WebElement boxSubject = driver.findElement(By.id("question_form_subject"));
        boxSubject.click();

        Thread.sleep(2000);

        WebElement subjectOptionOther = driver.findElement(
                By.xpath("//*[@id=\"question_form_subject\"]//option[@value='Subscription']")
        );
        subjectOptionOther.click();

        Thread.sleep(2000);

        WebElement boxMessage = driver.findElement(By.id("question_form_message"));
        boxMessage.click();
        boxMessage.clear();
        boxMessage.sendKeys("question message");

//        WebElement recaptchaValidation = driver.findElement(
//                By.xpath("//span[@id='recaptcha-anchor']/div")
//        );
//        recaptchaValidation.click();
        WebElement recaptchaValidation = driver.findElement(
                By.xpath("//*[@id='recaptcha-anchor']/div[4]"));
        new Actions(driver)
                .moveToElement(recaptchaValidation)
                .perform();

        Thread.sleep(7000);

        WebElement buttonSubmit = driver.findElement(By.name("commit"));
        buttonSubmit.click();

        Thread.sleep(2000);

        WebElement helpBlock = driver.findElement(
                By.xpath("//form[@id=\"new_question_form\"]//span[@class='help-block']")
        );
        String actualTextHelpBlock = helpBlock.getText();

        Assert.assertEquals(actualTextHelpBlock, expectedTextHelpBlock);

        driver.quit();
    }

    /**
     * TC_11_07
     * 1.  Открыть базовую ссылку
     * 2.  Нажать на единицы измерения Imperial: °F, mph
     * 3.  Нажать на единицы измерения Metric: °C, m/s
     * 4.  Подтвердить, что в результате этих действий, единицы измерения температуры
     * изменились с F на С
     */
    @Test
    public void testChangeUnitOfMeasurement() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String expectedMeasurementF = "°F";
        String expectedMeasurementC = "°C";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement areaMeasurementImperial = driver.findElement(
                By.xpath("//div[@class='switch-container']/div[text()='Imperial: °F, mph']")
        );
        areaMeasurementImperial.click();

        Thread.sleep(2000);

        String  currentTemp = driver.findElement(
                By.xpath("//div[@class='current-temp']//span")
        ).getText();
        String actualMeasurementF = currentTemp.substring(currentTemp.length()-2);

        WebElement areaMeasurementMetric = driver.findElement(
                By.xpath("//div[@class='switch-container']/div[text()='Metric: °C, m/s']")
        );
        areaMeasurementMetric.click();

        currentTemp = driver.findElement(
                By.xpath("//div[@class='current-temp']//span")
        ).getText();
        String actualMeasurementC = currentTemp.substring(currentTemp.length()-2);


        Assert.assertEquals(actualMeasurementF, expectedMeasurementF);
        Assert.assertEquals(actualMeasurementC, expectedMeasurementC);

        driver.quit();
    }

    /**
     * TC_11_08
     * 1.  Открыть базовую ссылку
     * 2.  Нажать на лого компании
     * 3.  Дождаться, когда произойдет перезагрузка сайта, и подтвердить, что текущая
     * ссылка не изменилась
     */
    @Test
    public void testReloadMainPage() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";

        driver.get(url);
        driver.manage().window().maximize();

        Thread.sleep(5000);

        WebElement logo = driver.findElement(
                By.xpath("//ul[@id=\"first-level-nav\"]/li/a/img")
        );
        logo.click();

        Thread.sleep(5000);

        String  currentUrl = driver.getCurrentUrl();

        Assert.assertEquals(currentUrl, url);

        driver.quit();
    }

    /**
     * TC_11_09
     * 1.  Открыть базовую ссылку
     * 2.  В строке поиска в навигационной панели набрать “Rome”
     * 3.  Нажать клавишу Enter
     * 4.  Подтвердить, что вы перешли на страницу в ссылке которой содержатся слова “find” и “Rome”
     * 5. Подтвердить, что в строке поиска на новой странице вписано слово “Rome”
     */
    @Test
    public void testSearchingCityCountry() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        String cityName = "Rome";
        String expectedResult = "Rome";

        driver.get(url);

        Thread.sleep(5000);

        WebElement searchCity = driver.findElement(
                By.xpath("//div[@id='desktop-menu']/form/input[@name='q']")
        );

        Thread.sleep(1000);
        searchCity.click();
        searchCity.sendKeys(cityName);
        Thread.sleep(1000);
        searchCity.sendKeys(Keys.ENTER);
        Thread.sleep(1000);
        String actualUrl = driver.getCurrentUrl();
        WebElement searchBox = driver.findElement(By.id("search_str"));
        String actualCityInSearchBox = searchBox.getAttribute("value");

        Thread.sleep(1000);

        Assert.assertEquals(actualCityInSearchBox, expectedResult);
        Assert.assertTrue(actualUrl.contains("find") && actualUrl.contains("Rome"));

        driver.quit();
    }

    /**
     * TC_11_10
     * 1.  Открыть базовую ссылку
     * 2.  Нажать на пункт меню API
     * 3.  Подтвердить, что на открывшейся странице пользователь видит 30 оранжевых кнопок
     */
    @Test
    public void testSearchingButtons() throws InterruptedException {
        System.setProperty("webdriver.chrome.driver",
                "C:/Users/IVA/IdeaProjects/chromedriver/chromedriver_win32/chromedriver.exe");
        WebDriver driver = new ChromeDriver();

        String url = "https://openweathermap.org/";
        int expectedNumOrangeButton = 30;

        driver.get(url);

        Thread.sleep(5000);

        WebElement menuAPI = driver.findElement(
                By.xpath("//div[@id=\"desktop-menu\"]//a[@href='/api']")
        );
        menuAPI.click();

        Thread.sleep(1000);

        int actualNumOrangeButton = driver.findElements(
                By.xpath("//*[@class='btn_block orange round'] | //*[@class='ow-btn round btn-orange']")
        ).size();

        Assert.assertEquals(expectedNumOrangeButton, actualNumOrangeButton);

        driver.quit();
    }

}
