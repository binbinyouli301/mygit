import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;

import org.apache.commons.io.FileUtils;
import org.junit.Test;


public class TestCopyFile {
	public static void main(String[] args) throws IOException {
		long startTime = System.currentTimeMillis();
		
		FileInputStream fis=new FileInputStream("movie.mkv");
		FileOutputStream fos=new FileOutputStream("movieCopy.mkv");
		
		FileChannel readChanel = fis.getChannel();
		FileChannel writeChanel = fos.getChannel();
		
		//声明缓冲区
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		while(true){
			buffer.clear();
			int len = readChanel.read(buffer);
			if(len==-1) break;
			buffer.flip();
			writeChanel.write(buffer);
		}
		readChanel.close();
		writeChanel.close();
		long end = System.currentTimeMillis();
		System.out.println(end - startTime);
	}
	
	@Test
	public void testCopy5(){
		//20.53
		try {
			FileInputStream fis=new FileInputStream("E:\\TestNIO\\Dawn.mkv");
			FileOutputStream fos=new FileOutputStream("E:\\TestNIO\\Dawn5.mkv");
			
			FileChannel readChanel = fis.getChannel();
			FileChannel writeChanel = fos.getChannel();
			
			//声明缓冲区
			ByteBuffer buffer = ByteBuffer.allocate(1024);
			
			while(true){
				buffer.clear();
				int len = readChanel.read(buffer);
				if(len==-1) break;
				buffer.flip();
				writeChanel.write(buffer);
			}
			readChanel.close();
			writeChanel.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	@Test
	public void testCopy1(){
		//19.99
		InputStream input = null;    
		OutputStream output = null;    
		try {
			input = new FileInputStream(new File("E:\\TestNIO\\Dawn.mkv"));
			output = new FileOutputStream(new File("E:\\TestNIO\\Dawn1.mkv"));        
			byte[] buf = new byte[1024];        
			int bytesRead;        
			while ((bytesRead = input.read(buf)) > 0) {
				output.write(buf, 0, bytesRead);
			}
			input.close();
			output.close();
		}catch(Exception e){

		}
	}

	
	@Test
	public void testCopy2(){
		//22.78
		  FileChannel inputChannel = null;    
	        FileChannel outputChannel = null;    
	    try {
	        inputChannel = new FileInputStream(new File("E:\\TestNIO\\Dawn.mkv")).getChannel();
	        outputChannel = new FileOutputStream(new File("E:\\TestNIO\\Dawn2.mkv")).getChannel();
	        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());
	        inputChannel.close();
	        outputChannel.close();
	    } catch(Exception e) {
	    }
	}
	
	@Test
	public void testCopy3(){
		//6.6
		try {
			FileUtils.copyFile(new File("E:\\TestNIO\\Dawn.mkv"), new File("E:\\TestNIO\\Dawn4.mkv"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCopy4(){
		//5.11
		try {
			Path path = Files.copy(new File("E:\\TestNIO\\Dawn.mkv").toPath(), new File("E:\\TestNIO\\Dawn6.mkv").toPath());
			System.out.println(path.toString());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	
	
	
	
}
