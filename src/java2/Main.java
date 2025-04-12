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

			if (request.startsWith("/get/search/article?keyword=")) {

				String[] params = request.split("=");

				if (params.length < 2) {
					System.out.println("키워드를 입력해주세요.");
					continue;
				}

				// 사용자가 입력한 키워드 값 변수 초기화
				// 이제 이 값으로 게시글 안의 제목 값과 부합하다면 전부 출력
				String keyword = params[1];

				// 키워드로 찾아진 모든 게시글을 담기위한 임시 리스트
				List<Article> findByArticleList = new ArrayList<Article>();

				for (int i = 0; i < articleList.size(); i++) {
					// 포함된 찾아야 검색이 되기 때문에 contains 메서드 사용
					if (articleList.get(i).getTitle().contains(keyword)) {
						findByArticleList.add(articleList.get(i));
					}
				}

				// 길이 값이 0 이라면 찾은 키워드가 없다는 뜻
				if (findByArticleList.size() == 0) {
					System.out.println(keyword + " 로 검색된 게시글이 없습니다.");
				} else {
					System.out.println(keyword + " 로 검색된 게시글을 출력합니다.");

					for (int i = 0; i < findByArticleList.size(); i++) {
						System.out.println("번호 : " + findByArticleList.get(i).getArticleId());
						System.out.println("제목 : " + findByArticleList.get(i).getTitle());
						System.out.println("내용 : " + findByArticleList.get(i).getBody());
						System.out.println();
					}
				}
			}

			// equals 로 하면 동적으로 변하는 게시글 번호값을 가져올 수 없음.
			// 그래서 contains 아니면 startsWith 메서드를 써야 함.
			else if (request.startsWith("/get/article?articleId=")) {
				// = 를 기준으로 쪼개기.
				// 조각이 생기기 때문에 [배열]이 리턴되는 것
				// ["/get/article?articleId", "특정정수값"]
				String[] params = request.split("=");

				// split 을 통한 배열의 길이 값이 2보다 작다면,
				// 사용자가 값을 입력하지 않은 것..
				// = 를 기준으로 잘랐기때문에 값을 입력했다면 무조건 배열의 길이 값은 2개 이상임.
				if (params.length < 2) {
					System.out.println("게시글 번호를 입력해주세요.");
					continue;
				}

				int articleId = 0; // try 안으로 변수가 들어가기 때문에 위에 더 넓은 코드 블록에서 활용하기 위한 변수 선언

				try { // ---> 정수화를 해야 하는데 사용자자가 문자정수열이 아닌 일반 문자를 입력했을때를 위한 처리

					// params[1] 에는 특정 정수값이 들어있는데 현재는 문자열
					// 그래서 Integer.parseInt 메서드로 정수화 진행
					// 곧 비교해야 할 articleList 안에 들어있는 게시글 번호는 정수이기때문에..
					articleId = Integer.parseInt(params[1]); // ----> 사용자가 입력한 값

				} catch (NumberFormatException e) { // 어차피 정수가 아니면 parseInt 메서드에서 에러가 뜨는데 해당 에러 Exception 명시
					System.out.println("정수를 입력해주세요.");
					continue;
				}

				// 찾은 게시글 하나를 담을 임시 Article 클래스
				Article article = null;

				for (int i = 0; i < articleList.size(); i++) {
					// 이미 게시글에 등록된 번호랑 사용자가 입력한 값이랑 비교
					if (articleList.get(i).getArticleId() == articleId) {
						// 여기를 통과하면 값을 찾은 것임.
						// 찾은 articleList 안에 있는 article 객체를
						// 위에 임시 Article 클래스 변수 안에다가 할당
						article = articleList.get(i);
					}
				}

				// for 문의 끝나고 최종적으로 article 안에 값이 null 이라면,
				// 입력했던 번호의 게시글을 찾지 못한 것.
				if (article == null) {
					System.out.println(articleId + "번 게시글은 존재하지 않습니다.");

				} else {
					System.out.println("====" + article.getArticleId() + "번 게시글 ====");
					System.out.println("작성일 : " + article.getRegDate());
					System.out.println("제목 : " + article.getTitle());
					System.out.println("내용 : " + article.getBody());
				}

			}

			else if (request.startsWith("/post/removeAll/article")) {
				if (articleList.size() == 0) {
					System.out.println("게시글이 존재하지 않습니다.");
					continue;
				}

				articleList.clear();
				System.out.println("모든 게시글이 삭제되었습니다.");
			}

			else if (request.startsWith("/post/remove/article?articleId=")) {

				/* 유효성 검사 및 예외 처리 시작 */
				String[] params = request.split("=");

				if (params.length < 2) {
					System.out.println("게시글 번호를 입력해주세요.");
					continue;
				}

				int articleId = 0;

				try {
					articleId = Integer.parseInt(params[1]); // ----> 사용자가 입력한 값

				} catch (NumberFormatException e) {
					System.out.println("정수를 입력해주세요.");
					continue;
				}

				/* 유효성 검사 및 예외 처리 끝 */

				/* 게시글이 삭제 되었는지 여부를 알기 위한 임시 변수 */
				boolean isRemoveArticle = false;

				for (int i = 0; i < articleList.size(); i++) {
					if (articleList.get(i).getArticleId() == articleId) {
						articleList.remove(i); // 현재 찾은 게시글의 인덱스 삭제 즉, 찾은 게시글 게시글 삭제 처리
						isRemoveArticle = true;
						break; // 찾았다면 더 지체하지말고 for 문 브레이크.
					}
				}

				if (isRemoveArticle) {
					System.out.println(articleId + "번 게시글을 삭제하였습니다.");

				} else {
					System.out.println(articleId + "번 게시글은 존재하지 않습니다.");
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

			}

			else if (request.equals("/exit")) {
				System.out.println("프로그램 종료");
				sc.close();
				break;

			}

			else if (request.equals(".")) {
				for (int i = 1; i <= 10; i++) {
					// 하나의 게시글 클래스
					Article article = new Article();
					articleLastId++;
					article.setArticleId(articleLastId);
					// 현재시간 객체안의 메서드 호출 (static 이라서 이렇게 가능)
					article.setRegDate(CurrentDateTime.now());
					article.setTitle("제목" + i);
					article.setBody("내용" + i);

					// 게시글 목록에 게시글 저장
					articleList.add(article);
				}

				System.out.println("테스트 게시글이 추가되었습니다.");
			}

			else {
				System.out.println("존재하지 않는 명령어 입니다.");

			}
		}
	}
}
