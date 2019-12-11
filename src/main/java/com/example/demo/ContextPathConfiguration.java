package com.example.demo;

import static org.springdoc.core.Constants.API_DOCS_URL;
import static org.springdoc.core.Constants.DEFAULT_VALIDATOR_URL;
import static org.springdoc.core.Constants.DEFAULT_WEB_JARS_PREFIX_URL;
import static org.springdoc.core.Constants.SWAGGER_UI_PATH;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.server.WebFilter;

/**
 * Spring MVC automatically configures via the property server.servlet.context-path but webflux
 * needs the below which is very annoying.
 */
@Configuration
public class ContextPathConfiguration {

  @Value(API_DOCS_URL)
  private String apiDocsUrl;
  @Value(SWAGGER_UI_PATH)
  private String uiPath;

  @Value("${server.servlet.context-path}")
  private String contextPath;

  @Bean
  RouterFunction<ServerResponse> routerFunction2() {
    StringBuilder sbUrl = new StringBuilder();
    sbUrl.append(contextPath);
    sbUrl.append(DEFAULT_WEB_JARS_PREFIX_URL);
    sbUrl.append(contextPath + apiDocsUrl);
    sbUrl.append(DEFAULT_VALIDATOR_URL);
    return route(GET(contextPath + uiPath), req -> ServerResponse.temporaryRedirect(URI.create(sbUrl.toString())).build());
  }


  @Bean
  @ConditionalOnProperty("server.servlet.context-path")
  @Order(Ordered.HIGHEST_PRECEDENCE)
  public WebFilter contextPathWebFilter(ServerProperties serverProperties) {
    String contextPath = serverProperties.getServlet().getContextPath();
    return (exchange, chain) -> {
      ServerHttpRequest request = exchange.getRequest();
      String requestPath = request.getURI().getPath();
      if (requestPath.startsWith(contextPath + "/") || requestPath.equals(contextPath)) {
        return chain.filter(exchange.mutate().request(request.mutate().contextPath(contextPath).build()).build());

      } else {
        throw new ResponseStatusException(HttpStatus.NOT_FOUND);
      }

    };
  }
}
