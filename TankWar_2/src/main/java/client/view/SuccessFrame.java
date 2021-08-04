package client.view;

import client.model.Chart;

import javax.swing.*;
import java.awt.*;

public class SuccessFrame extends JFrame {
    Chart chart;
    SuccessPanel panel;
    String picturePath;

    public SuccessFrame(int winNum,int loseNum) throws HeadlessException {
        chart = new Chart();
        picturePath = chart.savePieChart(winNum,loseNum);
        panel = new SuccessPanel();
        panel.setBounds(0,0,400,300);
        this.add(new SuccessPanel());
        this.setTitle("Rate");
        this.setLocationRelativeTo(null);
        this.setBounds(400,400,430,350);
        this.setVisible(true);
    }

    private class SuccessPanel extends JPanel{
        ImageIcon picture;

        public SuccessPanel() {
            picture = new ImageIcon(picturePath);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            picture.paintIcon(this,g,0,0);
        }
    }
}
