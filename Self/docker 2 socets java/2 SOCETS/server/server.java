import java.io.IOException;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

class Server {
    public static void main(String[] args) throws IOException, InterruptedException {
      System.out.println("start");
        ServerSocket listener = new ServerSocket(9090);
        System.out.println("w8 client");
        Socket socket = listener.accept();
        System.out.println("acept");
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
        int i = 0;
        while (true) {
            out.println(i++);
            Thread.sleep(600);
        }
    }
}

