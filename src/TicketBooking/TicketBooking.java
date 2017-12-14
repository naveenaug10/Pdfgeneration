package TicketBooking;

import java.awt.AWTException;
import java.util.Properties;
import java.util.concurrent.TimeUnit;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.SkipException;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Listeners;
import org.testng.annotations.Test;

import reporter.JyperionListener;

//Add listener for pdf report generation
@Listeners(JyperionListener.class)
public class TicketBooking extends BaseClass {

	WebDriver driver;
	//Testcase failed so screen shot generate
@BeforeTest

	  public void Loginbrowsers() throws AWTException, InterruptedException {
		  System.setProperty("webdriver.chrome.driver", "src/chromedriver.exe");
driver=new ChromeDriver();
driver.manage().window().maximize();
driver.get("http://newtours.demoaut.com");
	  }
	
	//User should be able to book a return ticket successfully by logging into application
	@Test(priority=0)
	  public void LoginValidation() 
	  {
		  String Expected="Welcome: Mercury Tours";
		  String ActualTitle=driver.getTitle();
		  	if (Expected.equals(ActualTitle)) 
		  		{
		  			System.out.println("The titles are same....");
		  		}
		  	else 
		  		{
		  			throw new SkipException("The titles are not same");
		  		}
		  driver.findElement(By.name("userName")).sendKeys("sepusers12");
		  driver.findElement(By.name("password")).sendKeys("test@123");
		  driver.findElement(By.name("login")).click();
		  driver.close();
	  }	    
	
	//Testcase for Validate the credentials of the user on the application
	@Test (priority=1)
	  public void BookReturnticket() {
	driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[4]/td[2]/select/option[6]")).click();
	driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[10]/td[2]/select/option[3]")).click();
		  driver.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
		  driver.findElement(By.name("findFlights")).click();
		  driver.findElement(By.name("reserveFlights")).click();
		  driver.findElement(By.name("passFirst0")).sendKeys("Rahul");
		  driver.findElement(By.name("passLast0")).sendKeys("Mishra");
		  driver.findElement(By.xpath("/html/body/div/table/tbody/tr/td[2]/table/tbody/tr[4]/td/table/tbody/tr/td[2]/table/tbody/tr[5]/td/form/table/tbody/tr[4]/td/table/tbody/tr[2]/td[3]/select/option[4]")).click();
		  driver.findElement(By.name("creditnumber")).sendKeys("411111111111111");
		  driver.findElement(By.name("buyFlights")).click();
		  String Confirmationtext=driver.getTitle();
		  System.out.println(Confirmationtext);
		  driver.close();
		  
	  }
	
	
	//After complete execution send pdf report by email
	@AfterSuite
	public void tearDown(){
		sendPDFReportByGMail("m1031923@mindtree.com", "N1v33n@10", "naveenkumar.sampath@adc.mindtree.com", "Flight Booking Ticket Confirmation Mail", "");
	}
	
	private static void sendPDFReportByGMail(String from, String pass, String to, String subject, String body) {
        Properties props = System.getProperties();
        //String host = "smtp.gmail.com";
        String host = "smtp-mail.outlook.com";
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", from);
        props.put("mail.smtp.password", pass);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        Session session = Session.getDefaultInstance(props);
        MimeMessage message = new MimeMessage(session);

        try {
        	//Set from address
            message.setFrom(new InternetAddress(from));
             message.addRecipient(Message.RecipientType.TO, new InternetAddress(to));
           //Set subject
            message.setSubject(subject);
            message.setText(body);
          
            BodyPart objMessageBodyPart = new MimeBodyPart();
            
            objMessageBodyPart.setText("Hi Buddy, "
            		+ "Have a Good Day. "
            		+ "Your Ticket Booking transaction was success. Refer the PDF file for More Details");
            
            Multipart multipart = new MimeMultipart();

            multipart.addBodyPart(objMessageBodyPart);

            objMessageBodyPart = new MimeBodyPart();

            //Set path to the pdf report file
            String filename = System.getProperty("user.dir")+"\\ticketbooking.pdf"; 
            //Create data source to attach the file in mail
            DataSource source = new FileDataSource(filename);
            
            objMessageBodyPart.setDataHandler(new DataHandler(source));

            objMessageBodyPart.setFileName(filename);

            multipart.addBodyPart(objMessageBodyPart);

            message.setContent(multipart);
            Transport transport = session.getTransport("smtp");
            transport.connect(host, from, pass);
            transport.sendMessage(message, message.getAllRecipients());
            transport.close();
        }
        catch (AddressException ae) {
            ae.printStackTrace();
        }
        catch (MessagingException me) {
            me.printStackTrace();
        }
    }
}
	

