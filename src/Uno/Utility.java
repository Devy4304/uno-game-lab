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
         * Writes a text-based user interface (TUI) styled box to the console, 
         * formatted with ASCII box-drawing characters.
         * The box encloses the provided text, which can be split into multiple lines
         * using a semicolon (;) as a separator.
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
            String[] innerTextSplit = innerText.split(";");
            if (!isBoxAbove) System.out.println("╔" + repeatString("═", width - 2) + "╗");
            else System.out.println("╠" + repeatString("═", width - 2) + "╣");
            for (String text : innerTextSplit) {
                String filteredText = text.replaceAll("\u001B\\[[;\\d]*m", ""); // Remove ANSI codes for length calculations
                int visibleLength = filteredText.length();
                if (visibleLength > width - 4) {
                    String croppedText = getCroppedText(width, text);
                    filteredText = text.replaceAll("\u001B\\[[;\\d]*m", "");
                }
                System.out.println("║ " + text + Colors.RESET + repeatString(" ", width - 4 - filteredText.length()) + " ║");
            }
            if (!isBoxBelow) System.out.println("╚" + repeatString("═", width - 2) + "╝");
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
            StringBuilder croppedText = new StringBuilder();
            int currentLength = 0;
            int i = 0;
            while (i < text.length() && currentLength < width - 4) {
                if (text.charAt(i) == '\u001B') {
                    int codeEnd = text.indexOf('m', i);
                    if (codeEnd != -1) {
                        croppedText.append(text, i, codeEnd + 1);
                        i = codeEnd + 1;
                        continue;
                    }
                }
                croppedText.append(text.charAt(i));
                currentLength++;
                i++;
            }
            return croppedText.toString();
        }


        private static String repeatString(String string, int numberOfRepeats) {
            return string.repeat(numberOfRepeats);
        }
    }
}
