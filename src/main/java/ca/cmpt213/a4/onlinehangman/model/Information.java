package ca.cmpt213.a4.onlinehangman.model;

public class Information {
    private final String WELCOME_MESSAGE;
    private final String CREATOR_NAME;
    private final String CREATOR_STUDENT_ID;
    private final String CREATOR_EMAIL;
    private final String GAME_RULES;

    public Information(){
        WELCOME_MESSAGE = "Welcome to Online Hangman";
        CREATOR_NAME = "Mike Kreutz";
        CREATOR_STUDENT_ID = "<Confidential>";
        CREATOR_EMAIL = "<Confidential>";
        GAME_RULES = "\nA player has a maximum of 7 incorrect guesses of various letters from the alphabet.\n" +
                "Therefore, an 8th incorrect guess warrants a \'GAMEOVER!\' " +
                "Each dash signifies a letter the word contains, some dashes may contain the same letter.\n" +
                "For each incorrect guess, a body part is added to the \'Hangman\'.\n" +
                "If an imaginary Hangman results from incorrect guesses (giving all the body parts), the game is over.\n" +
                "However, if the word is completed before the man can be made out, then\n" +
                "the player wins and the game is over.\n";
    }

    public String getWelcomeMessage() {
        return WELCOME_MESSAGE;
    }

    public String getCreatorName() {
        return CREATOR_NAME;
    }

    public String getCreatorStudentID() {
        return CREATOR_STUDENT_ID;
    }

    public String getCreatorEmail() {
        return CREATOR_EMAIL;
    }

    public String getGameRules() {
        return GAME_RULES;
    }
}
