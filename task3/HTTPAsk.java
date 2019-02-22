import java.net.*;
import java.io.*;
import java.util.StringTokenizer;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class HTTPAsk {
    public static void main( String[] args) throws MalformedURLException {
      int port;
      String request="";
      String targetHost ="";
      int targetPort = 0;
      String query = "";
      String value ="";
      String targetString = "";
      try{
        port = Integer.parseInt(args[0]);

      }catch(Exception ex){
        System.err.println("Usage: HTTPAsk host port <data to server>");
        return;
      }
      try{
        ServerSocket serversocket = new ServerSocket(port);
        System.out.println("Listening for connection on port " + port + "...");
      while(true){
        try(Socket cSocket = serversocket.accept()){
          BufferedReader fromClient = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
          request = fromClient.readLine();
          StringTokenizer tokenizer = new StringTokenizer(request);
          String method = tokenizer.nextToken();
          String url = "http://localhost:" + port + tokenizer.nextToken().toLowerCase();
          URL myUrl = new URL (url);
          if(myUrl.getPath().substring(1).equals("ask") && method.equals("GET")){
          query = myUrl.getQuery();
          Map<String, String> map = getQueryMap(query);
          targetHost = map.get("hostname");
          targetPort = Integer.parseInt(map.get("port"));
          targetString = map.get("string");
          if(targetString != null){
            value = TCPClient.askServer(targetHost,targetPort,targetString);
        }else {
            value = TCPClient.askServer(targetHost,targetPort);
        }
          String response =  "\nHTTP/1.1 200 OK \r\n\r\n" + value;
          System.out.println(response);
          cSocket.getOutputStream().write(response.getBytes("UTF-8"));
          fromClient.close();
        }else {
          String response = "\nHTTP/1.1 400 BAD REQUEST\r\n\r\n";
          cSocket.getOutputStream().write(response.getBytes("UTF-8"));
        }
      }// end of try that closes the socket
      }///end forever loop
    }catch(IOException ex){
          System.out.println(ex);
        }

      }///end of main


  //helper function that returns a HashMap of key and values where keys are the string before = and values are the string after =
  public static Map<String, String> getQueryMap(String query){
   String[] params = query.split("&");
   Map<String, String> map = new HashMap<String, String>();
   for (String param : params){
       String name = param.split("=")[0];
       String value = param.split("=")[1];
       map.put(name, value);
   }
   return map;
}//end of map function

}
