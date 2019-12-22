package com.leiyza.ui;

import com.leiyza.communicate.Communication;
import com.leiyza.communicate.Message;
import com.leiyza.model.User;
import org.apache.log4j.Logger;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame implements ActionListener {
    private static final Logger logger=Logger.getLogger(LoginFrame.class);
    LoginPanel loginPanel;
    RegisterPanel registerPanel;
    Communication communication;
    public LoginFrame(){
        loginPanel=new LoginPanel(this);
        this.setTitle("we're family");
        this.setLayout(null);
        this.setSize(800, 600);
        this.setLocationRelativeTo(null);
        this.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        this.setContentPane(loginPanel);
        this.setVisible(true);
        this.setResizable(false);
    }
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource().equals(loginPanel.loginButton)){//处理登录命令
            doLoginEvent();
        }else if(e.getSource().equals(loginPanel.registerButton)){//跳转到登录界面
            gotoRegisterPanel();
        }else if(e.getSource().equals(registerPanel.loginButton)){//跳转到注册界面
            gotoLoginPanel();
        }else if(e.getSource().equals(registerPanel.registerButton)){//处理注册命令
            doRegisterEvent();
        }
    }
    /*
    * 跳转到注册界面
    * */
    public void gotoRegisterPanel(){
        loginPanel.setVisible(false);
        if(registerPanel!=null){
            this.setContentPane(registerPanel);
            registerPanel.setVisible(true);
        }else {
            registerPanel=new RegisterPanel(this);
            this.setContentPane(registerPanel);
            registerPanel.setVisible(true);
        }
    }
    /*
    * 跳转到登录界面
    * */
    public void gotoLoginPanel(){
        registerPanel.setVisible(false);
        if(loginPanel!=null){
            this.setContentPane(loginPanel);
            loginPanel.setVisible(true);
        }else {
            loginPanel=new LoginPanel(this);
            this.setContentPane(loginPanel);
            loginPanel.setVisible(true);
        }
    }
    /*
    * 处理登录命令
    * */
    public void doLoginEvent(){
        if(loginPanel.check()){
            if(communication==null){
                communication=Communication.getInstance();
            }
            User  user=loginPanel.getUser();
            Message message =communication.login(user);
            if(message!=null){
                if(message.getMessageHead().isSuccessFlag())
                {
                    logger.info(message.getTextMessage().getMessageContent());
                    this.dispose();
                    ChatUI chatUI=new ChatUI(message);
                    //chatUI.startProcess();
                }else {
                    logger.info(message.getTextMessage().getMessageContent());
                    JOptionPane.showMessageDialog(this,message.getTextMessage().getMessageContent(),"登录失败！",JOptionPane.ERROR_MESSAGE);
                }
            }else {
                JOptionPane.showMessageDialog(this,"与服务器连接断开,请尝试重新启动或联系管理员","登录失败！",JOptionPane.ERROR_MESSAGE);
            }


        }
    }
    /*
    * 处理注册命令
    * */
    public void doRegisterEvent(){
        if(registerPanel.check()){
            User user=registerPanel.getUser();
            if(communication==null){
                communication=Communication.getInstance();
            }
            Message message =communication.register(user);
            if(message!=null) {
                if (!message.getMessageHead().isSuccessFlag()) {
                    logger.info(message.getTextMessage().getMessageContent());
                    JOptionPane.showMessageDialog(this, message.getTextMessage().getMessageContent(), "注册失败", JOptionPane.ERROR_MESSAGE);
                } else {
                    logger.info(message.getTextMessage().getMessageContent());
                    JOptionPane.showMessageDialog(this, message.getTextMessage().getMessageContent(), "注册成功", JOptionPane.INFORMATION_MESSAGE);
                    gotoLoginPanel();
                }
            }else {
                JOptionPane.showMessageDialog(this,"与服务器连接断开,请尝试重新启动或联系管理员","注册失败！",JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}
