package com.leiyza.communicate;

import com.leiyza.ui.LoginFrame;
import org.apache.log4j.Logger;

import java.util.LinkedList;
import java.util.Queue;

public class MsgRecvThread extends Thread {
    private static final Logger logger=Logger.getLogger(MsgRecvThread.class);
    private static final Client client=Client.getInstance();
    private boolean stop=true;
    private boolean isStopped=false;
    public MsgRecvThread(){
    }
    @Override
    public void run() {
        while (stop){
            Message message=client.recvMessageFromServer();
            if(message!=null){
                logger.info("收到一条数据,放进消息队列");
                MessageQueue.messageQueue.add(message);
            }
        }
        isStopped=true;
    }
    public boolean isStopped(){
        return isStopped;
    }
    public void Stop(){
        stop=false;
    }
}
