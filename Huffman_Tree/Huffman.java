import java.util.HashMap;

public class Huffman {
	
	public HashMap<Character, Integer> num = new HashMap<Character, Integer>();
	// 문자와 빈도수를 저장하는 hashmap
	public TreeNode huffman = null; // huffman 트리를 만들기 위한 treenode형 객체 선언
	
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
		}// 해당작업 반복
	}
	
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
}
