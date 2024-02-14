package it.epicode.w6d3.exceptions;

public class NotFoundException extends Exception {
    public NotFoundException(String msg) {
        super(msg);
    }
    public NotFoundException() {
        super();
    }
}
