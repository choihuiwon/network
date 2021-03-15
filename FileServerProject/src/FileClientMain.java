import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 파일명 : FileClientWorker
 * 프로젝트명 : FileServerProject
 * 작성일 : 2021-03-15
 * 작성자 : 최희원
 *@version 0.0.1
 * 개요 : 클라이언트 프로그램
 */
public class FileClientMain {
	public static void main(String[] args) {
		
		String fileName = "info.png";
		Socket server = null;
		FileInputStream fis = null;
		DataOutputStream dos = null;
		
		try {
			// 서버 접속
			server = new Socket("127.0.0.1",1234);
			// 스트림 초기화
			fis = new FileInputStream(fileName);	// 파일 클래스
			dos = new DataOutputStream(server.getOutputStream());
			
			
			// 파일명을 전송
			dos.writeUTF(fileName);
			
			// 서버로 파일을 전송
			// buffer
			byte[]  buffer = new byte[1024];
			int total = 0;
			while(true) {
				int count = fis.read(buffer);
				if(count == -1) break;
				dos.write(buffer, 0, count);
				dos.flush();
				total += count;	// 전송한 바이트 수 누적
			}
			System.out.println("파일 전송 완료");
			System.out.println(total + "byte 전송");
			
		} catch (IOException e) {
			e.printStackTrace();
		}finally {
			// close
			try {
				if(dos!=null) dos.close();
				if(fis!=null) fis.close();
				if(server!=null) server.close();
			} catch (IOException e2) {
				e2.printStackTrace();
			}
		}
	}
}
