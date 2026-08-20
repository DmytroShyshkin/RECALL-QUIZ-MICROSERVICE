package com.dmytro.quiz_service.infrastructure.MongoDegub;


import org.springframework.context.ApplicationContext;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import lombok.AllArgsConstructor;

import org.springframework.boot.context.event.ApplicationReadyEvent;

import java.util.Arrays;

@Component
@AllArgsConstructor
public class MongoBeanLogger {
    
    private final ApplicationContext ctx;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        String[] clients = ctx.getBeanNamesForType(com.mongodb.client.MongoClient.class);
        String[] templates = ctx.getBeanNamesForType(org.springframework.data.mongodb.core.MongoTemplate.class);
        System.out.println("MongoClient beans: " + Arrays.toString(clients));
        System.out.println("MongoTemplate beans: " + Arrays.toString(templates));
    }
}