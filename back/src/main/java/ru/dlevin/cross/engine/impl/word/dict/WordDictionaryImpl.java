package ru.dlevin.cross.engine.impl.word.dict;

import org.jetbrains.annotations.NotNull;
import ru.dlevin.cross.engine.api.word.Word;
import ru.dlevin.cross.engine.api.word.WordPattern;
import ru.dlevin.cross.engine.api.word.dict.WordDictionary;

import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class WordDictionaryImpl implements WordDictionary {

    private static final char minLetter = 'а';
    private static final char maxLetter = 'я';
    static final char dictionarySpan = maxLetter - minLetter + 1;

    private final Map<Integer, WordChain> chains = new HashMap<>();
    private int wordsCount = 0;

    @Override
    public boolean addWord(@NotNull Word word) {
        int wordLength = word.getLength();
        WordChain chain = chains.computeIfAbsent(wordLength, k -> new WordChain(wordLength));
        boolean added = chain.add(word);
        if (added) {
            wordsCount++;
        }
        return added;
    }

    @Override
    public int size() {
        return wordsCount;
    }

    @Override
    public int size(int letterCount) {
        WordChain chain = chains.get(letterCount);
        return chain == null ? 0 : chain.size();
    }

    @Override
    public boolean removeWord(@NotNull Word word) {
        WordChain chain = chains.get(word.getLength());
        boolean deleted = chain != null && chain.remove(word);
        if (deleted) {
            wordsCount--;
        }
        return deleted;
    }

    @NotNull
    @Override
    public Iterator<Word> search(@NotNull WordPattern pattern) {
        WordChain chain = chains.get(pattern.getLength());
        return chain == null ? Collections.emptyIterator() : new WordChainIterator(chain, pattern);
    }
}
