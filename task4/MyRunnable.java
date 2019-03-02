import java.net.*;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
public class MyRunnable implements Runnable {
	String request="";
    String line = "";
    String targetHost ="";
    int targetPort = 0;
    String query = "";
    String value ="";
    String targetString = "";
		int serverPort;
	public Socket cSocket;

	public MyRunnable (Socket socket,int serverPort){
		cSocket = socket;
		this.serverPort = serverPort;

	}

	@Override
	public void run() {
		      try {
	          BufferedReader fromClient = new BufferedReader(new InputStreamReader(cSocket.getInputStream()));
	          request = fromClient.readLine();
	          //System.out.println(request);
	          line = fromClient.readLine();
	          while(line.length()!= 0){
	            line = fromClient.readLine();
	          }
	          String [] split = request.split(" ");
	          //String [] split2 = split[1].split("?");
	          String method = split[0];
	          String url = "http://localhost:" + serverPort + split[1];
	          URL myUrl = new URL (url);
	          try{
	          if(myUrl.getPath().equals("/ask") && method.equals("GET") && (split[2].equals("HTTP/1.1") || split[2].equals("HTTP/1.0"))){
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
	          String response =  "HTTP/1.1 200 OK \r\n\r\n" + value;
	          cSocket.getOutputStream().write(response.getBytes("UTF-8"));

	        } // if statment to checkk if ask and get match
	        else {
	          String response = "HTTP/1.1 400 Bad Request\r\n\r\n";
	          cSocket.getOutputStream().write(response.getBytes("UTF-8"));
	        }

	        //fromClient.close();

	      }catch(Exception ex){
	        //if the TCPClient returns an exception
	        String response = "HTTP/1.1 404 Not Found\r\n\r\n";
	        cSocket.getOutputStream().write(response.getBytes("UTF-8"));
	      }
				 cSocket.close();
	    }catch(Exception ex){
	    	System.out.println(ex);
	    }
	}
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
