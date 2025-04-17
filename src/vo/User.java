package vo;

public class User {

	private int userId; // 유저고유번호
	private String userLoginId; // 아이디
	private String userLoginPw; // 비밀번호
	private String regDate; // 가입일
	private String userName; // 유저이름
	private boolean userState = true; // 유저 상태

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUserLoginId() {
		return userLoginId;
	}

	public void setUserLoginId(String userLoginId) {
		this.userLoginId = userLoginId;
	}

	public String getUserLoginPw() {
		return userLoginPw;
	}

	public void setUserLoginPw(String userLoginPw) {
		this.userLoginPw = userLoginPw;
	}

	public String getRegDate() {
		return regDate;
	}

	public void setRegDate(String regDate) {
		this.regDate = regDate;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public boolean isUserState() {
		return userState;
	}

	public void setUserState(boolean userState) {
		this.userState = userState;
	}

}
