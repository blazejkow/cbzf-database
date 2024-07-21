package com.cbzf.apis.wartoscodzywcza.repository;

import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Nutrition repository
 */
@Repository
public interface NutritionRepository extends JpaRepository<NutritionEntity, NutritionPrimaryKey> {
    List<NutritionEntity> findByNutritionPrimaryKeyIdProdukt(Integer id, Sort sort);

    @Query("SELECT DISTINCT n.nutritionPrimaryKey.idProdukt FROM NutritionEntity n WHERE LOWER(n.nazwa) LIKE LOWER(CONCAT('%', :nazwa, '%')) AND n.zawartosc IS NOT NULL AND n.zawartosc > 0")
    List<Integer> findDistinctIdProduktByNazwaContaining(String nazwa);

    @Query(value = "select nazwa_grupy, nazwa, zawartosc, jednostka, procent_rws, zawartosc_porcja, procent_rws_porcja, indeks, legenda from wartosc_odzywcza WHERE id_produkt = :idProdukt", nativeQuery = true)
    List<Object[]> getNutritionReport(Integer idProdukt);
}
