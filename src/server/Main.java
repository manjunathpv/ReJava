package server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

/**
 * This class implements java Socket server
 * @author pankaj
 *
 */
public class Main {

    //static ServerSocket variable
    private static ServerSocket server;
    //socket server port on which it will listen
    private static int port = 5840;

    public static void main(String args[]) throws IOException, ClassNotFoundException{
        //create the socket server object
        server = new ServerSocket(port);
        Scanner in = new Scanner(System.in);
        //keep listens indefinitely until receives 'exit' call or program terminates
        System.out.println("Welcome to Home Java Server");
        System.out.println("Usage:");
        System.out.println("Type Exit to terminate the server");
        int totalConnections = 0;
        Socket socket = null;
        while(true){
            //creating socket and waiting for client connection
            //read from socket to ObjectInputStream object
            socket = server.accept();
            System.out.println(socket.getInetAddress().getHostName());
            totalConnections++;
            ObjectInputStream ois = new ObjectInputStream(socket.getInputStream());
            //convert ObjectInputStream object to String
            String message = (String) ois.readObject();
            System.out.println(message);
            //create ObjectOutputStream object
            ObjectOutputStream oos = new ObjectOutputStream(socket.getOutputStream());
            //write object to Socket
            oos.writeObject("Hi Client "+message);
            //close resources
            ois.close();
            oos.close();
            //terminate the server if client sends exit request
            if(message.equalsIgnoreCase("exit"))
            {
                System.out.println("Client Connection close");
                socket.close();
                break;
            }

        }
        //close the ServerSocket object
        System.out.println(totalConnections);
        String terminate = in.nextLine();
        if(terminate.equalsIgnoreCase("exit"))
        {
            System.out.println("Shutting down Socket server!!");
            server.close();
        }

    }

}
