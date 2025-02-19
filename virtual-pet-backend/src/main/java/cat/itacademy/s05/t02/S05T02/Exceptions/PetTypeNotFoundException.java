package cat.itacademy.s05.t02.S05T02.Exceptions;

public class PetTypeNotFoundException extends RuntimeException {
    public PetTypeNotFoundException(String message) {
        super(message);
    }
}
