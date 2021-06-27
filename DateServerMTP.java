
/**
 * Simple Multithreaded java server which creates a thread pool that can 
 * service each request and to print out the date for each client.
 * The thread pool controls the number of threads that can run concurrently.
 * 
 * @author Sabini Ethan
 * @date 2/1/2021
 * @version 11.0.8
 */
import java.net.*;
import java.io.*;
import java.util.concurrent.*;
import java.util.*;

public class DateServerMTP {
    public static void main(String[] args) {
        int port = 0;
        if (args.length > 0) {
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
        // pool of threads to manage each client request
        ExecutorService threadExe = Executors.newCachedThreadPool();
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
                workers.add(new Worker(client));
                size++;
                // get created worker and run it
                threadExe.execute(workers.get(size - 1));
                workers.remove(size - 1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try { // close connections
                threadExe.shutdown();
                sock.close();
            } catch (IOException ie) {
                ie.printStackTrace();
            }
        }
    }
}
