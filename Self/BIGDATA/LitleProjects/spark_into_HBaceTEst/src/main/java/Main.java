import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.MasterNotRunningException;
import org.apache.hadoop.hbase.client.HBaseAdmin;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.io.ImmutableBytesWritable;
import org.apache.hadoop.hbase.mapred.TableOutputFormat;
import org.apache.hadoop.hbase.mapreduce.TableInputFormat;
import org.apache.hadoop.hbase.util.Bytes;
import org.apache.hadoop.mapreduce.Job;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.PairFunction;
import scala.Tuple2;

import javax.xml.bind.SchemaOutputResolver;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Oleksandr_Shainoga on 8/22/2017.
 */
public class Main {
    public static void main(String[] args) {
//       —driver-memory 512m —executor-memory 512m
        SparkConf conf = new SparkConf().setAppName("VerySimpleStreamingApp");
//                .setMaster("yarn-client").set("spark.executor.instances", "1").set("spark.executor.cores", "1").set("spark.executor.memory", "512m").set("spark.driver.memory", "512m");
        JavaSparkContext sc = new JavaSparkContext(conf);
        ArrayList<String> a = new ArrayList<>();
        a.add("1");
        a.add("2");
        a.add("3");
        JavaRDD<String> test = sc.parallelize(a);

        Configuration config = null;
        try {
            config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.property.clientPort", "2181");
            config.set("hbase.zookeeper.quorum", "sandbox.hortonworks.com");
            config.set("zookeeper.znode.parent", "/hbase-unsecure");
//            config.set("hbase.zookeeper.quorum", "sandbox.hortonworks.com");
//            config.set("hbase.zookeeper.property.clientPort","2181");
//            config.set("zookeeper.znode.parent", "/hbase-unsecure");
//           config.set("hbase.master", "sandbox.hortonworks.com");
//            config.setInt("hbase.master.port",16000 );

//            config.set("hbase.master", "localhost:16000");
            HBaseAdmin.checkHBaseAvailable(config);
            System.out.println("HBase is running!");
        } catch (MasterNotRunningException e) {
            System.out.println("HBase is not running!");
            System.exit(1);
        } catch (Exception ce) {
            ce.printStackTrace();
        }

        config.set(TableInputFormat.INPUT_TABLE, "testTable");
//
// new Hadoop API configuration
        Job newAPIJobConfiguration1 = null;
        try {
            newAPIJobConfiguration1 = Job.getInstance(config);
        } catch (IOException e) {
            System.out.println("troubleWithCONFIG");
            e.printStackTrace();
        }
        newAPIJobConfiguration1.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "testTable");
        newAPIJobConfiguration1.setOutputFormatClass(org.apache.hadoop.hbase.mapreduce.TableOutputFormat.class);

        JavaPairRDD<ImmutableBytesWritable, Put> hbasePuts = test.mapToPair(new PairFunction<String, ImmutableBytesWritable, Put>() {
            @Override
            public Tuple2<ImmutableBytesWritable, Put> call(String row) throws Exception {

                Put put = new Put(Bytes.toBytes(row + "17"));
                put.add(Bytes.toBytes("TestCF"), Bytes.toBytes("a"), Bytes.toBytes("sawa"));
//                put 'testTable', 'row4', 'TestCF', 'value2'


                return new Tuple2<ImmutableBytesWritable, Put>(new ImmutableBytesWritable(), put);
            }

        });
        Integer i = 2;
        i.toString();
         hbasePuts.saveAsNewAPIHadoopDataset(newAPIJobConfiguration1.getConfiguration());

    }
}
