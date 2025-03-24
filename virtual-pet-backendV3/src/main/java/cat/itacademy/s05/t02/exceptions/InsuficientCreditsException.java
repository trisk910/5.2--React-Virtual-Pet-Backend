package cat.itacademy.s05.t02.exceptions;

public class InsuficientCreditsException extends RuntimeException {
    public InsuficientCreditsException(String message) {
        super(message);
    }
}
