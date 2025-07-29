package scenario;

import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import utility.CommonUtil;

public class ArigaTest extends TestBase {

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
	 * 各テスト後の処理
	 * テストがfailした場合、スクショをとる
	 * @param result
	 */
	@AfterMethod(alwaysRun = true)
	public void after(ITestResult result) {
		//failしたらスクショ
		if (result.getStatus() != ITestResult.SUCCESS) {
			CommonUtil.takeScreenShot(driver, ".\\result", result.getMethod().getMethodName());
		}
		//ブラウザを閉じる
		driver.quit();
	}
	
	/**
	 * 1.ログイン
	 * 2.必須項目情報のみで社員登録
	 * 3.登録した社員を削除
	 * 4.ログアウト
	 */
	@Test(groups = "scenario")
	public void scenarioTest001() {
	  //1.ログイン
	  //1-1 メールアドレス入力
	  
	  //1-2 パスワード入力
	  login("admin@jpd.co.jp", "test");
	  //1-3 ログインボタン押下
	  
	  //トップページに遷移したことを確認
		
	  //2.必須項目情報のみで社員登録
	  
	  //2-1 従業員情報ボタン押下
	  click("/html/body/div[2]/div[1]/div/div/form[3]/button");
	  // 従業員情報一覧画面に遷移したことを確認
	  
	  //2-2　新規登録ボタン押下
	  click("/html/body/div[2]/div[3]/form/input");
	  // 従業員情報登録画面に遷移したことを確認
	  inputEmployeeInfo("3000","test@jpd.co.jp");
	  //2-3-1 社員番号入力
//	  input("3000","//*[@id=\"employeeNo\"]");
	  //2-3-1 氏名入力
//	  input("テスト太郎","//*[@id=\"fullName\"]");
	  //2-3-2 メールアドレス入力
		
	  //2-3-3 ランク選択
		
	  //2-3-4 パスワード入力
		
	  //2-3-5 パスワード（確認用）入力
				
	  //2-3-6 登録ボタン押下
	  click("/html/body/div[2]/form/div[1]/input");
	  // 従業員詳細画面が表示されることを確認
//	  click("/html/body/div[2]/table/tbody/tr[1]/td[6]/div/form/input");
	  //　MSG-I-EMP-001が表示されることを確認
		
	  // 入力された内容が表示されることを確認

	  //3.登録した社員を削除
	  //3-1　削除ボタン押下
	  click("/html/body/div[2]/div[1]/div/button");
	  
	  // 削除確認ダイアログが表示されることを確認
	  click("//*[@id=\"deleteModal\"]/div/div/div[3]/form/button[1]");
	  // 確認メッセージが正しいことを確認
	
	  //3-2 削除ボタン押下
		
	  // 従業員情報一覧画面が表示されることを確認
		
	  //　MSG-I-EMP-003が表示されることを確認		

	  //4.ログアウト
	  //4-1　グローバルナビからログアウトを選択
	  click("/html/body/div[1]/div/div/div[2]/div/button");
	  System.out.println("ログアウト");
	  click("//*[@id=\"menu\"]/ul/form[2]/button");
	  //5.ログアウト
			
		// ログイン画面に遷移することを確認
	//1.ログイン
      
      // ホーム画面が開く
//      assertPageTitle("ホーム");
//
//      //2.必須項目情報のみで社員登録
//      // 新規登録ボタンを押す
//      click("//*[text()='従業員情報検索']");
//      // ページ確認
//      assertPageTitle("従業員情報検索");
//
//      // 新規登録画面を開く
//      click("//*[text()='新規登録']");
//      // ページ確認
//      assertPageTitle("従業員情報登録");
//      
//      //社員情報入力
//      final String employeeNum = "0001";
//      final String mailaddress = "Purota.NIHON@jpd.co.jp";
//      inputEmployeeInfo(employeeNum, mailaddress);
//      // 登録
//      click("//*[@id=\"upload\"]");
//      //メッセージバー確認
//      assertMessageBar("従業員情報の登録が完了しました。");
//      assertPageTitleDisplayedMessageBar("従業員情報詳細");
//
//      //3.登録した社員を削除
//      click("/html/body/div[2]/div[1]/button");
//      assertText("/html/body/div[2]/div[2]/div[1]/p[1]", "以下の従業員情報を削除してもよろしいでしょうか");
//      assertText("/html/body/div[2]/div[2]/div[1]/p[2]", "・" + "日本プロ太");
//      click("//*[@id=\"modalExecute\"]");
//      //メッセージバー確認
//      assertMessageBar("従業員情報の削除が完了しました。");
//      assertPageTitle("従業員情報検索");
//
//      //4.ログアウト
//      click("/html/body/header/nav/div/ul/li[4]/a");
//      click("//*[@id=\"glovalNaviLoginDropMenu2\"]/li[2]/form");
	}
}