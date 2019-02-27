package client;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * This class implements java socket client
 *
 * @author pankaj
 */
public class Main {

    public static void main(String[] args) throws UnknownHostException, IOException, ClassNotFoundException, InterruptedException {
        //get the localhost IP address, if server is running on some other IP, you need to use that
        InetAddress host = InetAddress.getLocalHost();
        Socket socket = null;
        ObjectOutputStream oos = null;
        ObjectInputStream ois = null;
        Scanner in = new Scanner(System.in);
        System.out.println("Welcome to Java Home Client");
        System.out.print("Type in you client name:");
        String clientName = in.nextLine();

        while (true) {
            //establish socket connection to server
            socket = new Socket(host.getHostName(), 5840);

            //write to socket using ObjectOutputStream
            oos = new ObjectOutputStream(socket.getOutputStream());
            oos.writeObject("Connecting from client " + clientName);
            String message = in.nextLine();
            if (message.equalsIgnoreCase("exit")) {
                oos.writeObject("exit");
                System.out.println("Your client will terminate now");
                System.out.println("Bye");
                break;
            } else oos.writeObject(message);
            //read the server response message
            ois = new ObjectInputStream(socket.getInputStream());
            String getMessage = (String) ois.readObject();
            System.out.println("Message: " + getMessage);
            //close resources
            ois.close();
            oos.close();
            Thread.sleep(100);
        }
    }
}
