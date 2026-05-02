package com.gympro.gymprobackend.controller;

import com.gympro.gymprobackend.entity.Booking;
import com.gympro.gymprobackend.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/bookings")
public class  BookingController {

    @Autowired
    private BookingService bookingService;


    // POST /api/bookings/{clientId}/{sessionId} - Create new booking
    @PostMapping("/{clientId}/{sessionId}")
    public ResponseEntity<Booking> createBooking(@PathVariable Long clientId, @PathVariable Long sessionId) {
        Booking createdBooking = bookingService.createBooking(clientId, sessionId);
        return ResponseEntity.ok(createdBooking);
    }

}