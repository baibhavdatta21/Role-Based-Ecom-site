package Spb.Ecom.utils;

public class KMPAlgorithm {
	private static void computeLPSArray(String pattern, int[] lps) {
        int m = pattern.length();
        int len = 0; // Length of the previous longest prefix suffix
        int i = 1;

        lps[0] = 0; // LPS of the first character is always 0

        while (i < m) {
            if (pattern.charAt(i) == pattern.charAt(len)) {
                len++;
                lps[i] = len;
                i++;
            } else {
                if (len != 0) {
                    len = lps[len - 1];
                } else {
                    lps[i] = 0;
                    i++;
                }
            }
        }
    }

    // KMP algorithm to find the first occurrence of the pattern in the text
    public static int kmpSearch(String text, String pattern) {
        int n = text.length();
        int m = pattern.length();

        // Edge case: If the pattern is empty
        if (m == 0) {
            return 0;
        }

        int[] lps = new int[m];
        computeLPSArray(pattern, lps);

        int i = 0; // Index for the text
        int j = 0; // Index for the pattern

        while (i < n) {
            if (text.charAt(i) == pattern.charAt(j)) {
                i++;
                j++;
            }

            if (j == m) {
                return i - j; // Pattern found at index (i - j)
            } else if (i < n && text.charAt(i) != pattern.charAt(j)) {
                // Mismatch after j matches
                if (j != 0) {
                    j = lps[j - 1];
                } else {
                    i++;
                }
            }
        }

        return -1; // Pattern not found
    }
}
