import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) {
        int port;
        String lineRequest = "";
        String wholeRequest = "";

        try {
            port = Integer.parseInt(args[0]);
        }
        catch (Exception ex) {
            System.err.println("Usage: TCPAsk host port <data to server>");
            return;
        }
        try{
          ServerSocket serversocket = new ServerSocket(port);
          System.out.println("Listening for connection on port " + port + "...");
          while(true){
           try(Socket cSocket = serversocket.accept()){
             BufferedReader fromClient = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
             lineRequest = fromClient.readLine();

             while(lineRequest.length() != 0){
               wholeRequest += lineRequest + '\n';
               lineRequest = fromClient.readLine();
             }

             String response =  "HTTP/1.1 200 OK \r\n\r\n" + wholeRequest;

             cSocket.getOutputStream().write(response.getBytes("UTF-8"));

             wholeRequest = "";

           }

         }///////end of big while loop
        }catch(IOException ex){
            System.out.println("error");
          }
   }
}
