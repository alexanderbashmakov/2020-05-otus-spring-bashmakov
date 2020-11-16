package ru.otus.spring.integration.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.channel.DirectChannel;
import org.springframework.integration.channel.PublishSubscribeChannel;
import org.springframework.integration.dsl.IntegrationFlow;
import org.springframework.integration.dsl.IntegrationFlows;
import org.springframework.integration.dsl.MessageChannels;
import ru.otus.spring.integration.domain.OtusStudent;

@Configuration
public class IntegrationConfig {
    @Bean
    public DirectChannel startCourseChannel() {
        return MessageChannels.direct().get();
    }

    @Bean
    public PublishSubscribeChannel graduateChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel noPassedTasksChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel noPassedProjectChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel projectChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public PublishSubscribeChannel qualifyingChannel() {
        return MessageChannels.publishSubscribe().get();
    }

    @Bean
    public IntegrationFlow taskFlow() {
        return IntegrationFlows.from("startCourseChannel")
                .handle("studyService", "checkTasks")
                .<OtusStudent, Boolean>route(OtusStudent::getIsAllTasksChecked, mapping -> mapping
                            .subFlowMapping(false, sf -> sf.channel("noPassedTasksChannel"))
                            .subFlowMapping(true, sf -> sf.channel("projectChannel")))
                .get();
    }

    @Bean IntegrationFlow projectFlow() {
        return IntegrationFlows.from("projectChannel")
                .handle("studyService", "checkProjectWork")
                .<OtusStudent, Boolean>route(OtusStudent::getIsProjectDone, mapping -> mapping
                            .subFlowMapping(false, sf -> sf.channel("noPassedProjectChannel"))
                            .subFlowMapping(true, sf -> sf.channel("qualifyingChannel")))
                .get();
    }

    @Bean
    public IntegrationFlow noPassedTasksFlow() {
        return IntegrationFlows.from("noPassedTasksChannel")
                .handle("graduateService", "negativeGraduate")
                .channel("graduateChannel")
                .get();
    }

    @Bean
    public IntegrationFlow noPassedProjectFlow() {
        return IntegrationFlows.from("noPassedProjectChannel")
                .handle("graduateService", "negativeGraduate")
                .channel("graduateChannel")
                .get();
    }

    @Bean
    public IntegrationFlow qualifyingFlow() {
        return IntegrationFlows.from("qualifyingChannel")
                .handle("graduateService", "positiveGraduate")
                .channel("graduateChannel")
                .get();
    }

}
