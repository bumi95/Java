JAVA
====
> 자바를 이용한 프로젝트 혹은 과제를 기술합니다.

[1. Huffman Tree 구현](https://github.com/bumi95/Java/tree/main/Huffman_Tree)
====================
[2. 최적의 편집 순서열 구현](https://github.com/bumi95/Java/tree/main/Dynamic_programming)
=========================
## 문제 정의
> 두 개의 문자열을 입력 받고 삽입, 삭제 및 교체 비용에 따라 최적의 문자열 편집 순서를 결정합니다.
* 입력으로는 두 문자열과 길이, 삽입 비용, 삭제 비용, 교체 비용을 입력 받습니다.
* 출력으로는 비용(i,j) 표와 최적의 편집 순서열(맨 앞 연산부터 차례로), 입력 문자열에 차례로 적용한 내용을 출력합니다.
* 편집 순서가 올바른지 테스트하기 위해 문자열 X에 적용하여 최종 결과가 문자열 Y가 되는지 확인하는 함수를 포함합니다.
## 문제 설계
> 문자열 편집을 위한 edit 클래스를 구현합니다.
* 두 문자열을 표현하기 위한 arraylist 자료구조
* 삽입, 삭제, 교체 비용
* 문자열 편집 함수
* 최적의 편집 순서열 출력 함수
> 편집 문자 표와 비용 표를 출력하기 위한 cost 클래스를 구현합니다.
* 비용을 출력하기 위한 2차원 배열과 편집 문자를 담을 2차원 배열
* 비용 삽입, 출력 함수 및 편집 문자 삽입, 출력 함수
* 비용 표 출력 함수
* 최적의 편집 순서열 탐색 함수
> 메인 클래스에서는 문자열과 길이 등을 입력 받는 메인 함수만을 포함합니다.
## 구현 및 결과
### edit 클래스
> X와 Y 문자열에 대한 arraylist 배열을 선언합니다.
```java
ArrayList<Character>X = new ArrayList<>(); // X 문자 arraylist배열
ArrayList<Character>Y = new ArrayList<>(); // Y 문자 arraylist배열
```
> 문자 배열로 선언한 이유는 편집 순서열에 맞춰 문자열을 편집할 때 문자 단위로 편집이 이루어지기 때문입니다.   
> 아래는 최종적으로 출력할 최적의 편집 순서열을 담을 변수와 비용 변수들입니다.
```java
String s_edit; // 최적의 편집 순서열	
int Insertcost; // 삽입 비용
int Deletecost; // 삭제 비용
int Changecost; // 교체 비용
```
> 생성자 함수에서 멤버 변수들을 모두 초기화 시킵니다.
```java
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
```
> 아래의 함수는 cost 클래스의 객체를 선언하여 각 삽입, 삭제 및 교체 비용을 통해 비용 표와 편집 문자 표를 만듭니다.   
> 만들어진 비용 표를 cost 객체의 멤버 함수를 통하여 출력하고 최적의 편집 순서열을 cost 객체의 역추적 함수를 통하여 초기화합니다.   
> 최적의 편집 순서열을 차례로 적용한 입력 문자열의 변화를 출력합니다.
```java
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
```
> 아래의 함수는 최적의 편집 순서열과 입력 문자열에 차례로 적용한 문자열을 출력합니다.   
> cost 객체의 역추적 함수를 이용하여 초기화 한 최적의 편집 순서열은 거꾸로 나열되어있기 때문에 역순으로 출력합니다.   
> 입력 문자열에서 편집 순서열에 의해 변하는 과정을 보여주기 위하여 문자 단위로 편집하여 출력합니다.
```java
public void ApplyString() {
	// 최적 편집 순서열 출력, 입력문자열에 차례로 적용한 문자열 출력
	char[] e = new char[s_edit.length()]; 
	// s_edit문자열은 최적의 편집 순서열이 거꾸로 나열되어있기 때문에
	// 역순으로 출력하기 위해 char 배열 e를 선언
	for(int i = 0; i<e.length; i++)
		e[i] = s_edit.charAt(i);   // 배열 초기화
	System.out.print("최적의 편집 순서열: ");
	for(int i = e.length-1; i>=0; i--)
		System.out.print(e[i]+" ");  // 역순으로 출력
	System.out.println();
	
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
```
### cost 클래스
> 비용 및 편집 문자 표를 만들기 위해 2차원 배열을 선언합니다.
```java
private int cost[][]; // 비용 2차원 배열
private String edit[][]; // 편집 2차원 배열
```
> 생성자 함수를 통해 배열을 초기화합니다.   
> 비용 0과 편집이 없는 초기 상태를 포함해야하므로 배열의 크기는 X문자열의 길이 + 1, Y문자열의 길이 + 1입니다.
```java
public Cost(int n, int m) {
	cost = new int[n][m];
	edit = new String[n][m];
	// n : X문자열의 길이 +1, m : Y문자열의 길이 +1
	for(int i = 0; i < n; i++)
		for(int j = 0; j < m; j++) {
			cost[i][j]=0;
			edit[i][j]=""; // 배열 초기화
		}
}
```
> 비용 삽입 및 출력 함수, 편집 문자 삽입 및 출력 함수 입니다.   
> cost 클래스의 모든 함수들은 edit 클래스의 함수 내에서 사용됩니다.
```java
public void putCost(int i, int j, int cost) {
	this.cost[i][j] = cost;
} // cost 값 삽입 함수

public void putEdit(int i, int j, String edit) {
	this.edit[i][j] = edit;
} // 편집 문자 삽입 함수

public int getCost(int i, int j) {
	return cost[i][j];
} // 해당 위치 cost 값 출력 함수

public String getEdit(int i, int j) {
	return edit[i][j];
} // 해당 위치 edit 값 출력 함수
```
> 비용 표를 출력하는 함수입니다.
```java
public void showCost() {
	System.out.println("c(i,j)표");
	for(int i=0; i<cost.length; i++) {
		for(int j=0; j<cost[i].length; j++)
			System.out.print(cost[i][j] + "    ");
		System.out.println();
	}
} // cost 표 출력 함수
```
> edit 클래스의 편집 함수에서 최적의 편집 문자열을 저장하는 변수를 초기화할 때 사용했던 역추적 함수입니다.   
> 편집 문자 배열을 역추적하여 최적의 편집 순서열을 역순으로 저장합니다.
```java
public String backTrackCost(String a) { 
	// edit 배열을 역추적해서 최적 편집 순서열을 찾는 함수
	int i = cost.length-1;
	int j = cost[0].length-1;
	// cost 배열의 마지막 인덱스 i, j
	while(true) {
		if(i==0 && j==0) // 역추적을 끝내면 종료
			break;
		else {
			a += edit[i][j]; // 최적의 편집에 해당하는 문자열 추가
			if(edit[i][j]=="I")
				j--;
			else if(edit[i][j]=="D")
				i--;
			else {
				i--;
				j--;
			} // 편집 테이블을 이용해 최적 편집 순서열을 역추적
		}
	}
	return a;
}
```
### 메인 클래스
> 메인 클래스는 메인 함수만을 포함합니다.
> 두 문자열의 길이를 입력받고, X와 Y 문자열을 입력받습니다.   
> 삽입, 삭제 및 교체 비용을 입력 받은 후에 edit 클래스의 객체를 생성하고 편집을 수행합니다.
```java
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
```
### 수행 결과
> 아래의 입력으로 출력된 결과입니다.
```
두 문자열의 길이를 입력하시오 : 10 7
X 문자열을 입력하시오 : abddcaacbc
Y 문자열을 입력하시오 : bccacdb
삽입, 삭제, 교체 비용을 입력하시오 : 3 2 4
```
![image03](https://user-images.githubusercontent.com/39798011/123600468-aa793780-d831-11eb-8198-906d8fe5aeeb.jpg)
> 다른 입력으로 출력된 결과입니다.
```
두 문자열의 길이를 입력하시오 : 7 12
X 문자열을 입력하시오 : abcddfe
Y 문자열을 입력하시오 : bbcadbbeffec
삽입, 삭제, 교체 비용을 입력하시오 : 2 2 3
```
![image04](https://user-images.githubusercontent.com/39798011/123600746-ead8b580-d831-11eb-9ded-defd2663fdad.jpg)


