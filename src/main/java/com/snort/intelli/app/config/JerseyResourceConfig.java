package com.snort.intelli.app.config;

import com.snort.intelli.app.resources.TodosJSONResource;
import com.snort.intelli.app.resources.TodosXMLResource;
import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

@Configuration
public class JerseyResourceConfig extends ResourceConfig {

    public JerseyResourceConfig(){
        //after creating bean of this class Resource /Controller bean Registered
        register(TodosXMLResource.class);
        register(TodosJSONResource.class);
    }

}
