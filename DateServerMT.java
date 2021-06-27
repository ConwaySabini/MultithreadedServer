
/**
 * Simple Multithreaded java server which will create a thread for each client
 * request and to print out the date for each client. The server can support
 * up to ten requests at a time.
 * 
 * @author Sabini Ethan
 * @date 2/1/2021
 * @version 11.0.8
 */

import java.net.*;
import java.io.*;
import java.util.*;

public class DateServerMT {
    private final static int NUM_WORKERS = 10;

    public static void main(String[] args) {
        int port = 0;
        if (args.length > 0) { // get port number
            port = Integer.parseInt(args[0]);
            if (port > 1023 && port < 65536) {
                System.out.print("listening on ");
                System.out.println(port);
            } else {
                System.err.println("Error: port number must be between 1024 and 65535");
                System.exit(0);
            }
        } else {
            System.err.println("Error: need port number as only argument");
            System.exit(0);
        }
        // array of threads for
        ArrayList<Thread> threads = new ArrayList<>();
        // runnable objects to create a thread with
        ArrayList<Runnable> workers = new ArrayList<>();
        int size = 0;
        ServerSocket sock = null;
        try {
            sock = new ServerSocket(port);
        } catch (Exception e) {
            System.err.println("Error on socket creation");
            e.printStackTrace();
        }
        try {
            while (true) { // continue to get client results
                Socket client = null;
                size = workers.size();
                client = sock.accept();
                if (size < NUM_WORKERS) {
                    workers.add(new Worker(client));
                    size++;
                    Thread task = new Thread(workers.get(size - 1));
                    threads.add(task);
                    task.start();
                    workers.remove(size - 1);
                    threads.remove(size - 1);
                } else {
                    System.err.println("Supports only 10 concurrent requests");
                    client.close();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { // close connections
                sock.close();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }
}
