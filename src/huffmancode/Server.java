package huffmancode;

import javax.swing.*;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.Transferable;
import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;
import java.util.List;

public class Server extends JFrame implements Runnable {

    JLabel statement = new JLabel();
    ServerSocket ss = null;
    private BufferedReader br = null;
    private PrintStream ps = null;
    Socket socket = null;
    String ClientAddressNotation = null;


    public static void main(String[] args) throws Exception {
        new Server();
    }

    public static String decoder(Map<Byte, String> huffmanCodes, String huffmanStr) {
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

    static String re(String str) {
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

    public Server() throws Exception {

        ss = new ServerSocket(13531);
        socket = ss.accept();
        ClientAddressNotation = "Client: " + socket.getInetAddress().getHostAddress() + " access!";
        System.out.println(ClientAddressNotation);
        br = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ps = new PrintStream(socket.getOutputStream());

        this.add(statement, BorderLayout.CENTER);
        this.setTitle("接收端");
        this.setBounds(1250, 400, 200, 200);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);

        new Thread(this).start();


        this.setVisible(true);
    }

    static int countTrans = 1;

    @Override
    public void run() {
        System.out.println("\n待解压的内容为:\n");
        while (true) {
            statement.setLocation(20, 20);
            statement.setText("⬤暂无传送内容");
            statement.setFont(new Font("黑体", Font.BOLD, 20));
            statement.setForeground(Color.RED);
            String msg = null;
            String huffmanStr = "null";
            String huffmanCode = "null";
            try {
                msg = br.readLine();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (msg.equals("TRANSMIT")) {
                try {
                    huffmanStr = br.readLine();
                    huffmanCode = br.readLine();
                    System.out.println("123" + huffmanStr);
                    if (huffmanStr.equals("null") || huffmanCode.equals("null")) {
                        continue;
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
                statement.setText("⬤接受到" + countTrans++ + "条传送内容");
                statement.setForeground(Color.GREEN);
                System.out.println(re(huffmanStr));
                ps.println("GET");
            } else {
                ps.println("ERROR");
            }
            System.out.println("1");
            System.out.println(huffmanStr);

            System.out.println("3");
            String spaceHuffmanCode[] = huffmanCode.split(" ");
            Map<Byte, String> huffmanCodes = new HashMap<Byte, String>();
            for (String i : spaceHuffmanCode) {
                String temp[] = i.split("=");
                byte key = Byte.parseByte(temp[0]);
                huffmanCodes.put(key, temp[1]);
            }
            System.out.println("\n接收到的哈夫曼编码为: \n");
            System.out.println(re(huffmanCodes.toString()));
            System.out.println("\n解压后的字符串为:\n");
            System.out.println(re(decoder(huffmanCodes, huffmanStr)));

            Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
            // 封装文本内容
            Transferable trans = new StringSelection(decoder(huffmanCodes, huffmanStr));
            // 把文本内容设置到系统剪贴板
            clipboard.setContents(trans, null);

            if (!huffmanStr.equals("") && !(huffmanStr == null)) {
                JOptionPane.showMessageDialog(null, "解压成功！\n\n 压缩后哈夫曼编码为: \n" + re(huffmanStr) + "\n\n解压后得到的结果为：\n" + re(decoder(huffmanCodes, huffmanStr)) + "\n\n======结果已复制到剪贴板！======", "解压成功", JOptionPane.WARNING_MESSAGE, Data.correct);
            }
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("");


//            String msg = null;
//            try {
//                msg = br.readLine();
//                if(msg.equals("TRANSMIT")) {
//                    System.out.println("get");
//                    ObjectInputStream objectOutputStream = new ObjectInputStream(socket.getInputStream());
//                    Byte[] huffmanCodeBytes = (Byte[]) objectOutputStream.readObject();
//                    Map<Byte, String> huffmanCode = (Map<Byte, String>) objectOutputStream.readObject();
//                    System.out.println(Arrays.toString(huffmanCodeBytes));
//                    System.out.println(huffmanCode);
//                }
//            } catch (IOException e) {
//                throw new RuntimeException(e);
//            } catch (ClassNotFoundException e) {
//                throw new RuntimeException(e);
//            }
        }
    }
}
