package com.leiyza.ui;

import com.leiyza.communicate.*;
import com.leiyza.model.User;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.*;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;

public class ChatUI implements ActionListener, MouseListener {

    private static final Logger logger=Logger.getLogger(ChatUI.class);
    private MsgRecvThread msgRecvThread=new MsgRecvThread();
    private ProcessThread processThread=new ChatUI.ProcessThread();

    private static JFrame root;
    private User user;
    private JPanel panelLeftUp;
    private JPanel panelLefMiddle;
    private JPanel panelLeftDown;
    private JPanel panelRightUp;
    private JPanel panelRightDown;
    private JButton sendButton;
    private JTextArea msgWriteArea;
    private static JTextArea logViewArea;
    private JScrollPane logPanel;
    private JLabel headImage;
    private JButton myFriendsButton;
    private JScrollPane myFriendsJSP;
    private JPanel myFriendsPanel;
    private CardLayout cardLayout;
    private JTextField addFriendFiled;
    private JTextField deleteFriendFiled;
    private JButton addFriendButton;
    private JButton deleteFriendButton;


    private static HashMap<String,JLabel>friendsJLabelMap=new HashMap<>();//好友标签列表
    private static HashMap<String,JLabel>strangersJLabelMap=new HashMap<>();//
    private static HashMap<String,JLabel>blackJLabelMap=new HashMap<>();//

    private static HashMap<String,User>friendMap=new HashMap<>();//
    private static HashMap<String,User>strangerMap=new HashMap<>();
    private static HashMap<String,User>blackMap=new HashMap<>();
    private static final List<String>hasSendMsgToAdd=new ArrayList<>();

    private static final HashMap<User,ConcurrentLinkedQueue<Message>> friendArray=new HashMap<>();
    private static final HashMap<User,ConcurrentLinkedQueue<Message>>strangerArray=new HashMap<>();
    private static final HashMap<User,ConcurrentLinkedQueue<Message>>blackArray=new HashMap<>();
    private Communication communication;
    private User toUser;//当前聊天对象
    private JLabel chatWithLabel;//当前聊天对象
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

    ChatUI(Message loginMessage) {
        this.user = loginMessage.getMessageHead().getFrom();
        root = new JFrame(user.getUserNo());
        root.setLayout(null);
        root.setSize(805, 600);
        root.setLocationRelativeTo(null);
        root.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        root.setResizable(false);
        Container container = root.getContentPane();
        container.setLayout(null);
        friendArray.putAll(loginMessage.getRelationArrays().getFriendArray()==null?new HashMap<User,ConcurrentLinkedQueue<Message>>():loginMessage.getRelationArrays().getFriendArray());
        logger.info("bb");
        strangerArray.putAll(loginMessage.getRelationArrays().getStrangerArray()==null?new HashMap<User,ConcurrentLinkedQueue<Message>>():loginMessage.getRelationArrays().getStrangerArray());
        blackArray.putAll(loginMessage.getRelationArrays().getBlackArray()==null?new HashMap<User,ConcurrentLinkedQueue<Message>>():loginMessage.getRelationArrays().getBlackArray());
        initFriendMap();
        initStrangerMap();
        initBlackMap();

        initLeftUp();
        initLeftMiddle();
        initLeftDown();
        initRightUp();
        initRightDown();
        container.add(panelLeftUp);
        container.add(panelLefMiddle);
        container.add(panelLeftDown);
        container.add(panelRightUp);
        container.add(panelRightDown);
        root.setVisible(true);
        msgRecvThread.start();
        processThread.start();
    }
    public void initFriendMap(){
        for(User user:friendArray.keySet()){
            friendMap.put(user.getUserNo(),user);
        }
    }
    public void initStrangerMap(){
        for(User user:strangerArray.keySet()){
            strangerMap.put(user.getUserNo(),user);
        }
    }
    public void initBlackMap(){
        for(User user:blackArray.keySet()){
            blackMap.put(user.getUserNo(),user);
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
    public void initLeftMiddle(){
        panelLefMiddle = new JPanel();
        panelLefMiddle.setLayout(null);
        panelLefMiddle.setBackground(Color.CYAN);
        panelLefMiddle.setBounds(0, 101, 200, 60);
        Font fontMiddle = new Font("仿宋", 0, 14);
        JLabel jLabel=new JLabel("输入账号:");
        jLabel.setFont(fontMiddle);
        jLabel.setBounds(5,10,65,20);
        addFriendFiled=new JTextField();
        addFriendFiled.setBounds(70,10,120,20);
        addFriendButton=new JButton("添加");
        addFriendButton.setFont(fontMiddle);
        addFriendButton.setBounds(20,35,65,18);
        addFriendButton.addActionListener(this);
        deleteFriendButton=new JButton("删除");
        deleteFriendButton.setFont(fontMiddle);
        deleteFriendButton.setBounds(110,35,65,18);
        deleteFriendButton.addActionListener(this);
        panelLefMiddle.add(jLabel);
        panelLefMiddle.add(addFriendFiled);
        panelLefMiddle.add(addFriendButton);
        panelLefMiddle.add(deleteFriendButton);



    }
    public void initLeftDown() {
        panelLeftDown = new JPanel();
        panelLeftDown.setOpaque(false);
        cardLayout=new CardLayout();
        panelLeftDown.setBackground(Color.red);
        panelLeftDown.setBounds(0, 161, 200, 500);
        myFriendsButton=new JButton("我的好友");
        myFriendsButton.setBounds(0,0,200,20);
        friendsJLabelMap=new HashMap<>();
        strangersJLabelMap=new HashMap<>();
        blackJLabelMap=new HashMap<>();
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
        int y= Math.min((friendMap.size() * 25), 225);
        jp2_jb2.setBounds(0, y+25+10, 200, 25);
        jp2_jb2.setHorizontalAlignment(SwingConstants.LEFT );

        jp2_jb3 = new JButton("> 黑名单");
        jp2_jb3.addActionListener(this);
        jp2_jb3.setLayout(null);
        jp2_jb3.setBounds(0, y+50+10, 200, 25);
        jp2_jb3.setHorizontalAlignment(SwingConstants.LEFT );

        //
        jp_jsp = new JPanel(new GridLayout(friendMap.size(),1));
        jsp = new JScrollPane(jp_jsp);

        //初始化好友列表
        for(Map.Entry<String,User>entry:friendMap.entrySet()){
            ConcurrentLinkedQueue<Message>messagesFromUser=friendArray.get(entry.getValue());
            User friend=entry.getValue();
            ImageIcon imageIcon = new ImageIcon("resources/Images/head.jpg");//第二种方法获取相应路径下的图片文件
            Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            int count=messagesFromUser.size();
            String msgCountText;
            if(count>0){
                msgCountText = "<html><body>" + friend.getUserName() +"     未读消息+"+count +"<br>(" + friend.getUserNo()+ ")</body></html>";
            }
            else {
                msgCountText = "<html><body>" + friend.getUserName() +"<br>(" + friend.getUserNo()+ ")</body></html>";

            }
            JLabel jLabel=new JLabel(msgCountText);
            jLabel.setIcon(icon);
            jLabel.addMouseListener(this);
            jLabel.setName(friend.getUserNo());
            jLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            friendsJLabelMap.put(friend.getUserNo(),jLabel);
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
        int y= Math.min((strangerMap.size() * 25), 225);
        jp3_jb3.setBounds(0, y+50+10, 200, 25);
        jp3_jb3.setHorizontalAlignment(SwingConstants.LEFT );

        jp_jsp2 = new JPanel(new GridLayout(strangerMap.size(),1));
        jsp2 = new JScrollPane(jp_jsp2);

        //
        for(Map.Entry<String,User>entry:strangerMap.entrySet()){
            User stranger=entry.getValue();
            ConcurrentLinkedQueue<Message>messagesFromUser=strangerArray.get(entry.getValue());
            ImageIcon imageIcon = new ImageIcon("resources/Images/head.jpg");//第二种方法获取相应路径下的图片文件
            Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            int count=messagesFromUser.size();
            String msgCountText;
            if(count>0){
                msgCountText= "<html><body>" + stranger.getUserName() +"     未读消息+"+count +"<br>(" + stranger.getUserNo()+ ")</body></html>";

            }else {
                msgCountText= "<html><body>" + stranger.getUserName() +"<br>(" + stranger.getUserNo()+ ")</body></html>";
            }
            JLabel jLabel=new JLabel(msgCountText);
            jLabel.setIcon(icon);
            jLabel.addMouseListener(this);
            jLabel.setName(stranger.getUserNo());
            jLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            strangersJLabelMap.put(stranger.getUserNo(),jLabel);
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

        int y= Math.min((blackMap.size() * 25), 225);
        jp4_jb3 = new JButton("↓ 黑名单");
        jp4_jb3.addActionListener(this);
        jp4_jb3.setLayout(null);
        jp4_jb3.setBounds(0, 50, 200, 25);
        jp4_jb3.setHorizontalAlignment(SwingConstants.LEFT );

        //假定10个好友
        jp_jsp3 = new JPanel(new GridLayout(blackMap.size(),1));
        jsp3 = new JScrollPane(jp_jsp3);

        for(Map.Entry<String,User>entry:blackMap.entrySet()){
            ConcurrentLinkedQueue<Message>messagesFromUser=blackArray.get(entry.getValue());
            User black=entry.getValue();
            ImageIcon imageIcon = new ImageIcon("resources/Images/head.jpg");//第二种方法获取相应路径下的图片文件
            Icon icon = new ImageIcon(imageIcon.getImage().getScaledInstance(25, 25, Image.SCALE_DEFAULT));
            int count=messagesFromUser.size();
            String msgCountText;
            if(count>0){
                msgCountText = "<html><body>" + black.getUserName() +"     未读消息+"+count +"<br>(" + black.getUserNo()+ ")</body></html>";
            }else {
                msgCountText = "<html><body>" + black.getUserName() +"<br>(" + black.getUserNo()+ ")</body></html>";
            }

            JLabel jLabel=new JLabel(msgCountText);
            jLabel.setIcon(icon);
            jLabel.addMouseListener(this);
            jLabel.setName(black.getUserNo());
            jLabel.setBorder(BorderFactory.createLineBorder(Color.CYAN));
            blackJLabelMap.put(black.getUserNo(),jLabel);
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
            chatWithLabel.setName("null");
        }else {
            chatWithLabel=new JLabel("Chatting with "+toUser.getUserName()+"("+toUser.getUserNo()+")",JLabel.CENTER);
            chatWithLabel.setName(toUser.getUserNo());
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
        //logViewArea.append(user.getUserNo() + "(leiyza)\n" + "你好，世界！");
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
        msgWriteArea.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        panelRightDown.add(msgWriteArea);
    }

    /*public void initUserMessageMap(){//初始化，每个人的消息队列
        for(Map.Entry<String,User>entry:friendMap.entrySet()){
            usersMsgQueueMap.put(entry.getKey(),new ConcurrentLinkedQueue<Message>());
        }
        for(Map.Entry<String,User>entry:strangerMap.entrySet()){
            usersMsgQueueMap.put(entry.getKey(),new ConcurrentLinkedQueue<Message>());
        }
        for(Map.Entry<String,User>entry:blackMap.entrySet()){
            usersMsgQueueMap.put(entry.getKey(),new ConcurrentLinkedQueue<Message>());
        }

    }*/

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
            if(toUser==null){
                JOptionPane.showMessageDialog(root,"请选择消息发送对象","提示",JOptionPane.WARNING_MESSAGE);
                return;
            }
            if(communication==null){
                communication= Communication.getInstance();
            }
            Message message=getMsgToSend();
            if(check(message)){
                msgWriteArea.setText("");
                String msg=user.getUserNo() + "("+user.getUserName()+")\n" + message.getTextMessage().getMessageContent()+"\n";
                logViewArea.append(msg);
                communication.sendMsg(message);
            }
        }
        if(e.getSource()==addFriendButton){//添加好友
            String userNo=addFriendFiled.getText();
            if(!checkAddFriend(userNo)){
                return;
            }
            hasSendMsgToAdd.add(userNo);
            User friend=new User();
            friend.setUserNo(userNo);
            Message message=new Message();
            message.getMessageHead().setFrom(user);
            message.getMessageHead().setCommandType(Commands.ADDFRIEND);
            message.getMessageHead().setTo(friend);
            if(communication==null){
                communication= Communication.getInstance();
            }
            communication.sendMsg(message);
            JOptionPane.showMessageDialog(root,"已发送");
        }
    }
    public boolean check(Message message){
        if(message.getTextMessage().getMessageContent()==null||message.getTextMessage().getMessageContent().length()<=0){
            return false;
        }else{
            return true;
        }
    }
    public boolean checkAddFriend(String userNo){
       if(!userNo.matches("[1-9][0-9]{4,9}")){
            JOptionPane.showMessageDialog(root,"请输入由5-10位数字组成且首位非0的字符串","提示",JOptionPane.ERROR_MESSAGE);
            return false;
       }else if(friendMap.containsKey(userNo)){
           JOptionPane.showMessageDialog(root,"该用户已是您的好友","提示",JOptionPane.ERROR_MESSAGE);
           return false;
       }else if(blackMap.containsKey(userNo)){
           JOptionPane.showMessageDialog(root,"该用户在您的黑名单中","提示",JOptionPane.ERROR_MESSAGE);
           return false;
       }else if(strangerMap.containsKey(userNo)){
           JOptionPane.showMessageDialog(root,"该用户在您的陌生人列表中","提示",JOptionPane.ERROR_MESSAGE);
           return false;
       }else if(hasSendMsgToAdd.contains(userNo)){
           JOptionPane.showMessageDialog(root,"您发送添加好友请求，请勿重复发送","提示",JOptionPane.ERROR_MESSAGE);
           return false;
       }else {
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
            chatWithLabel.setName(toUser.getUserNo());
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
        String name=e.getComponent().getName();
        if(toUser!=null&&!name.equals(toUser.getUserNo())){
            logViewArea.setText("");
        }
        if(friendsJLabelMap.containsKey(name)){
            logger.info("点击了我的好友"+name);
            User from=friendMap.get(name);
            ConcurrentLinkedQueue<Message>queue=friendArray.get(from);
            while (!queue.isEmpty()){
                Message messageOld=queue.poll();
                logViewArea.append(from.getUserNo() +"("+from.getUserName() +")\n" + messageOld.getTextMessage().getMessageContent()+"\n");
            }
            String msgCountText = "<html><body>" + from.getUserName() +"<br>(" + from.getUserNo()+ ")</body></html>";
            ChatUI.friendsJLabelMap.get(from.getUserNo()).setText(msgCountText);
            return friendMap.get(name);
        }
        if(strangersJLabelMap.containsKey(name)){
            logger.info("点击了我的陌生人"+name);
            User from=strangerMap.get(name);
            ConcurrentLinkedQueue<Message>queue=strangerArray.get(from);
            while (!queue.isEmpty()){
                Message messageOld=queue.poll();
                logViewArea.append(from.getUserNo() +"("+from.getUserName() +")\n" + messageOld.getTextMessage().getMessageContent()+"\n");
            }
            String msgCountText = "<html><body>" + from.getUserName() +"<br>(" + from.getUserNo()+ ")</body></html>";
            ChatUI.strangersJLabelMap.get(from.getUserNo()).setText(msgCountText);
            return strangerMap.get(name);
        }
        if(blackJLabelMap.containsKey(name)){
            logger.info("点击了我的黑名单"+name);
            User from=blackMap.get(name);
            ConcurrentLinkedQueue<Message>queue=blackArray.get(from);
            while (!queue.isEmpty()){
                Message messageOld=queue.poll();
                logViewArea.append(from.getUserNo() +"("+from.getUserName() +")\n" + messageOld.getTextMessage().getMessageContent()+"\n");
            }
            String msgCountText = "<html><body>" + from.getUserName() +"<br>(" + from.getUserNo()+ ")</body></html>";
            ChatUI.blackJLabelMap.get(from.getUserNo()).setText(msgCountText);
            return blackMap.get(name);
        }
        return null;
    }
    public class ProcessThread extends Thread{
        public void run() {
            while (!msgRecvThread.isStopped()){
                if(!MessageQueue.messageQueue.isEmpty()){
                    Message message=MessageQueue.messageQueue.poll();
                    assert message != null;
                    logger.info("收到来自:"+message.getMessageHead().getFrom().getUserNo()+"的消息"+message.getTextMessage().getMessageContent());
                    if(Commands.STOPPED_BY_OTHER.equals(message.getMessageHead().getCommandType())){
                        msgRecvThread.Stop();
                        JOptionPane.showMessageDialog(ChatUI.root,message.getTextMessage().getMessageContent(),"error",JOptionPane.ERROR_MESSAGE);
                    }else if(Commands.TALKING.equals(message.getMessageHead().getCommandType())){
                        logger.info("message from:"+message.getMessageHead().getFrom().toString()+",msg body:"+message.getTextMessage().getMessageContent() );
                        parseMessage(message);
                    }else if(Commands.ADDFRIEND.equals(message.getMessageHead().getCommandType())){
                        logger.info("message from:"+message.getMessageHead().getFrom().toString()+",msg body:"+message.getTextMessage().getMessageContent() );

                    }
                }
            }
            System.exit(0);
        }
        public void parseMessage(Message message){
            User from=message.getMessageHead().getFrom();
            from=friendMap.get(from.getUserNo());
            if(toUser!=null){
                logger.info("当前聊天的是"+toUser.getUserNo()+" label"+chatWithLabel.getName());
            }
            if(chatWithLabel.getName().equals(from.getUserNo())){
                ConcurrentLinkedQueue<Message>queue=friendArray.get(from);
                while (!queue.isEmpty()){
                    Message messageOld=queue.poll();
                    logViewArea.append(from.getUserNo() +"("+from.getUserName() +")\n" + messageOld.getTextMessage().getMessageContent()+"\n");
                }
                logViewArea.append(from.getUserNo() +"("+from.getUserName() +")\n" + message.getTextMessage().getMessageContent()+"\n");
            }else {
                if(ChatUI.friendMap.containsKey(from.getUserNo())){
                    from=friendMap.get(from.getUserNo());
                    friendArray.get(from).add(message);
                    int count=friendArray.get(from).size();
                    String msgCountText = "<html><body>" + from.getUserName() +"     未读消息+"+count +"<br>(" + from.getUserNo()+ ")</body></html>";
                    logger.info(msgCountText);
                    ChatUI.friendsJLabelMap.get(from.getUserNo()).setText(msgCountText);
                }else if(ChatUI.strangerMap.containsKey(from.getUserNo())){
                    from=strangerMap.get(from.getUserNo());
                    strangerArray.get(from).add(message);
                    int count=strangerArray.get(from).size();
                    String msgCountText = "<html><body>" + from.getUserName() +"     未读消息+"+count +"<br>(" + from.getUserNo()+ ")</body></html>";
                    ChatUI.strangersJLabelMap.get(from.getUserNo()).setText(msgCountText);
                }else if(ChatUI.blackMap.containsKey(from.getUserNo())){
                    from=blackMap.get(from.getUserNo());
                    blackArray.get(from).add(message);
                    int count=blackArray.get(from).size();
                    String msgCountText = "<html><body>" + from.getUserName() +"     未读消息+"+count +"<br>(" + from.getUserNo()+ ")</body></html>";
                    ChatUI.blackJLabelMap.get(from.getUserNo()).setText(msgCountText);
                }
            }
            return;
        }
    }
    public void startProcess(){
        msgRecvThread.start();
        processThread.start();
    }

}
