package com.example.demo;

import com.example.demo.model.Tweet;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

/**
 * Created by rajeevkumarsingh on 08/09/17.
 */
@RestController
public class TweetController {
  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get All Tweets")})
  @GetMapping("/tweets")

  public Flux<Tweet> getAllTweets() {
    return Flux.just(new Tweet("hello"));
  }

  @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "get All Tweets2")})
  @GetMapping("/tweets2")
  @Operation(parameters = @Parameter(name = "dave", required = true, in = ParameterIn.HEADER, schema = @Schema(allowableValues = {"dave1", "dave2"}, defaultValue = "dave2")))
  public Flux<Tweet> getAllTweets2() {
    return Flux.just(new Tweet("hello"));
  }


}
