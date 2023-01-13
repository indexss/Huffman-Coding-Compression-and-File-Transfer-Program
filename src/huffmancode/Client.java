package huffmancode;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.List;

public class Client extends JFrame {
    public BufferedReader br = null;
    public static PrintStream ps = null;

    //字符串
    static String content = "";
    static PrintStream showHuffmanTreePre;
    static PrintStream showHuffmanTreeMid;
    static PrintStream showHufmanCode;
    static PrintStream showTree;
    static PrintStream resultWriter;

    static {
        try {
            showTree = new PrintStream(new FileOutputStream("src/huffmancode/Docs/huffmanTree.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            showHufmanCode = new PrintStream(new FileOutputStream("src/huffmancode/Docs/huffmanCode.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            showHuffmanTreeMid = new PrintStream(new FileOutputStream("src/huffmancode/Docs/midOrderHuffmanTree.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        try {
            showHuffmanTreePre = new PrintStream(new FileOutputStream("src/huffmancode/Docs/preOrderHuffmanTree.txt"));
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    //按钮
    private JButton openFileButton = new JButton("打开文件");
    private JButton loadFileButton = new JButton("载入文件");
    private JButton observeTreeButton = new JButton("查看哈夫曼树");
    private JButton zipButton = new JButton("压缩");
    private JButton unzipButton = new JButton("解压");
    private JButton submitButton = new JButton("提交");
    private JButton transmitButton = new JButton();

    //文本框
    JTextArea inputTextField = new JTextArea();

    //文字提示
    private JLabel inputNotation = new JLabel();
    private JLabel fileNotation = new JLabel();
    private JLabel slogan = new JLabel();
    private JLabel information = new JLabel();

    //压缩图标
    private JLabel zipIcon = new JLabel();

    //


    public static void main(String[] args) throws Exception {
        new Client();
    }

    public Client() throws Exception {
        Socket s = new Socket("127.0.0.1", 13531);
        br = new BufferedReader(new InputStreamReader(s.getInputStream()));
        ps = new PrintStream(s.getOutputStream());
        this.setTitle("哈夫曼压缩软件 客户端");
        this.setBounds(410, 200, 550, 350);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setLayout(null);

        //从文本框输入提示
        inputNotation.setBounds(55, 23, 200, 25);
        inputNotation.setText("从文本框输入：");
        inputNotation.setFont(new Font("黑体", Font.BOLD, 20));
        this.add(inputNotation);

        //输入文本框
//        JTextArea textArea = new JTextArea();
        TextArea textArea = new TextArea("",20,43,TextArea.SCROLLBARS_BOTH);
        textArea.setText("欢迎使用哈夫曼压缩与解压程序！");
        textArea.setFont(new Font("黑体", Font.BOLD, 15));
        textArea.setBounds(20, 52, 300, 120);
//        textArea.setLineWrap(true);

//        JScrollPane scroll=new JScrollPane(textArea);
//        scroll.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
//        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
//        Container container=this.getContentPane();
//        container.setBounds(20,52,300,120);
//        container.add(scroll);
        this.add(textArea);


        //提交按钮
        submitButton.setBounds(20, 177, 300, 30);
        submitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("提交内容: " + textArea.getText());
                content = textArea.getText();
                textArea.setText("");
                JOptionPane.showMessageDialog(null, "您已提交成功！提交待压缩的内容为：\n\n" + content, "您已提交成功", JOptionPane.WARNING_MESSAGE, Data.correct);
            }
        });
        this.add(submitButton);

        //直线
        JLabel linePic = new JLabel();
        linePic.setIcon(Data.line);
        linePic.setBounds(326, 75, 10, 230);
        this.add(linePic);


        //文件导入图标
        JLabel fileIcon = new JLabel();
        fileIcon.setIcon(Data.file);
        fileIcon.setBounds(20, 205, 50, 50);
        this.add(fileIcon);

        //从文件导入文字
        fileNotation.setBounds(55, 217, 200, 25);
        fileNotation.setText("从文件导入：");
        fileNotation.setFont(new Font("黑体", Font.BOLD, 20));
        this.add(fileNotation);

        //打开文件按钮
        openFileButton.setBounds(20, 247, 90, 60);
        openFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Desktop desktop = Desktop.getDesktop();
                File file = new File("src/huffmancode/Docs/unzippedFile.txt");
                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        this.add(openFileButton);

        //载入文件按钮
        loadFileButton.setBounds(120, 247, 90, 60);
        loadFileButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                StringBuilder stringBuilder = new StringBuilder();
                String lineTxt = null;
                File file = new File("src/huffmancode/Docs/unzippedFile.txt");
                InputStreamReader read = null;
                try {
                    read = new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8);
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                BufferedReader bufferedReader = new BufferedReader(read);

                while (true) {
                    try {
                        if (!((lineTxt = bufferedReader.readLine()) != null)) break;
                    } catch (IOException ex) {
                        throw new RuntimeException(ex);
                    }
                    stringBuilder.append(lineTxt + "\n");
                }
                String resultTxt = stringBuilder.toString();
                textArea.setText(resultTxt);
                System.out.println(resultTxt);
                content = resultTxt;
            }
        });

        this.add(loadFileButton);

        //打开二叉树按钮
        observeTreeButton.setBounds(220, 247, 90, 60);
        observeTreeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8);
                List<Node> nodes = getNodes(contentBytes);
                Node huffmanTreeRoot = createHuffmanTree(nodes);
                huffmanTreeRoot.print("", true);
                showHuffmanTreePre.println("\n先序遍历结果：");
                showHuffmanTreeMid.println("\n中序遍历结果：");
                showHufmanCode.println("\n哈夫曼编码对应表：");
                for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
                    if (entry.getKey() > 0) {
                        showHufmanCode.print((char) ((int) entry.getKey()) + " = " + entry.getValue() + "  ");
                    } else {
                        showHufmanCode.print(entry.getKey() + " = " + entry.getValue() + "  ");
                    }
                }
                System.out.println("preOrder");
                huffmanTreeRoot.preOrder();
                System.out.println("midOrder");
                huffmanTreeRoot.midOrder();
                JOptionPane.showMessageDialog(null, "查看二叉树遍历结果", "哈夫曼树遍历结果", JOptionPane.WARNING_MESSAGE, Data.correct);
                Desktop desktop = Desktop.getDesktop();
                File file = new File("src/huffmancode/Docs/preOrderHuffmanTree.txt");
                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                file = new File("src/huffmancode/Docs/midOrderHuffmanTree.txt");
                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                file = new File("src/huffmancode/Docs/huffmanCode.txt");
                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
                file = new File("src/huffmancode/Docs/huffmanTree.txt");
                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        this.add(observeTreeButton);

        //压缩图标
        zipIcon.setIcon(Data.zip);
        zipIcon.setBounds(325, 10, 50, 50);
        this.add(zipIcon);

        //txt图标
        JLabel txtIcon = new JLabel();
        txtIcon.setIcon(Data.text);
        txtIcon.setBounds(20, 10, 50, 50);
        this.add(txtIcon);


        //slogan
        slogan.setBounds(375, 15, 200, 25);
        slogan.setText("哈夫曼压缩与解压软件");
        slogan.setFont(new Font("黑体", Font.CENTER_BASELINE, 17));
        this.add(slogan);

        //information
        information.setBounds(375, 40, 200, 15);
        information.setText("作者: 史林立  版本: v1.0.2");
        information.setFont(new Font("宋体", Font.BOLD, 10));
        this.add(information);

        //压缩按钮
        zipButton.setBounds(340, 70, 95, 100);
        zipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("yasuoanniu");
                //生成哈夫曼树对应的哈夫曼编码
                //1. 将哈夫曼编码表存储在Map<Byte, String> 32:01 97:100 100:11000
                huffmanCodes = new HashMap<Byte, String>();
                //2. 在生成哈夫曼编码表示，需要去拼接路径，定义一个StringBuilder, 存储某个叶子结点的路径
                stringBuilder = new StringBuilder();
                huffmanStr = null;
                System.out.println("原字符串: " + content);
                byte[] contentBytes = content.getBytes(StandardCharsets.UTF_8); //获得ascii码数组
                System.out.println("原始二进制: \n" + re(Arrays.toString(contentBytes)));
                byte[] zipBytes = huffmanZip(contentBytes);
                System.out.println("正向哈夫曼表:\n" + re(huffmanCodes.toString()));
                System.out.println("压缩后字节数组:\n" + re(Arrays.toString(zipBytes)));
                System.out.println("");
                System.out.println("原长度: " + contentBytes.length + " 压缩后长度: " + zipBytes.length + " 压缩率: " + ((double) (contentBytes.length - zipBytes.length) / contentBytes.length));
                System.out.println("");

                JOptionPane.showMessageDialog(null, "待压缩字符串：\n" + content + "\n\n压缩前字节为：\n" + re(Arrays.toString(contentBytes)) + "\n\n压缩后字节为：\n" + re(Arrays.toString(zipBytes)) + "\n\n" + "压缩后哈夫曼编码为：\n" + re(huffmanStr) + "\n\n" + "原长度: " + contentBytes.length + " 压缩后长度: " + zipBytes.length + " 压缩率: " + ((double) (contentBytes.length - zipBytes.length) / contentBytes.length), "压缩成功！", JOptionPane.WARNING_MESSAGE, Data.correct);
                try {
                    resultWriter = new PrintStream(new FileOutputStream("src/huffmancode/Docs/result.txt"));
                    resultWriter.println("待压缩字符串：\n" + content + "\n\n压缩前字节为：\n" + re(Arrays.toString(contentBytes)) + "\n\n压缩后字节为：\n" + re(Arrays.toString(zipBytes)) + "\n\n" + "压缩后哈夫曼编码为：\n" + re(huffmanStr) + "\n\n" + "原长度: " + contentBytes.length + " 压缩后长度: " + zipBytes.length + " 压缩率: " + ((double) (contentBytes.length - zipBytes.length) / contentBytes.length));
                } catch (FileNotFoundException ex) {
                    throw new RuntimeException(ex);
                }
                Desktop desktop = Desktop.getDesktop();
                File file = new File("src/huffmancode/Docs/result.txt");
                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        this.add(zipButton);


        //解压按钮
        unzipButton.setBounds(340 + 105, 70, 95, 100);
        unzipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println("待解压哈夫曼编码：\n" + re(huffmanStr));
                System.out.println("解压后字符串成果：" + re(decoder(huffmanCodes)));
                JOptionPane.showMessageDialog(null, "解压成功！\n\n 压缩后哈夫曼编码为: \n" + re(huffmanStr) + "\n\n解压后得到的结果为：\n" + re(decoder(huffmanCodes)), "解压成功", JOptionPane.WARNING_MESSAGE, Data.correct);
                resultWriter.println("============以下为解码结果============");
                resultWriter.println("解压成功！\n\n 压缩后哈夫曼编码为: \n" + re(huffmanStr) + "\n\n解压后得到的结果为：\n" + re(decoder(huffmanCodes)));
                Desktop desktop = Desktop.getDesktop();
                File file = new File("src/huffmancode/Docs/result.txt");
                if (file.exists()) {
                    try {
                        desktop.open(file);
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                }
            }
        });
        this.add(unzipButton);

        //传送按钮
        transmitButton.setBounds(340, 180, 200, 100);
        transmitButton.setIcon(Data.upload);
        transmitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                ps.println("TRANSMIT");
                ps.println(huffmanStr);
                String tempHuffmanCode = "";
                for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
                    tempHuffmanCode = tempHuffmanCode + entry.getKey() + "=" + entry.getValue() + " ";
                }
                ps.println(tempHuffmanCode);
//                try {
//                    ObjectOutputStream objectOutputStream = new ObjectOutputStream(s.getOutputStream());
//                    objectOutputStream.writeObject(huffmanCodeBytesCopy);
//                    objectOutputStream.writeObject(huffmanCodes);
//                } catch (IOException ex) {
//                    throw new RuntimeException(ex);
//                }


            }
        });
        this.add(transmitButton);

        this.setVisible(true);
    }

    private static String re(String str) {
        int i = str.length() / 50;
        int a = 0;
        int b = 49;
        String re = "";
        while (i != 0) {
            i--;
            re = re + str.substring(a, b) + "\n";
            a += 50;
            b += 50;
        }
        re = re + str.substring(a);
        return re;
    }

    //创建带字符与频数的Node
    static class Node implements Comparable<Node> {
        Byte data;  //存字符
        int weight; //频数
        Node left;
        Node right;

        public void print(String prefix, boolean isTail) {
            showTree.println(prefix + (isTail ? "└── " : "├── ") + this.toString()); //showTree是PrintStream实例
            System.out.println(prefix + (isTail ? "└── " : "├── ") + this.toString());

            if (right != null) {
                right.print(prefix + (isTail ? "    " : "│   "), (null == left ? true : false));
            }

            if (left != null) {
                left.print(prefix + (isTail ? "    " : "│   "), true);
            }

        }

        public Node(Byte data, int weight) {
            this.data = data;
            this.weight = weight;
        }

        @Override
        public int compareTo(Node o) {
            return this.weight - o.weight;
        }

        @Override
        public String toString() {
            return " { " + data + ":" + weight + " } ";
        }

        //先序遍历
        public void preOrder() {
            System.out.print(this);
            showHuffmanTreePre.println(this);

            if (this.left != null) {
                this.left.preOrder();
            }
            if (this.right != null) {
                this.right.preOrder();
            }
        }

        public void midOrder() {
            if (this.left != null) {
                this.left.midOrder();
            }
            System.out.print(this);
            showHuffmanTreeMid.println(this);

            if (this.right != null) {
                this.right.midOrder();
            }
        }
    }


    //通过List创造赫夫曼树
    public static Node createHuffmanTree(List<Node> nodes) {
        while (nodes.size() != 1) {
            //排序
            Collections.sort(nodes);

            //取出前两个树
            Node leftNode = nodes.get(0);
            Node rightNode = nodes.get(1);

            //创建一棵新的树，根节点没有字符data，只有频数
            Node parent = new Node(null, leftNode.weight + rightNode.weight);
            parent.left = leftNode;
            parent.right = rightNode;

            //增添与移除
            nodes.remove(leftNode);
            nodes.remove(rightNode);
            nodes.add(parent);
        }
        return nodes.get(0);
    }

    /**
     * @param bytes 字符串ascii数组
     * @return 一个List 泛型为Node 如果打印list调用Node的toString方法 每个元素打印字符ascii与频数
     */
    public static List<Node> getNodes(byte[] bytes) {
        ArrayList<Node> nodes = new ArrayList<>();

        //存储每个byte出现的次数 -> map[key, value]
        Map<Byte, Integer> counts = new HashMap<>();
        for (byte b : bytes) {
            Integer count = counts.get(b);
            if (count == null) { //Map中没有
                counts.put(b, 1);
            } else {
                counts.put(b, count + 1);
            }
        }

        //把每个键值对转化成一个Node对象，并加入到nodes集合
        for (Map.Entry<Byte, Integer> entry : counts.entrySet()) { //遍历
            nodes.add(new Node(entry.getKey(), entry.getValue()));
        }
        return nodes;
    }


    public static String decoder(Map<Byte, String> huffmanCodes) {
        Map<String, String> map = new HashMap<String, String>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), ((int) (entry.getKey()) + ""));
        }
//        System.out.println("反向哈夫曼表：" + map);
        List<String> list = new ArrayList<>();
//        System.out.println("待解哈夫曼编码：" + re(huffmanStr));
        for (int i = 0; i < huffmanStr.length(); ) {
            int count = 1;
            boolean flag = true;
            String s = null;

            while (flag) {
                String key = huffmanStr.substring(i, i + count);
                s = map.get(key);
                if (s == null) {
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(s);
            i = i + count;
        }

        StringBuilder stringBuilder1 = new StringBuilder();
        byte[] bs = new byte[list.size()];
        for (int i = 0; i < list.size(); i++) {
            bs[i] = Byte.parseByte(list.get(i));
        }
        return new String(bs);
    }

    //生成哈夫曼树对应的哈夫曼编码
    //1. 将哈夫曼编码表存储在Map<Byte, String> 32:01 97:100 100:11000
    static Map<Byte, String> huffmanCodes = new HashMap<Byte, String>();
    //2. 在生成哈夫曼编码表示，需要去拼接路径，定义一个StringBuilder, 存储某个叶子结点的路径
    static StringBuilder stringBuilder = new StringBuilder();
    static String huffmanStr = null;


    /**
     * 将传入的node节点的所有叶子结点的哈夫曼编码得到，并放入到huffmanCodes集合中
     *
     * @param node          传入的根节点
     * @param code          路径：左子节点是0，右子节点是1
     * @param stringBuilder 用于拼接路径
     */
    public static void getCodes(Node node, String code, StringBuilder stringBuilder) {
        StringBuilder stringBuilder2 = new StringBuilder(stringBuilder);
        //将传入的code加入stringBuilder2
        stringBuilder2.append(code);
        if (node != null) {
            //判断是叶子结点还是中间结点
            if (node.data == null) { //非叶子结点
                //左递归
                getCodes(node.left, "0", stringBuilder2);
                //右递归
                getCodes(node.right, "1", stringBuilder2);
            } else { //叶子结点
                huffmanCodes.put(node.data, stringBuilder2.toString());
            }
        }

    }

    //为调用方便 重载getCodes

    public static Map<Byte, String> getCodes(Node rootNode) {
        if (rootNode == null) {
            return null;
        } else {
            getCodes(rootNode.left, "0", stringBuilder);
            getCodes(rootNode.right, "1", stringBuilder);
            return huffmanCodes;
        }
    }


    /**
     * 字符串对应的byte[]数组，通过生成的哈夫曼编码表，返回一个压缩字节数组
     *
     * @param bytes        原始的字符串对应的byte数组
     * @param huffmanCodes 生成的huffman编码Map
     * @return 压缩后的字节数组
     */
    //编写一个方法，将字符串对应的byte[]数组，通过生成的哈夫曼编码表，返回一个压缩字节数组
    public static byte[] zip(byte[] bytes, Map<Byte, String> huffmanCodes) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : bytes) {
            stringBuilder.append(huffmanCodes.get(b));
        }
        System.out.println("原字符串对应哈夫曼编码:\n" + re(stringBuilder.toString()));
        huffmanStr = stringBuilder.toString();

        //转为byte数组
        int len;
        if (stringBuilder.length() % 8 == 0) {
            len = stringBuilder.length() / 8;
        } else {
            len = stringBuilder.length() / 8 + 1;
        }
        //存储压缩后byte数组
        byte[] huffmanCodeBytes = new byte[len];
        int index = 0; //记录第几个byte
        for (int i = 0; i < stringBuilder.length(); i += 8) {
            String strByte;
            if (i + 8 > stringBuilder.length()) { //非8的倍数
                strByte = stringBuilder.substring(i);
            } else {
                strByte = stringBuilder.substring(i, i + 8);
            }
            //将strByte转成byte 放入huffmanCodeBytes
            huffmanCodeBytes[index] = (byte) Integer.parseInt(strByte, 2);
            index++;
        }
        return huffmanCodeBytes;
    }


    /**
     * @param bytes 原始的字符串对应的字节数组
     * @return 经过哈夫曼压缩过后的字节数组
     */
    //使用一个方法 将前面的方法封装起来 便于调用
    public static byte[] huffmanZip(byte[] bytes) {
        List<Node> nodes = getNodes(bytes);
        Node huffmanTreeRoot = createHuffmanTree(nodes);
        Map<Byte, String> huffmanCodes = getCodes(huffmanTreeRoot);
        byte[] huffmanCodeBytes = zip(bytes, huffmanCodes);
        return huffmanCodeBytes;
    }

    static byte[] huffmanCodeBytesCopy = null;

    /**
     * 将一个byte转成一个二进制的字符串
     *
     * @param b
     * @param flag 标识是否需要补高位，若为ture，表示需要补高位，如果是false，不需要补
     * @return byte b对应的补码二进制字符串
     */
    //数据的解压
    public static String byteToBitString(boolean flag, byte b) {
        int temp = b;
        //如果是正数 需要补位
        if (flag) {
            temp |= 256; //按位或 256为1 0000 0000 不会影响负数
        }

        String str = Integer.toBinaryString(temp); //temp对应的二进制补码
        if (flag || temp < 0) {
            return str.substring(str.length() - 8);
        } else {
            return str;
        }
    }


    /**
     * @param huffmanCodes 哈夫曼编码表
     * @param huffmanBytes 哈夫曼压缩得到的字节数组 [-88, .....]
     * @return 原来的字符串对应的字节数组
     */
    //对压缩数据的解码
    public static byte[] decode(Map<Byte, String> huffmanCodes, byte[] huffmanBytes) {
        //拿到huffmanBytes对应的二进制字符串 1011....
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < huffmanBytes.length; i++) {
            //判断是否为最后一个字节
            boolean flag = (i == huffmanBytes.length - 1);
            stringBuilder.append(byteToBitString(!flag, huffmanBytes[i]));
        }
        //把字符串按照指定的哈夫曼编码进行解码
        //哈夫曼编码进行调换，要反向查询
        Map<String, Byte> map = new HashMap<String, Byte>();
        for (Map.Entry<Byte, String> entry : huffmanCodes.entrySet()) {
            map.put(entry.getValue(), entry.getKey());
        }
//        System.out.println("反向哈夫曼表：" + re(map.toString()));

        //创建一个集合，存放byte
        List<Byte> list = new ArrayList<>();
        for (int i = 0; i < stringBuilder.length(); ) {
            int count = 1; //扫描块计数器
            boolean flag = true;
            Byte b = null;

            while (flag) {
                //取出一串'1' '0'
                String key = stringBuilder.substring(i, i + count);
                b = map.get(key);
                if (b == null) { //未匹配到
                    count++;
                } else {
                    flag = false;
                }
            }
            list.add(b);
            i += count;
        }
        //for循环结束后 list中存放了所有字符108 32 97...
        byte[] bytes = new byte[list.size()];
        for (int i = 0; i < bytes.length; i++) {
            bytes[i] = list.get(i);
        }
        return bytes;
    }

}
