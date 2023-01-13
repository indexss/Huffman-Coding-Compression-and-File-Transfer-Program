package Topic1;

import java.util.Scanner;

public class Linkedlist {
    public static void main(String[] args) {
        SingleLinkedList singleLinkedList = new SingleLinkedList();
        Scanner scanner = new Scanner(System.in);
        int input;

        while(scanner.hasNext()){
            input = Integer.parseInt(scanner.next());
            singleLinkedList.addNode(new Node(input));
        }

        System.out.print("The original linkedlist is: ");
        singleLinkedList.printLinkedList();
        System.out.println(" ");
        System.out.println("length of this linkedlist is: " + singleLinkedList.size());
//        singleLinkedList.sort();

        int size = singleLinkedList.size();


        singleLinkedList.sortPtr();
        System.out.print("The sorted linkedlist is: ");
//        //=====================
        singleLinkedList.printLinkedList();

    }
}

class Node{
    public int data;
    public Node next = null; //指向下一个node

    //generator
    public Node(int gdata){
        this.data = gdata;
    }

    @Override
    public String toString(){
        return this.data + "   ";
    }
}

class SingleLinkedList{
    //头节点
    public Node headNode = new Node(0);

    //添加节点
    public void addNode(Node newNode){
        //尾插法 需要辅助指针 tempPtr
        Node tempPtr = headNode;
        while(true){
            if(tempPtr.next == null){
                break;
            }
            tempPtr = tempPtr.next;
        }
        tempPtr.next = newNode;
    }

    //打印链表
    public void printLinkedList(){
        if(headNode.next == null){
            System.out.println("链表为空");
            return;
        }

        Node tempPtr = headNode.next;
        while(true){
            System.out.print(tempPtr);
            if(tempPtr.next == null){
                break;
            }
            tempPtr = tempPtr.next;
        }
    }

    public int size(){
        int length = 0;
        Node tempPtr = headNode.next;
        while(true){
            length ++;
            if(tempPtr.next == null){
                break;
            }
            tempPtr = tempPtr.next;
        }
        return length;
    }

    public void sortPtr(){
        int size = this.size();

        for(int i = 0; i < size - 1; i++){
            Node tempPtrRight = headNode.next;
            Node tempPtrLeft = headNode;
            for(int j = 0; j< size - i - 1; j++){
                if(tempPtrRight.data > tempPtrRight.next.data){
                    tempPtrLeft.next = tempPtrRight.next;
                    tempPtrRight.next = tempPtrRight.next.next;
                    tempPtrLeft.next.next = tempPtrRight;
                    tempPtrRight = tempPtrLeft.next;
                }
                tempPtrLeft = tempPtrLeft.next;
                tempPtrRight = tempPtrRight.next;
            }
        }
    }

    public void sortData(){
        int size = this.size();

        for(int i = 0; i < size - 1; i++){
            Node temp = headNode.next;
            for(int j = 0; j< size - i - 1; j++){
                if(temp.data > temp.next.data){
                    int tmp = temp.data;
                    temp.data = temp.next.data;
                    temp.next.data = tmp;
                }
                temp = temp.next;
            }
        }

    }
}