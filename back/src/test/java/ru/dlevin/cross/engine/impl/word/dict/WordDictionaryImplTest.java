package ru.dlevin.cross.engine.impl.word.dict;

import org.jetbrains.annotations.NotNull;
import org.junit.Test;
import ru.dlevin.cross.engine.api.word.Word;
import ru.dlevin.cross.engine.impl.word.WordImpl;
import ru.dlevin.cross.engine.impl.word.WordPatternImpl;

import java.util.Iterator;
import java.util.stream.Stream;

import static org.junit.Assert.*;

public class WordDictionaryImplTest {
    private static final String word1 = "слава";
    private static final String word2 = "слива";
    private static final String word3 = "слово";

    @NotNull
    private WordDictionaryImpl dictionary = new WordDictionaryImpl();

    @Test
    public void testSizeEmpty() {
        assertEquals(0, dictionary.size());
    }

    @Test
    public void testDictionarySpane() {
        assertEquals(32, WordDictionaryImpl.dictionarySpan);
    }

    @Test
    public void testSizeWordAdded() {
        dictionary.addWord(new WordImpl(word3));
        assertEquals(1, dictionary.size());
        assertEquals(1, dictionary.size(5));
        assertEquals(0, dictionary.size(1));
    }

    @Test
    public void testSearchAfterSortedAddition() {
        addWords(word1, word2, word3);

        assertSampleSearchPreconditions();
        assertSearchResults();
    }

    @Test
    public void testSearchAfterUnsortedAddition() {
        addWords(word2, word3, word1);

        assertSampleSearchPreconditions();
        assertSearchResults();
    }

    @Test
    public void testSearchAfterUnsortedAddition2() {
        addWords(word1, word3, word2);

        assertSampleSearchPreconditions();
        assertSearchResults();
    }

    private void addWords(String... words) {
        for (String word : words) {
            dictionary.addWord(new WordImpl(word));
        }
    }

    @Test
    public void testSearchFullKnownPattern() {
        dictionary.addWord(new WordImpl(word1));
        dictionary.addWord(new WordImpl(word3));
        dictionary.addWord(new WordImpl(word2));

        assertSampleSearchPreconditions();

        Iterator<Word> iterator = dictionary.search(new WordPatternImpl(word1));
        assertFoundWords(iterator, word1);

        iterator = dictionary.search(new WordPatternImpl(word2));
        assertFoundWords(iterator, word2);

        iterator = dictionary.search(new WordPatternImpl(word3));
        assertFoundWords(iterator, word3);
    }

    private void assertSampleSearchPreconditions() {
        assertEquals(3, dictionary.size());
        assertEquals(3, dictionary.size(5));
        assertEquals(0, dictionary.size(1));
    }

    private void assertSearchResults() {
        Iterator<Word> iterator = dictionary.search(new WordPatternImpl("*л***"));
        assertFoundWords(iterator, word1, word2, word3);

        iterator = dictionary.search(new WordPatternImpl("****а"));
        assertFoundWords(iterator, word1, word2);

        iterator = dictionary.search(new WordPatternImpl("*****"));
        assertFoundWords(iterator, word1, word2, word3);

        iterator = dictionary.search(new WordPatternImpl("с*"));
        assertFoundWords(iterator);
    }

    private void assertFoundWords(Iterator<Word> iterator, String... words) {
        Stream.of(words).forEach(word -> {
            assertTrue(iterator.hasNext());
            assertEquals("Word " + word + " is expected to be next in iterator, but it was not", word, iterator.next().getText());
        });
        assertFalse(iterator.hasNext());
    }
}
