import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileReader;

public class main {
    public static void main(String[] args) throws ParseException {
   String json="{\"action\": \"edit\", \"change_size\": -6, \"flags\": \"M\", \"is_anon\": false, \"is_bot\": false, \"is_minor\": true, \"is_new\": false, \"is_unpatrolled\": false, \"ns\": \"Mainv\", \"page_title\": \"RAF Long Marston\", \"parent_rev_id\": \"796847414\", \"rev_id\": \"782205906\", \"summary\": \"clean up, [[WP:AWB/T|typo(s) fixed]]: 1940's → 1940s using [[Project:AWB|AWB]]\", \"url\": \"https://en.wikipedia.org/w/index.php?diff=796847414&oldid=782205906\", \"user\": \"Nihlus Kryik\"}";
  System.out.println(json);
        System.out.println(" "+json.charAt(160)+json.charAt(161)+ json.charAt(162)+json.charAt(163) +json.charAt(164)+"    q");
        JSONParser parser = new JSONParser();
        Object obj = parser.parse(json);
        JSONObject jsonObject = (JSONObject) obj;
        String name = (String) jsonObject.get("action");
        System.out.println(name);
        String name1=(String) jsonObject.get("user");
        System.out.println(name1);


    }
}
