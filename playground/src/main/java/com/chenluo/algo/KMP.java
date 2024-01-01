package com.chenluo.algo;

import java.util.Arrays;
import java.util.stream.Collectors;

public class KMP {
    public static void main(String[] args) {
        String text = "asdfdvadfgdsfg";
        String pattern = "dfd"; // lps = [0,0,1]

        KMP kmp = new KMP();
        System.out.println(kmp.find(pattern, text));
        System.out.println(kmp.find("aaaa", "aaaaaaaaaaaaaaaab"));
        System.out.println(kmp.find("issip", "mississippi"));
        System.out.println(kmp.find("adcadde", "adcadcaddcadde"));

    }

    private int find(String pattern, String text) {
        if (pattern.isEmpty()) {
            return 0;
        }
        int[] lps = constructLongestMatchPrefixAndSuffix(pattern);
        int posInText = 0;
        int posInPattern = 0;
        while (posInText < text.length()) {
            if (text.charAt(posInText) == pattern.charAt(posInPattern)) {
                posInText++;
                posInPattern++;
            } else {
                if (posInPattern != 0) {
                    // aabbaa -> 0,1,0,0,1,2
                    // if aabbac, it doesn't match at last position
                    // and lps is 1, so we can say, at least we can restart from a,
                    // because the text and pattern agree on aabba and also match a
                    // potentially, the start position of match is updated with diff posInPattern - lps[posInPattern-1]
                    posInPattern = lps[posInPattern - 1];
                } else {
                    // no matched char in pattern
                    // so no above shortcut
                    // have to increase by 1.
                    posInText++;
                }
            }
            if (posInPattern == pattern.length()) {
                return posInText - pattern.length();
            }
        }

        return -1;
    }

    private int[] constructLongestMatchPrefixAndSuffix(String pattern) {
        int[] result = new int[pattern.length()];
        result[0] = 0;
        for (int i = 1; i < pattern.length(); i++) {
            result[i] = findLongestMatchPrefixAndSuffix(pattern, result, result[i - 1], i);

        }
        System.out.println(Arrays.stream(result).mapToObj(i -> String.valueOf(i)).collect(Collectors.toList()));

        return result;

    }

    private int findLongestMatchPrefixAndSuffix(String pattern, int[] lps, int lastLength, int curIdx) {
        if (pattern.charAt(lastLength) == pattern.charAt(curIdx)) {
            return lastLength + 1;
        } else {
            if (lastLength - 1 < 0) {
                return 0;
            }
            return findLongestMatchPrefixAndSuffix(pattern, lps, lps[lastLength - 1], curIdx);
        }

    }
}
