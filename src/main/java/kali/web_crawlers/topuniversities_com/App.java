package kali.web_crawlers.topuniversities_com;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class App {

	public static void fetchFinalData(String subjectUrl,BufferedWriter bw) throws IOException{
	/*
	 * fetching data for particular domain/subject
	 * "https://www.topuniversities.com/universities/country/india/level/undergrad/subject/computer-science-information-systems"; 	
	 */
		Document subjectDoc=Jsoup.connect(subjectUrl).get();
		
	/*
	 * there is ul in subjectDoc that have the list of universities and their QS World University Ranking.
	 * sometimes there QS World Ranking is not available in ranking div so we making condition for the particular div
	 * that div has child or not.
	 */
		Element ulOfUniversities=subjectDoc.getElementById("universities-search");
		Elements all_li_ofUniversities=ulOfUniversities.select("li");
	//	System.out.println(universitis.select("li"));
	//	System.out.println(all_li_ofUniversities.size());
		
		for(int i=0;i<all_li_ofUniversities.size();i++){
			
			Element uni=all_li_ofUniversities.get(i);
			System.out.println(uni.select("a h2"));
			String universityName=uni.select("a h2").text();
			System.out.println(uni.select("a div").get(2).text());
			String universityRankingCouldBeAvailable=uni.select("a div").get(2).text();
			
		}	
	}
	
	public static void main(String[] args) throws IOException {
		fetchFinalData("https://www.topuniversities.com/universities/country/india/level/undergrad/subject/computer-science-information-systems",new BufferedWriter(new FileWriter("countryname.txt")));
		System.out.println("Started At: "+System.currentTimeMillis());
		String baseUrl="https://www.topuniversities.com/universities";
		Document baseUrlDoc=Jsoup.connect(baseUrl).get();
		
		
		baseUrlDoc.getElementById("pro-search_api_aggregation_2");
		Element countryListSelect=baseUrlDoc.getElementById("pro-search_api_aggregation_2");
	//	System.out.println(baseUrlDoc.getElementById("pro-search_api_aggregation_2"));
		Elements allCountryListOptions=countryListSelect.select("option");
	//	System.out.println(allCountryListOptions.size());
		
		
	aa:	for(int i=0;i<allCountryListOptions.size();i++){
			
			String countryNameOptionValue=allCountryListOptions.get(i).attr("value");
			System.out.println(countryNameOptionValue);
			String countryNameOptionText=allCountryListOptions.get(i).text();
			System.out.println(countryNameOptionText);
			
			String countryUrl="/country/"+countryNameOptionValue;
			Document countryDoc=Jsoup.connect(baseUrl+countryUrl).get();
			
			//level like postgraduate/undergraduate in dropdown
			Element levelSelect=countryDoc.getElementById("edit-study-level");
			Elements levelOptions=levelSelect.select("option");
			
			//condition because first two element of levelOption is not useful 
			if(levelOptions.size()>3){
				
				for(int j=2;j<levelOptions.size();j++){
					
					String level=levelOptions.get(j).attr("value");
					System.out.println(level);
					Document subjectDoc=Jsoup.connect(baseUrl+"/country/"+allCountryListOptions.get(i).attr("value")+level).get();
					System.out.println(baseUrl+"/country/"+allCountryListOptions.get(i).attr("value")+level);
					Element subjectSelect=subjectDoc.getElementById("pro-search_api_aggregation_4");
					if(subjectSelect!=null){
						
						System.out.println(subjectSelect);
						Elements subjectOptions=subjectSelect.select("option");
						System.out.println(subjectOptions.size());
						System.out.println(subjectOptions);
						
						for(int k=0;k<subjectOptions.size();k++){
						
							Document finalDetailDoc=Jsoup.connect(baseUrl+"/country/"
														+allCountryListOptions.get(i).attr("value")
														+level+"/subject/"
														+subjectOptions.get(k).attr("value")).get();
							System.out.println(subjectOptions.get(k).attr("value"));
							fetchFinalData(baseUrl+"/country/"
									+allCountryListOptions.get(i).attr("value")
									+level+"/subject/"
									+subjectOptions.get(k).attr("value"),new BufferedWriter(new FileWriter("countryname.txt")));
							System.out.println(baseUrl+"/country/"
									+allCountryListOptions.get(i).attr("value")
									+level+"/subject/"
									+subjectOptions.get(k).attr("value"));
						}
						
					}
					System.out.println("out from loop");					
				}
			}
			else{
				System.out.println("||?////\"\"|?|?/|\"?||");
				break aa;
				}
		}
		
		File countryNameFile=new File("countryname.txt");
		if(!countryNameFile.exists())	countryNameFile.createNewFile();
		FileWriter fileWriter = new FileWriter(countryNameFile,true);
		BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
	//	bufferedWriter.write(doc.toString());
		bufferedWriter.newLine();
		bufferedWriter.flush();
		bufferedWriter.close();
		

	}

}
