package Uno;

import java.util.Scanner;

/**
 * Utility class provides a set of static methods and constants
 * that can be commonly used across different areas of an application.
 * It serves as a helper class, grouped with utility functions
 * designed to perform specific, reusable tasks.
 * <p>
 * This class cannot be instantiated, and all methods are intended
 * to be accessed in a static manner.
 * <p>
 * The class does not maintain any state or instance-level data.
 * <p>
 * Extends the Object class, inheriting its default behaviors.
 */
public class Utility {
    public static class Console {
        /**
         * The Colors class provides a collection of ANSI escape codes for text
         * formatting, including colors and backgrounds, in supported terminal environments.
         * These constants can be used to modify text appearance in the console,
         * such as changing text colors, background colors, or resetting formatting.
         * <p>
         * The behavior of these codes depends on the terminal's support for ANSI escape codes
         * and may not function in non-compliant environments.
         * <p>
         * Text color constants:
         * - BLACK, RED, GREEN, YELLOW, BLUE, PURPLE, CYAN, WHITE
         * <p>
         * Background color constants:
         * - BLACK_BACKGROUND, RED_BACKGROUND, GREEN_BACKGROUND,
         *   YELLOW_BACKGROUND, BLUE_BACKGROUND, PURPLE_BACKGROUND,
         *   CYAN_BACKGROUND, WHITE_BACKGROUND
         * <p>
         * Use RESET to reset formatting back to the default state.
         */
        public static class Colors {
            public static final String RESET = "\u001B[0m";
            public static final String BLACK = "\u001B[30m";
            public static final String RED = "\u001B[31m";
            public static final String GREEN = "\u001B[32m";
            public static final String YELLOW = "\u001B[33m";
            public static final String BLUE = "\u001B[34m";
            public static final String PURPLE = "\u001B[35m";
            public static final String CYAN = "\u001B[36m";
            public static final String WHITE = "\u001B[37m";
            // Background colors
            public static final String BLACK_BACKGROUND = "\u001B[40m";
            public static final String RED_BACKGROUND = "\u001B[41m";
            public static final String GREEN_BACKGROUND = "\u001B[42m";
            public static final String YELLOW_BACKGROUND = "\u001B[43m";
            public static final String BLUE_BACKGROUND = "\u001B[44m";
            public static final String PURPLE_BACKGROUND = "\u001B[45m";
            public static final String CYAN_BACKGROUND = "\u001B[46m";
            public static final String WHITE_BACKGROUND = "\u001B[47m";
        }

        private static final int boxWidth = 40;
        private static final Scanner scanner = new Scanner(System.in);
        private static boolean canDisplayANSICodes = true;
        private static boolean hasCheckedForANSI = false;

        /**
         * Checks if the console supports ANSI escape codes and updates the internal state.
         * <p>
         * This method prompts the user to determine whether ANSI color codes are
         * displayed correctly in their terminal. It displays a test message in green text
         * and asks the user to confirm its appearance. The result of the user's input is
         * stored in a class-level flag to indicate whether ANSI support is available.
         * <p>
         * The check is performed only once during the runtime to avoid redundant prompts.
         * Later calls to this method will not prompt the user again if the check
         * has already been completed.
         */
        public static void checkForANSI() {
            if (!hasCheckedForANSI) {
                Utility.Console.writeTUIBox("ANSI SUPPORT CHECK;" + "-".repeat(Utility.Console.getBoxWidth() - 4) + ";Is the following text green?;" + Utility.Console.Colors.GREEN + "HOPEFULLY GREEN TEXT" + Utility.Console.Colors.RESET + ";1) Yes;2) No", false, false);
                canDisplayANSICodes = (Utility.Console.getNumericalInput(1, 2) == 1);
                hasCheckedForANSI = true;
            }
        }

        /**
         * Determines whether the current console environment supports ANSI escape codes.
         * This method relies on an internal class-level flag that tracks if ANSI codes
         * can be displayed in the terminal. The flag is updated based on user confirmation
         * during an initial check performed by the `checkForANSI` method.
         *
         * @return true if the console supports ANSI escape codes; false otherwise
         */
        public static boolean supportsANSICodes() {
            return canDisplayANSICodes;
        }

        /**
         * Retrieves the width of the box used for console-based text formatting or layout.
         *
         * @return the width of the box as an integer
         */
        public static int getBoxWidth() {
            return boxWidth;
        }

        /**
         * Clears the console screen by printing ANSI escape codes.
         * This method resets the cursor position to the top-left corner of the console
         * and clears all visible text. It uses `System.out.print` to send the escape
         * sequences and `System.out.flush` to ensure immediate execution.
         * <p>
         * Note: This method is platform-dependent and works primarily in terminals
         * that support ANSI escape codes. It may not function as expected in certain
         * IDE terminals or non-ANSI-compliant environments.
         */
        public static void clear() {
            for (int i = 0; i < 50; i++) {
                System.out.println();
            }
        }

        /**
         * Prompts the user to input a numerical value within the specified range.
         * Continues to prompt until a valid input is provided.
         *
         * @param min the minimum acceptable value (inclusive)
         * @param max the maximum acceptable value (inclusive)
         * @return the valid numerical input provided by the user
         */
        public static int getNumericalInput(int min, int max) {
            int input = 0;
            do {
                System.out.print("  => ");
                input = scanner.nextInt();
            } while (input < min || input > max);
            return input;
        }

        /**
         * Prompts the user for string input via the console and returns the entered value.
         * The method ensures that any residual input in the scanner's buffer is cleared
         * before capturing the new input. It also provides a prompt symbol to indicate
         * that user input is expected.
         *
         * @return the string entered by the user
         */
        public static String getStringInput() {
            System.out.print("\n  => ");
            scanner.nextLine(); // Clear buffer
            String in = scanner.nextLine();
            System.out.println();
            return in;
        }

        /**
         * Prompts the user to enter their username via the console.
         * Displays a formatted message asking for the username and waits for user input.
         *
         * @return the username entered by the user as a string
         */
        public static String askForUsername() {
            writeTUIBox("What is your username?", false, false);
            return getStringInput();
        }

        /**
         * Prompts the user to select a wild card color from a predefined set of options
         * (Red, Yellow, Green, Blue) and returns the chosen color.
         * This method displays a menu of color choices, waits for numerical input within
         * the valid range, and maps the input to its corresponding color. If an invalid
         * or out-of-bounds input is provided, the method defaults to returning the Red color.
         *
         * @return the selected wildcard color as one of the enumerated values in {@code Card.Colors}
         */
        public static Card.Colors askForWildColor() {
            writeTUIBox("What color do you want?;" +
                    Colors.RESET + "1) " + Colors.RED+"Red;" +
                    Colors.RESET + "2) " + Colors.YELLOW+"Yellow;" +
                    Colors.RESET + "3) " + Colors.GREEN+"Green;" +
                    Colors.RESET + "4) " + Colors.BLUE+"Blue" +
                    Colors.RESET
                    ,false, false
            );

            switch (getNumericalInput(1, 4)) {
                case 2 -> {
                    return Card.Colors.YELLOW;
                }
                case 3 -> {
                    return Card.Colors.GREEN;
                }
                case 4 -> {
                    return  Card.Colors.BLUE;
                }
                default -> {
                    return Card.Colors.RED; // Defaults to red if somehow it is not in-bounds, 1 option is also covered here
                }
            }
        }

        /**
         * Writes a text-based User Interface (TUI) box to the console, formatting the
         * input text within a bordered box structure. The method optionally allows
         * for connector-style linking with a box above or below the current box.
         *
         * @param innerTextSplit an array of strings, where each element represents a line of text
         *                       to be displayed inside the box
         * @param isBoxBelow     a boolean indicating whether the box has a connector at the bottom
         *                       linking it to another box below
         * @param isBoxAbove     a boolean indicating whether the box has a connector at the top
         *                       linking it to another box above
         */
        public static void writeTUIBox(String[] innerTextSplit, boolean isBoxBelow, boolean isBoxAbove) {
            // Make the top bar, with or without connectors on top
            if (!isBoxAbove) System.out.println("╔" + repeatString("═", boxWidth - 2) + "╗");
            else System.out.println("╠" + repeatString("═", boxWidth - 2) + "╣");


            for (String text : innerTextSplit) {
                // Remove ANSI codes for length calculations
                String filteredText = text.replaceAll("\u001B\\[[;\\d]*m", "");
                // Get the length
                int visibleLength = filteredText.length();
                // Crop it if necessary (should never have to, hopefully)
                if (visibleLength > boxWidth - 4) {
                    filteredText = text.replaceAll("\u001B\\[[;\\d]*m", "");
                }
                String croppedText = getCroppedText(text);
                // Print it out!
                System.out.println("║ " + croppedText + Colors.RESET + repeatString(" ", boxWidth - 4 - filteredText.length()) + " ║");
            }
            // Make the bottom bar, with or without connectors on the bottom
            if (!isBoxBelow) System.out.println("╚" + repeatString("═", boxWidth - 2) + "╝");
        }

        /**
         * Writes a text-based User Interface (TUI) box to the console, formatting the input text
         * within a bordered box structure. This method simplifies the process by accepting a single
         * string as input, which is split into multiple lines for display within the box. The method
         * also allows for optional connections to a box above or below the current box.
         *
         * @param innerText  a semicolon-separated string where each segment represents a line of text
         *                   to be displayed inside the box
         * @param isBoxBelow a boolean indicating whether the box has a connector at the bottom
         *                   linking it to another box below
         * @param isBoxAbove a boolean indicating whether the box has a connector at the top
         *                   linking it to another box above
         */
        public static void writeTUIBox(String innerText, boolean isBoxBelow, boolean isBoxAbove) {
            writeTUIBox(innerText.split(";"), isBoxBelow, isBoxAbove);
        }

        /**
         * Crops the input text to fit within the specified width,
         * while handling special ANSI escape codes to maintain
         * text formatting. The cropped result will be limited to
         * ensure the final text's visible portion is within the
         * given width.
         *
         * @param text the input text to be cropped, which may include ANSI escape codes
         * @return a cropped version of the input text that fits within the specified width
         */
        private static String getCroppedText(String text) {

            /*
                NOTE: I did get a little help from AI here,
                but I understand it fully and have added some
                comments below to explain it. My previous idea
                that I had was a custom escape code translation
                system using the § symbol (which I did fully code).
                However, that was a bad idea, so I went back to
                standard ANSI escape codes and used a little AI to
                get the code working. Thanks, IntelliJ.
             */

            StringBuilder croppedText = new StringBuilder();
            int currentLength = 0; // Current VISIBLE text length (non-ANSI code)
            int i = 0; // Index for iterating through the text

            // Run until it's the right length or done with the text
            while (i < text.length() && currentLength < Console.boxWidth - 4) {
                // Adds it to the output without increasing the currentLength if it's an ansi code
                if (text.charAt(i) == '\u001B') { // ANSI escape character, not a String
                    int codeEnd = text.indexOf('m', i); // Find where the ANSI code ends
                    if (codeEnd != -1) { // If it ends, append only the ANSI code
                        if (canDisplayANSICodes) croppedText.append(text, i, codeEnd + 1);
                        i = codeEnd + 1; // Make the index where the ANSI code ends
                        continue; // Finish this loop cycle early
                    }
                }
                croppedText.append(text.charAt(i)); // Append the letter if it's not a ANSI escape code
                currentLength++; // Increment currentLength
                i++; // Increment Index
            }
            return croppedText.toString(); // Return the output
        }

        /**
         * Repeats the given string a specified number of times. If the number of repeats
         * is zero or negative, an empty string is returned.
         *
         * @param string the string to be repeated
         * @param numberOfRepeats the number of times the string should be repeated;
         *                        must be a non-negative integer
         * @return a new string containing the repeated input string the specified
         *         number of times, or an empty string if the number of repeats is zero
         *         or negative
         */
        public static String repeatString(String string, int numberOfRepeats) {
            if (numberOfRepeats > 0) {
                return string.repeat(numberOfRepeats);
            } else {
                return "";
            }
        }
    }
}
