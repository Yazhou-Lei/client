package com.leiyza.ui;

import com.leiyza.communicate.Commands;
import com.leiyza.communicate.Communication;
import com.leiyza.communicate.Message;
import com.leiyza.communicate.MsgRecvThread;
import com.leiyza.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class ChatUI implements ActionListener, MouseListener {

    private JFrame root;
    private User user;
    private JPanel panelLeftUp;
    private JPanel panelLeftDown;
    private JPanel panelRightUp;
    private JPanel panelRightDown;
    private JButton sendButton;
    private JTextArea msgWriteArea;
    private JTextArea logViewArea;
    private JScrollPane logPanel;
    private JLabel headImage;
    private JButton myFriendsButton;
    private JScrollPane myFriendsJSP;
    private JPanel myFriendsPanel;
    private CardLayout cardLayout;

    private HashMap<String,User>myFriendsList;//我的好友
    private List<JLabel>myFriendsListJLabel;//好友标签列表
    private HashMap<String,User>strangersList;//陌生人
    private List<JLabel>strangersListJLabel;//
    private HashMap<String,User>blackList;//黑名单
    private List<JLabel>blackListJLabel;//

    private Communication communication;
    private User toUser;
    private MsgRecvThread thread;
    private JLabel chatWithLabel;
    //好友列表
    //第一张卡片
    JPanel jp1;
    JButton jp1_jb1,jp1_jb2,jp1_jb3;

    //第二张卡片
    JPanel jp2;
    JScrollPane jsp;
    JPanel jp_jsp;//用来放jsp
    JButton jp2_jb1,jp2_jb2,jp2_jb3;

    //第三张卡片
    JPanel jp3;
    JScrollPane jsp2;
    JPanel jp_jsp2;//用来放jsp2
    JButton jp3_jb1,jp3_jb2,jp3_jb3;

    //第四张卡片
    JPanel jp4;
    JScrollPane jsp3;
    JPanel jp_jsp3;
    JButton jp4_jb1,jp4_jb2,jp4_jb3;

    Font font = new Font("仿宋", 0, 18);

    ChatUI(User user) {
        this.user = user;
        root = new JFrame(user.getUserNo());
        root.setLayout(null);
        root.setSize(805, 600);
        root.setLocationRelativeTo(null);
        root.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        root.setResizable(false);
        Container container = root.getContentPane();
        container.setLayout(null);
        initMyFriendsList();//初始化好友列表
        initStrangersList();//初始化陌生人列表
        initBlackList();//初始化黑名单列表
        initLeftUp();
        initLeftDown();
        initRightUp();
        initRightDown();
        container.add(panelLeftUp);
        container.add(panelLeftDown);
        container.add(panelRightUp);
        container.add(panelRightDown);
        root.setVisible(true);
        //thread=new MsgRecvThread();
        //thread.start();
    }
    public void initMyFriendsList(){
        myFriendsList=new HashMap<>();
        for(int i=0;i<1;i++){
            User user=new User();
            user.setUserName("leiyazhou:"+i);
            user.setUserNo("642555054"+i);
            myFriendsList.put(user.getUserNo(),user);
        }
    }
    public void initStrangersList(){
        strangersList=new HashMap<>();
        for(int i=0;i<5;i++){
            User user=new User();
            user.setUserName("leiyazhou:"+i);
            user.setUserNo("642555054"+i);
            strangersList.put(user.getUserNo(),user);
        }
    }
    public void initBlackList(){
        blackList=new HashMap<>();
        for(int i=0;i<5;i++){
            User user=new User();
            user.setUserName("leiyazhou:"+i);
            user.setUserNo("642555054"+i);
            blackList.put(user.getUserNo(),user);
        }
    }
    public void initLeftUp() {
        panelLeftUp = new JPanel();
        panelLeftUp.setLayout(null);
        panelLeftUp.setBackground(Color.CYAN);
        panelLeftUp.setBounds(0, 0, 200, 100);
        ImageIcon imageIcon = new ImageIcon("resources/Images/head.jpg");//第二种方法获取相应路径下的图片文件
        Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(64, 64, Image.SCALE_DEFAULT));
        String userHeadInfo = "<html><body>" + user.getUserName() + "<br>" + user.getUserNo() + "</body></html>";
        headImage = new JLabel(userHeadInfo);
        headImage.setIcon(icon);
        headImage.setBounds(20, 20, 150, 60);
        panelLeftUp.add(headImage);

    }

    public void initLeftDown() {
        panelLeftDown = new JPanel();
        panelLeftDown.setOpaque(false);
        cardLayout=new CardLayout();
        panelLeftDown.setBackground(Color.red);
        panelLeftDown.setBounds(0, 100, 200, 500);
        myFriendsButton=new JButton("我的好友");
        myFriendsButton.setBounds(0,0,200,20);
        myFriendsListJLabel=new ArrayList<>();
        strangersListJLabel=new ArrayList<>();
        blackListJLabel=new ArrayList<>();
        firstCard();
        secondCard();
        thirdCard();
        fourthCard();

        panelLeftDown.setLayout(cardLayout);
        panelLeftDown.add(jp1,"1");
        panelLeftDown.add(jp2,"2");
        panelLeftDown.add(jp3,"3");
        panelLeftDown.add(jp4,"4");


    }
    public void firstCard(){
        jp1 = new JPanel();

        jp1_jb1 = new JButton("> 我的好友");
        jp1_jb1.addActionListener(this);
        jp1_jb1.setLayout(null);
        jp1_jb1.setSize(200, 25);
        jp1_jb1.setHorizontalAlignment(SwingConstants.LEFT );

        jp1_jb2 = new JButton("> 陌生人");
        jp1_jb2.addActionListener(this);
        jp1_jb2.setLayout(null);
        jp1_jb2.setBounds(0, 25, 200, 25);
        jp1_jb2.setHorizontalAlignment(SwingConstants.LEFT );

        jp1_jb3 = new JButton("> 黑名单");
        jp1_jb3.addActionListener(this);
        jp1_jb3.setLayout(null);
        jp1_jb3.setBounds(0, 50, 200, 25);
        jp1_jb3.setHorizontalAlignment(SwingConstants.LEFT );

        jp1.add(jp1_jb1);
        jp1.add(jp1_jb2);
        jp1.add(jp1_jb3);
        jp1.setLayout(null);
        jp1.setOpaque(false);

    }
    public void secondCard(){//显示好友列表
        jp2 = new JPanel();

        jp2_jb1 = new JButton("↓ 我的好友");
        jp2_jb1.addActionListener(this);
        jp2_jb1.setLayout(null);
        jp2_jb1.setSize(200, 25);
        jp2_jb1.setHorizontalAlignment(SwingConstants.LEFT );

        jp2_jb2 = new JButton("> 陌生人");
        jp2_jb2.addActionListener(this);
        jp2_jb2.setLayout(null);
        int y= Math.min((myFriendsList.size() * 25), 225);
        jp2_jb2.setBounds(0, y+25+10, 200, 25);
        jp2_jb2.setHorizontalAlignment(SwingConstants.LEFT );

        jp2_jb3 = new JButton("> 黑名单");
        jp2_jb3.addActionListener(this);
        jp2_jb3.setLayout(null);
        jp2_jb3.setBounds(0, y+50+10, 200, 25);
        jp2_jb3.setHorizontalAlignment(SwingConstants.LEFT );

        //
        jp_jsp = new JPanel(new GridLayout(myFriendsList.size(),1));
        jsp = new JScrollPane(jp_jsp);

        //初始化好友列表
        for(String userNo:myFriendsList.keySet()){
            ImageIcon imageIcon = new ImageIcon("resources/Images/head.jpg");//第二种方法获取相应路径下的图片文件
            Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            String userHeadInfo = "<html><body>" + myFriendsList.get(userNo).getUserName() + "<br>(" + myFriendsList.get(userNo).getUserNo()+ ")</body></html>";
            JLabel jLabel=new JLabel(userHeadInfo);
            jLabel.setIcon(icon);
            jLabel.addMouseListener(this);
            jLabel.setName(userNo);
            jLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            myFriendsListJLabel.add(jLabel);
            jp_jsp.add(jLabel);
        }
        jsp.setBounds(1, 25, 200, y+10);

        //jsp.setLayout(null);错误！，jsp本来就没有布局
        jp2.add(jsp);
        jp2.add(jp2_jb1);
        jp2.add(jp2_jb2);
        jp2.add(jp2_jb3);
        jp2.setLayout(null);
        jp2.setOpaque(false);

    }
    public void thirdCard(){//显示陌生人
        jp3 = new JPanel();

        jp3_jb1 = new JButton("> 我的好友");
        jp3_jb1.addActionListener(this);
        jp3_jb1.setLayout(null);
        jp3_jb1.setSize(200, 25);
        jp3_jb1.setHorizontalAlignment(SwingConstants.LEFT );

        jp3_jb2 = new JButton("↓ 陌生人");
        jp3_jb2.addActionListener(this);
        jp3_jb2.setLayout(null);
        jp3_jb2.setBounds(0, 25, 200, 25);
        jp3_jb2.setHorizontalAlignment(SwingConstants.LEFT );

        jp3_jb3 = new JButton("> 黑名单");
        jp3_jb3.addActionListener(this);
        jp3_jb3.setLayout(null);
        int y= Math.min((strangersList.size() * 25), 225);
        jp3_jb3.setBounds(0, y+50+10, 200, 25);
        jp3_jb3.setHorizontalAlignment(SwingConstants.LEFT );

        jp_jsp2 = new JPanel(new GridLayout(strangersList.size(),1));
        jsp2 = new JScrollPane(jp_jsp2);

        //
        for(String userNo:strangersList.keySet()){
            ImageIcon imageIcon = new ImageIcon("resources/Images/head.jpg");//第二种方法获取相应路径下的图片文件
            Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            String userHeadInfo = "<html><body>" + strangersList.get(userNo).getUserName() + "<br>(" + strangersList.get(userNo).getUserNo()+ ")</body></html>";
            JLabel jLabel=new JLabel(userHeadInfo);
            jLabel.setIcon(icon);
            jLabel.addMouseListener(this);
            jLabel.setName(userNo);
            jLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            strangersListJLabel.add(jLabel);
            jp_jsp2.add(jLabel);
        }

        jsp2.setBounds(1, 50, 200, y+10);

        jp3.add(jsp2);
        jp3.add(jp3_jb1);
        jp3.add(jp3_jb2);
        jp3.add(jp3_jb3);
        jp3.setLayout(null);
        jp3.setOpaque(false);

    }
    public void fourthCard(){//显示黑名单
        jp4 = new JPanel();

        jp4_jb1 = new JButton("> 我的好友");
        jp4_jb1.addActionListener(this);
        jp4_jb1.setLayout(null);
        jp4_jb1.setSize(200, 25);
        jp4_jb1.setHorizontalAlignment(SwingConstants.LEFT );

        jp4_jb2 = new JButton("> 陌生人");
        jp4_jb2.addActionListener(this);
        jp4_jb2.setLayout(null);
        jp4_jb2.setBounds(0, 25, 200, 25);
        jp4_jb2.setHorizontalAlignment(SwingConstants.LEFT );

        int y= Math.min((blackList.size() * 25), 225);
        jp4_jb3 = new JButton("↓ 黑名单");
        jp4_jb3.addActionListener(this);
        jp4_jb3.setLayout(null);
        jp4_jb3.setBounds(0, 50, 200, 25);
        jp4_jb3.setHorizontalAlignment(SwingConstants.LEFT );

        //假定10个好友
        jp_jsp3 = new JPanel(new GridLayout(blackList.size(),1));
        jsp3 = new JScrollPane(jp_jsp3);

        for(String userNo:blackList.keySet()){
            ImageIcon imageIcon = new ImageIcon("resources/Images/head.jpg");//第二种方法获取相应路径下的图片文件
            Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            String userHeadInfo = "<html><body>" + blackList.get(userNo).getUserName() + "<br>(" + blackList.get(userNo).getUserNo()+ ")</body></html>";
            JLabel jLabel=new JLabel(userHeadInfo);
            jLabel.setIcon(icon);
            jLabel.addMouseListener(this);
            jLabel.setName(userNo);
            jLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            blackListJLabel.add(jLabel);
            jp_jsp3.add(jLabel);
        }

        jsp3.setBounds(1, 75, 200, y+10);

        jp4.add(jsp3);
        jp4.add(jp4_jb1);
        jp4.add(jp4_jb2);
        jp4.add(jp4_jb3);
        jp4.setLayout(null);
        jp4.setOpaque(false);

    }

    public void initRightUp() {
        panelRightUp = new JPanel();
        panelRightUp.setLayout(null);
        //panelRightUp.setBackground(Color.black);
        panelRightUp.setBounds(200, 0, 600, 400);

        if(toUser==null){
            chatWithLabel=new JLabel("null",JLabel.CENTER);
        }else {
            chatWithLabel=new JLabel("Chatting with "+toUser.getUserName()+"("+toUser.getUserNo()+")",JLabel.CENTER);
        }
        chatWithLabel.setFont(font);
        chatWithLabel.setBounds(0,0,600,20);
        //chatWithLabel.setBackground(Color.white);
        //chatWithLabel.setOpaque(true);
        //chatWithLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
        logViewArea = new JTextArea(40, 40);
        logViewArea.setFont(font);
        logViewArea.setLineWrap(true);//自动换行
        logViewArea.setEditable(false);
        logViewArea.append(user.getUserNo() + "(leiyza)\n" + "你好，世界！");
        logPanel = new JScrollPane(logViewArea);
        logPanel.setBounds(0, 20, 600, 380);
        panelRightUp.add(chatWithLabel);
        panelRightUp.add(logPanel);
    }

    public void initRightDown() {
        panelRightDown = new JPanel();
        panelRightDown.setLayout(null);
        panelRightDown.setBackground(Color.BLUE);
        panelRightDown.setBounds(200, 400, 600, 200);
        sendButton = new JButton("send");
        sendButton.addActionListener(this);
        sendButton.setFont(font);
        sendButton.setBounds(455, 0, 150, 20);
        panelRightDown.add(sendButton);
        msgWriteArea = new JTextArea();
        msgWriteArea.setBounds(0, 0, 455, 200);
        panelRightDown.add(msgWriteArea);
    }

    public static void main(String[] args) {
        User user = new User();
        user.setUserNo("642555054");
        user.setUserName("leiyazhou");
        new ChatUI(user);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==jp1_jb1)
        {
            cardLayout.show(panelLeftDown, "2");;
        }
        if(e.getSource()==jp1_jb2)
        {
            cardLayout.show(panelLeftDown, "3");;
        }
        if(e.getSource()==jp1_jb3)
        {
            cardLayout.show(panelLeftDown, "4");;
        }

        //第二张卡片的按钮
        if(e.getSource()==jp2_jb1)
        {
            cardLayout.show(panelLeftDown, "1");;
        }
        if(e.getSource()==jp2_jb2)
        {
            cardLayout.show(panelLeftDown, "3");;
        }
        if(e.getSource()==jp2_jb3)
        {
            cardLayout.show(panelLeftDown, "4");;
        }

        //第三张卡片的按钮
        if(e.getSource()==jp3_jb1)
        {
            cardLayout.show(panelLeftDown, "2");;
        }
        if(e.getSource()==jp3_jb2)
        {
            cardLayout.show(panelLeftDown, "1");;
        }
        if(e.getSource()==jp3_jb3)
        {
            cardLayout.show(panelLeftDown, "4");;
        }

        //第四张卡片的按钮
        if(e.getSource()==jp4_jb1)
        {
            cardLayout.show(panelLeftDown, "2");;
        }
        if(e.getSource()==jp4_jb2)
        {
            cardLayout.show(panelLeftDown, "3");;
        }
        if(e.getSource()==jp4_jb3)
        {
            cardLayout.show(panelLeftDown, "1");;
        }

        //发送按钮
        if(e.getSource()==sendButton){
            if(communication==null){
                communication= Communication.getInstance();
            }
            Message message=getMsgToSend();
            if(check(message)){
                communication.sendMsg(message);
            }
        }
    }
    public boolean check(Message message){
        if(message.getTextMessage().getMessageContent()==null||message.getTextMessage().getMessageContent().length()<=0){
            return false;
        }else{
            return true;
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {
        toUser=getToUser(e);
        if(toUser==null){
            chatWithLabel.setText("null");
        }else {
            chatWithLabel.setText("Chatting with "+toUser.getUserName()+"("+toUser.getUserNo()+")");
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {

    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
    public Message getMsgToSend(){
        Message message=new Message();
        message.getMessageHead().setFrom(user);
        message.getMessageHead().setTo(toUser);
        message.getMessageHead().setCommandType(Commands.TALKING);
        message.getTextMessage().setMessageContent(msgWriteArea.getText());
        return message;
    }
    public User getToUser(MouseEvent e){
        for(int i=0;i<myFriendsListJLabel.size();i++){
            if(e.getSource()==myFriendsListJLabel.get(i)){
                return myFriendsList.get(myFriendsListJLabel.get(i).getName());
            }
        }
        for(int i=0;i<strangersListJLabel.size();i++){
            if(e.getSource()==strangersListJLabel.get(i)){
                return strangersList.get(strangersListJLabel.get(i).getName());
            }
        }
        for(int i=0;i<blackListJLabel.size();i++){
            if(e.getSource()==blackListJLabel.get(i)){
                return blackList.get(blackListJLabel.get(i).getName());
            }
        }
        return null;
    }
    public HashMap<String, User> getMyFriendsList() {
        return myFriendsList;
    }

    public void setMyFriendsList(HashMap<String, User> myFriendsList) {
        this.myFriendsList = myFriendsList;
    }
}
