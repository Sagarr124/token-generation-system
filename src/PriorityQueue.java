
public class PriorityQueue {
	
	private int size = 0;
	
	public int getSize() {
		return size;
	}
	
	static class Node {
		private Node next;
		private final int key;
		private final Object data;
		
		Node(Object data, int key) {
			this.data = data;
			this.key = key;
			next = null;
		}

	}
	
	
	private static Node rear = null, front = null;

	// Utility function to remove front element from the queue and check for Queue Underflow
    public void dequeue() {
        if(isEmpty()) {
            System.out.println("Queue is empty.");
        }
        else if(front.next != null) {
            System.out.println("Removing  " + front.data);
            front = front.next;
            --size;
        }
        else {
            System.out.println("Removing  " + front.data);
            front = null;
            --size;
        }

    }

    // Utility function to add an item in the queue
    public void enqueue(Object item, int key){
        Node newNode = new Node(item, key);

        System.out.println("Inserting  " + item);

        if(isEmpty()) {
            front = rear = newNode;
  

        }
        else if(key == 1) { //if staff
        	newNode.next = front;
        	front = newNode;
        }
        else if(key == 2) { //if student
            rear.next = newNode;
            rear = newNode;
            
        }
        
        ++size;
    }
    

    // Utility function to return top element in a queue
    public Object peek() {
        return front.data;
    }

    // Utility function to check if the queue is empty or not
    public boolean isEmpty() {
        return front == null;
    }

}
