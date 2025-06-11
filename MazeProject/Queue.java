
import java.util.ArrayList;
import java.util.List;

public class Queue<T> { //<T> olunca generic oluyomuş diğer veri tiplerini de döndürmek için kullanılabiliyomuş

    
    private class Node {
        T data;
        Node next;

        Node(T data) {
            this.data = data;
            this.next = null;
        }
    }

    private Node front; 
    private Node rear;  
    private int size;

    public Queue() {
        front = null;
        rear = null;
        size = 0;
    }

    //kuyruğa eleman ekliyoruz
    public void enqueue(T data) {
        Node newNode = new Node(data);
        if (isEmpty()) {
            front =  newNode;
            rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
        size++;
    }

    // kuyruktan eleman çıkar
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = front.data;
        front = front.next;
        if (front == null) { //boş mu bakıp sıfırladık
            rear = null;
        }
        size--;
        return data;
    }

    // en öndeki elemanı yayınlatma
    public T peek() {
        if (isEmpty()) return null;
        return front.data;
    }

    //queeue boş mu
    public boolean isEmpty() {
        return front == null;
    }

    // kaç eleman var
    public int getSize() {
        return size;
    }

    // kuyruğu yazdırma
    public void printQueue() {
        System.out.print("Queue [front -> rear]: ");
        Node current = front;
        while (current != null) {
            System.out.print(current.data + " ");
            current = current.next;
        }
        System.out.println();
    }
    public List<T> toList() {
    List<T> list = new ArrayList<>();
    Node current = front;
    while (current != null) {
        list.add(current.data);
        current = current.next;
    }
    return list;
}

}
