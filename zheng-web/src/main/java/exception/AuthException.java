package exception;

/**
 * Created by XR on 2016/8/29.
 */
public class AuthException extends RuntimeException {
    public AuthException(String message){
        super(message);
    }
}
