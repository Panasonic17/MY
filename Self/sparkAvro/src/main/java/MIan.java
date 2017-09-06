import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.DataFrame;
import org.apache.spark.sql.SQLContext;

/**
 * Created by Oleksandr_Shainoga on 9/6/2017.
 */
public class MIan {
    public static void main(String[] args) {
        System.setProperty("hadoop.home.dir", "D:\\SparkBook");

        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("test");
        sparkConf.setMaster("local[5]");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        SQLContext sqlCtx = new SQLContext(sc);
        DataFrame df = sqlCtx.load("src/main/users.avro", "com.databricks.spark.avro");
        df.schema();
        df.show();
        
//        df.registerTempTable("json");
//         DataFrame df = sqlContext.load("/Users/tariq/avro_data/browser.avro/", "com.databricks.spark.avro");
//        df.schema();
//        df.show();

//        df.schema();
    }
}
