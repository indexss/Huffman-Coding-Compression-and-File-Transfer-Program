public class testStringBuilder {
    public static void main(String[] args) {
        StringBuilder stringBuilder1 = new StringBuilder();
        stringBuilder1.append("hello");
        StringBuilder stringBuilder2 = stringBuilder1;
        System.out.println("s1 " + stringBuilder1);
        System.out.println("s2 " + stringBuilder2);
        stringBuilder1.append("helloworld");
        System.out.println("s1 c" + stringBuilder1);
        System.out.println("s2 c" + stringBuilder2);
    }
}
