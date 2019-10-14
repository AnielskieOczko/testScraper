import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.text.StringEscapeUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.PrintWriter;
import java.util.Map;

public class test_scraper1 {

    public static void main(String[] args) throws Exception {
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";
        Unirest.setDefaultHeader("User-Agent",USER_AGENT);
        //text/html; charset=UTF-8

        final HttpResponse<String> response = Unirest.post("https://bitcoin.pl/?ajax-request=jnews")
                .header("Accept","application/json, text/javascript, */*; q=0.01")
                .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                .header("Referer","https://bitcoin.pl/")
                .header("user-agent",USER_AGENT)
                .header("accept-language", "en-US,en;q=0.9")
                .header("X-Requested-With","XMLHttpRequest")
                .header("accept-encoding:", "gzip, deflate, br")

                .queryString("lang","pl_PL")
                .queryString("action","jnews_first_load_action")
                .queryString("jnews_id",10311)
                .queryString("load_action[]", "view_counter")
                .asString();

        //System.out.println(response.getHeaders());
        //System.out.println(response.getBody());

        //System.out.println(Jsoup.parse(response.getBody()));

        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(response.getBody(), Map.class);
        String content = map.get("content").toString();

        Document document = Jsoup.parseBodyFragment(content);
        Element body = document.body();
        Elements links = body.select("div.jeg_postblock_content > h3 > a[href]");
        System.out.println(document);
/*
        PrintWriter writer = new PrintWriter("C:\\Users\\RJ\\Desktop\\links\\linksList.txt", "UTF-8");

        for (Element link : links) {
            String linkHref = link.attr("href");
            String  linkText = link.text();
            System.out.println(linkHref);
            writer.println(linkHref);

        }
        writer.close();
*/


    }
}


