package SQL;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * Created by Oleksandr_Shainoga on 9/5/2017.
 */
public class Main {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:\\SparkBook");

        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("test");
        sparkConf.setMaster("local[5]");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        SQLContext sqlCtx = new SQLContext(sc);
        DataFrame df = sqlCtx.jsonFile("src/sparc.json");
        df.registerTempTable("json");
     
//        df.schema();
    }
}
