package com.gympro.gymprobackend.service;

import com.gympro.gymprobackend.repository.BookingRepository;
import com.gympro.gymprobackend.repository.ClientRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class StatsService {
    @Autowired

    private BookingRepository bookingRepository;
    private ClientRepository clientRepository;

    public List<Object[]> peakBookingHours() {
        return bookingRepository.peakBookingHours();
    }

    public List<Object[]> countBookingsBySessionType(){
        return bookingRepository.countBookingsBySessionType();
    }

    public List<Object[]> countAttendancePerClient(){
        return bookingRepository.countAttendancePerClient();
    }
    public List<Object[]> clientAgeGroups(){
        return clientRepository.clientAgeGroups();
    }
    public List<Object[]> monthlyRevenue(){
        return clientRepository.monthlyRevenue();
    }


}
