import org.apache.spark.SparkConf;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

public class SparkSocetIN {
    public static void main(String[] args) throws IOException {
        SparkConf conf = new SparkConf().setMaster("local[*]")
                .setAppName("VerySimpleStreamingApp");
        JavaStreamingContext streamingContext =new JavaStreamingContext(conf, Durations.seconds(5));
        JavaReceiverInputDStream<String> lines = streamingContext.socketTextStream("127.0.0.1", 4459);
        lines.print();
        streamingContext.start();
        streamingContext.awaitTermination();
        // Execute the Spark workflow defined above


    }
}
