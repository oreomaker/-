package client.view;

import javax.swing.*;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;

import java.awt.*;
import java.io.IOException;

/**
 * @author Zhang Hao
 *
 * 该类生成指南窗口
 *
 * 该类使用第三方包org.apache.poi,读取docx文件信息，呈现在窗口.
 * */
public class GuideFrame extends JFrame {
    JTextArea textArea;
    JPanel panel;

    public GuideFrame() throws HeadlessException {
        textArea = new JTextArea(20,50);
        try {
            textArea.append(ReadDoc());
        } catch (IOException e) {
            e.printStackTrace();
        }
        panel = new JPanel();
        panel.add(textArea);
        panel.setBounds(50,50,250,300);
        this.setTitle("Guide");
        this.add(panel);
        this.setBounds(300,300,500,400);
        this.setVisible(true);
    }

    //读取docx文件信息
    public static String ReadDoc() throws IOException {
        String resullt = "";
        try {
            OPCPackage opcPackage = POIXMLDocument.openPackage("sample/TankWarGuide.docx");
            XWPFWordExtractor extractor = new XWPFWordExtractor(opcPackage);
            resullt = extractor.getText();
            extractor.close();
        } catch(Exception e){
            e.printStackTrace();
        }
        return resullt;
    }

}
