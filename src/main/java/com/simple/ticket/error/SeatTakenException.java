package com.simple.ticket.error;

public class SeatTakenException extends BusinessException {
    public SeatTakenException() {
        super("Már lefoglalt székre nem lehet jegyet eladni!", 20010);
    }
}