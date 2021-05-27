package ca.cmpt213.a4.onlinehangman.model;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

/**
 * class that provides methods for playing the 'hangman' game
 * and storing the information regarding the moves and status.
 *
 * @author Mike Kreutz
 */
public class Game {
    private final String fileName = "src/commonWords.txt";
    private final List<String> listOfWords;
    private final String WORD;
    private String hiddenWord;
    private final long ID;
    private String status;
    private final int MAX_INCORRECT;
    private int incorrectGuesses;
    private int totalGuesses;
    private String letter;


    public Game(long id) {
        listOfWords = new ArrayList<>();
        readInFile();
        WORD = chooseWord();
        initializeHiddenWord();
        ID = id;
        status = "Active";
        MAX_INCORRECT = 7;
        totalGuesses = 0;
        incorrectGuesses = 0;
    }//Game()-constructor


    //*****************
    // GETTERS
    //*****************

    public int getIncorrectGuesses() {
        return incorrectGuesses;
    }


    public int getTotalGuesses(){
        return totalGuesses;
    }


    public long getID(){
        return ID;
    }


    public String getWord(){
        return WORD;
    }


    public String getStatus(){
        return status;
    }


    public String getLetter(){
        return letter;
    }


    public String getHiddenWord(){
        return makeHiddenWordReadable();
    }


    //********************
    // SETTERS
    //********************

    public void setLetter(String letter){
        this.letter = letter;
    }


    private void setStatus(){
        if(incorrectGuesses > MAX_INCORRECT){
            status = "Lost";
        }
        if(hiddenWord.equals(WORD)){
            status = "Won";
        }
    }


    //****************************
    // PRIVATE-METHODS
    //****************************

    private void readInFile() {
        File sampleFile = new File(fileName);
        String word;
        try(Scanner fileReader = new Scanner(sampleFile)){
            while(fileReader.hasNextLine()){
                word = fileReader.nextLine();
                listOfWords.add(word);
            }
        }catch (FileNotFoundException e){
            e.printStackTrace();
        }
    }//readInFile()


    //gets a randomNumber based on the number of words in the file
    private int getRandomNumber(){
        Random random = new Random();
        return random.nextInt(listOfWords.size());
    }//getRandomNumber()


    //returns a new string containing hidden word's contents separated with spaces
    private String makeHiddenWordReadable(){
        String temp = String.valueOf(hiddenWord.charAt(0));

        for(int i = 1; i < hiddenWord.length(); i++){
            temp = temp + " " + hiddenWord.charAt(i);
        }
        return temp;
    }//makeHiddenWordReadable()


    //choose a random word from the word file and set it as WORD
    private String chooseWord(){
        int randomNumber = getRandomNumber();
        return listOfWords.get(randomNumber);
    }//chooseWord()


    //sets hidden word to have characters equal to '_'
    private void initializeHiddenWord(){
        for(int i = 0; i < WORD.length(); i++){
            if(i == 0){
                hiddenWord = "_";
            }else{
                hiddenWord += "_";
            }
        }
    }//initializeHiddenWord()


    //determines if the private letter variable is empty or not
    private boolean emptyGuess(){
        boolean allEmpty = true;
        for(int i = 0; i < letter.length(); i++){
            if(letter.charAt(i) != ' '){
                allEmpty = false;
                break;
            }
        }
        return allEmpty;
    }//emptyGuess()


    //**************************
    // PUBLIC-METHODS
    //**************************

    //controls the turn of the game
    public void guess(){
        if(!emptyGuess()){
            totalGuesses++;
            String temp = "";
            boolean letterInWord = false;
            for(int i = 0; i < WORD.length(); i++){
                if(letter.length() == 1 && WORD.charAt(i) == (letter.charAt(0))){
                    letterInWord = true;
                    if(i == 0){
                        temp = letter;
                    }else{
                        temp += letter;
                    }
                }else{
                    if(i == 0){
                        temp = String.valueOf(hiddenWord.charAt(i));
                    }else {
                        temp += hiddenWord.charAt(i);
                    }
                }
            }
            hiddenWord = temp;
            if(!letterInWord){
                incorrectGuesses++;
            }
        }//if(!emptyGuess())
        setStatus();
    }//guess()
}//Game-Class
