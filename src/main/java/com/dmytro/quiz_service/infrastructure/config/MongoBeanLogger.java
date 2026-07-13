package com.dmytro.quiz_service.infrastructure.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.Arrays;
@Component
public class MongoBeanLogger {
    @Autowired
    private ApplicationContext ctx;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        String[] clients = ctx.getBeanNamesForType(com.mongodb.client.MongoClient.class);
        String[] templates = ctx.getBeanNamesForType(org.springframework.data.mongodb.core.MongoTemplate.class);
        System.out.println("MongoClient beans: " + Arrays.toString(clients));
        System.out.println("MongoTemplate beans: " + Arrays.toString(templates));
    }
}