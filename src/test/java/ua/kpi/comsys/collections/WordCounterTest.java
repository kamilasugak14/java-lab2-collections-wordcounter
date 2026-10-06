package ua.kpi.comsys.collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotSame;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WordCounterTest {

    private WordCounter counter;

    @BeforeEach
    void setUp() {
        counter = new WordCounter();
    }

    @Nested
    class CountWords {

        @Test
        void countsRepeatedWordsAcrossLines() {
            Map<String, Integer> result = counter.countWords(List.of("Hello world", "hello again"));
            assertEquals(Map.of("hello", 2, "world", 1, "again", 1), result);
        }

        @Test
        void isCaseInsensitive() {
            assertEquals(Map.of("java", 3), counter.countWords(List.of("Java JAVA java")));
        }

        @Test
        void ignoresPunctuation() {
            Map<String, Integer> result = counter.countWords(List.of("Hello, world! Hello."));
            assertEquals(Map.of("hello", 2, "world", 1), result);
        }

        @Test
        void keepsDigitsInsideWords() {
            Map<String, Integer> result = counter.countWords(List.of("abc123 123 abc"));
            assertEquals(Map.of("abc123", 1, "123", 1, "abc", 1), result);
        }

        @Test
        void ignoresLeadingAndTrailingSeparators() {
            assertEquals(Map.of("word", 1), counter.countWords(List.of("  ...word...  ")));
        }

        @Test
        void splitsOnApostropheHyphenAndUnderscore() {
            Map<String, Integer> result = counter.countWords(List.of("don't well-known snake_case"));
            assertEquals(Map.of("don", 1, "t", 1, "well", 1, "known", 1, "snake", 1, "case", 1), result);
        }

        @Test
        void treatsNonLatinLettersAsSeparators() {
            assertEquals(Map.of("world", 1), counter.countWords(List.of("Привіт world")));
            assertEquals(Map.of("caf", 1), counter.countWords(List.of("café")));
        }

        @ParameterizedTest
        @ValueSource(strings = {"", " ", "   ", "!!! ???", "---", "Привіт світ"})
        void returnsEmptyMapWhenNoWords(String line) {
            assertTrue(counter.countWords(List.of(line)).isEmpty());
        }

        @Test
        void returnsEmptyMapForEmptyList() {
            assertTrue(counter.countWords(List.of()).isEmpty());
        }

        @Test
        void skipsNullLines() {
            Map<String, Integer> result = counter.countWords(Arrays.asList("a", null, "b a"));
            assertEquals(Map.of("a", 2, "b", 1), result);
        }

        @Test
        void returnsNewMapOnEachCall() {
            List<String> lines = List.of("one two");
            Map<String, Integer> first = counter.countWords(lines);
            first.put("three", 3);
            Map<String, Integer> second = counter.countWords(lines);
            assertNotSame(first, second);
            assertEquals(Map.of("one", 1, "two", 1), second);
        }

        @Test
        void throwsOnNullList() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> counter.countWords(null));
            assertEquals("textLines cannot be null", ex.getMessage());
        }
    }

    @Nested
    class GetUniqueWords {

        @Test
        void returnsUniqueWordsInLowerCase() {
            Set<String> result = counter.getUniqueWords(List.of("Hello World", "hello again"));
            assertEquals(Set.of("hello", "world", "again"), result);
        }

        @Test
        void collapsesDuplicates() {
            assertEquals(Set.of("a"), counter.getUniqueWords(List.of("a A a", "A")));
        }

        @Test
        void ignoresPunctuationAndNonLatin() {
            assertEquals(Set.of("test", "x1"), counter.getUniqueWords(List.of("Test, тест! x1.")));
        }

        @Test
        void returnsEmptySetForEmptyInput() {
            assertTrue(counter.getUniqueWords(List.of()).isEmpty());
            assertTrue(counter.getUniqueWords(List.of("", "  ", "...")).isEmpty());
        }

        @Test
        void skipsNullLines() {
            assertEquals(Set.of("a", "b"), counter.getUniqueWords(Arrays.asList("a", null, "b")));
        }

        @Test
        void throwsOnNullList() {
            IllegalArgumentException ex = assertThrows(IllegalArgumentException.class,
                    () -> counter.getUniqueWords(null));
            assertEquals("textLines cannot be null", ex.getMessage());
        }
    }
}
