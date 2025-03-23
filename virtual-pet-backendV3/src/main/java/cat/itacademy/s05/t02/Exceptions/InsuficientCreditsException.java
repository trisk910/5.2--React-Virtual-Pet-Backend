package cat.itacademy.s05.t02.Exceptions;

public class InsuficientCreditsException extends RuntimeException {
    public InsuficientCreditsException(String message) {
        super(message);
    }
}
