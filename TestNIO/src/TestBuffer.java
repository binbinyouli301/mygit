import java.nio.ByteBuffer;


public class TestBuffer {
	public static void main(String[] args) {
		byte[] bytes=new byte[10];
		ByteBuffer buffer=ByteBuffer.allocate(10);//ByteBuffer.wrap(bytes);
		
		//查看状态
		System.out.println("position:"+buffer.position());
		System.out.println("limit:"+buffer.limit());
		System.out.println("remining:"+buffer.remaining());
		System.out.println("capacity:"+buffer.capacity());

		//write 2 bytes
		System.out.println("======write 2 bytes=======");
		buffer.put((byte)97);
		buffer.put((byte)98);
		System.out.println("position:"+buffer.position());
		System.out.println("limit:"+buffer.limit());
		System.out.println("remining:"+buffer.remaining());
		System.out.println("capacity:"+buffer.capacity());
		
		//读取数据
		System.out.println("=====遍历====");
		/*buffer.limit(buffer.position());
		buffer.position(0);*/
		buffer.flip();
		while(buffer.hasRemaining()){
			System.out.println(buffer.get());
		}
		System.out.println("===============");
		//=====再次写1个byte====
		buffer.clear();
		buffer.put((byte)99);
		buffer.flip();
		while(buffer.hasRemaining()){
			System.out.println(buffer.get());
		}
	}
}
