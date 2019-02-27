import java.net.*;
import java.io.*;
import java.lang.*;

public class TCPClient {

    public static String askServer(String hostname, int port, String ToServer) throws  IOException {
		String fromServer = "";
		Socket mySocket = new Socket(hostname,port);
		mySocket.setSoTimeout(5000);
		long start = System.currentTimeMillis();
		BufferedReader infromServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));

		DataOutputStream outToServer = new DataOutputStream (mySocket.getOutputStream());
		outToServer.writeBytes(ToServer + '\n');
		int c = 0;
		long current = System.currentTimeMillis();
        try{
		while( (c = infromServer.read())!= -1 ){
			current = System.currentTimeMillis();
			fromServer += (char)c;
		}
        return fromServer;
		}
		catch(SocketTimeoutException e){
			mySocket.close();
			return fromServer;

		}

	}
    public static String askServer(String hostname, int port) throws  IOException {
		Socket mySocket = new Socket(hostname,port);
		mySocket.setSoTimeout(5000);
    	BufferedReader infromServer = new BufferedReader(new InputStreamReader(mySocket.getInputStream()));
    	String fromServer = "";
		int c = 0;
		long start = System.currentTimeMillis();
		long current = System.currentTimeMillis();
        try {
    	while((c = infromServer.read())!=-1 && (current - start) < 3000){
    		fromServer += (char)c;
			current = System.currentTimeMillis();


    	}
    	return fromServer;
		}catch(SocketTimeoutException e){
			mySocket.close();
			return fromServer;
		}
    }
}
