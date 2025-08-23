package com.simple.ticket.error;

public class EventAlreadyStartedException extends BusinessException {
    public EventAlreadyStartedException() {
        super("Olyan eseményre ami már elkezdődött nem lehet jegyet eladni!", 20011);
    }
}
