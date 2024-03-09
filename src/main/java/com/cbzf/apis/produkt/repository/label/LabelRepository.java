package com.cbzf.apis.produkt.repository.label;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Label repository
 */
@Repository
public interface LabelRepository extends JpaRepository<LabelEntity, Integer> {
    List<LabelEntity> findByIdProdukt(Integer id);
}
