package com.solver;

import java.util.InputMismatchException;
import java.util.Scanner;

public class Play {

    /**
     * Main method that controls the flow of the game
     */
    public static void main(String[] args) {

        Game game = new Game();
        boolean running = true;
        int cardOption;
        Scanner cardOptionScanner = new Scanner(System.in);

        printSplashArt();

        while (running) {
            System.out.print("""
                    Choose an option:
                    1. Input cards manually
                    2. Randomly pick 4 cards from a deck
                    3. Exit
                    """);

            while (true) {

                System.out.print("Enter a number (1-3): ");

                try {
                    cardOption = cardOptionScanner.nextInt();
                    if (cardOption == 1 || cardOption == 2 || cardOption == 3) {
                        break;
                    }
                    else {
                        System.out.println("Invalid input.");
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    cardOptionScanner.next();
                }
                catch (Exception e) {
                    e.printStackTrace();
                }

            }

            switch (cardOption) {
                case 1 -> game.inputCardsFromUser();
                case 2 -> game.generateCards();
                case 3 -> {
                    System.out.println("Farewell.");
                    return;
                }
            }

            game.findSolutions();
            game.printSolutions();
            game.printExecutionTime();
            game.savePrompt();

            System.out.print("""
                    Do you wish to solve again?
                    1. Yes
                    2. No
                    """);

            int continueOption;
            Scanner continueOptionScanner = new Scanner(System.in);

            while (true) {

                System.out.print("Enter a number (1 or 2): ");

                try {
                    continueOption = continueOptionScanner.nextInt();
                    if (continueOption == 1 || continueOption == 2) {
                        break;
                    }
                    else {
                        System.out.println("Invalid input.");
                    }
                }
                catch (InputMismatchException e) {
                    System.out.println("Invalid input.");
                    continueOptionScanner.next();
                }

            }

            switch (continueOption) {
                case 1 -> {}
                case 2 -> running = false;
            }
        }

        System.out.println("Farewell.");
    }

    /**
     * Prints the welcome splash art.
     */
    private static void printSplashArt() {
        System.out.print("""
                  ______   __    __         ______    ______   __     __     __  ________  _______ \s
                 /      \\ /  |  /  |       /      \\  /      \\ /  |   /  |   /  |/        |/       \\\s
                /$$$$$$  |$$ |  $$ |      /$$$$$$  |/$$$$$$  |$$ |   $$ |   $$ |$$$$$$$$/ $$$$$$$  |
                $$____$$ |$$ |__$$ |      $$ \\__$$/ $$ |  $$ |$$ |   $$ |   $$ |$$ |__    $$ |__$$ |
                 /    $$/ $$    $$ |      $$      \\ $$ |  $$ |$$ |   $$  \\ /$$/ $$    |   $$    $$<\s
                /$$$$$$/  $$$$$$$$ |       $$$$$$  |$$ |  $$ |$$ |    $$  /$$/  $$$$$/    $$$$$$$  |
                $$ |_____       $$ |      /  \\__$$ |$$ \\__$$ |$$ |_____$$ $$/   $$ |_____ $$ |  $$ |
                $$       |      $$ |      $$    $$/ $$    $$/ $$       |$$$/    $$       |$$ |  $$ |
                $$$$$$$$/       $$/        $$$$$$/   $$$$$$/  $$$$$$$$/  $/     $$$$$$$$/ $$/   $$/\s
                                
                """);
    }
}
