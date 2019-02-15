import java.net.*;
import java.io.*;

public class HTTPEcho {
    public static void main( String[] args) {
        int port;
		    String hello = "Hello";
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
             lineRequest = fromClient.readLine() + "\n";
             while(lineRequest.length() >2){
               wholeRequest += lineRequest;
               lineRequest = fromClient.readLine() + "\n";
             }
             //String response =  " HTTP/1.1 200 OK " + "\n" +"Content-Type: text/html\r\n\r\n" + wholeRequest;
             String response =  " HTTP/1.1 200 OK \r\n\r\n" + wholeRequest;
             //String header = " HTTP/1.1 200 OK " + "\n" +"Content-Type: text/html\r\n\r\n";
             cSocket.getOutputStream().write(response.getBytes("UTF-8"));
            /* cSocket.getOutputStream().write(header.getBytes("UTF-8"));
             File file = new File("C:\\Users\\Natan\\Desktop\\task2\\response.html");
				    int fileLength = (int) file.length();
			      String contentMimeType = "text/html";
				//read content to return to client
				   byte[] fileData = readFileData(file, fileLength);
           cSocket.getOutputStream().write(file,0,fileLength);
              */
             wholeRequest = "";

           }

         }///////end of big while loop
        }catch(IOException ex){
            System.out.println("error");
          }
   }
}
