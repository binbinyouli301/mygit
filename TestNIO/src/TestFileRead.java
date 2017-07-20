import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;


public class TestFileRead {
	public static void main(String[] args) throws IOException {
		FileInputStream fis=new FileInputStream("aa.txt");
		FileChannel channel = fis.getChannel();
		
		//声明缓冲区
		ByteBuffer buffer=ByteBuffer.allocate(1024);
		
		ByteArrayOutputStream bos=new ByteArrayOutputStream();
		while(true){
			buffer.clear();
			int len = channel.read(buffer);
			if(len==-1) break;
			buffer.flip();
			//读取数据
			while(buffer.hasRemaining()){
				bos.write(buffer.get());
			}
		}
		System.out.println(new String(bos.toByteArray()));
		channel.close();
	}
}
