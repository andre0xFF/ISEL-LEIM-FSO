package MappedMemory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;

abstract class ProcessCOM {
	private File file;
	private FileChannel channel;
	private MappedByteBuffer buffer;
	private final int BUFFER_MAX = 30;
	private RandomAccessFile raf;
	
	public ProcessCOM() {
		file = new File("processcom");
		try {
				raf = new RandomAccessFile(file, "rw");
				channel = raf.getChannel();
		} catch (FileNotFoundException e) { e.printStackTrace(); }
		
		try {
			buffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, BUFFER_MAX);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	protected String receiveMessage() {
		String message = new String();
		buffer.position(0);
			
		for(char c = buffer.getChar(); c != '\0'; c = buffer.getChar()) {
			message += c;
		}
		
		return message;
	}

	protected void sendMessage(String message) {
		buffer.position(0);
		for (int i = 0 ; i < message.length() ; ++i) {
			buffer.putChar(message.charAt(i));
		}
		
		buffer.putChar('\0');
	}
	
	protected void closeChannel() {
		try {
			channel.close();
			raf.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}

