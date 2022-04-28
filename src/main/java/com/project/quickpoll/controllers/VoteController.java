package com.project.quickpoll.controllers;

import javax.inject.Inject;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.project.quickpoll.models.Vote;
import com.project.quickpoll.repositories.VoteRepository;

@RestController
public class VoteController {

	@Inject
	private VoteRepository voteRepository;

	@PostMapping("/polls/{id}/vote")
	public ResponseEntity<?> createVote(@RequestBody Vote vote, @PathVariable Long id) {
		vote = voteRepository.save(vote);
		HttpHeaders responseHeaders = new HttpHeaders();
		responseHeaders.setLocation(
				ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(vote.getId()).toUri());
		return new ResponseEntity<>(null, responseHeaders, HttpStatus.CREATED);
	}

	@GetMapping("/polls/{pollId}/votes")
	public Iterable<Vote> getAllVotes(@PathVariable Long pollId) {
		return voteRepository.findVoteByPoll(pollId);
	}

}
