package com.url.shortner.url.shortner.service.persistence.repositories;

import com.url.shortner.url.shortner.service.persistence.entities.URLEntity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author Archetect
 */
@Repository
public interface UrlRepository extends JpaRepository<URLEntity,Long> {

}