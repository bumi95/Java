[Huffman Tree 구현](https://github.com/bumi95/Java/tree/main/Huffman_Tree)
====================
## 문제 정의
> 하나의 영문 파일(문자열)을 입력받아 각 문자에 할당된 허프만 코드와 빈도 수를 출력합니다.   
> 문자에는 space 문자도 포함됩니다.
## 문제 설계
> 힙과 허프만 트리에 사용할 트리 노드 클래스를 구현합니다.
* 트리 노드 클래스는 빈도 수와 문자를 저장하고 왼쪽 자식, 오른쪽 자식에 대한 노드를 멤버 변수로 가집니다.
* 생성자를 통해 빈도 수와 문자, 자식 노드를 초기화 합니다.
> 최소 힙을 사용합니다.
* 힙은 arraylist 자료구조를 사용하여 구현합니다.
* 생성자, 삽입, 삭제, empty case 함수를 구현합니다.
> 최소 힙과 노드 클래스를 사용하는 허프만 클래스를 구현합니다.
* 허프만 클래스는 문자와 빈도 수를 저장하는 hashmap을 멤버 변수로 가집니다.
* 최소 힙을 사용하여 허프만 트리를 만드는 허프만트리 함수를 구현합니다.
* 만들어진 허프만 트리를 탐색하여 각각의 문자와 코드를 출력하는 함수를 구현합니다.
> 메인 클래스에서는 메인 함수만을 포함하고 영문 파일을 입력 받습니다.
## 구현 및 결과
### 트리 노드 클래스
> 트리 노드 클래스입니다.
```java
public class TreeNode { // 히프와 huffman트리에 사용할 트리노드 객체
	
	public int num; // 빈도 수 
	public char word; // 문자
	public TreeNode leftNode;
	public TreeNode rightNode;
	
	public TreeNode(int num, char word) {
		
		this.num = num;
		this.word = word;
		leftNode = rightNode = null;
	}
}
```
> 최소 힙과 허프만 트리에서 사용할 노드이므로 트리 형태로 만들 수 있도록 왼쪽 자식과 오른쪽 자식 노드를 멤버 변수로 갖습니다.
### 최소 힙 클래스
> 아래 코드는 최소 힙에서 사용할 arraylist 자료구조 변수와 삽입 함수 입니다.
```java
ArrayList<TreeNode> heap = new ArrayList<TreeNode>();
	//arraylist 자료구조를 이용해 heap 생성
public Minheap() {
	heap.add(null); // 첫 번째 공간을 비운다
}
	
public void put(TreeNode n) { // 삽입 함수
		
	heap.add(n); // heap에 n 삽입
		
	int child = heap.size()-1;
	// heap의 마지막 원소 위치
	int currentNode = child/2;
	// child의 부모 위치
	while(currentNode >= 1 && heap.get(child).num < heap.get(currentNode).num) {
		Collections.swap(heap, child, currentNode);
		// 빈도수를 기준으로 자식원소의 빈도수가 부모보다 작다면 swap
		child = currentNode;
		currentNode = child/2;
		// 마지막 원소에서부터 루트까지
	}
}
```
> 최소 힙의 루트 노드의 인덱스를 1로 설정하기 위하여 힙의 0번째 인덱스 공간을 비웁니다.   
> 삽입 시에 부모 보다 작은 값이 삽입 된다면 위치를 변경하여 최소 힙을 만족시킵니다.   
> 아래 코드는 삭제 함수입니다.
```java
public TreeNode removeMin() { // 히프의 삭제
	if(isEmpty())
		return null;
	TreeNode minObj = heap.get(1); // 가장 작은 원소
	int lastObj = heap.size()-1; // 히프의 마지막 원소의 위치
	heap.set(1, heap.get(lastObj)); // 히프의 루트를 마지막원소로 대체
	heap.remove(lastObj);
		
	int currentNode = 1;
	int leftNode = currentNode*2;
	int rightNode = currentNode*2 + 1;
	// 루트부터 시작
		
	while(leftNode <= heap.size()-1) {
		// 왼쪽 자식이 존재하는 경우에만 실행한다.
		int tmp;
		if(rightNode > heap.size()-1) {
			// 오른쪽 자식이 없는 경우
			if(heap.get(leftNode).num >= heap.get(currentNode).num)
				// 왼쪽 자식(빈도수)이 더 크거나 같다면 종료
				break;
			tmp = leftNode;
		}
		else { // 왼쪽 자식 오른쪽 자식이 모두 있는 경우
			if(heap.get(leftNode).num >= heap.get(currentNode).num && heap.get(rightNode).num >= heap.get(currentNode).num)
	  			// 두 자식 노드가 부모 노드보다 더 크거나 같다면 종료
				break;
			tmp = (heap.get(leftNode).num < heap.get(rightNode).num) ? leftNode : rightNode;
			// 더 작은 노드로 변경한다.
		}
		Collections.swap(heap, tmp, currentNode);
		// tmp와 currentNode의 위치에 있는 값을 swap
			
		currentNode = tmp;
		leftNode = currentNode*2;
		rightNode = currentNode*2 + 1;
		// tmp의 위치부터 다시 시작
	}
	return minObj;
}
```
> 삭제 함수에서는 root 값을 삭제하여 반환하고 나머지 노드들을 최소 힙을 만족하도록 정렬합니다.
### 허프만 트리 클래스
> 허프만 클래스에서는 문자와 문자에 대한 빈도 수를 저장하기 위하여 HashMap을 사용합니다.
```java
public HashMap<Character, Integer> num = new HashMap<Character, Integer>();
	// 문자와 빈도수를 저장하는 hashmap
```
> 아래의 count 함수를 통해 메인 함수에서 입력 받은 문자열의 각 문자와 빈도 수를 hashmap 변수에 저장합니다.
```java
public void count(String s) {
	// 문자의 빈도수를 count
	for(int i=0; i<s.length(); i++) {
		char c = s.charAt(i); // 문자 하나씩 c에 저장
		if(num.containsKey(c))
			num.put(c, num.get(c)+1);
		else
			num.put(c, 1);
		// num이 해당문자를 이미 가지고 있다면 그 문자에 해당하는
		// value값을 1 증가. 없다면 key & value추가
	}
}
```
> 허프만 트리 함수에서 허프만 트리를 생성하기 위해 트리 노드 객체를 선언합니다.
```java
public TreeNode huffman = null; // huffman 트리를 만들기 위한 treenode형 객체 선언
```
> 아래의 허프만 트리 함수는 트리 노드 객체와 최소 힙을 이용하여 허프만 트리를 생성합니다.
```java
public void HuffmanTree() {
		
	Minheap h = new Minheap();
		
	if(num.isEmpty())
		return; // 빈도 수 센 것이 없으면 null
	
	for(char k : num.keySet())
		h.put(new TreeNode(num.get(k), k));
  	// 최소 히프에 문자와 빈도수 저장
	while(true) {
			
		TreeNode leftchild = h.removeMin();
		TreeNode rightchild = h.removeMin();
		// 최소노드 2개를 삭제
		huffman = new TreeNode(leftchild.num+rightchild.num,'.');
		// 삭제한 최소노드 2개로 부모노드 생성 두번째 인자 '.'은 해당 노드는 단말노드가 아닌
		// 내부노드 이므로 count하지 않는 값인 '.'로 전달
		huffman.leftNode = leftchild;
		huffman.rightNode = rightchild;
		// 부모노드의 왼쪽 오른쪽 자식에 최소노드 2개를 저장
		if(h.isEmpty())
			return; // 히프가 비어있으면 huffman 트리 완성
		
		h.put(huffman); // 최소 히프에 최소노드로 만든 부모노드를 다시 삽입
	} // 해당작업 반복
}
```
> 아래의 코드는 만들어진 허프만 트리를 탐색하여 문자와 빈도 수, 허프만 코드를 출력하는 함수입니다.
```java
public void showWord(TreeNode rt, int[] trace, int lastObj) {
	// huffman의 root를 받으면 각각 문자의 코드를 출력
	// trace배열은 트리에서 단말노드를 추적하기 위한 배열. 원소는 단말노드의 코드집합이다.
	if(rt.leftNode != null) {// 왼쪽 자식을 탐색할 경우
		trace[lastObj] = 0; // 0을 저장
		showWord(rt.leftNode, trace, lastObj + 1);
		// 해당 왼쪽자식에서부터 탐색 시작
	}
	if(rt.rightNode != null) { // 오른쪽 자식을 탐색할 경우
		trace[lastObj] = 1; // 1을 저장
		showWord(rt.rightNode, trace, lastObj + 1);
		// 해당 오른쪽자식에서부터 탐색 시작
	}
	if(rt.leftNode == null && rt.rightNode == null) {
		// 단말노드일 경우 해당 문자와 빈도수를 출력
		System.out.print(rt.word + "의 빈도 수: " + rt.num +", 코드 : ");
		printArr(trace, lastObj); // 해당 문자의 코드 출력
	}
}
public void printArr(int[] arr, int lastObj) {
	// 단말노드를 추적한 trace배열을 출력
	for(int i=0; i<lastObj; i++)
		System.out.print(arr[i]);
	System.out.println("");
}
```
### 메인 클래스
> 메인 클래스에서는 메인 함수를 포함하고 있습니다.   
> 메인 함수에서는 하나의 영문 파일을 입력받아 허프만 객체를 통해 트리를 만듭니다.
```java
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
```
### 수행 결과
> 아래의 입력으로 출력된 결과입니다.
```
aabccdaaa jjel bbda a
```
![image01](https://user-images.githubusercontent.com/39798011/123589788-b14d7d80-d824-11eb-9642-fba0d0322ecd.jpg)
> 다른 입력으로 출력된 결과입니다.
```
aasfeedfse sjelldljjf adfeejjspwz fjdjdjslzz
```
![image02](https://user-images.githubusercontent.com/39798011/123589945-f5d91900-d824-11eb-9905-087900500cf7.jpg)
* * *
[최적의 편집 순서열 구현](https://github.com/bumi95/Java/tree/main/Dynamic_programming)
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













