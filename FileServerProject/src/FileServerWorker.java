import java.io.DataInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

/**
 * 파일명 : FileServerWorker
 * 프로젝트명 : FileServerProject
 * 작성일 : 2021-03-15
 * 작성자 : 최희원
 *@version 0.0.1
 * 개요 : 서버워커 프로그램
 */
public class FileServerWorker extends Thread{
	private DataInputStream dis = null;
	private FileOutputStream fos = null;
	private Socket client;
	
	public FileServerWorker(Socket client) {
		try {
			this.client = client;
			// dis스트림 초기화
			dis = new DataInputStream(client.getInputStream());
			// 파일명을 받음
			String fileName = dis.readUTF();
			System.out.println("사용자가 요청한 파일 : " + fileName);
			
			// 경로
			String parentPath = client.getInetAddress().toString().replace(".", "_");
			String path = "c:\\fileupload\\"+ parentPath + "\\" + fileName;
						
			// 파일 유무 확인
			File file = new File(path);
			if(!file.getParentFile().exists()) file.getParentFile().mkdirs();
			
			// fos스트림 초기화
			fos = new FileOutputStream(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void run() {
		// 클라이언트가 보내는 파일을 받음
		// buffer
		byte[]  buffer = new byte[1024];
			try {
				while(true) {
					int count = dis.read(buffer);
					if(count == -1) break;
					fos.write(buffer, 0, count);
					fos.flush();
				}
				System.out.println("파일 받기 완료");
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				System.out.println(client.getInetAddress() + "님 접속 종료");
				FileServerMain.closeWorker(this);
			}
	}
}
