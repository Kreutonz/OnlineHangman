package ca.cmpt213.a4.onlinehangman.controllers;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;


/**
 * a class that inherits the functionality of the RuntimeException class
 * but has a different name
 *
 * @author Mike Kreutz
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND, reason = "game not found")
public class GameNotFoundException extends RuntimeException {
    GameNotFoundException(){
        super();
    }
}//GameNotFoundException()
