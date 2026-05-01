package com.gympro.gymprobackend.service;

import com.gympro.gymprobackend.entity.Offer;
import com.gympro.gymprobackend.repository.OfferRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
@Service
public class OfferService {
    @Autowired
    private OfferRepository  offerRepository;

    public List<Offer> getAllOffers(){
        return offerRepository.findAll() ;
    }

    public Offer getOfferById(Long id)   {
        return offerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Offer not found with id: " + id));
    }

    public Offer createOffer(Offer offer){
        return offerRepository.save(offer);
    }

    public Offer updateOffer(Offer offer,Long id){
        Offer existing=getOfferById(id);

        existing.setName(offer.getName());
        existing.setPrice(offer.getPrice());
        existing.setDurationDays(offer.getDurationDays());
        existing.setSessionsIncluded(offer.getSessionsIncluded());
        existing.setActive(offer.isActive());

        return offerRepository.save(existing);
    }

    public void deleteOffer (Long id){
        offerRepository.deleteById(id);
    }
}
