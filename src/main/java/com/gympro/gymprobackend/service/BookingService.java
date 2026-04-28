package com.gympro.gymprobackend.service;

import com.gympro.gymprobackend.entity.Booking;
import com.gympro.gymprobackend.entity.Client;
import com.gympro.gymprobackend.entity.Session;
import com.gympro.gymprobackend.repository.BookingRepository;
import com.gympro.gymprobackend.repository.ClientRepository;
import com.gympro.gymprobackend.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private SessionRepository sessionRepository;

    public Booking createBooking(Long clientId, Long sessionId) {

        Session session = sessionRepository.findById(sessionId)
                .orElseThrow(() -> new RuntimeException("Session not found"));

        Client client = clientRepository.findById(clientId)
                .orElseThrow(() -> new RuntimeException("Client not found"));

        long currentBookings = bookingRepository.countBySessionId(sessionId);

        if (currentBookings >= session.getMaxCapacity()) {
            throw new RuntimeException("Session is full. Capacity: " + session.getMaxCapacity());
        }

        Booking booking = new Booking();
        booking.setClient(client);
        booking.setSession(session);
        booking.setBookedAt(LocalDateTime.now());

        try {
            return bookingRepository.save(booking);
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Client is already booked in this session");
        }
    }
}