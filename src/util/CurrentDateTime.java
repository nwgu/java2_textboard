package util; // 유틸들만 모아두는 패키지, 주요 기능은 아니지만 개발에 필요한 기능들을 모아둠

import java.text.SimpleDateFormat;
import java.util.Date;

public class CurrentDateTime {
	public static String now() {
		// 시간 객체 가져오기
		Date now = new Date();
		// 포멧 맞추기 (형태 맞추기)
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		// 현재 시간 가져오기
		String formatedNow = formatter.format(now);

		return formatedNow;
	}
}
