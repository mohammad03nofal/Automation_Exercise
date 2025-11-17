package automationexercise;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

public class MyTestCases extends Data {
	
	
	@BeforeTest
	public void setup() throws SQLException
	{
		driver.get(HomePage);
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
		driver.manage().window().maximize();
		conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/classicmodels","root","0000");
	}
	
	@Test(priority=1)
	public void InsertRecord() throws SQLException
	{
		String query= "INSERT INTO customers (customerNumber, customerName, contactLastName,contactFirstName, phone, addressLine1, city ,state , Postalcode , country , salesRepEmployeeNumber, creditLimit) VALUE (185 ,'Khaled Nofal', 'Nofal', 'Khaled', '+1 416 555 9823', '123 King Abdullah St', 'Toronto', 'Toronto','6529271' ,'Canada', 1370, 50000.00)";
		
		stmt=conn.createStatement();
		int insertedRow=stmt.executeUpdate(query);
	}
	
	@Test(priority=3)
	public void ReadData() throws SQLException
	{
		String query="select * from customers where customerNumber='185'";
		stmt=conn.createStatement();
		rs=stmt.executeQuery(query);
		
		while(rs.next())
		{
			CustomerFirstName=rs.getString("contactFirstName").toString().trim();
			CustomerLastName=rs.getString("contactLastName").toString().trim();
			Email=CustomerFirstName+CustomerLastName+Domain;
			AddLine1=rs.getString("addressline1");
			ZipCode=rs.getString("Postalcode");
			CustomerCountryInDataBase=rs.getString("country");
			cityInDatabase=rs.getString("city");
			StateInDatabase=rs.getString("state");
			PhoneNumberInDatabase=rs.getString("phone");
		}
	}
	
   @Test(priority=2)
   public void Updatedata() throws SQLException
   {
	   String query ="update customers set addressLine1='1200 Bay Street'";
	   stmt=conn.createStatement();
	   int updatedrow=stmt.executeUpdate(query);
   }
   
   @Test(priority=4,enabled=true)
	public void DeleteRecord() throws SQLException
	{
		String query="delete  from customers where customerNumber=185 ";
		stmt=conn.createStatement();
		int deletedrow=stmt.executeUpdate(query);
	}
   
   @Test(priority=5,enabled=false)
   public void RegisterAccount() throws InterruptedException
   {
	   driver.navigate().to(SignUpPage);
	   WebElement Name=driver.findElement(By.name("name"));
	   WebElement email=driver.findElement(By.xpath("//input[@data-qa='signup-email']"));
	   WebElement signupbutton=driver.findElement(By.xpath("//button[@data-qa='signup-button']"));
	   Name.sendKeys(CustomerFirstName+" "+CustomerLastName);
	   email.sendKeys(Email);
	   Thread.sleep(2000);
	   signupbutton.click();
	   WebElement password=driver.findElement(By.id("password"));
	   WebElement Day=driver.findElement(By.id("days"));
	   Select MyDaySelection=new Select(Day);
	   MyDaySelection.selectByIndex(randomDay);
	   WebElement Month=driver.findElement(By.id("months"));
	   Select MyMonthSelection=new Select(Month);
	   MyMonthSelection.selectByIndex(randomMonth);

	   WebElement Year=driver.findElement(By.id("years"));
	   Select MyYearSelection=new Select(Year);
	   MyYearSelection.selectByIndex(randomYear);
	   Thread.sleep(2000);
	   WebElement FirstName=driver.findElement(By.id("first_name"));
	   WebElement LastName=driver.findElement(By.id("last_name"));
	   WebElement Company=driver.findElement(By.id("company"));
	   WebElement State=driver.findElement(By.id("state"));
	   WebElement street=driver.findElement(By.id("address1"));
	   WebElement address2=driver.findElement(By.id("address2"));
	   WebElement Country=driver.findElement(By.id("country"));
	   Select MyCountrySelection=new Select(Country);
	   MyCountrySelection.selectByContainsVisibleText(CustomerCountryInDataBase);
	   Thread.sleep(3000);
       WebElement City=driver.findElement(By.id("city"));
	   WebElement postcode=driver.findElement(By.id("zipcode"));
	   WebElement phoneNumber=driver.findElement(By.id("mobile_number"));
	   WebElement createAccountButton=driver.findElement(By.cssSelector(".btn.btn-default"));
	   
	   //Actions
	 
	   password.sendKeys(pass);
	   FirstName.sendKeys(CustomerFirstName);
	   LastName.sendKeys(CustomerLastName);
	   Company.sendKeys(comp);
	   street.sendKeys(AddLine1);
	   address2.sendKeys(AddLine2);
	   State.sendKeys(StateInDatabase);
	   City.sendKeys(cityInDatabase);
	   postcode.sendKeys(ZipCode);
	   phoneNumber.sendKeys(PhoneNumberInDatabase);
	   createAccountButton.click();
	   Thread.sleep(2000);
	   boolean actualResult=driver.getPageSource().contains("Your new account has been successfully created!");
	   Assert.assertEquals(actualResult, true);      
   }
   
   @Test(priority=6,enabled=false)
   public void LogoutTest()
   {
	   driver.navigate().to(HomePage);
	   WebElement logoutbutton=driver.findElement(By.xpath("//a[@href='/logout']"));
	   logoutbutton.click();
	   
   }
   
   @Test(priority=7)
   public void LoginTest() throws InterruptedException
   {
	   driver.navigate().to(LoginPage);
	   WebElement email=driver.findElement(By.xpath("//input[@data-qa='login-email']"));
	   WebElement loginpassword=driver.findElement(By.xpath("//input[@data-qa='login-password']"));
	   WebElement Loginbutton=driver.findElement(By.xpath("//button[@data-qa='login-button']"));
	   email.sendKeys(Email);
	   loginpassword.sendKeys(pass);
	   Thread.sleep(2000);
	   Loginbutton.click();
	   Thread.sleep(2000);
	   String expectedmessage="Logged in as";
	   boolean actualResult=driver.getPageSource().contains(expectedmessage);
	   Assert.assertEquals(actualResult, true);
   }
   
   @Test(priority=8)
   public void SearchProduct() throws InterruptedException
   {
	   driver.navigate().to(productsPage); 
	   WebElement searchProduct=driver.findElement(By.id("search_product"));
	   List <WebElement> itemNames=driver.findElements(By.tagName("p"));
	    for (int i = 2; i < itemNames.size(); i += 2) {
	        productIndexes.add(i);
	    }
	    int randomproduct=rand.nextInt(productIndexes.size());
	   String product=itemNames.get(randomproduct).getText();
	   Thread.sleep(2000);
	   searchProduct.sendKeys(product);
	   WebElement searchSubmit=driver.findElement(By.id("submit_search"));
	   searchSubmit.click();   
   }
   
   @Test(priority=9,enabled=true)
   public void AddRandomProductTest()
   {
	   driver.navigate().to(HomePage);
	   
	   for (int i = 1; i <=3; i++) 
	   {  
		List <WebElement> Products = driver.findElements(By.cssSelector(".fa.fa-plus-square"));
		int RandomProduct = rand.nextInt(Products.size());
	   Products.get(RandomProduct).click();
	   boolean StockOut=driver.getPageSource().contains("Out Of Stock");
	   if(!StockOut)
	   {
	   WebElement AddTocartbtn=driver.findElement(By.cssSelector(".btn.btn-default.cart"));
	   AddTocartbtn.click();
	   }
	   driver.navigate().back();
	   }   
   }
   
   
   
   @Test(priority=10,enabled=true)
   public void CheckoutTest() throws InterruptedException
   {
	  driver.navigate().to(CartPage); 
	  WebElement proceedtocheckout=driver.findElement(By.cssSelector(".btn.btn-default.check_out"));
	  proceedtocheckout.click();
	  WebElement PlaceOrder=driver.findElement(By.cssSelector(".btn.btn-default.check_out"));
	  PlaceOrder.click();
	  WebElement CardName=driver.findElement(By.name("name_on_card"));
	  WebElement CardNumber=driver.findElement(By.name("card_number"));
	  String Card_Name=CustomerFirstName+" "+CustomerLastName;
	  CardName.sendKeys(Card_Name);
	  CardNumber.sendKeys(cardnum);
	  driver.findElement(By.name("cvc")).sendKeys("311");
	  driver.findElement(By.name("expiry_month")).sendKeys("12");
	  driver.findElement(By.name("expiry_year")).sendKeys("2025");
      WebElement payconfirm=driver.findElement(By.xpath("//button[@data-qa='pay-button']"));
      payconfirm.click();
      Thread.sleep(2000);
      boolean actualResult=driver.getPageSource().contains("Your order has been confirmed!");
      Assert.assertEquals(actualResult, true);  
   }

	
   
   
}
