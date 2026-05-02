package com.gympro.gymprobackend.service;

import com.gympro.gymprobackend.entity.Session;
import com.gympro.gymprobackend.repository.SessionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public List<Session> getAllSessions() {
        return sessionRepository.findAll();
    }

    public Session getSessionById(Long id) {
        return sessionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Session not found with id: " + id));
    }

    public Session createSession(Session session) {
        return sessionRepository.save(session);
    }

    public Session updateSession(Long id, Session session) {
        Session existing = getSessionById(id);

        existing.setName(session.getName());
        existing.setSessionType(session.getSessionType());
        existing.setCoach(session.getCoach());
        existing.setRoom(session.getRoom());
        existing.setStartTime(session.getStartTime());
        existing.setDurationMin(session.getDurationMin());
        existing.setMaxCapacity(session.getMaxCapacity());
        existing.setRecurring(session.isRecurring());

        return sessionRepository.save(existing);
    }

    public void deleteSession(Long id) {
        sessionRepository.deleteById(id);
    }
}