package ru.otus.library.route;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import ru.otus.library.handler.BookHandler;

import static org.springframework.http.MediaType.APPLICATION_JSON;
import static org.springframework.web.reactive.function.server.RequestPredicates.accept;
import static org.springframework.web.reactive.function.server.RequestPredicates.contentType;

@RequiredArgsConstructor
@EnableWebFlux
@Configuration
public class WebRouter {

    private final BookHandler bookHandler;

    @Bean
    public RouterFunction<ServerResponse> routerFunction (){
        return RouterFunctions.route()
                .GET("/api/book", bookHandler::findAll)
                .POST("/api/book", contentType(APPLICATION_JSON), bookHandler::create)
                .GET("/api/book/{id}", bookHandler::findById)
                .PUT("/api/book/{id}", contentType(APPLICATION_JSON), bookHandler::update)
                .DELETE("/api/book/{id}", bookHandler::deleteById)
                .build();
    }
}
