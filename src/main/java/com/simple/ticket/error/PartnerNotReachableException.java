package com.simple.ticket.error;

public class PartnerNotReachableException extends BusinessException {
    public PartnerNotReachableException() {
        super("A külső rendszer nem elérhető!", 20404);
    }
}
