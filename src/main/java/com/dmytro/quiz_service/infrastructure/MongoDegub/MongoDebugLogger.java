package com.dmytro.quiz_service.infrastructure.MongoDegub;

import com.mongodb.ConnectionString;

import lombok.AllArgsConstructor;

import org.springframework.context.ApplicationContext;
import org.springframework.core.env.Environment;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@AllArgsConstructor
public class MongoDebugLogger {

    private final ApplicationContext ctx;

    private final Environment env;

    @EventListener(ApplicationReadyEvent.class)
    public void onReady() {
        Object mongoBean = ctx.getBean("mongo");
        Object templateBean = ctx.getBean("mongoTemplate");
        System.out.println("Mongo bean class: " + (mongoBean == null ? "null" : mongoBean.getClass().getName()));
        System.out.println("MongoTemplate bean class: " + (templateBean == null ? "null" : templateBean.getClass().getName()));

        String uri = env.getProperty("spring.data.mongodb.uri");
        System.out.println("spring.data.mongodb.uri (raw): " + (uri == null ? "null" : uri.replaceAll(":(.*)@", ":*****@")));
        try {
            ConnectionString cs = new ConnectionString(uri);
            System.out.println("ConnectionString hosts: " + cs.getHosts());
            System.out.println("ConnectionString isSrv: " + cs.isSrvProtocol());
            System.out.println("ConnectionString database: " + cs.getDatabase());
        } catch (Exception e) {
            System.out.println("Failed to parse ConnectionString: " + e.getMessage());
        }

        // Optional: try to reflectively get settings from the mongo bean (best-effort)
        try {
            java.lang.reflect.Method m = mongoBean.getClass().getMethod("getSettings");
            Object settings = m.invoke(mongoBean);
            System.out.println("MongoClient.getSettings() class: " + (settings == null ? "null" : settings.getClass().getName()));
            System.out.println("MongoClient settings toString: " + (settings == null ? "null" : settings.toString()));
        } catch (NoSuchMethodException nsme) {
            System.out.println("MongoClient.getSettings() not available on bean class.");
        } catch (Exception ex) {
            System.out.println("Error reading MongoClient settings reflectively: " + ex.getMessage());
        }

        // Env checks
        System.out.println("MONGODB_URI env present: " + (System.getenv("MONGODB_URI") != null));
        System.out.println("SPRING_DATA_MONGODB_URI env present: " + (System.getenv("SPRING_DATA_MONGODB_URI") != null));
        System.out.println("SPRING_DATA_MONGODB_HOST env present: " + (System.getenv("SPRING_DATA_MONGODB_HOST") != null));
    }
}