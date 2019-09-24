package com.example.demo;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.media.Schema;
import io.swagger.v3.oas.models.parameters.HeaderParameter;
import org.springdoc.api.OpenApiCustomiser;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class SpringdocWebfluxContextApplication {

  public static void main(String[] args) {
    SpringApplication.run(SpringdocWebfluxContextApplication.class, args);
  }

  @Bean
  public OpenAPI customOpenAPI() {
    Schema<String> schema = new Schema<>();
    schema.addEnumItemObject("consumer1");
    schema.addEnumItemObject("swagger");
    schema.addEnumItemObject("consumer3");
    schema.setDefault("swagger");
    return new OpenAPI()
        .components(new Components().addParameters("myConsumerTypeHeader", new HeaderParameter().required(true).name("My-Consumer-Type").description("The II Consumer Type").schema(schema)));
  }

  @Bean
  public OpenApiCustomiser consumerTypeHeaderOpenAPICustomiser() {
    return openApi -> openApi.getPaths().values().stream().flatMap(pathItem -> pathItem.readOperations().stream())
        .forEach(operation -> operation.addParametersItem(new HeaderParameter().$ref("#/components/parameters/myConsumerTypeHeader")));
  }

}
