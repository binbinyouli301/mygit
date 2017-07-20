import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;


public class TestNIOServer {
   public static void main(String[] args) throws IOException {
	   //创建ServerSocketChannel
	   ServerSocketChannel ssc=ServerSocketChannel.open();
	   //设置通道非阻塞
	   ssc.configureBlocking(false);
	   //绑定监听端口
	   ssc.socket().bind(new InetSocketAddress(9999));//老版NIO的写法
	   //ssc.bind(new InetSocketAddress(9999));//新版本 NIO的写法
	   
	   //创建一个通道选择器
	   Selector selector=Selector.open();
	   //注册通道到通道选择器当中，第二个参数为需要调的IO事件
	   ssc.register(selector, SelectionKey.OP_ACCEPT);
	   
	   while(true){
		   //返回可用的通道个数
		   System.out.println("选择可用通道....");
		   int num = selector.select();
		   if(num<=0) continue;
		   
		   //获取可用(就绪的)通道
		   Iterator<SelectionKey> iterator = selector.selectedKeys().iterator();
		   while(iterator.hasNext()){
				//得到一个事件类型或是通道
			   SelectionKey key = iterator.next();
			   if(key.isAcceptable()){
				   System.out.println("有人链接我");
				   //获得上面注册的ssc
				   ServerSocketChannel serverSocketChannel=(ServerSocketChannel) key.channel();
				   SocketChannel sc = serverSocketChannel.accept();
				   sc.configureBlocking(false);//设置非阻塞
				   sc.register(selector, SelectionKey.OP_READ);
			   }if(key.isReadable()){	//判断请求是否可读
				   System.out.println("读取客户端数据....");
				   SocketChannel sc=(SocketChannel) key.channel();
				   ByteBuffer buffer=ByteBuffer.allocate(1024);
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
				   System.out.println("服务器收到:"+new String(bos.toByteArray()));
				   //注册写事件
				   sc.register(selector, SelectionKey.OP_WRITE,bos);
			   }if(key.isWritable()){
				   SocketChannel sc=(SocketChannel) key.channel();
				   ByteArrayOutputStream bos = (ByteArrayOutputStream) key.attachment();
				   bos.write("服务器追加数据".getBytes());
				   ByteBuffer buffer=ByteBuffer.allocate(bos.toByteArray().length);
				   buffer.put(bos.toByteArray());
				   buffer.flip();
				   sc.write(buffer);
				   sc.close();
			   }
			   //将处理过的事件从通道里面移除，避免多次被处理
			   iterator.remove();
		   }
	   }
	   
   }
}
