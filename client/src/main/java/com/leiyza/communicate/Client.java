package com.leiyza.communicate;
import org.apache.log4j.Logger;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.SocketAddress;

public class Client {
    private static final Logger logger=Logger.getLogger(Client.class);
    private static final int PORT=1228;
    private static final String HOST="127.0.0.1";
    private Socket socket=null;
    private ObjectInputStream ois;
    private ObjectOutputStream oos;
    private static final Client instance=new Client();
    public  static Client getInstance() {
        return instance;
    }
    public boolean connectServer(){
        if(socket==null){
            try {
                socket=new Socket(HOST,PORT);
                logger.info("连接服务器成功");
                return true;
            }catch (IOException e){
                logger.info("连接服务器失败");
                e.printStackTrace();
                return false;
            }
        }
        return false;
    }
    public boolean reConnectServer(){
        logger.info("远端连接已断开，尝试重连");
        //先关闭本地socket以及输入输出流
        closeSocket();
        socket=null;
        return connectServer();
    }
    public void closeSocket(){
        if(ois!=null){
            try {
                ois.close();
                ois=null;
                logger.info("关闭输入流");
            }catch (IOException e){
                logger.info("关闭输入流失败");
                e.printStackTrace();
            }
        }
        if(oos!=null){
            try {
                logger.info("关闭输出流");
                oos.close();
                oos=null;
            } catch (IOException e) {
                logger.info("关闭输出流失败");
                e.printStackTrace();
            }
        }
        if(socket!=null){
            try {
                logger.info("关闭socket");
                socket.close();
                socket=null;
            } catch (IOException e) {
                logger.info("关闭socket失败");
                e.printStackTrace();
            }
        }

    }
    public boolean checkConnecting(){
        try {
            oos=new ObjectOutputStream(socket.getOutputStream());
            return true;
        } catch (IOException e) {
            logger.info("与服务器连接断开");
            e.printStackTrace();
            return false;
        }
    }
    public boolean sendMessageToServer(Message message) {
        logger.info("向服务器发送数据");
        if(checkConnecting()){
            try {
                oos.writeObject(message);
                oos.flush();
                return true;
            }catch (Exception e){
                logger.info("向服务器发送数据失败");
            }
        } else {
            closeSocket();
            connectServer();
            sendMessageToServer(message);
        }
        return false;
    }
    public Message recvMessageFromServer(){
        logger.info("从服务器接收数据");
        Message message;
        try {
            ois=new ObjectInputStream(socket.getInputStream());
            message=(Message) ois.readObject();
            return message;
        }catch (IOException | ClassNotFoundException e){
            logger.info("接收失败");
            e.printStackTrace();
            return new Message();
        }
    }
}
