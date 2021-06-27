import java.util.ArrayList;
import java.util.Collections;

public class Minheap {
	
	ArrayList<TreeNode> heap = new ArrayList<TreeNode>();
	//arraylist 자료구조를 이용해 heap 생성
	public Minheap() {
		heap.add(null); // 첫 번째 공간을 비운다
	}
	
	public void put(TreeNode n) { // 삽입 함수
		
		heap.add(n); // heap에 노드 삽입
		
		int child = heap.size()-1;
		// heap의 마지막 원소 위치
		int currentNode = child/2;
		// child의 부모 위치
		while(currentNode >= 1 && heap.get(child).num < 
					heap.get(currentNode).num) {
			Collections.swap(heap, child, currentNode);
			// 빈도수를 기준으로 자식원소의 빈도수가 부모보다 작다면 swap
			child = currentNode;
			currentNode = child/2;
			// 마지막 원소에서부터 루트까지
		}
	}
	
	public boolean isEmpty() { // heap이 비어있다면 true
		return (heap.size() <= 1);
	}
	
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
				if(heap.get(leftNode).num >=
						heap.get(currentNode).num)
					// 왼쪽 자식(빈도수)이 더 크거나 같다면 종료
					break;
				tmp = leftNode;
			}
			else { // 왼쪽 자식 오른쪽 자식이 모두 있는 경우
				if(heap.get(leftNode).num >=
			  heap.get(currentNode).num && heap.get(rightNode).num >=
							 heap.get(currentNode).num)
			// 두 자식 노드가 부모 노드보다 더 크거나 같다면 종료
					break;
				tmp = (heap.get(leftNode).num <
				   heap.get(rightNode).num) ? leftNode : rightNode;
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
}
