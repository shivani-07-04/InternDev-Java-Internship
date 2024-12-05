
import java.util.Random;
import java.util.Scanner;

public class NumberGuessingGame {
    public static void main(String[] args) {
        // Generate a random number between 1 and 100
        Random random = new Random();
        int numberToGuess = random.nextInt(100) + 1;

        // Initialize the number of tries
        int numberOfTries = 0;

        // Create a Scanner object to read the user's input
        Scanner scanner = new Scanner(System.in);

        System.out.println("Welcome to the number guessing game!");
        System.out.println("I'm thinking of a number between 1 and 100.");
        System.out.println("Try to guess the number in as few attempts as possible.\n");

        while (true) {
            // Ask the user for their guess
            System.out.print("Enter your guess: ");
            int userGuess = scanner.nextInt();

            // Increment the number of tries
            numberOfTries++;

            // Check if the user's guess is correct
            if (userGuess == numberToGuess) {
                System.out.println("\nCongratulations! You've guessed the number in " + numberOfTries + " tries.");
                break;
            } else if (userGuess < numberToGuess) {
                System.out.println("Your guess is too low. Try again!");
            } else {
                System.out.println("Your guess is too high. Try again!");
            }
        }

        scanner.close();
    }
}
