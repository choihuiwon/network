import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;

/**
 * 파일명 : FileServerMain
 * 프로젝트명 : FileServerProject
 * 작성일 : 2021-03-15
 * 작성자 : 최희원
 *@version 0.0.1
 * 개요 : 서버 프로그램
 */
public class FileServerMain {
	public static ArrayList<FileServerWorker> list = new ArrayList<FileServerWorker>();
	public static void closeWorker(FileServerWorker worker) {
		list.remove(worker);
		System.out.println("현재 접속중인 클라이언트 수 : " + list.size());
	}
	public static void main(String[] args) {
		ServerSocket server = null;
		Socket client = null;

		try {
			server = new ServerSocket(1234);
			
			while(true) {
				client = server.accept();
				
				System.out.println(client.getInetAddress()+"님 접속");
				
				// 클라이언트 정보를 가지고 클라이언트와 통신을 담당할 스레드를 생성, 실행
				FileServerWorker worker = new FileServerWorker(client);
				worker.start();
				list.add(worker);
				System.out.println("현재 접속중인 클라이언트 : " + list.size());
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
