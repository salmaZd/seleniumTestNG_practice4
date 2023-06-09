import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

public class SmartBuy_Test1 {

	public WebDriver driver;
	public int numOfTry = 1000;
	public int inventoryItems = 6;
	SoftAssert softassert = new SoftAssert();

	@Test
	public void Test_add_SAMSUNG_50_inch() {

		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));

		for (int i = 0; i < numOfTry; i++) {

			if (i == inventoryItems) {
				numOfTry = i;
				break;

			}

			driver.findElement(By.xpath(
					"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[3]/div[1]/div/div/form[1]/div[1]/button"))
					.click();
			driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();

			String msg = driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/div[1]")).getText();

			if (msg.contains("Sorry")) {

				numOfTry = i;
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[1]")).click();

			} else {
				driver.findElement(By.xpath("//*[@id=\"addToCartLayer\"]/a[2]")).click();

			}
		}

	}

	@Test
	public void check_the_price() {

		driver.navigate().back();

		String single_item_price = driver
				.findElement(By.xpath(
						"//*[@id=\"newtab-Featured\"]/div/div[1]/div/div/div/div[2]/div/div[2]/div[2]/div/div/span[3]"))
				.getText();

		String[] updated_singe_item_price = single_item_price.split("JOD");
		String final_single_item_price = updated_singe_item_price[0].trim();
//		System.out.println(single_item_price);
		System.out.println("===========================");
		System.out.println(final_single_item_price);
		System.out.println("===========================");

		String updated = final_single_item_price.replace(",", "");

//		String rr = "1,111.0";
//		String newrr = rr.replace(",", "");
//		double dd = Double.parseDouble(newrr);
//		System.out.println(dd);

		Double final_price = Double.parseDouble(updated);
		System.out.println(final_price);
		System.out.println(final_price * numOfTry);

		String actualTitle = driver.getTitle();
		softassert.assertEquals(actualTitle, "SmartBuy | Electronics Store for Online Shopping in Jordan", "whyyyyy");

		softassert.assertEquals(final_price * numOfTry, 1500.0);

		softassert.assertAll();

	}

	@BeforeTest
	public void beforeTest() {

		WebDriverManager.chromedriver().setup();
		driver = new ChromeDriver();
		driver.manage().window().maximize();
		driver.get("https://smartbuy-me.com/smartbuystore/");
		driver.findElement(By.xpath("/html/body/main/header/div[2]/div/div[2]/a")).click();

	}

}
