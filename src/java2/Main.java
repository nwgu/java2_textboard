package java2;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import util.CurrentDateTime;
import vo.Article;
import vo.User;

public class Main {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		// 유저 목록 저장소
		List<User> userList = new ArrayList<User>();

		// 로그인 유저의 상태를 유지시키기 위한 변수 생성 (세션)
		// https://nwgu.github.io/java/2025/04/05/java2/#-%EC%84%B8%EC%85%98
		User userSession = null; // 이게 null 이면 로그아웃 상태

		int userLastId = 0; // 유저 고유 번호도 유니크하며, 1씩 증가되어야 함.

		// 게시글 목록 저장소
		List<Article> articleList = new ArrayList<Article>();

		int articleLastId = 0;

		System.out.println("텍스트 게시판 시작");

		while (true) {
			System.out.print("명령어 : ");

			String request = sc.next();

			/**************** 유저 ****************/

			if (request.startsWith("/post/update/pw/user")) { // 유저 비밀번호 변경
				if (userSession == null) {
					System.out.println("로그인이 필요한 기능 입니다.");
					continue;
				}

				System.out.print("새로운 비밀번호 : ");
				String newPw = sc.next();
				System.out.print("비밀번호 확인 : ");
				String newPwConfirm = sc.next();

				if (!newPw.equals(newPwConfirm)) {
					System.out.println("비밀번호가 일치하지 않습니다.");

				} else {
					userSession.setUserLoginPw(newPw);
					System.out.println("회원정보가 변경되었습니다. 다시 로그인 해주세요.");
					userSession = null;
				}
			}

			else if (request.equals("/auth/logout")) { // 로그아웃
				if (userSession != null) {
					userSession = null;
					System.out.println("로그아웃 완료");
				}
			}

			else if (request.equals("/auth/login")) { // 로그인

				if (userSession != null) {
					System.out.println("이미 로그인된 상태 입니다.");
					continue;
				}

				System.out.print("아이디 : ");
				String userLoginId = sc.next();

				System.out.print("비밀번호 : ");
				String userLoginPw = sc.next();

				User findByUser = null;

				for (User user : userList) {
					if (user.getUserLoginId().equals(userLoginId) && user.getUserLoginPw().equals(userLoginPw)) {
						findByUser = user;
					}
				}

				if (findByUser == null) {
					System.out.println("회원정보가 일치하지 않습니다.");
				} else {
					// 로그인 완료
					userSession = findByUser;
					System.out.println("로그인이 완료 되었습니다. " + findByUser.getUserName() + "님 안녕하세요~!");
				}

			}

			else if (request.equals("/post/user")) { // 회원가입

				System.out.print("사용할 아이디 : ");
				String userLoginId = sc.next();

				/* 유저 로그인 아이디 중복 체크 시작 */
				boolean chkUserLoginId = false;

				for (User user : userList) {
					if (user.getUserLoginId().equals(userLoginId)) {
						chkUserLoginId = true;
					}
				}

				if (chkUserLoginId) {
					System.out.println("이미 가입된 회원 입니다.");
					continue;
				}
				/* 유저 로그인 아이디 중복 체크 끝 */

				System.out.print("사용할 비밀번호 : ");
				String userLoginPw = sc.next();

				if (userLoginPw.length() < 5) {
					System.out.println("비밀번호는 최소 4자리 이상 입력해주세요.");
					continue;
				}

				System.out.print("비밀번호 확인 : ");
				String userLoginPwConfirm = sc.next();

				if (!userLoginPw.equals(userLoginPwConfirm)) {
					System.out.println("비밀번호를 다시 확인해 주세요.");
					continue;
				}

				System.out.print("사용자 이름 : ");
				String userName = sc.next();

				User user = new User();

				userLastId++;
				user.setUserId(userLastId);
				user.setUserLoginId(userLoginId);
				user.setUserLoginPw(userLoginPw);
				user.setUserName(userName);
				user.setRegDate(CurrentDateTime.now());

				userList.add(user);

				System.out.println("회원가입이 완료 되었습니다.");
			}

			/**************** 게시글 ****************/
			else if (request.startsWith("/post/update/article?articleId=")) { // 게시글 수정

				if (userSession == null) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

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

				Article findByArticle = null; // 게시글 찾았을 때 담을 임시 Article

				for (Article article : articleList) {
					if (article.getArticleId() == articleId) {
						findByArticle = article; // 게시글을 찾았다면 Article 을 통째로 임시 Article 에 넣어준다.
					}
				}

				// 여기서 최종적으로 findByArticle 의 값을 체크
				if (findByArticle == null) {
					System.out.println(articleId + "번 게시글은 존재하지 않습니다.");

				} else {

					sc.nextLine();

					System.out.print("수정할 게시글 제목 : ");
					String newTitle = sc.nextLine();
					findByArticle.setTitle(newTitle); // 만들어 두었던 setter 메서드로 값을 수정 (새로 세팅)

					System.out.print("수정할 게시글 내용 : ");
					String newBody = sc.nextLine();
					findByArticle.setBody(newBody);

					System.out.println(articleId + "번 게시글 수정이 완료되었습니다.");
				}

			}

			else if (request.startsWith("/get/search/article")) {

				if (userSession == null) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

				// \\이스케이프 를 쓰는 이유는 자바에서 물음표(?) 자체를 문자로 인식하지 않기 때문에 문자 자체를 알려주기 위함.
				String[] path = request.split("\\?");
				// 이렇게 자르게 되면 path 에는 [ "/get/search/article", "title=title&body=body" ] 이런식으로
				// 들어있게 된다.
				// 해당 기능은 title 파라메터 혹은 body 파라메터 둘 다 필요로 하기 때문에 하나씩 쪼개는 작업이 필요함.
				// 우선 크게 & 를 잘라야 됨. request.split("&");
				// String params = request.split("&"); --> params 에는 & 를 기준으로 잘랐기 때문에 1개가
				// 들어있을수도, 2개가 들어있을수도있음.
				// 동적으로 계속 변할수도 있기 때문에 for 문을 쓰면 됨.

				// 파라메터 저장 Map
				Map<String, String> paramsMap = new HashMap<>();

				for (String params : path[1].split("&")) {
					// 마지막으로 한 번 더 split 을 해줘야 함
					// params 에는 title=title 혹은 body=body 의 단순 문자열 형태가 들어가 있는데
					// 추후 값을 꺼내고 관리하기 위해서 이 문자열을 Map 객체 안에다가 넣어줘야 됨 .
					String[] keyValues = params.split("=");

					// --> 이렇게 하면 추후 어떤 파라메터가 들어오든지 전부 다 쓰기 편한 Map 형태로 만들어주기 때문에 다른 곳에다가 응용해도 됨
					paramsMap.put(keyValues[0], keyValues[1]);
				}

				// 여기까지 통과가 됐다면, 현재 paramsMap 안에는 사용자가 쓴 파라메터 값들이 Map 형태로 정렬 되어있다는 뜻.

				// 입력된 값을 새로운 변수 안에다가 넣어준다.
				// 이걸 하려고 위에서부터 문자열 split 을 진행한 것
				String title = paramsMap.get("title");
				String body = paramsMap.get("body");

				// 이제 for 문을 돌면서 해당 값들을 찾아야 함 .
				// 우선 조건이 3개가 존재한다.
				// 1. title 과 body 가 둘 다 존재할 때
				// 2. title 하나만 있을 때
				// 3. body 하나만 있을 때

				List<Article> findByArticles = new ArrayList<>();

//				버전 1
				for (Article article : articleList) {
					if (title != null && body != null) { // title 과 body 가 둘 다 존재할 때

						// title 과 body 가 전부 비교해서 해당 값과 맞는 게시글 찾아서 임시 리스트 변수에 추가
						if (article.getTitle().contains(title) && article.getBody().contains(body)) {
							findByArticles.add(article);
						}

					} else if (title != null) { // title 만 존재할 때

						// title 을 비교해서 해당 값과 맞는 게시글 찾아서 임시 리스트 변수에 추가
						if (article.getTitle().contains(title)) {
							findByArticles.add(article);
						}

					} else if (body != null) { // body 만 존재할 때

						// body 을 비교해서 해당 값과 맞는 게시글 찾아서 임시 리스트 변수에 추가
						if (article.getBody().contains(body)) {
							findByArticles.add(article);
						}
					}
				}

				// 여기서 최종적으로 findByArticles 에 값이 있는지 없는지 체크
				if (findByArticles.size() == 0) {
					System.out.println("찾는 게시글이 존재하지 않습니다.");

				} else {
					System.out.println("==== 검색된 게시글 ====");

					for (Article article : findByArticles) {
						System.out.println("====" + article.getArticleId() + "번 게시글 ====");
						System.out.println("작성일 : " + article.getRegDate());
						System.out.println("제목 : " + article.getTitle());
						System.out.println("내용 : " + article.getBody());
					}
				}

			}

			// equals 로 하면 동적으로 변하는 게시글 번호값을 가져올 수 없음.
			// 그래서 contains 아니면 startsWith 메서드를 써야 함.
			else if (request.startsWith("/get/article?articleId=")) {

				if (userSession == null) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

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
					if (articleList.get(i).getArticleId() == articleId
							&& articleList.get(i).getUserId() == userSession.getUserId()) {
						// 여기를 통과하면 값을 찾은 것임.
						// 찾은 articleList 안에 있는 article 객체를
						// 위에 임시 Article 클래스 변수 안에다가 할당
						article = articleList.get(i);
					}
				}

				// for 문의 끝나고 최종적으로 article 안에 값이 null 이라면,
				// 입력했던 번호의 게시글을 찾지 못한 것.
				if (article == null) {
					System.out.println(articleId + "번 게시글은 존재하지 않거나, 권한이 없습니다.");

				} else {
					System.out.println("====" + article.getArticleId() + "번 게시글 ====");
					System.out.println("작성일 : " + article.getRegDate());
					System.out.println("작성자 : " + article.getUserName());
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

				if (userSession == null) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

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
					if (articleList.get(i).getArticleId() == articleId
							&& articleList.get(i).getUserId() == userSession.getUserId()) {
						articleList.remove(i); // 현재 찾은 게시글의 인덱스 삭제 즉, 찾은 게시글 게시글 삭제 처리
						isRemoveArticle = true;
						break; // 찾았다면 더 지체하지말고 for 문 브레이크.
					}
				}

				if (isRemoveArticle) {
					System.out.println(articleId + "번 게시글을 삭제하였습니다.");

				} else {
					System.out.println(articleId + "번 게시글은 존재하지 않거나, 권한이 없습니다.");
				}

			}

			else if (request.startsWith("/get/article?sort=")) {

				if (userSession == null) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

				String[] params = request.split("=");

				if (params.length < 2) {
					System.out.println("정렬 값을 입력해주세요. (desc, asc)");
					continue;
				}

				String sort = params[1]; // 이 값은 desc, asc 혹은 의미 없는 값이 리턴 됨.

				if (sort.equals("desc")) { // 내림차순 (최신)

					Collections.reverse(articleList); // 컬렉션 관련 유용한 유틸에서 reverse 메서드 사용

					System.out.println("==== " + userSession.getUserName() + " 님이 작성한 게시글 리스트 ====");
					for (Article article : articleList) {
						// 게시글에 등록된 작성자 고유 아이디와 현재 로그인된(세션) 유저의 고유 아이디를 비교한다.
						// 여기를 통과하면 본인이 작성한 게시글만 보이게 되는 것.
						if (article.getUserId() == userSession.getUserId()) {
							System.out.println("번호 : " + article.getArticleId());
							System.out.println("작성자 : " + article.getUserName());
							System.out.println("제목 : " + article.getTitle());
							System.out.println("내용 : " + article.getBody());
							System.out.println();
						}
					}

					Collections.reverse(articleList); // 객체 값들은 계속 공유 되고 있기 때문에 거꾸로 돌렸다면, 다시 되돌려주는 작업 적용

				} else if (sort.equals("asc")) { // 오름차순 (과거)

					System.out.println("==== " + userSession.getUserName() + " 님이 작성한 게시글 리스트 ====");

					for (Article article : articleList) {
						if (article.getUserId() == userSession.getUserId()) {
							System.out.println("번호 : " + article.getArticleId());
							System.out.println("작성자 : " + article.getUserName());
							System.out.println("제목 : " + article.getTitle());
							System.out.println("내용 : " + article.getBody());
							System.out.println();
						}
					}

				} else {
					System.out.println("유효한 정렬 값을 입력해주세요. (desc, asc)");
				}

			}

			else if (request.equals("/post/article")) {

				if (userSession == null) {
					System.out.println("로그인이 필요한 기능입니다.");
					continue;
				}

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
				article.setUserName(userSession.getUserName()); // 세션에서 유저 이름 꺼내오기
				article.setUserId(userSession.getUserId()); // 세션에서 유저 번호 꺼내오기

				// 게시글 목록에 게시글 저장
				articleList.add(article);

				System.out.println("게시글이 작성되었습니다.");
			}

			else if (request.equals("/get/help")) {

				System.out.println("=== 도움말 ===");
				System.out.println("/exit - 프로그램 종료");
				System.out.println("/get/help - 도움말 출력");
				System.out.println("/get/article?articleId= - 게시글 상세 출력");
				System.out.println("/post/user -> 유저 생성");
				System.out.println("/auth/login -> 로그인");
				System.out.println("/auth/logout -> 로그아웃");

				System.out.println("/post/article -> 게시글 작성");
				System.out.println("/post/update/article?articleId= -> 게시글 수정");
				System.out.println("/post/remove/article?articleId= -> 게시글 삭제");
				System.out.println("/post/removeAll/article -> 게시글 전부 삭제");
				System.out.println("/get/article?sort={sort} -> 게시글 출력(desc, asc)");

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
