package HotelSystem.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException() {
        super("El usuario no ha sido encontrado en el sistema");
    }

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
