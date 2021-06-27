import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.print("두 문자열의 길이를 입력하시오: ");
		Scanner sc = new Scanner(System.in);
		int n = sc.nextInt();
		int m = sc.nextInt();
		sc.nextLine();
		System.out.print("X 문자열을 입력하시오: ");
		String x = sc.nextLine();

		System.out.print("Y 문자열을 입력하시오: ");
		String y = sc.nextLine();
		
		System.out.print("삽입, 삭제, 교체 비용을 입력하시오: ");
		int ic = sc.nextInt();
		int dc = sc.nextInt();
		int cc = sc.nextInt();
		sc.close();
		
		MyEdit e = new MyEdit(ic, dc, cc, x, y); // MyEdit클래스 객체 생성
		e.myeditF(n+1, m+1); // cost 배열의 크기 전달
	}
}
