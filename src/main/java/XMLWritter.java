import com.thoughtworks.xstream.XStream;
import com.thoughtworks.xstream.io.xml.DomDriver;

import java.util.Map;

public class XMLWritter {

    public static String writeToXML (Map articleData) {

        XStream xstream = new XStream(new DomDriver());

        article myArticle = new article();
        myArticle.setTitle(articleData.get("title").toString());
        myArticle.setBody(articleData.get("body").toString());
        myArticle.setDate(articleData.get("date").toString());
        myArticle.setURL(articleData.get("url").toString());

        return xstream.toXML(myArticle) + "\n\n";
    }

}
