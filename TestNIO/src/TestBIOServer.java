import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;


public class TestBIOServer {
	public static void main(String[] args) throws IOException, InterruptedException {
		server2();
	}
	public static void server()throws IOException, InterruptedException{
		ServerSocket ss=new ServerSocket(9999);
		//阻塞直达用用户请求到来返回一个socket (输入/输出流)
		while(true){
			System.out.println("服务器监听9999 port...");
			final Socket socket = ss.accept();
			System.out.println("有请求过来....");
			//读取客户端数据
			InputStream inputStream = socket.getInputStream();
			InputStreamReader isr=new InputStreamReader(inputStream);
			BufferedReader br=new BufferedReader(isr);
			String value=null;
			StringBuilder sb=new StringBuilder();
			while((value=br.readLine())!=null){
				sb.append(value);
			}
			Thread.sleep(2000);
			System.out.println("服务器收到:"+sb.toString());
			
			//写数据到客户端
			OutputStream outputStream = socket.getOutputStream();
			PrintWriter printWriter=new PrintWriter(outputStream);
			printWriter.println("你好客户端,这是服务器..");
			printWriter.flush();
			
			socket.close();			
		}
		
	}
	public static void server1()throws IOException, InterruptedException{
		ServerSocket ss=new ServerSocket(9999);
		//阻塞直达用用户请求到来返回一个socket (输入/输出流)
		while(true){
			System.out.println("服务器监听9999 port...");
			final Socket socket = ss.accept();
			new Thread(){
				public void run() {
					try {
						System.out.println("有请求过来....");
						//读取客户端数据
						InputStream inputStream = socket.getInputStream();
						InputStreamReader isr=new InputStreamReader(inputStream);
						BufferedReader br=new BufferedReader(isr);
						String value=null;
						StringBuilder sb=new StringBuilder();
						while((value=br.readLine())!=null){
							sb.append(value);
						}
						//Thread.sleep(10000);
						System.out.println("服务器收到:"+sb.toString());
						
						//写数据到客户端
						OutputStream outputStream = socket.getOutputStream();
						PrintWriter printWriter=new PrintWriter(outputStream);
						printWriter.println("你好客户端,这是服务器..");
						printWriter.flush();
						
						socket.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				};
			}.start();
		}
	}
	public static void server2()throws IOException, InterruptedException{
		ServerSocket ss=new ServerSocket(9999);
		ExecutorService executorService=Executors.newFixedThreadPool(10);
		//阻塞直达用用户请求到来返回一个socket (输入/输出流)
		while(true){
			System.out.println("服务器监听9999 port...");
			final Socket socket = ss.accept();
			executorService.submit(new Runnable() {
				
				public void run() {
					// TODO Auto-generated method stub
					try {
						System.out.println("有请求过来....");
						//读取客户端数据
						InputStream inputStream = socket.getInputStream();
						InputStreamReader isr=new InputStreamReader(inputStream);
						BufferedReader br=new BufferedReader(isr);
						String value=null;
						StringBuilder sb=new StringBuilder();
						while((value=br.readLine())!=null){
							sb.append(value);
						}
						System.out.println("服务器收到:"+sb.toString()+" ThreadID:"+Thread.currentThread().getId());
						
						//写数据到客户端
						OutputStream outputStream = socket.getOutputStream();
						PrintWriter printWriter=new PrintWriter(outputStream);
						printWriter.println("你好客户端,这是服务器..");
						printWriter.flush();
						
						socket.close();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			});
		}
	}
}
