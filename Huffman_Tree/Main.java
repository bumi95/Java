import java.util.Scanner;

public class Main {

	public static void main(String[] args) {
		System.out.print("하나의 영문 파일을 입력하시오(space문자 포함): ");
		Scanner sc = new Scanner(System.in);
		String eng = sc.nextLine(); // 영문 입력
		
		Huffman huff = new Huffman(); // 호프만 클래스 생성
		huff.count(eng); // 영문파일 입력
		huff.HuffmanTree(); // 최소히프에 문자와 빈도수를 저장하는 함수 실행
		int []arr = new int[huff.num.size()-1]; // 트리 추적을 위한 배열 선언
		
		System.out.println("각 문자에 할당된 코드");
		huff.showWord(huff.huffman, arr, 0); // 호프만 코드 출력
		sc.close();
	}
}
