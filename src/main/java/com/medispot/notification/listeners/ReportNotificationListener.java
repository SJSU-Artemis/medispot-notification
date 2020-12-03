package com.medispot.notification.listeners;

import org.springframework.cloud.aws.messaging.listener.annotation.SqsListener;
import org.springframework.stereotype.Service;

@Service
public class ReportNotificationListener {
    static final String QUEUE_NAME = "medispotconnector-reports-queue";

    @SqsListener(QUEUE_NAME)
    public void receive(String message){
        System.out.println(message);
    }
}
