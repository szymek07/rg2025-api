package pl.sp6pat.ham.rg.utils;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class SimilarPatternMatch {

    private static final Map<Character, Set<Character>> SIMILAR_MAP = new HashMap<>();

    static {
        SIMILAR_MAP.put('o', Set.of('o', '0'));
        SIMILAR_MAP.put('0', Set.of('o', '0'));
        SIMILAR_MAP.put('l', Set.of('l', '1'));
        SIMILAR_MAP.put('i', Set.of('l', '1'));
        SIMILAR_MAP.put('s', Set.of('s', '5'));
        SIMILAR_MAP.put('z', Set.of('z', '2'));
        SIMILAR_MAP.put('b', Set.of('b', '8'));
    }

    public static boolean matchesPattern(String pattern, String text) {
        if (pattern == null || text == null) {
            return pattern == text;
        }
        if (pattern.length() != text.length()) {
            return false;
        }

        for (int i = 0; i < pattern.length(); i++) {
            char pc = Character.toLowerCase(pattern.charAt(i));
            char tc = Character.toLowerCase(text.charAt(i));

            if (SIMILAR_MAP.containsKey(pc)) {
                if (!SIMILAR_MAP.get(pc).contains(tc)) {
                    return false;
                }
            } else {
                if (pc != tc) {
                    return false;
                }
            }
        }
        return true;
    }

}
