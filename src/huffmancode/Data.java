package huffmancode;

import javax.swing.*;
import java.net.URL;

public class Data {
    public static URL correctURL = Data.class.getResource("Statics/correct.png");
    public static URL textURL = Data.class.getResource("Statics/textIcon.png");
    public static URL fileURL = Data.class.getResource("Statics/fileIcon.png");
    public static URL zipURL = Data.class.getResource("Statics/zipIcon.png");
    public static URL lineURL = Data.class.getResource("Statics/line.png");
    public static URL uploadURL = Data.class.getResource("Statics/upload.png");

    public static ImageIcon correct;
    public static ImageIcon text;
    public static ImageIcon file;
    public static ImageIcon zip;
    public static ImageIcon line;
    public static ImageIcon upload;

    static{
        correct = new ImageIcon(correctURL);
        text = new ImageIcon(textURL);
        file = new ImageIcon(fileURL);
        zip = new ImageIcon(zipURL);
        line = new ImageIcon(lineURL);
        upload = new ImageIcon(uploadURL);
    }
}
