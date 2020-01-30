package wonjin.jsoup.sports;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class NaverSportsCrawling {
	public static void main(String[] args) {
		String url = "https://sports.news.naver.com/wfootball/index.nhn";
		
		Document doc = null;

		try {
			doc = Jsoup.connect(url).get();
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Elements articles = doc.select("div.home_news");
		String title = articles.select("h2").text().substring(0, 4);
		System.out.println("==========================================================");
		System.out.println("\t\t\t" + title);
		System.out.println("==========================================================");
		for(Element article : articles.select("li")) {
			System.out.println(article.text());
		}
		System.out.println("==========================================================");
	}
}