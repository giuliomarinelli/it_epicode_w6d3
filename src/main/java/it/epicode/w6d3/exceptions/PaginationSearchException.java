package it.epicode.w6d3.exceptions;

public class PaginationSearchException extends Exception {
    public PaginationSearchException(String msg) {
        super(msg);
    }
    public PaginationSearchException() {
        super();
    }
}