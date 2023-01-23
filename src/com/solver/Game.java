package com.solver;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * The {@code Game} class implements everything in the game:
 * user input, input validation, solution finding, and file output.
 */
public class Game {

    private static final String[] VALID_INPUTS = {
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
    };

    private static final String[] DECK_OF_CARDS = {
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K",
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K",
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K",
            "A", "2", "3", "4", "5", "6", "7", "8", "9", "10", "J", "Q", "K"
    };

    private static final double TARGET = 24.0;

    // An array of integer containing four numeric values corresponding to the cards that are being played
    private int[] cards;

    // All possible solutions for the cards that are being played
    private ArrayList<String> solutions;

    // Execution time of the brute force algorithm used to find all possible solutions to the puzzle
    private double executionTime;

    /**
     * Handles user input for the cards, validates it, and determines the numeric value for each card.
     * Sets {@code this.cards} to be an array of integers containing the corresponding numeric values of
     * four user-inputted cards, e.g. "1 4 11 13"
     */
    void inputCardsFromUser() {
        String finalCardString = "";
        Scanner cardScanner = new Scanner(System.in);

        while (true) {

            System.out.println("Valid cards are A, 2, 3, 4, 5, 6, 7, 8, 9, 10, J, Q, and K");
            System.out.println("Input your cards seperated by a space, e.g. \"A 10 K 3\".");
            System.out.print(">>> ");

            try {
                String cardsInput = cardScanner.nextLine();

                // Split the input string with one or more whitespace as the delimiter
                String[] arrayOfCards = cardsInput.split("\\s+");

                if (arrayOfCards.length != 4) {
                    throw new InputMismatchException();
                }

                for (int i = 0; i < arrayOfCards.length; i++) {
                    if (Arrays.asList(VALID_INPUTS).contains(arrayOfCards[i])) {
                        if (i == 3) {
                            arrayOfCards[i] = convertCards(arrayOfCards[i]);
                            finalCardString += String.format("%s", arrayOfCards[i]);
                            throw new NoSuchElementException();
                        }
                        else {
                            arrayOfCards[i] = convertCards(arrayOfCards[i]);
                            finalCardString += String.format("%s ", arrayOfCards[i]);
                        }
                    }
                    else {
                        throw new InputMismatchException();
                    }
                }
            }
            catch (InputMismatchException e) {
                System.out.print("Invalid input.\n\n");
            }
            catch (NoSuchElementException e) {
                break;
            }

        }

        String[] finalCardStringArray = finalCardString.split("\\s+");

        // Constructs a new array of integer containing the corresponding numeric value of user-inputted cards
        this.cards = new int[4];
        for (int i = 0; i < this.cards.length; i++) {
            this.cards[i] = Integer.parseInt(finalCardStringArray[i]);
        }

    }

    /**
     * Shuffles a deck of cards, takes four cards from it, and determines the numeric value for each card.
     * Sets {@code this.cards} to be an array of integers containing the corresponding numeric values of
     * four randomly generated cards, e.g. "1 10 2 9"
     */
    void generateCards() {

        // Constructs a separate List of Strings, backed by the DECK_OF_CARDS array, as a deck of cards
        List<String> newDeck = new ArrayList<>(Arrays.asList(DECK_OF_CARDS));

        // Shuffles the new deck
        Collections.shuffle(newDeck);

        // Draw four cards from the shuffled deck
        List<String> drawnCardsList = newDeck.subList(0, 4);

        String[] drawnCardsArray = drawnCardsList.toArray(new String[0]);

        System.out.print("Drawn four cards: ");

        for (int i = 0; i < drawnCardsArray.length; i++) {
            if (i == 3) {
                System.out.println(drawnCardsArray[i]);
            }
            else {
                System.out.printf("%s ", drawnCardsArray[i]);
            }
        }

        for (int i = 0; i < drawnCardsArray.length; i++) {
            drawnCardsArray[i] = convertCards(drawnCardsArray[i]);
        }

        // Constructs a new array of integer containing the corresponding numeric value of randomly generated cards
        this.cards = new int[4];
        for (int i = 0; i < this.cards.length; i++) {
            this.cards[i] = Integer.parseInt(drawnCardsArray[i]);
        }

    }


    /**
     * Converts King, Queen, Jack, and Ace to its respective numeric value.
     */
    private String convertCards(String cards) {

        if (Objects.equals(cards, "A")) {
            return "1";
        }
        if (Objects.equals(cards, "J")) {
            return "11";
        }
        if (Objects.equals(cards, "Q")) {
            return "12";
        }
        if (Objects.equals(cards, "K")) {
            return "13";
        }

        return cards;
    }

    /**
     * Implements brute force algorithm to find all possible solutions.
     * Each solution (in mathematical expression) is added to the {@code solutions} ArrayList as a String.
     */
    void findSolutions() {

        System.out.printf("%d %d %d %d\n", cards[0], cards[1], cards[2], cards[3]);

        // Constucts a new empty ArrayList of Strings
        this.solutions = new ArrayList<>();

        double startTime = System.nanoTime();

        // Four nested for loops to generate all possible permutations
        // of the four integers in this.cards
        for (int i = 0; i < this.cards.length; i++) {
            for (int j = 0; j < this.cards.length; j++) {
                if (i == j) {
                    continue;
                }
                for (int k = 0; k < this.cards.length; k++) {
                    if (i == k || j == k) {
                        continue;
                    }
                    for (int l = 0; l < this.cards.length; l++) {
                        if (i == l || j == l || k == l) {
                            continue;
                        }

                        double a = this.cards[i];
                        double b = this.cards[j];
                        double c = this.cards[k];
                        double d = this.cards[l];

                        int intA = this.cards[i];
                        int intB = this.cards[j];
                        int intC = this.cards[k];
                        int intD = this.cards[l];

                        // All meaningful parentheses permutations with a, b, c, and d as integers,
                        // and op as a mathematical operator (+, -, *, or /) are as follows:
                        //
                        // ((a op b) op c) op d
                        // (a op (b op c)) op d
                        // a op ((b op c) op d)
                        // a op (b op (c op d))
                        // (a op b) op (c op d)
                        //
                        // Note that the mathematical operator / is real division as opposed to integer division.
                        //
                        // The reason for all possible permutations with zero and one pair of brackets are not
                        // included is because their semantic would be similar with other solutions.
                        // For example, given four integers 4, 2, 1, and 4,these solutions have similar semantics:
                        // (4 + 2) * 1 * 4
                        // (1 * 4) * (2 + 4)
                        //
                        // Another example of two solutions with similar semantics:
                        // 11 * 2 * 1 + 2
                        // ((11 * 2) * 1) + 2
                        //
                        // So, all possible permutations with zero and one pair of brackets are not computed
                        // to minimize the number of solutions with similar semantics.

                        // ((a op b) op c) op d
                        if (((a + b) + c) + d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) + %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a + b) + c) - d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) + %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a + b) + c) * d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) + %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a + b) + c) / d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) + %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a + b) - c) + d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) - %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a + b) - c) - d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) - %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a + b) - c) * d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) - %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a + b) - c) / d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) - %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a + b) * c) + d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) * %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a + b) * c) - d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) * %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a + b) * c) * d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) * %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a + b) * c) / d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) * %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a + b) / c) + d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) / %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a + b) / c) - d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) / %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a + b) / c) * d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) / %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a + b) / c) / d == TARGET) {
                            this.solutions.add(String.format("((%d + %d) / %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a - b) + c) + d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) + %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a - b) + c) - d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) + %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a - b) + c) * d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) + %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a - b) + c) / d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) + %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a - b) - c) + d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) - %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a - b) - c) - d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) - %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a - b) - c) * d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) - %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a - b) - c) / d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) - %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a - b) * c) + d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) * %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a - b) * c) - d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) * %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a - b) * c) * d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) * %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a - b) * c) / d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) * %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a - b) / c) + d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) / %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a - b) / c) - d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) / %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a - b) / c) * d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) / %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a - b) / c) / d == TARGET) {
                            this.solutions.add(String.format("((%d - %d) / %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a * b) + c) + d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) + %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a * b) + c) - d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) + %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a * b) + c) * d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) + %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a * b) + c) / d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) + %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a * b) - c) + d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) - %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a * b) - c) - d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) - %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a * b) - c) * d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) - %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a * b) - c) / d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) - %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a * b) * c) + d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) * %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a * b) * c) - d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) * %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a * b) * c) * d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) * %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a * b) * c) / d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) * %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a * b) / c) + d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) / %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a * b) / c) - d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) / %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a * b) / c) * d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) / %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a * b) / c) / d == TARGET) {
                            this.solutions.add(String.format("((%d * %d) / %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a / b) + c) + d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) + %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a / b) + c) - d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) + %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a / b) + c) * d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) + %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a / b) + c) / d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) + %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a / b) - c) + d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) - %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a / b) - c) - d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) - %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a / b) - c) * d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) - %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a / b) - c) / d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) - %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a / b) * c) + d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) * %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a / b) * c) - d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) * %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a / b) * c) * d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) * %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a / b) * c) / d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) * %d) / %d", intA, intB, intC, intD));
                        }
                        if (((a / b) / c) + d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) / %d) + %d", intA, intB, intC, intD));
                        }
                        if (((a / b) / c) - d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) / %d) - %d", intA, intB, intC, intD));
                        }
                        if (((a / b) / c) * d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) / %d) * %d", intA, intB, intC, intD));
                        }
                        if (((a / b) / c) / d == TARGET) {
                            this.solutions.add(String.format("((%d / %d) / %d) / %d", intA, intB, intC, intD));
                        }

                        // (a op (b op c)) op d
                        if ((a + (b + c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d + %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a + (b + c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d + %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a + (b + c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d + %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a + (b + c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d + %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a + (b - c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d - %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a + (b - c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d - %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a + (b - c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d - %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a + (b - c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d - %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a + (b * c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d * %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a + (b * c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d * %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a + (b * c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d * %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a + (b * c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d * %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a + (b / c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d / %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a + (b / c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d / %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a + (b / c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d / %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a + (b / c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d + (%d / %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a - (b + c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d + %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a - (b + c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d + %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a - (b + c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d + %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a - (b + c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d + %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a - (b - c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d - %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a - (b - c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d - %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a - (b - c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d - %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a - (b - c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d - %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a - (b * c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d * %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a - (b * c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d * %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a - (b * c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d * %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a - (b * c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d * %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a - (b / c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d / %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a - (b / c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d / %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a - (b / c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d / %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a - (b / c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d - (%d / %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a * (b + c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d + %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a * (b + c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d + %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a * (b + c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d + %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a * (b + c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d + %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a * (b - c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d - %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a * (b - c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d - %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a * (b - c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d - %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a * (b - c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d - %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a * (b * c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d * %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a * (b * c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d * %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a * (b * c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d * %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a * (b * c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d * %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a * (b / c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d / %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a * (b / c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d / %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a * (b / c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d / %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a * (b / c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d * (%d / %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a / (b + c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d + %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a / (b + c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d + %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a / (b + c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d + %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a / (b + c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d + %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a / (b - c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d - %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a / (b - c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d - %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a / (b - c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d - %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a / (b - c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d - %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a / (b * c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d * %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a / (b * c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d * %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a / (b * c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d * %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a / (b * c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d * %d)) / %d", intA, intB, intC, intD));
                        }
                        if ((a / (b / c)) + d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d / %d)) + %d", intA, intB, intC, intD));
                        }
                        if ((a / (b / c)) - d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d / %d)) - %d", intA, intB, intC, intD));
                        }
                        if ((a / (b / c)) * d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d / %d)) * %d", intA, intB, intC, intD));
                        }
                        if ((a / (b / c)) / d == TARGET) {
                            this.solutions.add(String.format("(%d / (%d / %d)) / %d", intA, intB, intC, intD));
                        }

                        // a op ((b op c) op d)
                        if (a + ((b + c) + d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d + %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b + c) - d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d + %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b + c) * d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d + %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b + c) / d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d + %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b - c) + d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d - %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b - c) - d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d - %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b - c) * d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d - %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b - c) / d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d - %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b * c) + d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d * %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b * c) - d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d * %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b * c) * d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d * %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b * c) / d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d * %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b / c) + d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d / %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b / c) - d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d / %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b / c) * d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d / %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a + ((b / c) / d) == TARGET) {
                            this.solutions.add(String.format("%d + ((%d / %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b + c) + d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d + %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b + c) - d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d + %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b + c) * d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d + %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b + c) / d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d + %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b - c) + d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d - %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b - c) - d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d - %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b - c) * d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d - %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b - c) / d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d - %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b * c) + d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d * %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b * c) - d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d * %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b * c) * d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d * %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b * c) / d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d * %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b / c) + d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d / %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b / c) - d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d / %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b / c) * d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d / %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a - ((b / c) / d) == TARGET) {
                            this.solutions.add(String.format("%d - ((%d / %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b + c) + d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d + %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b + c) - d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d + %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b + c) * d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d + %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b + c) / d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d + %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b - c) + d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d - %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b - c) - d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d - %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b - c) * d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d - %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b - c) / d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d - %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b * c) + d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d * %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b * c) - d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d * %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b * c) * d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d * %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b * c) / d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d * %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b / c) + d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d / %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b / c) - d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d / %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b / c) * d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d / %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a * ((b / c) / d) == TARGET) {
                            this.solutions.add(String.format("%d * ((%d / %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b + c) + d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d + %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b + c) - d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d + %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b + c) * d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d + %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b + c) / d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d + %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b - c) + d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d - %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b - c) - d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d - %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b - c) * d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d - %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b - c) / d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d - %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b * c) + d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d * %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b * c) - d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d * %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b * c) * d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d * %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b * c) / d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d * %d) / %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b / c) + d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d / %d) + %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b / c) - d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d / %d) - %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b / c) * d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d / %d) * %d)", intA, intB, intC, intD));
                        }
                        if (a / ((b / c) / d) == TARGET) {
                            this.solutions.add(String.format("%d / ((%d / %d) / %d)", intA, intB, intC, intD));
                        }

                        // a op (b op (c op d))
                        if (a + (b + (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d + (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a + (b + (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d + (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a + (b + (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d + (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a + (b + (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d + (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a + (b - (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d - (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a + (b - (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d - (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a + (b - (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d - (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a + (b - (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d - (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a + (b * (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d * (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a + (b * (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d * (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a + (b * (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d * (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a + (b * (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d * (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a + (b / (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d / (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a + (b / (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d / (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a + (b / (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d / (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a + (b / (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d + (%d / (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a - (b + (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d + (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a - (b + (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d + (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a - (b + (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d + (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a - (b + (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d + (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a - (b - (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d - (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a - (b - (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d - (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a - (b - (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d - (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a - (b - (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d - (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a - (b * (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d * (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a - (b * (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d * (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a - (b * (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d * (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a - (b * (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d * (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a - (b / (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d / (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a - (b / (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d / (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a - (b / (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d / (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a - (b / (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d - (%d / (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a * (b + (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d + (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a * (b + (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d + (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a * (b + (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d + (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a * (b + (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d + (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a * (b - (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d - (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a * (b - (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d - (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a * (b - (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d - (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a * (b - (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d - (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a * (b * (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d * (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a * (b * (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d * (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a * (b * (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d * (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a * (b * (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d * (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a * (b / (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d / (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a * (b / (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d / (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a * (b / (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d / (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a * (b / (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d * (%d / (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a / (b + (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d + (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a / (b + (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d + (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a / (b + (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d + (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a / (b + (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d + (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a / (b - (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d - (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a / (b - (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d - (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a / (b - (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d - (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a / (b - (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d - (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a / (b * (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d * (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a / (b * (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d * (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a / (b * (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d * (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a / (b * (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d * (%d / %d))", intA, intB, intC, intD));
                        }
                        if (a / (b / (c + d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d / (%d + %d))", intA, intB, intC, intD));
                        }
                        if (a / (b / (c - d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d / (%d - %d))", intA, intB, intC, intD));
                        }
                        if (a / (b / (c * d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d / (%d * %d))", intA, intB, intC, intD));
                        }
                        if (a / (b / (c / d)) == TARGET) {
                            this.solutions.add(String.format("%d / (%d / (%d / %d))", intA, intB, intC, intD));
                        }

                        // (a op b) op (c op d)
                        if ((a + b) + (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) + (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) + (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) + (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) + (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) + (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) + (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) + (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) - (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) - (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) - (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) - (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) - (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) - (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) - (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) - (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) * (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) * (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) * (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) * (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) * (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) * (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) * (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) * (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) / (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) / (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) / (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) / (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) / (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) / (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a + b) / (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d + %d) / (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) + (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) + (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) + (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) + (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) + (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) + (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) + (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) + (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) - (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) - (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) - (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) - (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) - (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) - (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) - (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) - (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) * (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) * (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) * (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) * (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) * (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) * (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) * (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) * (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) / (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) / (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) / (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) / (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) / (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) / (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a - b) / (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d - %d) / (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) + (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) + (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) + (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) + (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) + (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) + (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) + (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) + (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) - (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) - (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) - (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) - (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) - (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) - (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) - (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) - (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) * (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) * (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) * (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) * (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) * (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) * (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) * (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) * (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) / (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) / (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) / (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) / (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) / (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) / (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a * b) / (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d * %d) / (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) + (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) + (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) + (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) + (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) + (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) + (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) + (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) + (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) - (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) - (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) - (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) - (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) - (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) - (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) - (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) - (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) * (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) * (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) * (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) * (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) * (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) * (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) * (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) * (%d / %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) / (c + d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) / (%d + %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) / (c - d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) / (%d - %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) / (c * d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) / (%d * %d)", intA, intB, intC, intD));
                        }
                        if ((a / b) / (c / d) == TARGET) {
                            this.solutions.add(String.format("(%d / %d) / (%d / %d)", intA, intB, intC, intD));
                        }

                    }
                }
            }
        }

        double endTime = System.nanoTime();
        this.executionTime = (endTime - startTime) / 1000000000;

    }

    /**
     * Prints all solution Strings in {@code solutions} ArrayList
     * and determines if the user wants to save the solutions in
     * a text file. If yes, it calls {@code saveSolutions()}.
     */
    void printSolutions() {

        if (this.solutions.size() == 0) {
            System.out.println("No solutions were found.");
            return;
        }

        System.out.printf("%d solution(s) found:\n", this.solutions.size());

        for (String solution : this.solutions) {
            System.out.println(solution);
        }

    }

    /**
     * Prompts the user if they want to save the solutions to a text file or not.
     */
    void savePrompt() {

        System.out.print("""
                Do you want to save the solutions to a text file?
                1. Yes
                2. No
                """);

        Scanner fileOptionScanner = new Scanner(System.in);

        int fileOption;

        while (true) {

            System.out.print("Enter a number (1 or 2): ");

            try {
                fileOption = fileOptionScanner.nextInt();
                if (fileOption == 1 || fileOption == 2) {
                    break;
                }
                else {
                    System.out.println("Invalid input.");
                }
            }
            catch (InputMismatchException e) {
                System.out.println("Invalid input.");
                fileOptionScanner.next();
            }

        }

        switch (fileOption) {
            case 1 -> saveSolutions();
            case 2 -> {}
        }

    }

    /**
     * Asks the user for the name of the text file, validates it,
     * writes how many solutions were found, and writes every
     * solution Strings in {@code solutions} to the file.
     * Each solution String is seperated by a newline.
     */
    private void saveSolutions() {

        System.out.println("Input file name (ends with .txt), e.g. \"solutions.txt\".");

        Scanner fileNameScanner = new Scanner(System.in);
        String fileName;

        while (true) {

            System.out.print(">>> ");

            try {
                fileName = fileNameScanner.nextLine();
                fileName = fileName.strip();
                if (fileName.endsWith(".txt")) {
                    break;
                }
                else {
                    System.out.println("Invalid input.");
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input.");
            }

        }

        try (PrintWriter out = new PrintWriter(new FileWriter(fileName))) {
            out.printf("%d solution(s) found:\n", this.solutions.size());
            for (String solution : this.solutions) {
                out.println(solution);
            }
        }
        catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Prints the execution time of the brute force algorithm
     * used to find all possible solutions to the puzzle.
     */
    void printExecutionTime() {
        System.out.printf("Execution time: %.7f seconds\n", this.executionTime);
    }
}
