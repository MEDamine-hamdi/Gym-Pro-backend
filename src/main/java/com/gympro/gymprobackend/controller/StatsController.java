package com.gympro.gymprobackend.controller;

import com.gympro.gymprobackend.repository.ClientRepository;
import com.gympro.gymprobackend.repository.EmployeeRepository;
import com.gympro.gymprobackend.repository.OfferRepository;
import com.gympro.gymprobackend.repository.SessionRepository;
import com.gympro.gymprobackend.service.StatsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/stats")
public class StatsController {

    @Autowired private ClientRepository clientRepository;
    @Autowired private EmployeeRepository employeeRepository;
    @Autowired private SessionRepository sessionRepository;
    @Autowired private OfferRepository offerRepository;
    @Autowired private StatsService statsService;



    @GetMapping("/overview")
    public ResponseEntity<Map<String, Object>> overview() {
        Map<String, Object> stats = new HashMap<>();
        stats.put("totalClients",  clientRepository.countByActiveTrue());
        stats.put("totalEmployees", employeeRepository.countByActiveTrue());
        stats.put("totalSessions",  sessionRepository.count());
        stats.put("activeOffers",   offerRepository.countByActiveTrue());
        return ResponseEntity.ok(stats);
    }


    @GetMapping("/revenue-monthly")
    public ResponseEntity<List<Object[]>> revenueMonthly() {
        return ResponseEntity.ok(statsService.monthlyRevenue());
    }


    @GetMapping("/top-sessions")
    public ResponseEntity<List<Object[]>> topSessions() {
        return ResponseEntity.ok(statsService.countAttendancePerClient());
    }


    @GetMapping("/client-age-groups")
    public ResponseEntity<List<Object[]>> ageGroups() {
        return ResponseEntity.ok(statsService.clientAgeGroups());
    }
}
