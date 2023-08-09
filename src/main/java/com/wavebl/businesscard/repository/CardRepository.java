package com.wavebl.businesscard.repository;

import com.wavebl.businesscard.model.Card;
import com.wavebl.businesscard.model.CardState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CardRepository extends JpaRepository<Card, Long> {
    @Query("SELECT card FROM Card card WHERE card.state = :stateValue")
    List<Card> findByState(@Param("stateValue") CardState state);
}
