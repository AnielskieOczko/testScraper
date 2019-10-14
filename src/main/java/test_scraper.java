import com.fasterxml.jackson.databind.ObjectMapper;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import org.apache.commons.text.StringEscapeUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.PrintWriter;
import java.util.Map;

public class test_scraper {

    public static void main(String[] args) throws Exception {

        // set user agent
        final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/77.0.3865.90 Safari/537.36";
        Unirest.setDefaultHeader("User-Agent",USER_AGENT);

        // set article number to load in one request
        // current page to starts with, starts from newest
        final int ARTICLENUMBER = 5;
        final int CURRPAGENUM = 1;

        // make request to get simulate article load
        final HttpResponse<String> response = Unirest.post("https://bitcoin.pl/?ajax-request=jnews")

                .header("Accept","application/json, text/javascript, */*; q=0.01")
                .header("Content-Type","application/x-www-form-urlencoded; charset=UTF-8")
                .header("Referer","https://bitcoin.pl/")
                .header("user-agent",USER_AGENT)
                .header("accept-language", "en-US,en;q=0.9")
                .header("X-Requested-With","XMLHttpRequest")
                .header("accept-encoding:", "gzip, deflate, br")

                .queryString("lang","pl_PL")
                .queryString("action","jnews_module_ajax_jnews_block_5")
                .queryString("data[current_page]",CURRPAGENUM)
                .queryString("data[attribute][number_post]", ARTICLENUMBER)
                .asString();


        // create object mapper to parse json response
        ObjectMapper mapper = new ObjectMapper();
        Map map = mapper.readValue(response.getBody(), Map.class);
        String content = map.get("content").toString();

        //parse response and create HTML doc
        Document document = Jsoup.parseBodyFragment(content);
        Element body = document.body();

        // get all links to articles
        Elements links = body.select("div.jeg_postblock_content > h3 > a[href]");

        // create writer to store links in txt
        PrintWriter writer = new PrintWriter("C:\\Users\\RJ\\Desktop\\links\\linksList.txt", "UTF-8");
        PrintWriter jsonWriter = new PrintWriter("C:\\Users\\RJ\\Desktop\\links\\json.txt", "UTF-8");

        JSONObject mainobj = new JSONObject();
        JSONArray arr = new JSONArray();

        for (Element link : links) {
            String linkHref = link.attr("href");
            String  linkText = link.text();
            System.out.println(linkHref);
            writer.println(linkHref);

            // get article body text, post date, category
            Map articleData = getArticle.articleContainerToText(linkHref);
            System.out.println(articleData);
            // create json
            JSONObject myJson = jsonWritter.writeToJson(articleData);
            arr.put(myJson);
            mainobj.put("Articles", arr);
            System.out.println(mainobj);

        }

        writer.close();

            }
    }

