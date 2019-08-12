package com.example.demo;

import static org.springdoc.core.Constants.API_DOCS_URL;
import static org.springdoc.core.Constants.DEFAULT_VALIDATOR_URL;
import static org.springdoc.core.Constants.SWAGGER_UI_PATH;
import static org.springdoc.core.Constants.WEB_JARS_URL;
import static org.springframework.web.reactive.function.server.RequestPredicates.GET;
import static org.springframework.web.reactive.function.server.RouterFunctions.route;

import java.net.URI;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.HandlerTypePredicate;
import org.springframework.web.reactive.config.PathMatchConfigurer;
import org.springframework.web.reactive.config.ResourceHandlerRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.ServerResponse;

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
    sbUrl.append(WEB_JARS_URL);
    sbUrl.append(contextPath + apiDocsUrl);
    sbUrl.append(DEFAULT_VALIDATOR_URL);
    return route(GET(contextPath + uiPath), req -> ServerResponse.temporaryRedirect(URI.create(sbUrl.toString())).build());
  }

  @Bean
  public WebFluxConfigurer webJarsConfigurer() {
    return new WebFluxConfigurer() {
      @Override
      public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(contextPath + "/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/").resourceChain(false);
      }

      @Override
      public void configurePathMatching(PathMatchConfigurer configurer) {

        configurer.addPathPrefix(contextPath, HandlerTypePredicate.forAnyHandlerType());
      }
    };
  }
}
