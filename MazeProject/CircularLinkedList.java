public class CircularLinkedList<T> {

    private class Node {
        T data ;
        Node next;

        Node(T data){
            this.data = data;
        }
    }
    private Node head;
    private int size;
    public CircularLinkedList(){
        head = null;
        size = 0;
    }
    public void append(T data){
        Node newNode = new Node(data);
        if(head == null){
            head = newNode;
            head.next= head;
        }else{
            Node temp = head;
            while(temp.next != head){
                temp = temp.next;
            }
            temp.next = newNode;
            newNode.next=head;
        }
        size++;
    }
    public void rotate(){
        if(head != null&&head.next != head){
            head = head.next;
        }
    }
    public T[] toArray(T[] a){
        Node current = head;
        int i = 0;
        do{
            a[i++] = current.data;
            current = current.next;
        }while(current!=head);

        return a;
    }
    public int getSize(){
        return size;
    }
    public T getAt(int index){
        if(index>=size||index<0) return null;
        Node current = head;
        for(int i =0;i<index;i++) current = current.next;
        return current.data;
    }
}
