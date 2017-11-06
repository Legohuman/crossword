package ru.dlevin.cross.engine.impl.word.dict;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dlevin.cross.engine.api.word.Word;
import ru.dlevin.cross.engine.api.word.WordPattern;
import ru.dlevin.cross.engine.api.word.WordPatternLetter;
import ru.dlevin.cross.utils.WordUtils;

import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * User: Legohuman
 * Date: 09/05/16
 */
public class WordChainIterator implements Iterator<Word> {
    @NotNull
    private final WordPattern pattern;
    @NotNull
    private final WordChain chain;
    @NotNull
    private final List<WordPatternLetter> knownLetters;

    @Nullable
    private WordNode currentWord;
    private boolean specificWordFound;
    private boolean nextWordIsCurrent;

    WordChainIterator(@NotNull WordChain chain, @NotNull WordPattern pattern) {
        this.pattern = pattern;
        this.chain = chain;
        knownLetters = pattern.getKnownLetters();
    }

    @Override
    @NotNull
    public Word next() {
        if (!nextWordIsCurrent) {
            findNextWord();
        }
        nextWordIsCurrent = false; //Every next() invocation returns new next word, so flag is set to false
        if (currentWord == null) {
            throw new NoSuchElementException("Iterator has no more words");
        }
        return currentWord.word();

    }

    @Override
    public boolean hasNext() {
        if (!nextWordIsCurrent) {
            findNextWord();
        }
        return currentWord != null;
    }

    @NotNull
    private LetterNode[] currentLetterNodes() {
        LetterNode[] letterNodes = new LetterNode[knownLetters.size()];
        for (int i = 0; i < knownLetters.size(); i++) {
            WordPatternLetter letter = knownLetters.get(i);
            letterNodes[i] = currentWord == null ?
                    chain.getStartNode(letter.getIndex(), WordUtils.relativeCharIndex(letter.getValue())) :
                    currentWord.letter(letter.getIndex());
        }

        return letterNodes;
    }

    private void findNextWord() {
        if (knownLetters.isEmpty()) {
            findNextImmediateWord();
        } else if (pattern.allLettersKnown()) {
            findSpecificWord(pattern.toWord());
        } else {
            findNextWordByPattern();
        }
        nextWordIsCurrent = true;
    }

    private void findNextImmediateWord() {
        if (currentWord == null) {
            currentWord = chain.firstWord();
        } else {
            currentWord = chain.nextWord(currentWord);
        }
    }

    private void findSpecificWord(@NotNull Word word) {
        if (!specificWordFound) {
            currentWord = chain.getWordNode(word);
        } else {
            currentWord = null;
        }
        specificWordFound = true;
    }

    private void findNextWordByPattern() {
        WordNode maxCandidate = null;
        LetterNode[] curLetterNodes = currentLetterNodes();

        while (maxCandidate == null || !pattern.matchWord(maxCandidate.word())) {
            for (int i = 0; i < curLetterNodes.length; i++) {

                if (currentWord != null) {
                    curLetterNodes[i] = curLetterNodes[i] == null ? null : curLetterNodes[i].next;
                }

                LetterNode letterInWord = curLetterNodes[i];
                if (letterInWord != null) {
                    WordNode candidate = letterInWord.word;
                    if (maxCandidate == null || candidate.letterSum() > maxCandidate.letterSum()) {
                        maxCandidate = candidate;
                    }
                } else {
                    currentWord = null;
                    nextWordIsCurrent = true;
                    return;
                }

                curLetterNodes[i] = curLetterNodes[i].next;
            }
        }
        currentWord = maxCandidate;
    }

}
