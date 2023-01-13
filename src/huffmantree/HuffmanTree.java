package huffmantree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class HuffmanTree {
    public static void main(String[] args) {
        int arr[] = {13, 7, 8, 3, 29, 6, 1};
        Node rootNode = createHuffmanTree(arr);
        preOrder(rootNode);
    }

    /**
     *
     * @param root 哈夫曼树根节点
     */
    //前序遍历函数
    public static void preOrder(Node root){
        if(root != null){
            root.preOrder();
        }else{
            System.out.println("The tree is NULL!");
        }
    }

    /**
     *
     * @param arr 包含结点元素的数组
     * @return 创建好的哈夫曼根节点
     */
    public static Node createHuffmanTree(int[] arr){

        //arr所有元素转换成node,并放入ArrayList
        List<Node> nodes = new ArrayList<Node>();
        for(int value: arr) {
            nodes.add(new Node(value));
        }
        while(nodes.size() != 1) {
            //排序 从小到大
            Collections.sort(nodes);
            System.out.println(nodes);

            //取出根节点最小的两棵树
            Node leftTree = nodes.get(0);
            Node rightTree = nodes.get(1);

            //两个合成新树
            Node parent = new Node(leftTree.value + rightTree.value);
            parent.left = leftTree;
            parent.right = rightTree;

            //ArrayList删除两个小节点 加入新结点 排序
            nodes.remove(leftTree);
            nodes.remove(rightTree);
            nodes.add(parent);

//            System.out.println("process: " + nodes);
        }

        //返回root结点
        return nodes.get(0);
    }
}


//创建结点类
class Node implements Comparable<Node>{
    int value;
    char c;     //哈夫曼编码字符
    Node left;  //左孩子
    Node right; //右孩子

    /**
     *
     * @param val 结点数值
     */
    public Node(int val){
        this.value = val;
    }

    @Override
    public String toString(){
        return " { " + value + " } ";
    }

    //为支持排序 实现比较接口
    @Override
    public int compareTo(Node o){
        //从小到大排序
        return this.value - o.value;
    }

    public void preOrder(){
        System.out.println(this);
        if(this.left != null){
            this.left.preOrder();
        }
        if(this.right != null){
            this.right.preOrder();
        }
    }

}
