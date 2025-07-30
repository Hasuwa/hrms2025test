package scenario;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import utility.CommonUtil;

// @Test
public class ScenarioTest extends TestBase {

  /**
   * Chromeでアプリを開く
   */
  @BeforeMethod(alwaysRun = true)
  public void before() {
    driver = new ChromeDriver();
    driver.get("http://localhost:81/hrms2025/login");
    driver.manage().window().maximize();
  }

  /**
   * 各テスト後の処理 テストがfailした場合、スクショをとる
   * 
   * @param result
   */
  @AfterMethod(alwaysRun = true)
  public void after(ITestResult result) {
    // failしたらスクショ
    if (result.getStatus() != ITestResult.SUCCESS) {
      CommonUtil.takeScreenShot(driver, ".\\result", result.getMethod().getMethodName());
    }
    // ブラウザを閉じる
    driver.quit();
  }

  /**
   * 1.ログイン 2.必須項目情報のみで社員登録 3.登録した社員を削除 4.ログアウト
   */
  public void scenarioTest001() {
    // 1.ログイン
    // 1-1 メールアドレス入力
    final String usernameInput = "admin@jpd.co.jp";
    input("//*[@id=\"usernameInput\"]", usernameInput);
    // 1-2 パスワード入力
    final String passwordInput = "test";
    input("//*[@id=\"passwordInput\"]", passwordInput);
    // 1-3 ログインボタン押下
    click("//*[@value='ログイン']");
    // トップページに遷移したことを確認
    WebDriverWait waitToppage = new WebDriverWait(driver, Duration.ofSeconds(10));
    waitToppage.until(ExpectedConditions.titleIs("ホーム"));
    // 2.必須項目情報のみで社員登録
    // 2-1 従業員情報ボタン押下
    click("//button[.//text()[contains(., '従業員情報')]]");
    // 従業員情報一覧画面に遷移したことを確認
    WebDriverWait waitList = new WebDriverWait(driver, Duration.ofSeconds(10));
    waitList.until(ExpectedConditions.titleIs("従業員情報一覧"));
    // 2-2 新規登録ボタン押下
    click("//input[@type='submit' and contains(@value, '新規登録')]");
    // 従業員情報登録画面に遷移したことを確認
    WebDriverWait waitEmployeeForm = new WebDriverWait(driver, Duration.ofSeconds(10));
    waitEmployeeForm.until(ExpectedConditions.titleIs("従業員フォーム"));
    // 2-3- 社員番号入力
    final String emlpoyeeNo = "2000";
    input("//*[@id=\"employeeNo\"]", emlpoyeeNo);
    // 2-3-1 氏名入力
    final String fullName = "利用者三郎";
    input("//*[@id=\"fullName\"]", fullName);
    // 2-3-2 メールアドレス入力
    final String mailAddress = "user1@jpd.co.jp";
    input("//*[@id=\"mailAddress\"]", mailAddress);
    // 2-3- 性別
    WebDriverWait waitGender = new WebDriverWait(driver, Duration.ofSeconds(10));
    WebElement genderMale = waitGender.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//input[@type='radio' and @value='1']")));
    genderMale.click();
    // 2-3- 入社日
    // 2-3- 退職日
    // 2-3- 生年月日
    JavascriptExecutor js = (JavascriptExecutor) driver;
    js.executeScript("document.getElementsByName('hireDate')[0].value = '2023-04-01';");
    js.executeScript("document.getElementsByName('quitDate')[0].value = '2025-03-31';");
    js.executeScript("document.getElementsByName('birthDate')[0].value = '1990-01-01';");
    // 2-3-3 ランク選択
    WebElement dropdown = driver.findElement(By.name("employeeRank"));
    Select select = new Select(dropdown);
    select.selectByVisibleText("AS0");
    // 2-3- 所属選択
    WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // 1. 打开“選択”按钮，打开 modal
    // 找到“選択”按钮
    WebElement selectButton = wait.until(
        ExpectedConditions.presenceOfElementLocated(By.xpath("//button[contains(text(), '選択')]")));

    // スクロール
    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
        selectButton);

    // クリックできるまで待つ
    wait.until(ExpectedConditions.elementToBeClickable(selectButton));

    // JavaScriptでクリック
    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", selectButton);


    // モーダルを表示されるまで待つ
    wait.until(ExpectedConditions.attributeContains(By.id("organizationModal"), "class", "show"));

    try {
      Thread.sleep(300);
    } catch (InterruptedException e) {
      e.printStackTrace(); // 或用 Logger 记录
    }


    // 本社を選択
    WebElement honsyaSpan = wait.until(ExpectedConditions.presenceOfElementLocated(
        By.xpath("//span[text()='本社' and @onclick='selectOrganization(this)']")));

    ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView({block: 'center'});",
        honsyaSpan);

    ((JavascriptExecutor) driver).executeScript("arguments[0].click();", honsyaSpan);

    // クリックしたことを確認
    WebElement hiddenInput = driver.findElement(By.id("selectedOrganizationId"));
    String selectedValue = hiddenInput.getAttribute("value");
    System.out.println("選択された所属ID: " + selectedValue);

    // 2-3-4 パスワード入力
    final String password = "P@ssw0rd";
    input("//*[@id=\"password\"]", password);
    // 2-3-5 パスワード（確認用）入力
    final String confirmPassword = "P@ssw0rd";
    input("//*[@id=\"confirmPassword\"]", confirmPassword);
    // 2-3-6 登録ボタン押下
    click("//input[@value='登録']");
    // 従業員詳細画面が表示されることを確認

    waitList.until(ExpectedConditions.titleIs("従業員詳細画面"));
    // MSG-I-EMP-001が表示されることを確認

    // 入力された内容が表示されることを確認
    // 3-4 該当従業員がテーブルに表示されていることを確認
    List<WebElement> existingUser =
        driver.findElements(By.xpath("//table//tr[td[contains(text(), '利用者三郎')]]"));

    // 要素存在 ⇒ 表示されている
    Assert.assertFalse(existingUser.isEmpty(), "従業員が表示されていません。");


    // 3.登録した社員を削除
    // 3-1 削除ボタン押下

    // 削除確認ダイアログが表示されることを確認

    // 確認メッセージが正しいことを確認

    // 3-2 削除ボタン押下

    // 従業員情報一覧画面が表示されることを確認
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // 3-1 「削除」ボタンを押下（詳細画面右上）
    WebElement deleteBtn = wait.until(ExpectedConditions
        .elementToBeClickable(By.cssSelector("button.btn-delete[data-bs-toggle='modal']")));
    deleteBtn.click();

    // 3-1-2 モーダルが表示されることを確認
    WebElement modal = wait
        .until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#deleteModal.show")));

    // 3-1-3 モーダルのタイトルを確認
    WebElement modalTitle = modal.findElement(By.cssSelector(".modal-title"));
    Assert.assertEquals(modalTitle.getText(), "削除確認");

    // 3-1-4 モーダルの本文メッセージが正しいことを確認（番号：氏名形式）
    WebElement confirmText = modal.findElement(By.cssSelector(".modal-body p strong"));
    String text = confirmText.getText();
    Assert.assertTrue(text.matches("\\d+：.+"), "削除確認メッセージのフォーマットが正しくありません：" + text);

    // 3-2 モーダル内の「削除」ボタンを押下
    WebElement modalDeleteBtn = modal.findElement(By.cssSelector(".modal-footer .btn-delete"));
    modalDeleteBtn.click();

    // 3-3 従業員情報一覧画面に戻ることを確認
    wait.until(ExpectedConditions.titleIs("従業員情報一覧"));

    // 3-4 該当従業員がテーブルに表示されないことを確認
    List<WebElement> deletedUser =
        driver.findElements(By.xpath("//table//tr[td[contains(text(), '利用者三郎')]]"));
    Assert.assertTrue(deletedUser.isEmpty(), "削除された従業員がまだ一覧に残っています。");


    // MSG-I-EMP-003が表示されることを確認

    // 4.ログアウト
    // 4-1 グローバルナビからログアウトを選択
    // 5.ログアウト

    // ログイン画面に遷移することを確認


    // WebDriverWaitの準備
    wait = new WebDriverWait(driver, Duration.ofSeconds(10));

    // 1. 右上のログインメニュー（▼ボタン）をクリックしてメニューを表示
    WebElement loginMenuBtn = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'ログイン：')]")));
    loginMenuBtn.click();

    // 2. 「ログアウト」ボタンを探してクリック
    WebElement logoutBtn = wait.until(
        ExpectedConditions.elementToBeClickable(By.xpath("//button[contains(text(), 'ログアウト')]")));
    logoutBtn.click();

    // 3. ログイン画面に遷移したことを確認
    wait.until(ExpectedConditions.titleIs("ログイン"));


  }
}
