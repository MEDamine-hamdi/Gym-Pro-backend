package com.gympro.gymprobackend.service;

import com.gympro.gymprobackend.entity.Client;
import com.gympro.gymprobackend.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientService {
    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAllClients(){
        return clientRepository.findAll();
    }

    public Client getClientById(long id){
        return clientRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Client not found with id: " + id));
    }
    public Client createClient(Client client){
        return clientRepository.save(client);
    }

    public Client updateClient(long id,Client client){
        Client existing=getClientById(id);

        existing.setFirstName(client.getFirstName());
        existing.setLastName(client.getLastName());
        existing.setEmail(client.getEmail());
        existing.setDateOfBirth(client.getDateOfBirth());
        existing.setJoinDate(client.getJoinDate());
        existing.setActive(client.isActive());

        return clientRepository.save(existing);
    }

    public void deleteClient(Long id){
        clientRepository.deleteById(id);
    }

}
