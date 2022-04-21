package com.project.quickpoll.controllers;

import java.net.URI;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.quickpoll.models.Poll;
import com.project.quickpoll.repositories.PollRepository;

@RestController
public class PollController {

	@Inject
	public PollRepository pollRepository;

	@GetMapping("/polls")
	public ResponseEntity<Iterable<Poll>> getAllPolls() {
		return new ResponseEntity<>(pollRepository.findAll(), HttpStatus.OK);
	}

	@PostMapping("/polls")
	public ResponseEntity<?> createPoll(@RequestBody Poll poll) {
		poll = pollRepository.save(poll);

		HttpHeaders responseHeaders = new HttpHeaders();
		URI newPollURI = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(poll.getId())
				.toUri();
		responseHeaders.setLocation(newPollURI);

		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

}
