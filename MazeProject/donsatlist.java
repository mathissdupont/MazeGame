//abi buranın ismini değiştirmedim ya diğer her yeri buna göre yazmışız o yüzden böyle kaldı raporda dikkat ederiz
public class donsatlist{
    private class node{
        int rowIndex;
        CircularLinkedList<MazeTile> rowList;
        node next;

        node(int rowIndex, CircularLinkedList<MazeTile> rowList){
            this.rowIndex=rowIndex;
            this.rowList=rowList;
        }
    }

    private node head;
    public void addRow(int rowIndex,CircularLinkedList<MazeTile> list){  //linkedliste satır ekleme işlemi burda yapılıyo
        node newNode = new node(rowIndex, list);
        newNode.next = head;
        head = newNode;
    }
    public CircularLinkedList<MazeTile> getRow(int rowIndex){
        node current = head;
        while(current != null){
            if(current.rowIndex==rowIndex){
                return current.rowList;
            }
            current = current.next;  
        }
        return null;
    }
    public boolean contains(int rowIndex){
        node current = head;
        while(current != null){
            if(current.rowIndex==rowIndex) return true;
            current = current.next;
        }
        return false;
    }
    public int[] getAllIndexes(){
        int count = 0;
        node current = head;
        while(current!=null){
            count++;
            current = current.next;
        }
        int[] indexes = new int[count];
        current = head;
        int i = 0;
        while(current!=null){
            indexes[i++]=current.rowIndex;
            current=current.next;
        }
        return indexes;
    }
    public int getRotatingRowCount() {  //bunu senin kodlarının birinde kullanılırken gördüm sanırım o yüzden ekledim
        int count = 0;
        node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    } 
}