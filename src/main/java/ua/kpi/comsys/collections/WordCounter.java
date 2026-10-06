package ua.kpi.comsys.collections;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Частотний аналізатор тексту.
 *
 * <p>Слово - це послідовність буквено-цифрових символів латиниці
 * ({@code a-z}, {@code A-Z}, {@code 0-9}). Усі інші символи вважаються
 * роздільниками. Слова приводяться до нижнього регістру.
 */
public class WordCounter {

    private static final Pattern SEPARATORS = Pattern.compile("[^a-zA-Z0-9]+");

    /**
     * Підраховує кількість входжень кожного слова.
     *
     * @param textLines рядки тексту
     * @return відображення «слово - кількість входжень»
     * @throws IllegalArgumentException якщо textLines == null
     */
    public Map<String, Integer> countWords(List<String> textLines) {
        Map<String, Integer> counts = new LinkedHashMap<>();
        for (String word : extractWords(textLines)) {
            counts.merge(word, 1, Integer::sum);
        }
        return counts;
    }

    /**
     * Повертає множину унікальних слів.
     *
     * @param textLines рядки тексту
     * @return множина унікальних слів у нижньому регістрі
     * @throws IllegalArgumentException якщо textLines == null
     */
    public Set<String> getUniqueWords(List<String> textLines) {
        return new LinkedHashSet<>(extractWords(textLines));
    }

    private List<String> extractWords(List<String> textLines) {
        if (textLines == null) {
            throw new IllegalArgumentException("textLines cannot be null");
        }
        List<String> words = new ArrayList<>();
        for (String line : textLines) {
            if (line == null) {
                continue;
            }
            for (String token : SEPARATORS.split(line)) {
                if (!token.isEmpty()) {
                    words.add(token.toLowerCase(Locale.ROOT));
                }
            }
        }
        return words;
    }
}
