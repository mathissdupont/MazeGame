//stack tanımını hocanın slaytlarından yaptım direkt
public class Stack {

    public class Node {
        String data;
        Node next;

        Node(String data) {
            this.data = data;
            this.next = null;
        }
    }
    
    public Node top;
    public int size = 0;

    public Stack() {
        this.top = null;
    }

    public boolean isEmpty() {
        return top == null;
    }

    public void push(String data) {
        Node newNode = new Node(data);
        newNode.next = top;
        top = newNode;
        size++;
    }

    public String pop() {
        if (isEmpty()) {
            return null;
        }
        String data = top.data;
        top = top.next;
        size--;
        return data;
    }

    public String peek() {
        if (isEmpty()) {
            return null;
        }
        return top.data;
    }
        
    public String[] getLastNMoves(int n) {
        String[] moves = new String[n];
        Node current = top;
        int i = 0;
        while (current != null && i < n) {
            moves[i++] = current.data;
            current = current.next;
        }
        return moves;
    }
    public int getSize() {
        return size; // Stack boyutunu döndürür
    }
    //override yazmayınca sarı uyarı verdi
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        Node current = top;
        while (current != null) {
            sb.append(current.data).append(" -> ");
            current = current.next;
        }
        sb.append("END");
        return sb.toString();
    }
}