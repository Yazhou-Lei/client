package com.leiyza;

import com.leiyza.ui.LoginFrame;

import javax.swing.*;
import java.awt.*;


public class Test extends JFrame{
        public Test(){
            setBounds(100,100,700,650);
            setDefaultCloseOperation(EXIT_ON_CLOSE);
            Container c=getContentPane();
            JLabel l=new JLabel("这是一个展示图片标签");
            ImageIcon imageIcon=new ImageIcon("resources/Images/head.jpg");//第二种方法获取相应路径下的图片文件
            Icon icon=new ImageIcon(imageIcon.getImage().getScaledInstance(32,32,Image.SCALE_DEFAULT));
            l.setIcon(icon);//添加图片
//        l.setSize(20,20);//设定标签大小，即使设定标签大小，也不会改变图片大小
            c.add(l);
            setVisible(true);
        }

        public static void main(String[] args) {
            // write your code here
            new LoginFrame();
        }
}



