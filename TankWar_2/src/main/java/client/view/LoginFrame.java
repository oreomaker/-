package client.view;

import client.net.NetThread;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;

public class LoginFrame extends JFrame {
    Starter starter;
    JLabel nameLabel;
    JTextField nameField;
    JButton logButton;
    JButton quitButton;
    JPanel middlePanel;
    JButton playButton;
    JComboBox nameBox;
    NetThread thread;
    private String userName="";
    JLabel passwordLabel;
    JPasswordField passwordField;
    JButton signButton;
    JButton successButton;
    JLabel noticeLabel;
    String password;

    JFrame invalidNoticeFrame;
    private JPanel invalidPanel;
    private JLabel invalidNoticeLabel;
    private JButton invalidOkButton;
    private SpringLayout invalidSpringLayout;

    JFrame askNoticeFrame;
    private JPanel askPanel;
    private JLabel askLabel;
    private JLabel askerLabel;
    private JButton askOkButton;
    private JButton askNoButton;
    private SpringLayout askSpringLayout;

    JFrame resultNoticeFrame;
    private JPanel resultPanel;
    private JLabel resultLabel;
    private JButton resultOkButton;
    private SpringLayout resultSpringLayout;

    public LoginFrame(NetThread thread, Starter starter) {
        this.thread = thread;
        this.starter = starter;

        this.setBounds(new Rectangle(400,370));
        passwordLabel = new JLabel("密码：");
        passwordField = new JPasswordField();
        signButton = new JButton("Sign Up");
        noticeLabel = new JLabel();

        nameLabel = new JLabel("输入用户名：");
        nameField = new JTextField();
        logButton = new JButton("Log in");
        playButton = new JButton("Play");
        middlePanel = new JPanel();
        quitButton = new JButton("Quit");
        successButton = new JButton("Successes");
        nameBox = new JComboBox();

        resultNoticeFrame=new JFrame();
        resultNoticeFrame.setBounds(new Rectangle(300,150));
        resultNoticeFrame.setLocationRelativeTo(null);
        resultPanel=new JPanel();
        resultOkButton=new JButton("OK");
        resultLabel=new JLabel();
        resultSpringLayout=new SpringLayout();

        askNoticeFrame=new JFrame();
        askNoticeFrame.setBounds(new Rectangle(300,150));
        askNoticeFrame.setLocationRelativeTo(null);
        askPanel=new JPanel();
        askNoButton=new JButton("NO");
        askOkButton=new JButton("OK");
        askerLabel=new JLabel();
        askLabel=new JLabel("向您发出对战请求，是否同意？");
        askSpringLayout=new SpringLayout();


        invalidNoticeFrame=new JFrame();
        invalidNoticeFrame.setBounds(new Rectangle(300,150));
        invalidNoticeFrame.setLocationRelativeTo(null);
        invalidPanel=new JPanel();
        invalidOkButton=new JButton("OK");
        invalidNoticeLabel=new JLabel("用户名错误");
        invalidSpringLayout=new SpringLayout();

        playButton.setEnabled(true);

        this.setLayout(new GridLayout(10,1));
        middlePanel.setLayout(new GridLayout(1,2));
        middlePanel.add(logButton);
        middlePanel.add(playButton);

        playButton.setEnabled(false);
        successButton.setEnabled(false);
        this.add(nameLabel);
        this.add(nameField);
        this.add(passwordLabel);
        this.add(passwordField);
        this.add(nameBox);
        this.add(middlePanel);
        this.add(successButton);
        this.add(signButton);
        this.add(quitButton);
        this.add(noticeLabel);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);

        //设置invalidBoard组件
        invalidNoticeFrame.setContentPane(invalidPanel);
        invalidPanel.setLayout(invalidSpringLayout);
        invalidPanel.add(invalidNoticeLabel);
        invalidPanel.add(invalidOkButton);
        SpringLayout.Constraints invalidLabelCons=invalidSpringLayout.getConstraints(invalidNoticeLabel);
        SpringLayout.Constraints invalidOkCons=invalidSpringLayout.getConstraints(invalidOkButton);
        //布置组件invalidBoard组件
        invalidLabelCons.setX(Spring.constant(50));
        invalidLabelCons.setY(Spring.constant(10));
        invalidLabelCons.setWidth(Spring.constant(100));
        invalidOkCons.setX(Spring.constant(10));
        invalidOkCons.setY(Spring.constant(60));
        invalidOkCons.setWidth(Spring.constant(60));
        invalidNoticeFrame.setVisible(false);

        //设置askBoard组件
        askNoticeFrame.setContentPane(askPanel);
        askPanel.setLayout(askSpringLayout);
        askPanel.add(askerLabel);
        askPanel.add(askLabel);
        askPanel.add(askNoButton);
        askPanel.add(askOkButton);
        SpringLayout.Constraints askerLabelCons=askSpringLayout.getConstraints(askerLabel);
        SpringLayout.Constraints askLabelCons=askSpringLayout.getConstraints(askLabel);
        SpringLayout.Constraints askOkCons=askSpringLayout.getConstraints(askOkButton);
        SpringLayout.Constraints askNoCons=askSpringLayout.getConstraints(askNoButton);
        //布置组件askBoard组件
        askerLabelCons.setX(Spring.constant(50));
        askerLabelCons.setY(Spring.constant(10));
        askLabelCons.setX(askerLabelCons.getConstraint(SpringLayout.WEST));
        askLabelCons.setY(Spring.sum(askerLabelCons.getConstraint(SpringLayout.SOUTH),Spring.constant(10)));
        askOkCons.setX(Spring.constant(10));
        askOkCons.setY(Spring.constant(60));
        askOkCons.setWidth(Spring.constant(60));
        askNoCons.setX(Spring.constant(120));
        askNoCons.setY(askOkCons.getConstraint(SpringLayout.NORTH));
        askNoCons.setWidth(Spring.constant(60));
        askNoticeFrame.setVisible(false);

        //设置resultBoard组件
        resultNoticeFrame.setContentPane(resultPanel);
        resultPanel.setLayout(resultSpringLayout);
        resultPanel.add(resultLabel);
        resultPanel.add(resultOkButton);
        SpringLayout.Constraints resultLabelCons=resultSpringLayout.getConstraints(resultLabel);
        SpringLayout.Constraints resultOkCons=resultSpringLayout.getConstraints(resultOkButton);
        //布置组件resultBoard组件
        resultLabelCons.setX(Spring.constant(50));
        resultLabelCons.setY(Spring.constant(10));
        resultLabelCons.setWidth(Spring.constant(200));
        resultOkCons.setX(Spring.constant(10));
        resultOkCons.setY(Spring.constant(60));
        resultOkCons.setWidth(Spring.constant(60));
        resultNoticeFrame.setVisible(false);

        logButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName = nameField.getText();
                String password = String.valueOf(passwordField.getPassword());

                nameField.setText("");
                passwordField.setText("");

                if(userName.equals("")||password.equals("")||userName.contains("|")||password.contains("|")){
                    JOptionPane.showMessageDialog(logButton,"用户名或密码不合规，请检查是否为空或含有‘|’，重新输入");
                } else{
                    try {
                        thread.sendMessage("login|"+userName+"|"+password+"\n");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        playButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String playWith = (String) nameBox.getSelectedItem();
                if(playWith!=null){
                    thread.getPlayers().add(playWith);
                    try {
                        thread.sendMessage("ask|"+userName+"|"+playWith+"\n");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        successButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    thread.sendMessage("success|"+userName+"\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        signButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                userName = nameField.getText();
                password = String.valueOf(passwordField.getPassword());

                nameField.setText("");
                passwordField.setText("");

                if(userName.equals("")||userName.contains("|")||password.contains("|")){
                    JOptionPane.showMessageDialog(logButton,"用户名或密码不合规，请检查是否为空或含有‘|’，重新输入");
                } else{
                    try {
                        thread.sendMessage("signup|"+userName+"|"+password+"\n");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //write and exit
                try {
                    if(userName.equals("")){
                        thread.sendMessage("quit|"+"anonymous"+"\n");
                    }else{
                        thread.sendMessage("quit|"+userName+"\n");
                    }
                    quit();
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
            }
        });

        invalidOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                invalidNoticeFrame.setVisible(false);
                userName="";
            }
        });

        askOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String asker=askerLabel.getText();
                try {
                    thread.sendMessage("play|"+asker+"|"+userName+"\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                askerLabel.setText("");
                askNoticeFrame.setVisible(false);
            }
        });

        askNoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String asker=askerLabel.getText();
                try {
                    thread.sendMessage("reject|"+asker+"\n");
                } catch (IOException ioException) {
                    ioException.printStackTrace();
                }
                thread.getPlayers().remove(asker);
                askerLabel.setText("");
                askNoticeFrame.setVisible(false);
            }
        });

        resultOkButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                resultNoticeFrame.setVisible(false);
                String result=resultLabel.getText();
                if (result.equals("对方拒绝对战")||result.equals("对方正在游戏中")){
                    thread.getPlayers().remove(thread.getPlayers().size()-1);
                }
                else{
                    //开始游戏
                    try {
                        thread.sendMessage("start|"+"\n");
                    } catch (IOException ioException) {
                        ioException.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    public void paintComponents(Graphics g) {
        super.paintComponents(g);
    }

    public void logIn(){
        signButton.setEnabled(false);
        logButton.setEnabled(false);
        playButton.setEnabled(true);
        successButton.setEnabled(true);
        noticeLabel.setText(userName+" Log In");
    }

    public void quit(){
        this.setVisible(false);
        starter.setVisible(true);
    }

    public void setNameBox(int length,String[] strings) {
        nameBox.removeAllItems();
        for (int i=1;i<length;i++){
            nameBox.addItem(strings[i]);
        }
        nameBox.removeItem(userName);
    }

    public void invalidNotice(){
        invalidNoticeFrame.setVisible(true);
    }

    public void askNotice(String name){
        askNoticeFrame.setVisible(true);
        askerLabel.setText(name);
    }

    public void resultNotice(int i){
        resultNoticeFrame.setVisible(true);
        if (i==0){
            resultLabel.setText("对方拒绝对战");
        }
        else if (i==1){
            resultLabel.setText("接受对战，点击ok开始游戏");
        }else {
            resultLabel.setText("对方正在游戏中");
        }
    }

    public void showSuccessFrame(int winNum,int loseNum) {
        new SuccessFrame(winNum,loseNum);
    }
}
