package com.leiyza.ui;

import com.leiyza.model.User;

import javax.swing.*;
import java.awt.*;

public class RegisterPanel extends JPanel {
    JLabel titleLabel=new JLabel("Cause you are my everything".toUpperCase());
    JLabel userAcctLabel=new JLabel("UserAcct:");
    JLabel userNameLabel=new JLabel("UserName:");
    JLabel passwordLabel=new JLabel("PassWord:");
    JTextField userNameField=new JTextField(10);
    JTextField userAcctField=new JTextField(10);
    JPasswordField passwordField=new JPasswordField(10);
    public JButton loginButton=new JButton("login");
    public JButton registerButton=new JButton("register");
    JLabel confirmPasswordLabel=new JLabel(" Confirm:");
    JPasswordField confirmPasswordField=new JPasswordField(10);
    Font font=new Font("仿宋",0,18);
    LoginFrame root;
    private User user;
    RegisterPanel(LoginFrame frame){
        root=frame;
        this.setLayout(null);
        titleLabel.setFont(new Font("title",1,22));
        titleLabel.setBounds(200,50,400,30);
        this.add(titleLabel);

        userAcctLabel.setFont(font);
        userAcctLabel.setBounds(250,160,150,20);
        this.add(userAcctLabel);
        userAcctField.setBounds(340,160,150,22);
        this.add(userAcctField);

        userNameLabel.setFont(font);
        userNameLabel.setBounds(250,190,150,20);
        this.add(userNameLabel);
        userNameField.setBounds(340,190,150,22);
        this.add(userNameField);

        passwordLabel.setFont(font);
        passwordLabel.setBounds(250,220,150,20);
        this.add(passwordLabel);
        passwordField.setBounds(340,220,150,22);
        this.add(passwordField);

        confirmPasswordLabel.setFont(font);
        confirmPasswordLabel.setBounds(250,250,150,20);
        this.add(confirmPasswordLabel);
        confirmPasswordField.setBounds(340,250,150,20);
        this.add(confirmPasswordField);

        loginButton.setFont(font);
        loginButton.setBounds(230,330,130,20);

        registerButton.setFont(font);
        registerButton.setBounds(400,330,130,20);

        loginButton.addActionListener(root);
        registerButton.addActionListener(root);

        this.add(loginButton);
        this.add(registerButton);
        this.setBackground(Color.CYAN);
    }

    public boolean check(){
        if(userNameField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"用户名不能为空！","error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(userAcctField.getText().isEmpty()){
            JOptionPane.showMessageDialog(this,"账号不能为空！","error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(passwordField.getPassword().length==0|| passwordField == null){
            JOptionPane.showMessageDialog(this,"请输入密码！","error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        if(!String.copyValueOf(passwordField.getPassword()).equals(String.copyValueOf(confirmPasswordField.getPassword()))){
            JOptionPane.showMessageDialog(this,"请确认密码是否一致！","error",JOptionPane.ERROR_MESSAGE);
            return false;
        }
        return true;
    }
    public User getUser(){
        user=new User();
        user.setUserNo(userAcctField.getText());
        user.setUserName(userNameField.getText());
        user.setPassword(getPassword());
        System.out.println(user.getUserNo()+" "+user.getUserName()+" "+user.getPassword());
        return user;
    }
    public String getPassword(){
        return String.copyValueOf(passwordField.getPassword());
    }
}
