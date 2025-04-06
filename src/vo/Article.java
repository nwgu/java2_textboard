package vo; // vo --> Value Object 의 약자 (데이터를 담아 전달하는 목적으로 보통 생성)

// 게시글을 담당하는 클래스 
public class Article {

	private int articleId;
	private String title;
	private String body;

	public int getArticleId() {
		return articleId;
	}

	public void setArticleId(int articleId) {
		this.articleId = articleId;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getBody() {
		return body;
	}

	public void setBody(String body) {
		this.body = body;
	}

}
