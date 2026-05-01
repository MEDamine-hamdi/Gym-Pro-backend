package com.gympro.gymprobackend.repository;
import com.gympro.gymprobackend.entity.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ClientRepository extends JpaRepository<Client, Long>{
    // ClientRepository.java — age group query
    @Query(value = "SELECT CASE WHEN age BETWEEN 18 AND 25 THEN '18-25' WHEN age BETWEEN 26 AND 35 THEN '26-35' WHEN age BETWEEN 36 AND 45 THEN '36-45' ELSE '46+' END as age_group, COUNT(*) FROM (SELECT TIMESTAMPDIFF(YEAR, date_of_birth, CURDATE()) as age FROM clients WHERE active=1) t GROUP BY age_group", nativeQuery = true)
    List<Object[]> clientAgeGroups();

    // ClientOfferRepository.java — monthly revenue
    @Query(value = "SELECT DATE_FORMAT(co.start_date, '%Y-%m') as month, SUM(o.price) as revenue FROM client_offers co JOIN offers o ON co.offer_id=o.id WHERE co.paid=1 GROUP BY month ORDER BY month", nativeQuery = true)
    List<Object[]> monthlyRevenue();

    long countByActiveTrue();
}
