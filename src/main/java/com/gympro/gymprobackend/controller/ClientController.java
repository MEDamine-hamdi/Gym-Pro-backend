package com.gympro.gymprobackend.controller;

import com.gympro.gymprobackend.entity.Client;
import com.gympro.gymprobackend.service.ClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/clients")
public class ClientController {
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<Client>>getAllClients(){
        List<Client>clients=clientService.getAllClients();
        return ResponseEntity.ok(clients);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Client>getClientById(@PathVariable Long id){
        Client client=clientService.getClientById(id);
        return ResponseEntity.ok(client);
    }

    @PostMapping
    public ResponseEntity<Client>createClient(@RequestBody Client client){
        Client createdClient=clientService.createClient(client);
        return ResponseEntity.ok(createdClient);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Client>updateClient(@PathVariable Long id,@RequestBody Client client ){
        Client updatedClient=clientService.updateClient(id,client);
        return ResponseEntity.ok(updatedClient);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteClient(@PathVariable Long id){
        clientService.deleteClient(id);
        return ResponseEntity.ok("Client deleted successfully");
    }





}
