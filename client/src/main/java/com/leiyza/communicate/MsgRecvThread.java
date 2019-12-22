package com.leiyza.communicate;

import java.util.LinkedList;
import java.util.Queue;

public class MsgRecvThread extends Thread {
    private static final Client client=Client.getInstance();
    public MsgRecvThread(){
    }
    @Override
    public void run() {
        while (true){
            Message message=client.recvMessageFromServer();
            MessageQueue.messageQueue.add(message);
        }
    }
}
