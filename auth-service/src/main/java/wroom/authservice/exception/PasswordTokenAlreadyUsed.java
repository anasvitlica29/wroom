package wroom.authservice.exception;

public class PasswordTokenAlreadyUsed extends RuntimeException {

	public PasswordTokenAlreadyUsed(String message) {
        super(message);
    }
	
}
