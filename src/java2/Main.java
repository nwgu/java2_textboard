package java2;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

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

			if (request.equals("/get/article")) {

				if (articleList.size() == 0) {
					System.out.println("게시글이 존재하지 않습니다.");
				} else {
					System.out.println("==== 게시글 리스트 ====");
					for (int i = articleList.size() - 1; i >= 0; i--) {
						System.out.println("번호 : " + articleList.get(i).getArticleId());
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

				// 하나의 게시글 Map
				Article article = new Article();
				articleLastId++;
				article.setArticleId(articleLastId);
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
