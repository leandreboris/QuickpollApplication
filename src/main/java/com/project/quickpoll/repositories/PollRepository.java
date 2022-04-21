package com.project.quickpoll.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.quickpoll.models.Poll;

public interface PollRepository extends CrudRepository<Poll, Long>{

}
