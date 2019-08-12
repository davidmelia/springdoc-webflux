package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SpringdocWebfluxContextApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringdocWebfluxContextApplication.class, args);
  }

  // @Bean
  // public WebFluxConfigurer contextPathConfigurer() {
  // return new WebFluxConfigurer() {
  // @Override
  // public void configurePathMatching(PathMatchConfigurer configurer) {
  //
  // configurer.addPathPrefix("/dave", HandlerTypePredicate.forAnyHandlerType());
  // }
  // };
  //
  // }

}
