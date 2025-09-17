package pl.sp6pat.ham.rg.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
class SimilarPatternMatchTest {

    @Test
    void testZeroAndO() {
        assertTrue(SimilarPatternMatch.matchesPattern("hello", "hell0"));
        assertFalse(SimilarPatternMatch.matchesPattern("hello", "hella"));
    }

    @Test
    void testOneAndL() {
        assertTrue(SimilarPatternMatch.matchesPattern("life", "l1fe"));
        assertFalse(SimilarPatternMatch.matchesPattern("life", "li1e"));
    }

    @Test
    void testFiveAndS() {
        assertTrue(SimilarPatternMatch.matchesPattern("sample", "samp1e"));
        assertFalse(SimilarPatternMatch.matchesPattern("sample", "sampl5"));
    }

    @Test
    void testTwoAndZ() {
        assertTrue(SimilarPatternMatch.matchesPattern("buzz", "bu22"));
        assertFalse(SimilarPatternMatch.matchesPattern("buzz", "bus2"));
    }

    @Test
    void testEightAndB() {
        assertTrue(SimilarPatternMatch.matchesPattern("bob", "b08"));
        assertFalse(SimilarPatternMatch.matchesPattern("bob", "bo8b")); // długość inna
    }

    @Test
    void testDifferentLength() {
        assertFalse(SimilarPatternMatch.matchesPattern("hello", "helloo"));
    }

    @Test
    void testNulls() {
        assertTrue(SimilarPatternMatch.matchesPattern(null, null));
        assertFalse(SimilarPatternMatch.matchesPattern("abc", null));
    }

    @Test
    void testRegular() {
        assertTrue(SimilarPatternMatch.matchesPattern("awkh", "awkh"));
        assertFalse(SimilarPatternMatch.matchesPattern("awkh", "awkm"));
    }

    @Test
    void test0F() {
        assertTrue(SimilarPatternMatch.matchesPattern("0F", "0F"));
        assertTrue(SimilarPatternMatch.matchesPattern("0f", "Of"));
        assertTrue(SimilarPatternMatch.matchesPattern("0F", "0f"));
        assertTrue(SimilarPatternMatch.matchesPattern("0f", "0F"));

        assertTrue(SimilarPatternMatch.matchesPattern("0F", "OF"));
        assertTrue(SimilarPatternMatch.matchesPattern("0f", "Of"));
        assertTrue(SimilarPatternMatch.matchesPattern("0F", "Of"));
        assertTrue(SimilarPatternMatch.matchesPattern("0f", "OF"));

        assertTrue(SimilarPatternMatch.matchesPattern("0F", "oF"));
        assertTrue(SimilarPatternMatch.matchesPattern("0f", "of"));
        assertTrue(SimilarPatternMatch.matchesPattern("0F", "of"));
        assertTrue(SimilarPatternMatch.matchesPattern("0f", "oF"));
    }

}