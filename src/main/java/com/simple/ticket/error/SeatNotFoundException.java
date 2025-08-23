package com.simple.ticket.error;

public class SeatNotFoundException extends BusinessException {
    public SeatNotFoundException() {
        super("Nem létezik ilyen szék!", 20002);
    }
}
