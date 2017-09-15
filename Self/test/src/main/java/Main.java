import java.util.ArrayList;

/**
 * Created by Oleksandr_Shainoga on 9/7/2017.
 */
public class Main {
    static void qwe(String s){
        for (int i = 0; i <10 ; i++) {
            System.out.println(i+"  "+ s);
        }
    }
    public static void main(String[] args) throws Exception {

        ArrayList<String> a = new ArrayList<String>();
        a.add("1");
        a.add("2");
        a.add("3");
        a.add("4");
        a.add("5");
        a.add("6");
        a.add("7");
        a.stream().peek(a1->qwe(a1)).forEach(System.out::println);
    }


}
