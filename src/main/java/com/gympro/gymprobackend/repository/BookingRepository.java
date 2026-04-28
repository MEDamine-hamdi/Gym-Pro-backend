package com.gympro.gymprobackend.repository;

import com.gympro.gymprobackend.entity.Booking;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    long countBySessionId(Long sessionId);

    // How many sessions did each client attend? (for retention: attended > 1)

    @Query("SELECT b.client.id, COUNT(b) FROM Booking b WHERE b.attended = true GROUP BY b.client.id")
    List<Object[]> countAttendancePerClient();

    // Most popular session types by total bookings
    @Query("SELECT s.sessionType, COUNT(b) as cnt FROM Booking b JOIN b.session s GROUP BY s.sessionType ORDER BY cnt DESC")
    List<Object[]> countBookingsBySessionType();

    // Peak booking hours
    @Query(value = "SELECT HOUR(s.start_time) as hr, COUNT(*) as cnt FROM bookings b JOIN sessions s ON b.session_id=s.id GROUP BY hr ORDER BY cnt DESC", nativeQuery = true)
    List<Object[]> peakBookingHours();
}


