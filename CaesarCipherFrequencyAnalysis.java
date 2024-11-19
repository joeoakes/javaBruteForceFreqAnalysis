import java.util.*;

public class CaesarCipherFrequencyAnalysis {

    // English letter frequencies
    private static final double[] ENGLISH_FREQUENCIES = {
        8.167, 1.492, 2.782, 4.253, 12.702, 2.228, 2.015, 6.094, 
        6.966, 0.153, 0.772, 4.025, 2.406, 6.749, 7.507, 1.929, 
        0.095, 5.987, 6.327, 9.056, 2.758, 0.978, 2.360, 0.150, 
        1.974, 0.074
    };

    public static void main(String[] args) {
        String ciphertext = "Khoor Zruog"; // Example encrypted text
        String bestGuess = decryptUsingFrequencyAnalysis(ciphertext);
        System.out.println("Decrypted text (best guess): " + bestGuess);
    }

    // Method to decrypt using frequency analysis
    public static String decryptUsingFrequencyAnalysis(String ciphertext) {
        String bestDecryption = "";
        double lowestChiSquare = Double.MAX_VALUE;

        for (int shift = 0; shift < 26; shift++) {
            String decryptedText = decryptWithShift(ciphertext, shift);
            double chiSquare = calculateChiSquare(decryptedText);

            if (chiSquare < lowestChiSquare) {
                lowestChiSquare = chiSquare;
                bestDecryption = decryptedText;
            }
        }

        return bestDecryption;
    }

    // Method to decrypt text with a specific shift
    public static String decryptWithShift(String text, int shift) {
        StringBuilder decryptedText = new StringBuilder();

        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char base = Character.isUpperCase(c) ? 'A' : 'a';
                decryptedText.append((char) ((c - base - shift + 26) % 26 + base));
            } else {
                decryptedText.append(c); // Keep non-alphabetic characters unchanged
            }
        }

        return decryptedText.toString();
    }

    // Method to calculate Chi-Square statistic for frequency analysis
    public static double calculateChiSquare(String text) {
        int[] letterCounts = new int[26];
        int totalLetters = 0;

        // Count letter occurrences
        for (char c : text.toCharArray()) {
            if (Character.isLetter(c)) {
                char lowerCaseChar = Character.toLowerCase(c);
                letterCounts[lowerCaseChar - 'a']++;
                totalLetters++;
            }
        }

        // Calculate Chi-Square statistic
        double chiSquare = 0.0;

        for (int i = 0; i < 26; i++) {
            double observed = letterCounts[i];
            double expected = totalLetters * ENGLISH_FREQUENCIES[i] / 100;
            chiSquare += Math.pow(observed - expected, 2) / expected;
        }

        return chiSquare;
    }
}
