package scenario;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utility.CommonUtil;

public class HiragaScenarioTest extends TestBase {

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
  public void scenarioTest001() {
    // 1.ログイン
    // 1-1 メールアドレス入力

    // 1-2 パスワード入力

    // 1-3 ログインボタン押下
    login("admin@jpd.co.jp", "test");

    // トップページに遷移したことを確認
    assertText("/html/body/div[2]/div[2]/div/div[1]/h3", "お知らせ");

    // 2.必須項目情報のみで社員登録
    // 2-1 従業員情報ボタン押下
    click("//button[contains(text(), \"従業員情報\")]");

    // 従業員情報一覧画面に遷移したことを確認
    assertPageTitle("/html/body/div[2]/div[1]/h2", "従業員情報一覧");
    // 2-2 新規登録ボタン押下
    click("//*[contains(@value, \"新規登録\")]");
    // 従業員情報登録画面に遷移したことを確認
    assertPageTitle("/html/body/div[2]/h2", "新規従業員登録");
    // 2-3-1 氏名入力

    // 2-3-2 メールアドレス入力

    // 2-3-3 ランク選択

    // 2-3-4 パスワード入力

    // 2-3-5 パスワード（確認用）入力

    // 2-3-6 登録ボタン押下
    inputEmployeeInfo("1000", "cicd@jpd.co.jp");


    // 従業員詳細画面が表示されることを確認
    assertPageTitle("/html/body/div[2]/div[1]/h2", "従業員情報詳細");


    // MSG-I-EMP-001が表示されることを確認
    assertMessageBar("従業員情報の登録が完了しました。");

    // 入力された内容が表示されることを確認
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[1]/td", "1000");
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[2]/td", "日本プロ太");
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[3]/td", "cicd@jpd.co.jp");
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[4]/td", "男");
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[5]/td", "2022/04/01");
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[6]/td", "2025/04/01");
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[7]/td", "2002/05/04");
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[8]/td", "AS2");
    assertText("/html/body/div[2]/div[3]/table/tbody/tr[9]/td", "ICTソリューション事業部");

    // 3.登録した社員を削除
    // 3-1 削除ボタン押下
    click("//button[contains(text(), \"削除\")]");

    // 削除確認ダイアログが表示されることを確認
    assertText("//*[@id=\"deleteModal\"]/div/div/div[1]/h5", "削除確認");

    // 確認メッセージが正しいことを確認
    assertText("//*[@id=\"deleteModal\"]/div/div/div[2]/p[1]", "以下の従業員情報を削除してもよろしいでしょうか。");
    assertText("//*[@id=\"deleteModal\"]/div/div/div[2]/p[2]/strong", "1000：日本プロ太");

    // 3-2 削除ボタン押下
    click("//*[@id=\"deleteModal\"]/div/div/div[3]/form/button[1]");


    // 従業員情報一覧画面が表示されることを確認
    assertText("/html/body/div[2]/div[1]/h2", "従業員情報一覧");

    // MSG-I-EMP-003が表示されることを確認
    assertText("/html/body/div[1]/div/div/div[3]/span", "従業員情報の削除が完了しました。");

    // 4.ログアウト
    // 4-1 グローバルナビからログアウトを選択
    click("//button[contains(text(), \"ログイン\")]");
    // 5.ログアウト
    click("//button[contains(text(), \"ログアウト\")]");

    // ログイン画面に遷移することを確認
    assertText("/html/body/div[2]/div/div/p", "ログアウトしました");

  }
}
