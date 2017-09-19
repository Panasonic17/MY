import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.Socket;

class Client {
    public static void main(String[] args) throws IOException, InterruptedException {

        Socket s = new Socket("server", 9090);
        BufferedReader input =
                new BufferedReader(new InputStreamReader(s.getInputStream()));
        while(true){
        String answer = input.readLine();
        System.out.println(answer);
        }

    }
}
