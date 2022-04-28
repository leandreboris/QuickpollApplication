package com.project.quickpoll.repositories;

import org.springframework.data.repository.CrudRepository;

import com.project.quickpoll.models.Options;

public interface OptionRepository extends CrudRepository<Options, Long> {

}
