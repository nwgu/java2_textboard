package java2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import util.CurrentDateTime;
import vo.Article;

public class Main {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// 게시글 목록 저장소
		List<Article> articleList = new ArrayList<Article>();
		int articleLastId = 0;

		System.out.println("텍스트 게시판 시작");

		while (true) {
			System.out.print("명령어 : ");

			String request = sc.next();

			// equals 로 하면 동적으로 변하는 게시글 번호값을 가져올 수 없음.
			// 그래서 contains 아니면 startsWith 메서드를 써야 함.
			if (request.contains("/get/article?articleId=")) {
				// = 를 기준으로 쪼개기.
				// 조각이 생기기 때문에 [배열]이 리턴되는 것
				// ["/get/article?articleId", "특정정수값"]
				String[] params = request.split("=");

				// params[1] 에는 특정 정수값이 들어있는데 현재는 문자열
				// 그래서 Integer.parseInt 메서드로 정수화 진행
				// 곧 비교해야 할 articleList 안에 들어있는 게시글 번호는 정수이기때문에..
				int articleId = Integer.parseInt(params[1]); // ----> 사용자가 입력한 값

				for (int i = 0; i < articleList.size(); i++) {
					// 이미 게시글에 등록된 번호랑 사용자가 입력한 값이랑 비교
					if (articleList.get(i).getArticleId() == articleId) {
						// 여기를 통과하면 값을 찾은 것임.
						System.out.println("====" + articleList.get(i).getArticleId() + "번 게시글 ====");
						System.out.println("작성일 : " + articleList.get(i).getRegDate());
						System.out.println("제목 : " + articleList.get(i).getTitle());
						System.out.println("내용 : " + articleList.get(i).getBody());
					}
				}

			}

			else if (request.equals("/get/article")) {

				if (articleList.size() == 0) {
					System.out.println("게시글이 존재하지 않습니다.");
				} else {
					System.out.println("==== 게시글 리스트 ====");
					for (int i = articleList.size() - 1; i >= 0; i--) {
						System.out.println("번호 : " + articleList.get(i).getArticleId());
						System.out.println("작성일 : " + articleList.get(i).getRegDate());
						System.out.println("제목 : " + articleList.get(i).getTitle());
						System.out.println("내용 : " + articleList.get(i).getBody());
						System.out.println();
					}
				}
			}

			else if (request.equals("/post/article")) {

				sc.nextLine(); // 버퍼 비우기

				System.out.print("제목 : ");
				String title = sc.nextLine(); // 공백 및 엔터를 가져감.

				System.out.print("내용 : ");
				String body = sc.nextLine();

				// 하나의 게시글 클래스
				Article article = new Article();
				articleLastId++;
				article.setArticleId(articleLastId);
				// 현재시간 객체안의 메서드 호출 (static 이라서 이렇게 가능)
				article.setRegDate(CurrentDateTime.now());
				article.setTitle(title);
				article.setBody(body);

				// 게시글 목록에 게시글 저장
				articleList.add(article);

				System.out.println("게시글이 작성되었습니다.");
			}

			else if (request.equals("/get/help")) {

				System.out.println("=== 도움말 ===");
				System.out.println("/exit - 프로그램 종료");
				System.out.println("/get/help - 도움말 출력");

				System.out.println("/post/article - 게시글 작성");
				System.out.println("/get/article - 게시글 리스트 출력");

			} else if (request.equals("/exit")) {
				System.out.println("프로그램 종료");
				sc.close();
				break;

			} else {
				System.out.println("존재하지 않는 명령어 입니다.");

			}
		}
	}
}
