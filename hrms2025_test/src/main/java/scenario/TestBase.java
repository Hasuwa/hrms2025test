package scenario;

import static org.testng.Assert.*;
import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class TestBase {

  WebDriver driver;
  Duration waitTime;
  WebDriverWait wait;

  // アプリのパス
  static final String SHINABLE_CLOUD_PATH = "http://localhost:8081/hrms2025/login?logout";

  /**
   * 入力
   * 
   * @param xpath
   * @param data
   */
  public void input(String xpath, String data) {
    waitForElementVisible(xpath);
    driver.findElement(By.xpath(xpath)).sendKeys(data);
  }

  /**
   * クリック
   * 
   * @param xpath
   */
  public void click(String xpath) {
    waitForElementVisible(xpath);
    WebElement element = driver.findElement(By.xpath(xpath));

    try {
      element.click(); // 通常のクリック
    } catch (Exception e) {
      // JavaScriptで強制クリック
      JavascriptExecutor js = (JavascriptExecutor) driver;
      js.executeScript("arguments[0].click();", element);
    }

  }

  // public void click(String xpath) {
  // WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
  // WebElement element = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpath)));
  // element.click();
  // }


  /**
   * 表示待ち
   * 
   * @param xpath
   */
  public void waitForElementVisible(String xpath) {
    waitTime = Duration.ofSeconds(20);
    wait = new WebDriverWait(driver, waitTime);
    wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)));
  }

  /**
   * テキストの表示待ち
   * 
   * @param xpath
   */
  public void waitForElementTextChange(String xpath, String text) {
    waitTime = Duration.ofSeconds(20);
    wait = new WebDriverWait(driver, waitTime);
    wait.until(
        ExpectedConditions.textToBePresentInElement(driver.findElement(By.xpath(xpath)), text));
  }


  public void inputDate(String xpath, String dateValue) {
    waitForElementVisible(xpath);
    WebElement element = driver.findElement(By.xpath(xpath));
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("arguments[0].value = arguments[1];", element, dateValue);
  }


  /**
   * テキストの確認
   * 
   * @param xpath
   * @param name
   */
  public void assertText(String xpath, String text) {
    waitForElementVisible(xpath);
    assertEquals(driver.findElement(By.xpath(xpath)).getText(), text);
  }

  /**
   * ログイン
   * 
   * @param mailAddress
   * @param password
   */
  public void login(String mailAddress, String password) {
    final String MAILADDRESS_INPUT_XPATH = "//*[@id=\"usernameInput\"]";
    final String PASSWORD_INPUT_XPATH = "//*[@id=\"passwordInput\"]";
    final String LOGIN_BUTTON_XPATH = "/html/body/div[2]/div/form/div[3]/input";
    // input mailaddress
    input(MAILADDRESS_INPUT_XPATH, mailAddress);
    // input password
    input(PASSWORD_INPUT_XPATH, password);
    // click login button
    click(LOGIN_BUTTON_XPATH);
  }

  /**
   * 社員情報を入力
   * 
   * @param employeeNum
   * @param mailaddress
   */
  public void inputEmployeeInfo(String employeeNo, String mailAddress) {
    // input employee num
    input("//*[@id=\"employeeNo\"]", employeeNo);
    // input name
    input("//*[@id=\"fullName\"]", "日本プロ太");
    // input mailaddress
    input("//*[@id=\"mailAddress\"]", mailAddress);
    // select gender (男)
    click("//*[@id=\"gender1\"]");
    // input hiredate
    inputDate("//*[@id=\"hireDate\"]", "2025-03-29");
    // input birthdate
    inputDate("//*[@id=\"birthDate\"]", "2000-07-29");
    // select rank
    click("//*[@id=\"employeeRank\"]");
    click("//*[@id=\"employeeRank\"]/div[1]/option");
    // select organization
    click("/html/body/div[2]/form/table/tbody/tr[9]/td/button");
    // dialog
    click("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/button");
    click("//*[@id=\"de1c657b-6ab9-412b-8137-32d0496ad58a\"]/li[3]/button");
    click("//*[@id=\"0b9ff3de-5317-4944-9921-d77d23151ce3\"]/li/span");
    // // //入力出来ていない時があったのでリトライを入れる
    // // if (driver.findElement(By.xpath("//*[@id=\"organizationName\"]")).getText().equals(null))
    // {
    // // // select group
    // // click("//*[@id=\"open\"]");
    // // //dialog
    // // click("//*[@id=\"node0\"]");
    // // click("//*[@id=\"select\"]");
    // // }
    // input password
    input("//*[@id=\"password\"]", "test");
    input("//*[@id=\"confirmPassword\"]", "test");
  }

  /**
   * ページタイトルの確認
   * 
   * @param page title
   */
  public void assertPageTitle(String title) {
    waitForElementTextChange("/html/body/div[2]/div[1]/h2", title);
    ExpectedConditions.titleIs(title);
  }

  /**
   * ページタイトルの確認（メッセージバー表示時）
   * 
   * @param page title
   */
  public void assertPageTitleDisplayedMessageBar(String title) {
    waitForElementTextChange("/html/body/div[2]/div[1]/h1", title);
    ExpectedConditions.titleIs(title);
  }

  /**
   * メッセージバーの確認
   * 
   * @param message
   */
  public void assertMessageBar(String message) {
    waitForElementVisible("/html/body/div[1]/div/div/div[3]");
    assertText("/html/body/div[1]/div/div/div[3]/span", message);
  }
}
