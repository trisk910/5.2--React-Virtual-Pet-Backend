package cat.itacademy.s05.t02.S05T02.Exceptions;

public class PlayerNotFoundException extends RuntimeException {
    public PlayerNotFoundException(int id) {
        super("Player not found with id:" + id);
    }
}
