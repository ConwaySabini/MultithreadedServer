
/**
 * Created by rtdimpsey on 4/13/17.
 */
import java.net.*;
import java.io.*;

public class DateClient430 {
    public static void main(String[] args) {
        int port = 0;
        if (args.length > 0) { // get port number
            port = Integer.parseInt(args[0]);
            System.out.print("listening on ");
            System.out.println(port);
        } else {
            System.err.println("Error: need port number as only argument");
            System.exit(0);
        }
        try {
            Socket sock = new Socket("127.0.0.1", port);
            InputStream in = sock.getInputStream();
            BufferedReader bin = new BufferedReader(new InputStreamReader(in));
            String line;
            while ((line = bin.readLine()) != null) {
                System.out.println(line);
            }
            sock.close();
        } catch (IOException ie) {
        }
    }
}
