import java.net.*;
import java.io.*;

public class ConcHTTPAsk {
  public static void main( String[] args) throws MalformedURLException {
     int serverPort;
     try{
       serverPort = Integer.parseInt(args[0]);

     }catch(Exception ex){
       System.err.println("Usage: HTTPAsk host port <data to server>");
       return;
     }

     try{
       ServerSocket serversocket = new ServerSocket(serverPort);
       System.out.println("Listening for connection on port " + serverPort + "...");
     while(true){
       Socket cSocket = serversocket.accept();
       Thread myThread = new Thread(new MyRunnable(cSocket,serverPort));
       myThread.start();

       }

   }catch(IOException ex){
         System.out.println(ex);
       }


    }///end of main
}
