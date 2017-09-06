import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.LinkedList;

/**
 * Created by Oleksandr_Shainoga on 8/21/2017.
 */
public class test {
    public static void main(String[] args) throws IOException {
        String filePath = "queries.txt";
        BufferedReader fbr = new BufferedReader(new FileReader(filePath));
        String query;
        int counter =1;
        LinkedList<String> queries=new LinkedList<>();
        while ((query = fbr.readLine()) != null) {
            queries.add(query);
        }
        Collections.shuffle(queries);
        for (String s:queries){
//            vars.put("query_" + counter, s);
            counter++;
        }
        fbr.close();
    }
}
