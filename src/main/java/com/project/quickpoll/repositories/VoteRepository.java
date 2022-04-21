package com.project.quickpoll.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.quickpoll.models.Vote;

public interface VoteRepository extends CrudRepository<Vote, Long>{

}
