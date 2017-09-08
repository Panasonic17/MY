import java.io.BufferedReader;
import java.io.FileReader;

/**
 * Created by Oleksandr_Shainoga on 9/7/2017.
 */
public class Main {
    public static void main(String[] args) throws Exception {

        String filePath = "D:/queries.csv";
        BufferedReader fbr = new BufferedReader(new FileReader(filePath));
        String row;
        int counter =1;
        fbr.readLine();
        while ((row = fbr.readLine()) != null) {
            String query= row.split(",")[1];
//            vars.put("query_" + counter, query);
            System.out.println(query);
            counter++;
        }
//        vars.put("count",counter+"");
        fbr.close();
    }
}
