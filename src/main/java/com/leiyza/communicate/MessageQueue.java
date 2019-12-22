package com.leiyza.communicate;

import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public class MessageQueue {
    public static final Queue<Message> messageQueue= new ConcurrentLinkedQueue<>();
}
