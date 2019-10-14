import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class getArticle {

    public static Map articleContainerToText(String articleURL) throws IOException {

        Document articleDoc = Jsoup.connect(articleURL).get();
        String articleTitle = articleDoc.select("div.entry-header h1").text();
        String articleBody = articleDoc.getElementsByClass("content-inner").text();
        String articleDate = articleDoc.select("div.jeg_meta_date a").text();
        String articleCategory = articleDoc.select("div.jeg_meta_category span a").text();

        Map<String, String> articleData = new HashMap<String,String>();
        articleData.put("title", articleTitle);
        articleData.put("body", articleBody);
        articleData.put("date", articleDate);
        articleData.put("category", articleCategory);

        return articleData;
    }
}
