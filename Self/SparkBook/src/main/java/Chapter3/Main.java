package Chapter3;


import org.apache.spark.Accumulator;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.broadcast.Broadcast;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf();
        sparkConf.setAppName("test");
        sparkConf.setMaster("local[5]");
        JavaSparkContext sc = new JavaSparkContext(sparkConf);
        final Broadcast<Integer> i = sc.broadcast(2);
        Accumulator<Integer> acc=sc.accumulator(1);
        acc.add(1);
        acc.add(1);
        acc.add(-5);
        List<Integer> data = Arrays.asList(1, 2, 3, 4, 5);
        JavaRDD<Integer> distData = sc.parallelize(data);
        distData.persist(org.apache.spark.storage.StorageLevel.DISK_ONLY());

        distData.filter(a->true);
        distData.mapPartitions(iter -> {
            System.out.println(iter.toString());
            return new ArrayList<>();
        }).count();
        while(true){}
    }
}
