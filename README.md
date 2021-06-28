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
		// 삭제한 최소노드 2개로 부모노드 생성 두번째 인자 '.'은 해당 			   노드는 단말노드가 아닌
		// 내부노드 이므로 count하지 않는 값인 '.'로 전달
		huffman.leftNode = leftchild;
		huffman.rightNode = rightchild;
		// 부모노드의 왼쪽 오른쪽 자식에 최소노드 2개를 저장
		if(h.isEmpty())
			return; // 히프가 비어있으면 huffman 트리 완성
		
		h.put(huffman); // 최소 히프에 최소노드로 만든 부모노드를 					   다시 삽입
	}// 해당작업 반복
}
```
> 아래의 코드는 만들어진 허프만 트리를 탐색하여 문자와 빈도 수, 허프만 코드를 출력하는 함수입니다.
```java
public void showWord(TreeNode rt, int[] trace, int lastObj) {
	// huffman의 root를 받으면 각각 문자의 코드를 출력
	// trace배열은 트리에서 단말노드를 추적하기 위한 배열. 원소는 			   단말노드의 코드집합이다.
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








