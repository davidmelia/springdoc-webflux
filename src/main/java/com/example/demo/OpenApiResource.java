package com.example.demo;

import io.swagger.v3.oas.models.OpenAPI;
import java.util.List;
import java.util.Optional;
import org.springdoc.core.AbstractRequestBuilder;
import org.springdoc.core.AbstractResponseBuilder;
import org.springdoc.core.GeneralInfoBuilder;
import org.springdoc.core.OpenAPIBuilder;
import org.springdoc.core.OperationBuilder;
import org.springdoc.core.RequestBodyBuilder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.reactive.result.method.RequestMappingInfoHandlerMapping;

@RestController
public class OpenApiResource extends org.springdoc.api.OpenApiResource {

  private final Optional<List<OpenAPICustomiser>> openApiCustomisers;

  private OpenAPI openApi;

  public OpenApiResource(OpenAPIBuilder openAPIBuilder, AbstractRequestBuilder requestBuilder, AbstractResponseBuilder responseBuilder, OperationBuilder operationParser,
      GeneralInfoBuilder infoBuilder, RequestBodyBuilder requestBodyBuilder, RequestMappingInfoHandlerMapping requestMappingHandlerMapping, Optional<List<OpenAPICustomiser>> openApiCustomisers) {
    super(openAPIBuilder, requestBuilder, responseBuilder, operationParser, infoBuilder, requestBodyBuilder, requestMappingHandlerMapping);
    this.openApiCustomisers = openApiCustomisers;
  }

  @Override
  protected OpenAPI getOpenApi() {
    if (openApi == null) {
      openApi = super.getOpenApi();
      if (openApiCustomisers.isPresent()) {
        openApiCustomisers.get().stream().forEach(openApiCustomiser -> openApiCustomiser.customise(openApi));
      }
    }

    return openApi;
  }

}
