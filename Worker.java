
/**
 * Simple Worker class implements the runnable interface to run in a thread.
 * A Worker is passed the client socket on construction and prints the current
 * date on the client side.
 * 
 * @author Sabini Ethan
 * @date 2/1/2021
 * @version 11.0.8
 */
import java.io.*;
import java.net.*;

public class Worker implements Runnable {
    private PrintWriter pout = null;
    private Socket client = null;

    Worker(Socket cl) {
        this.client = cl;
    }

    public void run() {
        try {
            PrintWriter print = new PrintWriter(this.client.getOutputStream(), true);
            this.pout = print;
            pout.println(new java.util.Date().toString());
            client.close();
        } catch (Exception e) {
            System.err.println("Error fetching date");
        }
    }
}
