import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;




import org.apache.poi.xssf.eventusermodel.XSSFReader;
import org.apache.poi.xssf.model.SharedStringsTable;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.xml.sax.Attributes;
import org.xml.sax.ContentHandler;
import org.xml.sax.InputSource;
import org.xml.sax.SAXException;
import org.xml.sax.XMLReader;
import org.xml.sax.helpers.DefaultHandler;
import org.xml.sax.helpers.XMLReaderFactory;

import java.util.Iterator;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.util.List;


import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.NicelyResynchronizingAjaxController;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlButton;
import com.gargoylesoftware.htmlunit.html.HtmlDivision;
import com.gargoylesoftware.htmlunit.html.HtmlElement;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;
import com.gargoylesoftware.htmlunit.javascript.host.file.File;

//import jxl.Sheet;
//import jxl.Workbook;
//import jxl.write.*;
//import jxl.write.Number;







public class ScrapeST {

	public static void main(String[] args) throws EncryptedDocumentException, InvalidFormatException {

		//final WebClient webClient = new WebClient(BrowserVersion.CHROME);
		try {
/*
			webClient.getOptions().setJavaScriptEnabled(true);
			webClient.getCookieManager().setCookiesEnabled(true);
			webClient.waitForBackgroundJavaScript(5*60*1000);
            webClient.waitForBackgroundJavaScriptStartingBefore(5*60*1000);
			webClient.getOptions().setThrowExceptionOnScriptError(false);
			webClient.setAjaxController(new NicelyResynchronizingAjaxController());

			HtmlPage myPage = ((HtmlPage) webClient.getPage("https://mentor.socialtalent.co/panel/secure/login?ref=panel"));*/

//			HtmlElement usrname = myPage.getElementByName("username");
//		    usrname.click();
//		    usrname.type("vinodkatta3@gmail.com");
//
//		    HtmlElement psswrd = myPage.getElementByName("password");
//		    psswrd.click();
//		    psswrd.type("socialtalent5229");
//
//		    HtmlElement button = myPage.getElementByName("login");
//
//		    HtmlPage page2 = button.click();
//		    //System.out.println(page2.getWebResponse().getContentAsString());
//
//
//
//		    List<HtmlForm> forms = page2.getForms();
//		    //System.out.println(forms.size());
///*		    for(int i=0;i<forms.size();i++)
//		    {
//		    	 System.out.println(forms.get(i));
//		    	 System.out.println("********************");
//		    }*/
//		    HtmlForm form=forms.get(1);

			String inputfilepath = "phrases_45k_58k.xlsx";//C:\\Users\\Vinod\\Desktop\\phrases_15k.xlsx";
			InputStream inp = new FileInputStream(inputfilepath);
			//String outputfilepath = "C:\\Users\\Vinod\\Desktop\\outputphrases.xlsx";

//		    Workbook workbook = null;
//		    workbook=Workbook.getWorkbook(file);
//		    Sheet sheet = workbook.getSheet(0);
//		    Cell cell1 = (Cell) sheet.getCell(0, 0);
//		    System.out.print(cell1.ge + ":");
//		    Workbook wb = new XSSFWorkBook();
//		    Sheet sheet = (Sheet) wb.getSheetAt(0);
//		    int rowscount = sheet.getLastRowNum();
//		    System.out.println("no. of rows in excel: "+rowscount);

			Workbook wb = new XSSFWorkbook(inp);
			Sheet sheet = wb.getSheetAt(0);
			Workbook wbOutput = new XSSFWorkbook();
			Sheet sheetOutput=wbOutput.createSheet();
			ExecutorService executor = Executors.newFixedThreadPool(16);
			for (int row = 0; row < sheet.getLastRowNum(); row++) {
				//System.out.println(sheet.getRow(7).getCell(1).getNumericCellValue());
				Runnable scrapper = new MultithreadScrape(sheet.getRow(row).getCell(0).getStringCellValue(),sheet.getRow(row).getCell(1).getNumericCellValue(),sheetOutput,wbOutput);//,wb
				executor.execute(scrapper);//calling execute method of ExecutorService
			}
			executor.shutdown();
			while (!executor.isTerminated()) {   }
			wb.close();
			wbOutput.close();






//		    HtmlTextInput titles = form.getInputByName("jobtitles");
//		    HtmlTextInput skills = form.getInputByName("skills");
//		    HtmlTextInput cities = form.getInputByName("cities");
//		    HtmlButton b = form.getButtonByName("submit");
//
//		    titles.setValueAttribute("sales");
//		    skills.setValueAttribute("java");
//		    cities.setValueAttribute("San Jose");
//		    HtmlPage page3;
//		    page3 = b.click();
//
//		    List<HtmlDivision> text = page3.getByXPath("//div[@class='item-string']");
//		    System.out.println(text.get(0).asText());

		} catch (FailingHttpStatusCodeException e) {

			e.printStackTrace();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally
		{
			//webClient.close();
		}


	}

}
class MultithreadScrape implements Runnable{
	
	String phrase;
	Double occurance;
	Sheet sheetOutput;
	Workbook wbOutput;
	public MultithreadScrape(String phrase,Double occurance,Sheet sheet,Workbook wbOutput) { //,Workbook wb
		// TODO Auto-generated constructor stub
		//this.page=page;
		this.phrase=phrase;
		this.occurance=occurance;
		this.sheetOutput=sheet;
		this.wbOutput=wbOutput;
	}

	public void run() {
		// TODO Auto-generated method stub
		try {
			this.performscrape( this.phrase, this.occurance, this.sheetOutput); //,this.wb
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void performscrape(String phrase,Double occurance, Sheet sheetOutput) throws IOException, Exception{//,Workbook wb
        final WebClient webClient = new WebClient(BrowserVersion.CHROME);
        webClient.getOptions().setJavaScriptEnabled(true);
        webClient.getCookieManager().setCookiesEnabled(true);
        webClient.waitForBackgroundJavaScript(5*60*1000);
        webClient.waitForBackgroundJavaScriptStartingBefore(5*60*1000);
        webClient.getOptions().setThrowExceptionOnScriptError(false);
        webClient.setAjaxController(new NicelyResynchronizingAjaxController());

        HtmlPage myPage = ((HtmlPage) webClient.getPage("https://mentor.socialtalent.co/panel/secure/login?ref=panel"));
        
        System.out.println("**"+Thread.currentThread().getId()+"**Enter");
		System.out.println("Phrase================="+phrase+"====");
		
		HtmlElement usrname = myPage.getElementByName("username");
		usrname.click();
		usrname.type("vinodkatta3@gmail.com");

		HtmlElement psswrd = myPage.getElementByName("password");
		psswrd.click();
		psswrd.type("socialtalent5229");

		HtmlElement button = myPage.getElementByName("login");

		HtmlPage page2 = button.click();
		
		//Thread.sleep(1000);
		
        List<HtmlForm> forms = page2.getForms();
		HtmlForm form=forms.get(1);

		HtmlTextInput titles = form.getInputByName("jobtitles");
//	    HtmlTextInput skills = this.form.getInputByName("skills");
//	    HtmlTextInput cities = this.form.getInputByName("cities");
		HtmlButton b = form.getButtonByName("submit");

		titles.setValueAttribute(phrase);
//	    skills.setValueAttribute("java");
//	    cities.setValueAttribute("San Jose");
		HtmlPage page3;
		try {
			page3 = b.click();
			List<HtmlDivision> text = page3.getByXPath("//div[@class='item-string']");
//			StringBuffer str=new StringBuffer();
//			str.append(phrase).append("\t").append(occurance).append("\t").append(text.get(0).asText()).append("\n");
//			System.out.println(str);
		    WriteToOutputFile.writeOutputFile(phrase,occurance,text.get(0).asText(),this.sheetOutput, this.wbOutput);

			//Row row1=sheet.getRow(row);
			//Cell cell1=row1.createCell(2);
			//Cell cell=row1.createCell(2);
			//cell1.setCellValue(phrase);
			//cell.setCellValue(text.get(0).asText());
//		    try (OutputStream fileOut = new FileOutputStream("C:\\Users\\Vinod\\Desktop\\phrasesoutput1.xlsx")) {
//	            wb.write(fileOut);
//	        }


			//System.out.println("**"+Thread.currentThread().getId()+"**Exit");
			webClient.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


}
class WriteToOutputFile{
	public synchronized static void writeOutputFile(String phrase, Double occurance, String scrappedPhrase, Sheet sheetOutput, Workbook wbOutput) throws FileNotFoundException, IOException {
//		Row row;
		 
//		if(sheetOutput.getLastRowNum()==0)
//			row=sheetOutput.createRow(0);
//		else
		Row	row=sheetOutput.createRow(sheetOutput.getLastRowNum()+1);
		Cell phraseCell=row.createCell(0);
		Cell occuranceCell=row.createCell(1);
		Cell scrappedPhraseCell=row.createCell(2);
		phraseCell.setCellValue(phrase);
		String doublevalue=String.valueOf(occurance);
		occuranceCell.setCellValue(Integer.parseInt(doublevalue.substring(0,doublevalue.length()-2)));
		scrappedPhraseCell.setCellValue(scrappedPhrase);
		try (OutputStream fileOut = new FileOutputStream("phrasesoutputKC_45k_58k.xlsx")) {
            wbOutput.write(fileOut);
        }
		System.out.println("888888888888888888888**********************************************"+Thread.currentThread().getId()+"************************************************************888888888888888888888");
		
	}
}
