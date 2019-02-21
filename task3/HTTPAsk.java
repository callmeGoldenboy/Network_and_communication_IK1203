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
      String [] params = null;
      String [] params2 = null;
      String [] params3 = null;
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
          String method = tokenizer.nextToken().toUpperCase();
          String url = "http://localhost:8888" + tokenizer.nextToken().toLowerCase();
          //String url = "http://localhost:8888" + request.substring(4).toLowerCase();
          URL myUrl = new URL (url);
          //System.out.println(request);
          String query = myUrl.getQuery();
          /*Map<String, String> map = getQueryMap(query);
          Set<String> keys = map.keySet();
          String targetHost = map.get("hostname");
          int targetPort = Integer.parseInt(map.get("port"));
          String value = TCPClient.askServer(targetHost,targetPort);*/
          if(query != null){
           params = query.split("&");
           params2 = params[0].split("=");
           params3 = params[1].split("=");
           targetHost = params2 [1];
        }else{
          System.out.println("query is empty");
        }
          int targetPort = Integer.parseInt(params3[1]);
          String value = TCPClient.askServer(targetHost,targetPort);
          String response =  "HTTP/1.1 200 OK \r\n\r\n" + value;
          cSocket.getOutputStream().write(response.getBytes("UTF-8"));
          fromClient.close();
        }
      }
    }catch(IOException ex){
          System.out.println(ex);
        }

      }///end of main


  public static Map<String, String> getQueryMap(String query){
   String[] params = query.split("&");
   Map<String, String> map = new HashMap<String, String>();
   for (String param : params)
   {
       String name = param.split("=")[0];
       String value = param.split("=")[1];
       map.put(name, value);
   }
   return map;
}//end of map function

}
