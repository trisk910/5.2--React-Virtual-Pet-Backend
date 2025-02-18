package cat.itacademy.s05.t02.S05T02.Exceptions;

public class GameNotFoundException extends RuntimeException {
    public GameNotFoundException(String idGame) {
        super("Game not found with id " + idGame);
    }
}
