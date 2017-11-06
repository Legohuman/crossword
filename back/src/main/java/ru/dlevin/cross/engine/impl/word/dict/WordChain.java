package ru.dlevin.cross.engine.impl.word.dict;

import org.apache.commons.lang3.Validate;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import ru.dlevin.cross.engine.api.word.Word;
import ru.dlevin.cross.utils.WordUtils;

import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

/**
 * User: Legohuman
 * Date: 19/10/15
 */
public class WordChain {
    @NotNull
    private final LetterNode[][] startNodes;
    @NotNull
    private final LetterNode[][] endNodes;
    @NotNull
    private final NavigableMap<Word, WordNode> nodes = new TreeMap<>();

    public WordChain(int letterCount) {
        Validate.isTrue(letterCount > 0, "Letter count in word is expected to be more than zero.");
        this.endNodes = new LetterNode[letterCount][32];
        this.startNodes = new LetterNode[letterCount][32];
    }

    boolean add(@NotNull Word word) {
        String text = word.getText();
        WordUtils.checkValidRussianWord(text);

        WordNode wordNode = new WordNode(text);
        boolean added = nodes.put(word, wordNode) == null;
        boolean isLastEntry = nodes.size() == 1 || wordNode.equals(nodes.lastEntry().getValue());
        boolean isFirstEntry = !isLastEntry && wordNode.equals(nodes.firstEntry().getValue());

        if (added) {
            for (int i = 0; i < text.length(); i++) {
                LetterNode letterNode = wordNode.letter(i);
                char c = text.charAt(i);
                int ci = WordUtils.relativeCharIndex(c);
                Validate.isTrue(ci >= 0, "Unexpected negative char index came from letter " + c + " in word " + text);

                if (isLastEntry) {
                    LetterNode endNode = getEndNode(i, ci);
                    if (endNode != null) {
                        endNode.next = letterNode;
                    } else {
                        setStartNode(i, ci, letterNode);
                    }
                    setEndNode(i, ci, letterNode);

                } else if (isFirstEntry) {
                    LetterNode startNode = getStartNode(i, ci);
                    if (startNode != null) {
                        letterNode.next = startNode;
                    } else {
                        setEndNode(i, ci, letterNode);
                    }
                    setStartNode(i, ci, letterNode);
                } else {
                    LetterNode prevNode = null;
                    LetterNode currentNode = getStartNode(i, ci);
                    if (currentNode == null) {
                        setStartNode(i, ci, letterNode);
                    }

                    while (currentNode != null && currentNode.word.letterSum() < letterNode.word.letterSum()) {
                        prevNode = currentNode;
                        currentNode = currentNode.next;
                    }

                    if (prevNode != null) {
                        prevNode.next = letterNode;
                        if (currentNode != null) {
                            letterNode.next = currentNode;
                        }
                    } else {
                        setEndNode(i, ci, letterNode);
                    }
                }
            }
        }
        return added;
    }

    boolean remove(@NotNull Word word) {
        return nodes.remove(word) != null; //todo remove letter nodes
    }

    int size() {
        return nodes.size();
    }

    @Nullable
    LetterNode getStartNode(int letterIndex, int relativeCharIndex) {
        return startNodes[letterIndex][relativeCharIndex];
    }

    void setStartNode(int letterIndex, int relativeCharIndex, @NotNull LetterNode node) {
        startNodes[letterIndex][relativeCharIndex] = node;
    }

    @Nullable
    LetterNode getEndNode(int letterIndex, int relativeCharIndex) {
        return endNodes[letterIndex][relativeCharIndex];
    }

    void setEndNode(int letterIndex, int relativeCharIndex, @NotNull LetterNode node) {
        endNodes[letterIndex][relativeCharIndex] = node;
    }

    @Nullable
    WordNode firstWord() {
        Map.Entry<Word, WordNode> entry = nodes.firstEntry();
        return entry == null ? null : entry.getValue();
    }

    @Nullable
    WordNode nextWord(@NotNull WordNode referenceWord) {
        Map.Entry<Word, WordNode> entry = nodes.higherEntry(referenceWord.word());
        return entry == null ? null : entry.getValue();
    }

    @Nullable
    WordNode getWordNode(@NotNull Word word) {
        return nodes.get(word);
    }
}
