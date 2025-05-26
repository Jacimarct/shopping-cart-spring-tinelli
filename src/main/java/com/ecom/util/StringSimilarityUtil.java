package com.ecom.util;

/* public class StringSimilarityUtil {

    public static int levenshteinDistance(String s1, String s2) {
        int[][] dp = new int[s1.length() + 1][s2.length() + 1];

        for (int i = 0; i <= s1.length(); i++) {
            for (int j = 0; j <= s2.length(); j++) {
                if (i == 0) {
                    dp[i][j] = j;
                } else if (j == 0) {
                    dp[i][j] = i;
                } else if (s1.charAt(i - 1) == s2.charAt(j - 1)) {
                    dp[i][j] = dp[i - 1][j - 1];
                } else {
                    dp[i][j] = 1 + Math.min(
                        Math.min(dp[i - 1][j], dp[i][j - 1]),
                        dp[i - 1][j - 1]
                    );
                }
            }
        }

        return dp[s1.length()][s2.length()];
    }

    public static double similarity(String s1, String s2) {
        int maxLen = Math.max(s1.length(), s2.length());
        if (maxLen == 0) return 1.0;
        return 1.0 - ((double) levenshteinDistance(s1, s2) / maxLen);
    }
}
*/
public class StringSimilarityUtil {
    public static double similarity(String s1, String s2) {
        if (s1 == null || s2 == null) return 0.0;
        int maxLength = Math.max(s1.length(), s2.length());
        if (maxLength == 0) return 1.0;
        int matchCount = 0;
        for (int i = 0; i < Math.min(s1.length(), s2.length()); i++) {
            if (s1.charAt(i) == s2.charAt(i)) matchCount++;
        }
        return (double) matchCount / maxLength;
    }
}

