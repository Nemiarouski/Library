package com.example.library.utils;

import com.example.library.model.Ticket;
import java.util.Comparator;

public class TicketComparator implements Comparator<Ticket> {
    @Override
    public int compare(Ticket o1, Ticket o2) {
        if (!o1.getBook().equals(o2.getBook())) {
            return o1.getBook().compareTo(o2.getBook());
        } else if (!o1.getDateFrom().equals(o2.getDateFrom())) {
            return o1.getDateFrom().compareTo(o2.getDateFrom());
        } else if (!o1.getDateTo().equals(o2.getDateTo())) {
            return o1.getDateTo().compareTo(o2.getDateTo());
        }
        return 0;
    }
}