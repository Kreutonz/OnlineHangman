package ca.cmpt213.a4.onlinehangman.controllers;

import ca.cmpt213.a4.onlinehangman.model.Game;
import ca.cmpt213.a4.onlinehangman.model.Information;
import ca.cmpt213.a4.onlinehangman.model.Message;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;


/**
 * Controller class that allows the communication between thymeleaf and Springboot
 * using the Game class to play a game of 'hangman' and display the results
 * in an online web browser using HTML & CSS features
 *
 * @author Mike Kreutz
 */
@Controller
public class HangmanController {
    private Message promptMessage;   //SKELETON EXAMPLE: a resusable String object to display a prompt message at the screen
    private Information gameInformation;
    private List<Game> listOfGames;
    private AtomicLong controllerId;


    //works like a constructor, but wait until dependency injection is done, so it's more like a setup
    @PostConstruct
    public void hangmanControllerInit() {
        promptMessage = new Message("Initializing...");  //SKELETON EXAMPLE
        gameInformation = new Information();
        listOfGames = new ArrayList<>();
        controllerId = new AtomicLong();
    }//hangmanControllerInit()


    //SKELETON EXAMPLE
    @GetMapping("/helloworld")
    @ResponseStatus(value = HttpStatus.OK)              //returns status code: 200
    public String showHelloworldPage(Model model) {
        promptMessage.setMessage("You are at the helloworld page!");
        model.addAttribute("promptMessage", promptMessage);

        // take the user to helloworld.html
        return "helloworld";
    }//showHelloWorldPage()


    /* provides the information for 'welcome.html', returns the string 'welcome' used by
       thymeleaf to redirect the web browser to 'welcome.html' */
    @GetMapping("/welcome")
    @ResponseStatus(value = HttpStatus.OK)              //returns status code: 200
    public String showWelcomePage(Model model){
        model.addAttribute("gameInformation", gameInformation);

        return "welcome";
    }//showWelcomePage()


    /* when redirected from 'welcome.html' using thymeleaf, a new game object
       is created with a new word to choose from to play the 'hangman' game. */
    @PostMapping("/game")
    @ResponseStatus(value = HttpStatus.MOVED_PERMANENTLY)           //returns status code: 301
    public String showGamePage(@ModelAttribute("letter")String letter, Model model){
        Game gameObject = new Game(controllerId.incrementAndGet());

        model.addAttribute("gameObject", gameObject);
        model.addAttribute("letter", letter);

        gameObject = (Game)model.getAttribute("gameObject");        //gets the updated game object
        listOfGames.add(gameObject);                                             //adds updated game to list

        //source: https://stackoverflow.com/questions/52329740/redirect-to-another-page-using-spring-thymeleaf
        //using thymeleaf: redirects the game object to game/{id}.html
        return "redirect:game/" + gameObject.getID();
    }//showGamePage()


    /* when redirected from 'game.html' using thymeleaf, allows the current game to be played.
       Checks status of game, when game is over, win/lost, redirects to 'gameover.html'
       using thymeleaf */
    @PostMapping("/game/{id}")
    @ResponseStatus(value = HttpStatus.CREATED)                    //returns status code: 201
    public String playExistingGame(@PathVariable("id")long id, @ModelAttribute("letter")String letter, Model model){

        //converts the id of type 'long' to int so that arrayList indices can be accessed
        int convertedID = (int)id;

        //gets the game from the game List based on the id
        Game gameObject = listOfGames.get(convertedID - 1);

        gameObject.setLetter(letter);
        gameObject.guess();                 //updates the turn for the game

        model.addAttribute("gameObject", gameObject);
        model.addAttribute("letter", letter);

        gameObject = (Game)model.getAttribute("gameObject");
        listOfGames.set(convertedID - 1, gameObject);


        String status = gameObject.getStatus();
        if(gameObject.getStatus() != null && (status.equals("Won") || status.equals("Lost"))){
            return "gameover";
        }else{
            return "game";
        }
    }//playExistingGame()


    /* allows user to access game through url using this mapping, allowing for multiple games
       to be played simultaneously. Game can be access using 'game/{id}' and the game with
       that ID can be played until the status changes. If game ID does not exist,
       GameNotFoundException() is thrown */
    @GetMapping("/game/{id}")
    @ResponseStatus(value = HttpStatus.OK)                          //returns status code: 200
    public String getExistingGame(@PathVariable("id")long id, Model model){
        if(id > listOfGames.size() || id < 0){
            model.addAttribute("id", id);
            throw new GameNotFoundException();
        }
        else{
            Game gameObject = listOfGames.get((int)id - 1);
            model.addAttribute("gameObject", gameObject);

            String status = gameObject.getStatus();
            if(gameObject.getStatus() != null && (status.equals("Won") || status.equals("Lost"))) {
                return "gameover";
            }
        }
        return "game";
    }//getExistingGame()


    /* Exception handler that handles the GameNotFoundException() that can be thrown in
       getExistingGame() metho. Allows thymeleaf to return 'gamenotfound.html' */
    @ResponseStatus(value = HttpStatus.NOT_FOUND)                //returns status code: 404
    @ExceptionHandler(GameNotFoundException.class)
    public String gameNotFoundException()
    {
        return "gamenotfound";
    }//ExceptionHandler()

}//HangmanController Class