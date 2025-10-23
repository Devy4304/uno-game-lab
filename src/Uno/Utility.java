package Uno;

import java.awt.*;

public class Utility {
    public static class Console {
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
         * Writes a text-based user interface (TUI) styled box to the console using ASCII
         * box-drawing characters. The text is displayed as separate lines inside the box.
         * Additional options allow for seamless continuation of boxes above or below.
         *
         * @param innerTextSplit an array of strings representing the lines of text to display inside the box.
         *                       Each element corresponds to a separate line inside the box.
         * @param width the total width of the box, including borders. The text within the box
         *              will be aligned and cropped if it exceeds the specified width.
         * @param isBoxBelow a flag indicating whether the box below this section should continue
         *                   the border. When true, connectors will be used on the lower border.
         * @param isBoxAbove a flag indicating whether the box above this section should continue
         *                   the border. When true, connectors will be used on the upper border.
         */
        public static void writeTUIBox(String[] innerTextSplit, int width, boolean isBoxBelow, boolean isBoxAbove) {
            // Make the top bar, with or without connectors on top
            if (!isBoxAbove) System.out.println("╔" + repeatString("═", width - 2) + "╗");
            else System.out.println("╠" + repeatString("═", width - 2) + "╣");


            for (String text : innerTextSplit) {
                // Remove ANSI codes for length calculations
                String filteredText = text.replaceAll("\u001B\\[[;\\d]*m", "");
                // Get the length
                int visibleLength = filteredText.length();
                // Crop it if necessary (should never have to, hopefully)
                if (visibleLength > width - 4) {
                    String croppedText = getCroppedText(width, text);
                    filteredText = text.replaceAll("\u001B\\[[;\\d]*m", "");
                }
                // Print it out!
                System.out.println("║ " + text + Colors.RESET + repeatString(" ", width - 4 - filteredText.length()) + " ║");
            }
            // Make the bottom bar, with or without connectors on the bottom
            if (!isBoxBelow) System.out.println("╚" + repeatString("═", width - 2) + "╝");
        }

        /**
         * Writes a text-based user interface (TUI) styled box to the console,
         * formatted with ASCII box-drawing characters. This method simplifies
         * the usage by internally splitting the text into lines using a semicolon (;)
         * as a separator and delegates the rendering to the corresponding overloaded method.
         *
         * @param innerText the text to display inside the box. Semicolons can
         *                  separate multiple lines.
         * @param width the total width of the box, including borders. The text
         *              inside will be aligned and cropped if it exceeds this width.
         * @param isBoxBelow a flag indicating whether the box below this section
         *                   should continue the border.
         * @param isBoxAbove a flag indicating whether the box above this section
         *                   should continue the border.
         */
        public static void writeTUIBox(String innerText, int width, boolean isBoxBelow, boolean isBoxAbove) {
            writeTUIBox(innerText.split(";"), width, isBoxBelow, isBoxAbove);
        }

        /**
         * Crops the input text to fit within the specified width,
         * while handling special ANSI escape codes to maintain 
         * text formatting. The cropped result will be limited to
         * ensure the final text's visible portion is within the 
         * given width.
         *
         * @param width the maximum number of visible characters allowed for the text
         * @param text the input text to be cropped, which may include ANSI escape codes
         * @return a cropped version of the input text that fits within the specified width
         */
        private static String getCroppedText(int width, String text) {

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
            while (i < text.length() && currentLength < width - 4) {
                // Adds it to the output without increasing the currentLength if it's an ansi code
                if (text.charAt(i) == '\u001B') { // ANSI escape character, not a String
                    int codeEnd = text.indexOf('m', i); // Find where the ANSI code ends
                    if (codeEnd != -1) { // If it ends, append only the ANSI code
                        croppedText.append(text, i, codeEnd + 1);
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


        private static String repeatString(String string, int numberOfRepeats) {
            return string.repeat(numberOfRepeats);
        }
    }
}
