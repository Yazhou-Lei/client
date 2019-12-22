package com.leiyza.ui;

import com.leiyza.model.User;

import javax.swing.*;
import java.awt.*;

public class LoginPanel extends JPanel {
    JLabel titleLabel=new JLabel("Cause you are my everything".toUpperCase());
    JLabel userNameLabel=new JLabel("UserAcct:");
    JLabel passwordLabel=new JLabel("PassWord:");
    JTextField userNameField=new JTextField(10);
    JPasswordField passwordField=new JPasswordField(10);
    public JButton loginButton=new JButton("login");
    public JButton registerButton=new JButton("register");
    Font font=new Font("仿宋",0,18);
    User user;
    String password;
    LoginFrame root;
    LoginPanel(LoginFrame jFrame){
        this.root=jFrame;
        loginButton.addActionListener(root);
        registerButton.addActionListener(root);
        this.setLayout(null);
        titleLabel.setFont(new Font("title",1,22));
        titleLabel.setBounds(200,50,400,30);
        this.add(titleLabel);
        userNameLabel.setFont(font);
        userNameLabel.setBounds(250,160,150,20);
        this.add(userNameLabel);
        userNameField.setBounds(340,160,150,22);
        this.add(userNameField);
        passwordLabel.setFont(font);
        passwordLabel.setBounds(250,190,150,20);
        this.add(passwordLabel);
        passwordField.setBounds(340,190,150,22);
        this.add(passwordField);
        loginButton.setFont(font);
        loginButton.setBounds(230,300,130,20);
        registerButton.setFont(font);
        registerButton.setBounds(400,300,130,20);
        this.add(loginButton);
        this.add(registerButton);
        this.setBackground(Color.CYAN);
    }
    public boolean check(){
        if(userNameField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"用户名不能为空！","error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(passwordField.getPassword().length==0|| passwordField == null){
            JOptionPane.showMessageDialog(this,"请输入密码！","error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    public User getUser(){
        user=new User();
        user.setUserNo(userNameField.getText());
        user.setPassword(getPassword());
        return user;
    }
    public String getPassword(){
        return String.copyValueOf(passwordField.getPassword());
    }
}
