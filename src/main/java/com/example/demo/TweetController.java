package com.example.demo;

import com.example.demo.model.Tweet;
import com.example.demo.model.TweetId;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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


  @GetMapping("/tweets/{id}")
  public Mono<Tweet> getTweet1(@PathVariable TweetId id) {
    return Mono.just(new Tweet("hello" + id.getValue()));
  }


}
