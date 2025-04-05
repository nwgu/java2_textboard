package java2;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("텍스트 게시판 시작");

		while (true) {
			System.out.print("명령어 : ");

			String request = sc.next();

			if (request.equals("/post/article")) {

				sc.nextLine(); // 버퍼 비우기

				System.out.print("제목 : ");
				String title = sc.nextLine(); // 공백 및 엔터를 가져감.

				System.out.print("내용 : ");
				String body = sc.nextLine();

				// 하나의 게시글 Map
				Map<String, String> article = new HashMap<String, String>();
				article.put("title", title);
				article.put("body", body);
				
				
			}

			else if (request.equals("/get/help")) {

				System.out.println("=== 도움말 ===");
				System.out.println("/exit - 프로그램 종료");
				System.out.println("/get/help - 도움말 출력");

				System.out.println("/post/article - 게시글 작성");

			} else if (request.equals("/exit")) {
				System.out.println("프로그램 종료");
				sc.close();
				break;
			}
		}
	}
}
