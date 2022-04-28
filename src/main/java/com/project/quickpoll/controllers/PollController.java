package com.project.quickpoll.controllers;

import java.net.URI;
import java.util.Optional;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
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
		Iterable<Poll> allPolls = pollRepository.findAll();
		return new ResponseEntity<>(allPolls, HttpStatus.OK);
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

	@GetMapping("/polls/{id}")
	public ResponseEntity<?> getPollById(@PathVariable Long id) throws Exception {
		Optional<Poll> poll = pollRepository.findById(id);
		if (!poll.isPresent()) {
			throw new Exception("Resource not found");
		}
		return new ResponseEntity<>(poll.get(), HttpStatus.OK);

	}

	@PutMapping("/polls/{id}")
	public ResponseEntity<?> updatePoll(@RequestBody Poll poll, @PathVariable Long id) throws Exception {
		Optional<Poll> oldPoll = pollRepository.findById(id);
		if(!oldPoll.isPresent()) {
			throw new Exception("Resource not found");
		}
		oldPoll.get().setQuestion(poll.getQuestion());
		oldPoll.get().setOptions(poll.getOptions());
		pollRepository.save(oldPoll.get());
		
		return new ResponseEntity<>(HttpStatus.OK);
	}

	@DeleteMapping("/polls/{id}")
	public ResponseEntity<?> deletePoll(@RequestBody Poll poll, @PathVariable Long id) {
		pollRepository.deleteById(id);
		return new ResponseEntity<>(HttpStatus.OK);
	}

}
