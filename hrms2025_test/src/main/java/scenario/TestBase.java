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
  static final String SHINABLE_CLOUD_PATH = "http://localhost:8081/hrms2025/login";

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
    driver.findElement(By.xpath(xpath)).click();
  }

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
    final String LOGIN_BUTTON_XPATH = "//*[@value=\"ログイン\"]";
    // input mailaddress
    input(MAILADDRESS_INPUT_XPATH, mailAddress);
    // input password
    input(PASSWORD_INPUT_XPATH, password);
    // click login button
    click(LOGIN_BUTTON_XPATH);
  }

  /**
   * 
   * @param elementId
   * @param dateValue
   */
  public void setDate(String elementId, String dateValue) {
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("document.getElementById('" + elementId + "').value='" + dateValue + "';");
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
    // select 性別
    click("//*[@id=\"gender1\"]");

    // 入社日
    setDate("hireDate", "2000-10-10");
    // 退職日
    setDate("quitDate", "3000-10-10");
    // 生年月日
    setDate("birthDate", "1000-10-10");

    // select rank
    click("//*[@id=\"employeeRank\"]");
    click("//*[@id=\"employeeRank\"]/div[1]/option");
    // select group

    WebElement button =
        driver.findElement(By.xpath("/html/body/div[2]/form/table/tbody/tr[9]/td/button"));
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", button);


    // dialog

    wait.until(ExpectedConditions.elementToBeClickable(
        By.xpath("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/button")));
    click("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[1]/span");

    // input password
    input("//*[@id=\"password\"]", "P@ssw0rd");
    input("//*[@id=\"confirmPassword\"]", "P@ssw0rd");
  }

  /**
   * ページタイトルの確認
   * 
   * @param page title
   */
  // public void assertPageTitle(String title) {
  // waitForElementTextChange("/html/head/title", title);
  // ExpectedConditions.titleIs(title);
  // }
  //
  /**
   * ページタイトルの確認
   * 
   * @param page title
   */
  public void assertPageTitle(String title) {
    waitTime = Duration.ofSeconds(20);
    wait = new WebDriverWait(driver, waitTime);
    wait.until(ExpectedConditions.titleIs(title));
    assertEquals(driver.getTitle(), title);
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
    waitForElementVisible("//*[@class=\"message-bar\"]");
    assertText("//*[@class=\"message-bar\"]/span", message);
  }
}
