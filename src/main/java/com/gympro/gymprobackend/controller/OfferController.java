package com.gympro.gymprobackend.controller;

import com.gympro.gymprobackend.entity.Offer;
import com.gympro.gymprobackend.service.OfferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import java.util.List;

@RestController
@RequestMapping("/api/offers")
public class OfferController {
    @Autowired OfferService offerService;

    @GetMapping
    public ResponseEntity<List<Offer>>getAllOffers(){
        List<Offer> offers= offerService.getAllOffers();
        return ResponseEntity.ok(offers);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Offer>getOfferById(@PathVariable Long id){
        Offer offer =offerService.getOfferById(id);
        return ResponseEntity.ok(offer);
    }

    @PostMapping
    public ResponseEntity<Offer>createOffer(@RequestBody Offer offer){
        Offer createdOffer=offerService.createOffer(offer);
        return ResponseEntity.ok(createdOffer);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Offer> updateOffer(@PathVariable Long id,@RequestBody Offer offer){
        Offer updatedOffer=offerService.updateOffer(id,offer);
        return ResponseEntity.ok(updatedOffer);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String>deleteOffer(@PathVariable Long id){
        offerService.deleteOffer(id);
        return ResponseEntity.ok("offer deleted successfully");
    }

}
