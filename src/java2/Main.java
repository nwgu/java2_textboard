package java2;

import java.util.Scanner;

public class Main {
	public static void main(String[] args) {

		Scanner sc = new Scanner(System.in);

		System.out.println("텍스트 게시판 시작");

		while (true) {
			System.out.print("명령어 : ");

			String request = sc.next();

			if (request.equals("/exit")) {
				System.out.println("프로그램 종료");
				break;
			}
		}
	}
}
