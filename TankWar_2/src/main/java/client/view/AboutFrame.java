package client.view;

import client.greedysnake.Snake;
import client.model.GameMap;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class AboutFrame extends JFrame {
    JTextArea area;
    JButton button;

    public AboutFrame() throws HeadlessException {
        area = new JTextArea(20,30);
        area.setEditable(false);
        try {
            setTextAreaContend();
        } catch (IOException e) {
            e.printStackTrace();
        }
        button = new JButton("Egg");
        button.setBounds(100,320,20,10);
        JPanel panel = new JPanel();
        panel.setBounds(10,10,200,300);
        panel.add(area);

        panel.add(button);
        this.setTitle("About");
        this.add(panel);
        this.pack();
        this.setVisible(true);

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                new Snake();
            }
        });
    }

    public void setTextAreaContend() throws IOException {
        InputStreamReader read = null;

        String filePath = GameMap.class.getClassLoader().getResource("sample/about.txt").getPath();

        File file = new File(filePath);
        try {
            read = new InputStreamReader(new FileInputStream(file),"UTF-8");
            //文件流是否存在
            if(file.isFile() && file.exists()){
                @SuppressWarnings("resource")
                BufferedReader reader = new BufferedReader(read);
                String line;
                while((line = reader.readLine()) != null){
                    area.append(line+"\n");
                }
            }else{
                throw new Exception("文件不存在");
            }
        } catch (Exception e) {

        }finally{
            //关闭文件流
            read.close();
        }
    }
}
