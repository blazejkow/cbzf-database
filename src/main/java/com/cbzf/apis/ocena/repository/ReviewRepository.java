package com.cbzf.apis.ocena.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Review repository
 */
@Repository
public interface ReviewRepository extends JpaRepository<ReviewEntity, ReviewPrimaryKey> {

    List<ReviewEntity> findByIdProdukt(Integer id);
}
