package automationexercise;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Random;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;

public class Data {

	WebDriver driver=new EdgeDriver();
	Connection conn;
	Statement stmt;
	ResultSet rs;
	String HomePage="https://automationexercise.com/";
	String SignUpPage="https://automationexercise.com/signup";
	String LoginPage="https://automationexercise.com/login";
	String CustomerFirstName;
	String CustomerLastName;
	String Domain="@gmail.com";
	Random rand=new Random();
	int RandomNumberForTheEmail=rand.nextInt(27634);
	int randomYear = rand.nextInt(1,120);
    int randomMonth = rand.nextInt(1,13);
    int randomDay = rand.nextInt(1,31);
	String Email;
	String pass="M@k20031312";
	String PhoneNumberInDatabase;
	String AddLine1;
	String AddLine2="Amman";
	String CustomerCountryInDataBase;
	String ZipCode;
	String comp="ProgressSoft";
	String StateInDatabase;
	String cityInDatabase;
}
