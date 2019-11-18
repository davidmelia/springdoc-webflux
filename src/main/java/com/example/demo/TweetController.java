package com.example.demo;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.model.Tweet;
import com.example.demo.model.TweetId;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Created by rajeevkumarsingh on 08/09/17.
 */
@RestController
public class TweetController {
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "get All Tweets") })
	@GetMapping("/tweets")
	public Flux<Tweet> getAllTweets() {
		return Flux.just(new Tweet("hello"));
	}

	@GetMapping("/tweets/{id}")
	public Mono<Tweet> getTweet1(@PathVariable TweetId id) {
		return Mono.just(new Tweet("hello" + id.getValue()));
	}

	@PostMapping(value = "/tweets/works", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Tweet> postWorks(@RequestBody Tweet tweet) {
		return Mono.just(tweet);
	}

	@PostMapping(value = "/tweets/does-not-work", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Tweet> postNotWorks(@RequestBody Mono<Tweet> tweet) {
		return tweet;
	}

	@PostMapping(value = "/tweets/work-around", consumes = MediaType.APPLICATION_JSON_VALUE)
	public Mono<Tweet> postNotWorkAround(
			@RequestBody @io.swagger.v3.oas.annotations.parameters.RequestBody(content = @Content(schema = @Schema(implementation = Tweet.class))) Mono<Tweet> tweet) {
		return tweet;
	}
}
