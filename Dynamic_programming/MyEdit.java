import java.util.ArrayList;

public class MyEdit {
	
	ArrayList<Character>X = new ArrayList<>(); // X 문자 arraylist배열
	ArrayList<Character>Y = new ArrayList<>(); // Y 문자 arraylist배열
	String s_edit; // 최적의 편집 순서열
	
	int Insertcost; // 삽입 비용
	int Deletecost; // 삭제 비용
	int Changecost; // 교체 비용
	
	public MyEdit(int ic, int dc, int cc, String x, String y) {
		// 생성자함수
		X.add(null);
		Y.add(null);
		// 인덱스 0의 위치에 null 삽입
		for(int i=0; i<x.length(); i++)
			X.add(x.charAt(i));
		for(int i=0; i<y.length(); i++)
			Y.add(y.charAt(i));
		//string으로 받아온 두 문자열을 문자단위로 나누어 arraylist 배열에 저장
		s_edit = "";
		// 편집 순서열을 빈 상태로 초기화
		Insertcost = ic;
		Deletecost = dc;
		Changecost = cc;
		// 삽입, 삭제, 교체 비용을 초기화
	}
	
	public void myeditF(int n, int m) {
		// 삽입, 삭제, 교체 함수
		Cost c = new Cost(n,m);
		// cost클래스의 객체 생성
		for(int i=0; i<n; i++) { // cost, edit 배열에 각각 값을 삽입
			for(int j=0; j<m; j++) {
				if(i==0 && j>0) { // 삽입 
					c.putCost(i, j, c.getCost(i,j-1)+Insertcost); 
					// cost 배열에 삽입 비용 삽입
					c.putEdit(i, j, "I"); 
					// edit 배열에 삽입 문자열 삽입
				}
				else if(i>0 && j==0) { // 삭제
					c.putCost(i, j, c.getCost(i-1, j)+Deletecost); 
					// cost 배열에 삭제 비용 삽입
					c.putEdit(i, j, "D"); 
					// edit 배열에 삭제 문자열 삽입
				}
				else if(i>0 && j>0) { 
					// 삽입, 삭제, 교체 중 가장 작은 값
					int ic = c.getCost(i, j-1)+Insertcost;
					int dc = c.getCost(i-1, j)+Deletecost;
					int cc; 
			   // 삽입, 삭제, 교체 비용을 비교하기위해 3개의 변수 선언
					if(X.get(i)==Y.get(j))
						cc=c.getCost(i-1, j-1); 
  				// 교체할 문자가 서로 같다면 교체 비용이 0이다.
					else
						cc=c.getCost(i-1, j-1)+Changecost;
					 
					int min = (ic > dc) ? dc : ic;
					min = (min > cc) ? cc : min; 
					// 3개의 비용 중 최소값
					 
					if(min == ic) { // 최소값이 삽입 비용
						c.putCost(i, j, ic);
						c.putEdit(i, j, "I");
					}
					else if(min == dc) { // 최소값이 삭제 비용
						c.putCost(i, j, dc);
						c.putEdit(i, j, "D");
					}
					else if(min == cc) { // 최소값이 교체 비용
						c.putCost(i, j, cc);
						c.putEdit(i, j, "C");
					}
				}
			}
		}
		c.showCost(); // cost 배열 출력
		s_edit = c.backTrackCost(s_edit); // 최적 편집 순서열 s_edit 초기화
		ApplyString(); 
			// 최적 편집 순서열과 입력문자열에 차례로 적용한 문자열 출력
	}
	public void ApplyString() {
		// 최적 편집 순서열 출력, 입력문자열에 차례로 적용한 문자열 출력
		char[] e = new char[s_edit.length()]; 
		// s_edit문자열은 최적의 편집 순서열이 거꾸로 나열되어있기 때문에
		// 역순으로 출력하기 위해 char 배열 e를 선언
		for(int i = 0; i<e.length; i++)
			e[i] = s_edit.charAt(i);
		// 배열 초기화
		System.out.print("최적의 편집 순서열: ");
		for(int i = e.length-1; i>=0; i--)
			System.out.print(e[i]+" ");
		System.out.println();
		// 역순으로 출력
		System.out.print("입력문자열에 차례로 적용하기: ");
		int y=0; // X문자열에 추가된 Y문자열의 마지막 문자 인덱스
		for(int i = e.length-1; i>=0; i--) {
			if(e[i] == 'I') { // 삽입일 경우
				y++;
				X.add(y,Y.get(y)); // X문자열에 Y[y]문자 추가
				for(int t=1; t<X.size(); t++)
					System.out.print(X.get(t));
				System.out.print("  ");
				// 출력
			}
			else if(e[i] == 'D') { // 삭제일 경우
				X.remove(y+1); 
				// Y[y]문자 다음에 있는 첫 번째 X문자 삭제
				for(int t=1; t<X.size(); t++)
					System.out.print(X.get(t));
				System.out.print("  ");
			} // 출력
			else if(e[i] == 'C') { // 교체일 경우
				y++;
				if(X.get(y) == Y.get(y)) { 
				// 교체할 X, Y문자가 서로 같으면 변경 없이 출력
					for(int t=1; t<X.size(); t++)
						System.out.print(X.get(t));
					System.out.print("  ");
				}
				else { // 문자가 다르면 교체 후 출력
					X.set(y, Y.get(y));
					for(int t=1; t<X.size(); t++)
						System.out.print(X.get(t));
					System.out.print("  ");
				}
			}
		}
		System.out.println();
	}
}
