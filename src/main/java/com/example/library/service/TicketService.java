package com.example.library.service;

import com.example.library.model.Ticket;
import com.example.library.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class TicketService {
    private TicketRepository ticketRepository;

    @Autowired
    public void setTicketRepository(TicketRepository ticketRepository) {
        this.ticketRepository = ticketRepository;
    }

    public void save(Ticket ticket) {
        ticketRepository.save(ticket);
    }

    public List<Ticket> findAll() {
        return ticketRepository.findAll();
    }

    public Ticket findById(Long id) {
        return ticketRepository.getById(id);
    }

    public void delete(Ticket ticket) {
        ticketRepository.delete(ticket);
    }

    public void update(Ticket ticket) {
        ticketRepository.saveAndFlush(ticket);
    }
}