import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SocetOut {
    public static void main(String[] args) throws IOException, InterruptedException {

        PrintWriter    out= null;

        ServerSocket servers = null;
        Socket       fromclient = null;

        // create server socket
        try {
            servers = new ServerSocket(4459);
        } catch (IOException e) {
        }

        try {
            fromclient= servers.accept();
        } catch (IOException e) {
        }


        out = new PrintWriter(fromclient.getOutputStream(),true);

        int i=0;
        while (true) {
            i++;
            out.println(i);
        }

    }
    }
