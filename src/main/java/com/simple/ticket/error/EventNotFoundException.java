package com.simple.ticket.error;

public class EventNotFoundException extends BusinessException {
    public EventNotFoundException() {
        super("Nem létezik ilyen esemény!", 20001);
    }
}
