import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;


public class TestBIOClient {
	//{type:"login",username:"zhangsan",password:"*****"}
	//{rescode:200,resstatus:"ok"}
	public static void main(String[] args) throws IOException {
		for(int i=0;i<100;i++){
			System.out.println(sendComand("客户端发送:"+i+"次请求"));
		}
	}
	public static String sendComand(String comand)throws IOException{
		Socket socket=new Socket();
		socket.connect(new InetSocketAddress(9999));
		
		//写数据到服务器
		OutputStream outputStream = socket.getOutputStream();
		PrintWriter printWriter=new PrintWriter(outputStream);
		printWriter.println(comand);
		printWriter.flush();
		socket.shutdownOutput();
		
		
		//读取服务器端数据
		InputStream inputStream = socket.getInputStream();
		InputStreamReader isr=new InputStreamReader(inputStream);
		BufferedReader br=new BufferedReader(isr);
		String value=null;
		StringBuilder sb=new StringBuilder();
		while((value=br.readLine())!=null){
			sb.append(value);
		}
		socket.close();
		return sb.toString();
	}
}
