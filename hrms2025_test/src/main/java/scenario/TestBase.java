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
   * 編集可能まで待機
   * 
   * @param xpath
   */
  public void waitForElementEditable(String xpath) {
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(20));
    wait.until(driver -> {
      WebElement element = driver.findElement(By.xpath(xpath));
      return element.isDisplayed() && element.isEnabled()
          && !element.getAttribute("readonly").equals("true");
    });
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
  public void inputEmployeeInfo(String employeeNum, String mailaddress) {
    // input employee num
    input("//*[@id=\"employeeNo\"]", employeeNum);
    // input name
    input("//*[@id=\"fullName\"]", "日本プロ太");
    // input mailaddress
    input("//*[@id=\"mailAddress\"]", mailaddress);
    // 性別
    click("//*[@id=\"gender1\"]");
    // すくろーーーーーる
    scrollToElement("//*[@id=\"gender1\"]");
    // 入社日
    input("//*[@id=\"hireDate\"]", "0020220401");
    // 生年月日
    input("//*[@id=\"birthDate\"]", "0020020504");
    // select rank
    click("//*[@id=\"employeeRank\"]/div[3]/option");
    // click("//*[@class=\"dropdown-content select-dropdown\"]/li[2]");

    // 先パスワード
    input("//*[@id=\"password\"]", "P@ssw0rd");
    input("//*[@id=\"confirmPassword\"]", "P@ssw0rd");

    // select group
    click("//button[contains(text(), \"選択\")]");
    // dialog
    click("//*[@id=\"organizationModal\"]/div/div/div[2]/div/li[3]/button");
    click("//*[@id=\"86f4cc69-f748-44ef-a6db-6a4fc4f4b465\"]/li[3]/span");


    click("/html/body/div[2]/form/div[1]/input");

  }

  /**
   * スクロール
   * 
   * @param xpath
   */
  public void scrollToElement(String xpath) {
    WebElement element = driver.findElement(By.xpath(xpath));
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
  }


  /**
   * ページタイトルの確認
   * 
   * @param page title
   */
  public void assertPageTitle(String xpath, String title) {
    waitForElementTextChange(xpath, title);
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
    waitForElementVisible("/html/body/div[1]/div/div/div[3]/span");
    assertText("/html/body/div[1]/div/div/div[3]/span", message);
  }
}
