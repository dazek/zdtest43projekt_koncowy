import net.bytebuddy.asm.Advice;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.pagefactory.ByAll;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class FirstTests {

    WebDriver driver; //utworzenie pustego pola driver,aby było dostępne we wszystkich metodach testowych
    WebDriverWait wait;

    public void highlightElement(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("arguments[0].setAttribute('style', 'background: yellow; border: 2px solid red;');", element);
    }

    @Before //warunki początkowe testów, wykona się przed kazdą metodą
    public void setup() {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        //ustawiamy property ze wskazaniem na chromedriver, którego użyjemy w testach
        driver = new ChromeDriver(); // otworzy nam przeglądarke
        System.out.println("wykonuje sie tutaj! przed metoda testowa");
        wait = new WebDriverWait(driver, 10);
    }

    @Test//kroki testowe -po prostu test do wykonania
    public void firstTest() {
        driver.get("https://dev.to"); //przejdz na strone dev.to
        WebElement sideBarVideo = driver.findElement(By.xpath("/html/body/div[9]/div/div/div[1]/aside/nav[1]/div/a[3]")); // znajdz element video z sekcji sidebar
        highlightElement(sideBarVideo);
        //sideBarVideo.click();
    }

    @Test
    public void openFirstVideoPage() {
        driver.get("https://dev.to");
        WebElement sideBarVideo = driver.findElement(By.partialLinkText("Videos"));
        highlightElement(sideBarVideo);
        sideBarVideo.click();
        //przechodzimy na strone z video
        //powinnismy poczekac na zaladowanie nowej strony

        wait.until(ExpectedConditions.urlToBe("https://dev.to/videos"));
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        highlightElement(firstVideo);
        firstVideo.click();
    }

    @Test
    public void higlihtFirstVideo() {
        driver.get("https://dev.to/videos");
        WebElement firstVideo = driver.findElement(By.className("video-image"));
        highlightElement(firstVideo);
        firstVideo.click();
    }

    //wejdz na strone dev.to
    //kliknac w podcasts
    //wybrac pierwszy podcasts-pobiore nazwe pierwszego podcastu z listy
    //sprawdzic czy jestem na odpowiedniej stronie -czy tytul podcastu sie zgadza
    //sprawdzic czy moge nacisnac play
    @Test
    public void selectFirstPodcast() {
        driver.get("https://dev.to");
        WebElement podcasts = driver.findElement(By.partialLinkText("Podcasts"));
        podcasts.click();
        wait.until(ExpectedConditions.urlToBe("https://dev.to/pod"));
        WebElement firstPodcast = driver.findElement(By.cssSelector(".content > h3:first-child"));
        String podcastTitleFromList = firstPodcast.getText();

        String firstPodcastFromListLink = driver.findElement(By.cssSelector("#substories > a:first-child")).getAttribute("href");

        firstPodcast.click();
        wait.until(ExpectedConditions.urlToBe(firstPodcastFromListLink));
        WebElement podcastTitle = driver.findElement(By.cssSelector(".title > h1:nth-child(2)"));
        String podcastTitleText = podcastTitle.getText();
        assertTrue(podcastTitleFromList.contains(podcastTitleText));
        WebElement record = driver.findElement(By.className("record"));
        record.click();
        WebElement initializing = driver.findElement(By.className("status-message"));

        wait.until(ExpectedConditions.invisibilityOf(initializing));

        WebElement recordWrapper = driver.findElement(By.className("record-wrapper"));
        String classAttribute = record.getAttribute("class");
        Boolean isPodcastPlayed = recordWrapper.getAttribute("class").contains("playing");

        assertTrue(isPodcastPlayed);


    }


}