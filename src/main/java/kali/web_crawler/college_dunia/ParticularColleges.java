package kali.web_crawler.college_dunia;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;

public class ParticularColleges {

	public static void main(String args[]) throws IOException, JSONException, UnirestException{
		
	String str=	Unirest.post("https://collegedunia.com/exams/entrance-exam")
		.header("Accept", "application/json")
		.header("Accept-Encoding", "gzip, deflate, br")
		.header("Referer", "https://exams.collegedunia.com/ap-eamcet/top-colleges-in-india-accepting-ap-eamcet")
		.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
		.header("Origin", "https://exams.collegedunia.com")
		.body("page=0&tab=top-colleges-in-india-accepting-ap-eamcet").asJson().getBody().getObject().toString();
	
	JSONObject json = new JSONObject(str);
    System.out.println(json.get("html").toString());
	
	System.out.println(str);
	 
	Document doc1=Jsoup.parse(json.get("html").toString());
	System.out.println(doc1.select("div[class=col-md-3 col-sm-6 col-xs-12 set_hight_participate_colleges]").size());
		
		
		Document doc = Jsoup.connect("https://collegedunia.com/exams/entrance-exam")
				.header("Accept", "application/json")
				.header("Accept-Encoding", "gzip, deflate, br")
				.header("Referer", "https://exams.collegedunia.com/ap-eamcet/top-colleges-in-india-accepting-ap-eamcet")
				.header("Content-Type", "application/x-www-form-urlencoded; charset=UTF-8")
				.header("Origin", "https://exams.collegedunia.com")
				.requestBody("page=1&tab=top-colleges-in-india-accepting-ap-eamcet").post();
		File fileToWrite=new File("participating_college"+"_"+System.currentTimeMillis()+"_.html");
		fileToWrite.createNewFile();
		FileWriter fileWriter = new FileWriter(fileToWrite,true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
		bufferedWriter.write(doc.toString());
		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();
		//System.out.println(doc);
	
		System.out.println(doc.select("div[class=&quot;col-md-3]").size());
	}
}
