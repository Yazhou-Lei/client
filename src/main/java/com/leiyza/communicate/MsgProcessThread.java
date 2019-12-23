package com.leiyza.communicate;

public class MsgProcessThread extends Thread{
    @Override
    public void run() {
        while (true){
            if(MessageQueue.messageQueue.isEmpty()){
                continue;
            }
            Message message=MessageQueue.messageQueue.poll();
        }
    }
}
