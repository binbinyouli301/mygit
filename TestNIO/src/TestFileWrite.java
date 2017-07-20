import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class TestFileWrite {
	public static void main(String[] args) throws IOException {
		FileOutputStream fis=new FileOutputStream("bb.txt");
		FileChannel channel = fis.getChannel();
		
		//声明缓冲区
		String str="hello world 你好吗,我很好";
		ByteBuffer buffer=ByteBuffer.allocate(str.getBytes().length);
		buffer.put(str.getBytes());
		buffer.flip();
		channel.write(buffer);
		
		channel.close();
		
	}
}
