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
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import scala.Tuple2;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.IOException;

public class SparkSocetIN {
    public static void main(String[] args) throws IOException {

        SparkConf conf = new SparkConf().setAppName("VerySimpleStreamingApp");
        JavaStreamingContext streamingContext = new JavaStreamingContext(conf, Durations.seconds(2));
        JavaReceiverInputDStream<String> lines = streamingContext.socketTextStream("127.0.0.1", 4459);
        JavaDStream<String> edits = lines.filter(line -> line.contains("edit"));

        Configuration config = null;
        try {
            config = HBaseConfiguration.create();
            config.set("hbase.zookeeper.property.clientPort", "2181");
            config.set("hbase.zookeeper.quorum", "sandbox.hortonworks.com");
            config.set("zookeeper.znode.parent", "/hbase-unsecure");
            HBaseAdmin.checkHBaseAvailable(config);
            System.out.println("HBase is running!");
        } catch (MasterNotRunningException e) {
            System.out.println("HBase is not running!");
            System.exit(1);
        } catch (Exception ce) {
            ce.printStackTrace();
        }
        config.set(TableInputFormat.INPUT_TABLE, "wiki");
        Job newAPIJobConfiguration1 = Job.getInstance(config);
        newAPIJobConfiguration1.getConfiguration().set(TableOutputFormat.OUTPUT_TABLE, "wiki");
        newAPIJobConfiguration1.setOutputFormatClass(org.apache.hadoop.hbase.mapreduce.TableOutputFormat.class);

        edits.foreachRDD(RDD1 -> {
            JavaPairRDD<ImmutableBytesWritable, Put> hbasePuts = RDD1.mapToPair(new PairFunction<String, ImmutableBytesWritable, Put>() {
                @Override
                public Tuple2<ImmutableBytesWritable, Put> call(String row) throws Exception {///
                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(row);
                    JSONObject jsonObject = (JSONObject) obj;
                    String action = (String) jsonObject.get("action");
                    String user = (String) jsonObject.get("user");
                    String url = (String) jsonObject.get("url");
                    if (url == null) {
                        url = "SAWA" + Math.random() + Math.random() * 1000;
                    }
                    Put put = new Put(Bytes.toBytes(url));
                    if (user != null) {
                        put.add(Bytes.toBytes("actions"), Bytes.toBytes("user"), Bytes.toBytes(user));
                    }
                    if (action != null) {
                        put.add(Bytes.toBytes("actions"), Bytes.toBytes("action"), Bytes.toBytes(action));
                    }
                    String is_anon= ((Boolean) jsonObject.get("is_anon")).toString();
                    if(is_anon!=null){
                        put.add(Bytes.toBytes("actions"), Bytes.toBytes("is_anon"), Bytes.toBytes(is_anon));
                    }
                    String is_bot=((Boolean) jsonObject.get("is_bot")).toString();
                    if(is_anon!=null){
                        put.add(Bytes.toBytes("actions"), Bytes.toBytes("is_bot"), Bytes.toBytes(is_bot));
                    }
                    String is_new=((Boolean) jsonObject.get("is_new")).toString();
                    if(is_anon!=null){
                        put.add(Bytes.toBytes("actions"), Bytes.toBytes("is_new"), Bytes.toBytes(is_new));
                    }
                    String page_title=(String) jsonObject.get("page_title");
                    if(is_anon!=null){
                        put.add(Bytes.toBytes("actions"), Bytes.toBytes("page_title"), Bytes.toBytes(page_title));
                    }
                    return new Tuple2<ImmutableBytesWritable, Put>(new ImmutableBytesWritable(), put);
                }
            });
            hbasePuts.saveAsNewAPIHadoopDataset(newAPIJobConfiguration1.getConfiguration());
        });
        streamingContext.start();
        streamingContext.awaitTermination();


    }
}
