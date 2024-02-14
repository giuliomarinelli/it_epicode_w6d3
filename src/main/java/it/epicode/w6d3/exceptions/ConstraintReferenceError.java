package it.epicode.w6d3.exceptions;

public class ConstraintReferenceError extends BadRequestException {
    public ConstraintReferenceError(String msg) {
        super(msg);
    }
    public ConstraintReferenceError() {
        super();
    }
}
