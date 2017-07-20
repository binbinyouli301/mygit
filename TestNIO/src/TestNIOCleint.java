import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public class TestNIOCleint {
   public static void main(String[] args) throws IOException {
	  //创建SocketChannel
	   SocketChannel sc=SocketChannel.open();
	   sc.connect(new InetSocketAddress(9999));//连接服务器
	   
	   String msg="你好服务器,我是客户端";
	   ByteBuffer buffer=ByteBuffer.allocate(msg.getBytes().length);
	   buffer.put(msg.getBytes());
	   buffer.flip();
	   sc.write(buffer);
	   sc.socket().shutdownOutput();//老版本NIO API
	   //sc.shutdownOutput();//新版本NIO API
	   
	   //读取服务器数据
	   ByteArrayOutputStream bos=new ByteArrayOutputStream();
	   while(true){
		   buffer.clear();
		   int len = sc.read(buffer);
		   if(len==-1) break;
		   buffer.flip();
		   while(buffer.hasRemaining()){
			   bos.write(buffer.get());
		   }
	   }
	   System.out.println("客户端收到:"+new String(bos.toByteArray()));
	   sc.close();
   }
}
