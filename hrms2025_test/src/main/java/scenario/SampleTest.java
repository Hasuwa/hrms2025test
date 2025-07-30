package scenario;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utility.CommonUtil;

public class SampleTest extends TestBase {

  /**
   * Chromeでアプリを開く
   */
  @BeforeMethod(alwaysRun = true)
  public void before() {
    driver = new ChromeDriver();
    driver.get(SHINABLE_CLOUD_PATH);
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
  @Test(groups = "scenario")
  public void sampleTest001() {
    // 1.ログイン
    login("admin@jpd.co.jp", "test");
    // ホーム画面が開く
    // assertPageTitle("ホーム");

    // 2.必須項目情報のみで社員登録
    // 従業員情報一覧画面を開く
    click("/html/body/div[2]/div[1]/div/div/form[3]/button");
    // ページ確認
    assertPageTitle("従業員情報一覧");

    // 新規登録ボタンを押す
    click("/html/body/div[2]/div[3]/form/input");
    // ページ確認
    // assertPageTitle("新規従業員登録");

    // 社員情報入力
    final String employeeNo = "3333";
    final String mailAddress = "Purota.NIHON@jpd.co.jp";
    inputEmployeeInfo(employeeNo, mailAddress);

    // 登録
    click("/html/body/div[2]/form/div[1]/input");
    // メッセージバー確認
    assertMessageBar("従業員情報の登録が完了しました。");
    // assertPageTitleDisplayedMessageBar("従業員情報詳細");

    // 3.登録した社員を削除
    click("/html/body/div[2]/div[1]/div/button");
    assertText("//*[@id=\"deleteModal\"]/div/div/div[2]/p[1]", "以下の従業員情報を削除してもよろしいでしょうか。");
    // // assertText("/html/body/div[2]/div[2]/div[1]/p[2]", "・" + "日本プロ太");
    click("//*[@id=\"deleteModal\"]/div/div/div[3]/form/button[1]");
    // メッセージバー確認
    assertMessageBar("従業員情報の削除が完了しました。");
    // assertPageTitle("従業員情報一覧");

    // 4.ログアウト
    click("/html/body/div[1]/div/div/div[2]/div/button");
    click("//*[@id=\"menu\"]/ul/form[2]/button");
  }
}
