package com.leiyza.communicate;

import com.leiyza.model.User;
import org.apache.log4j.Logger;

public class Communication {
    private static final Logger logger=Logger.getLogger(Communication.class);
    private Client client;
    private static final Communication communication=new Communication();

    private Communication(){
    }
    public static Communication getInstance(){
        return communication;
    }
    public Message login(User user){//登录
        logger.info("登录");
        if(client==null){
            client=Client.getInstance();
        }else {
            logger.info("清空上次登录失败未关闭的socket");
            client.closeSocket();
        }
        if(client.connectServer()){
            Message message=new Message();
            Message.MessageHead msgHead=new Message.MessageHead();
            msgHead.setCommandType(Commands.LOGIN);
            msgHead.setFrom(user);
            message.setMessageHead(msgHead);
            client.sendMessageToServer(message);
            return  client.recvMessageFromServer();
        }else {
            Message messageRes=new Message();
            Message.MessageHead messageHead=new Message.MessageHead();
            messageHead.setSuccessFlag(false);
            Message.TextMessage textMessage=new Message.TextMessage();
            textMessage.setMessageContent("与服务器连接失败,尝试退出重连，或联系管理员！");
            messageRes.setMessageHead(messageHead);
            messageRes.setTextMessage(textMessage);
            return messageRes;
        }
    }
    public Message register(User user){//注册
        logger.info("注册");
        if(client==null){
            client=Client.getInstance();
        }else {
            logger.info("清空上次注册失败未关闭的socket");
            client.closeSocket();
        }
        if(client.connectServer()){
            Message message=new Message();
            Message.MessageHead msgHead=new Message.MessageHead();
            msgHead.setCommandType(Commands.REGISTER);
            msgHead.setFrom(user);
            message.setMessageHead(msgHead);
            client.sendMessageToServer(message);
            return client.recvMessageFromServer();
        }else {
            Message messageRes=new Message();
            Message.MessageHead messageHead=new Message.MessageHead();
            messageHead.setSuccessFlag(false);
            Message.TextMessage textMessage=new Message.TextMessage();
            textMessage.setMessageContent("与服务器连接失败,尝试退出重连，或联系管理员！");
            messageRes.setMessageHead(messageHead);
            messageRes.setTextMessage(textMessage);
            return messageRes;
        }

    }
    public void sendMsg(Message message){
        if(client.sendMessageToServer(message)){
            logger.info("发送成功");
        }else {
            logger.info("发送失败");
        }
    }

}
