package io.corbs.todos;

import org.springframework.cloud.servicebroker.model.catalog.Catalog;
import org.springframework.cloud.servicebroker.model.catalog.Plan;
import org.springframework.cloud.servicebroker.model.catalog.ServiceDefinition;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TodoServiceCatalog {

    @Bean
    public Catalog catalog() {

        Plan sharedPlan = Plan.builder()
            .id("todos-shared")
            .name("todos-shared")
            .description("todos-shared provisions access to a shared 50 Todo limit per client service")
            .free(true)
            .build();

        Plan dedicatedPlan = Plan.builder()
            .id("todos-dedicated")
            .name("todos-dedicated")
            .description("todos-dedicated provisions a dedicated 100 Todo limit per client service")
            .free(true)
            .build();

        Plan enterprisePlan = Plan.builder()
            .id("todos-enterprise")
            .name("todos-enterprise")
            .description("todos-enterprise provisions a dedicated 1000 Todo limit per client w/ integrations")
            .free(true)
            .build();

        ServiceDefinition serviceDefinition = ServiceDefinition.builder()
                .id("todos-service")
                .name("todos-service")
                .description("Todo(s) as a Service anyone?")
                .bindable(true)
                .tags("todos", "service-broker", "pas", "spring-cloud-open-service-broker")
                .plans(sharedPlan, dedicatedPlan, enterprisePlan)
                .build();

        return Catalog.builder()
                .serviceDefinitions(serviceDefinition)
                .build();
    }
}
