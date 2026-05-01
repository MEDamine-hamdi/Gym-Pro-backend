package com.gympro.gymprobackend.repository;

import com.gympro.gymprobackend.entity.Offer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface OfferRepository extends JpaRepository<Offer,Long> {
    long countByActiveTrue();
}
