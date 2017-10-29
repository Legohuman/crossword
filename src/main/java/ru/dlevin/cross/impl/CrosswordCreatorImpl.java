package ru.dlevin.cross.impl;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dlevin.cross.api.CrosswordCreationContext;
import ru.dlevin.cross.api.CrosswordCreationListener;
import ru.dlevin.cross.api.CrosswordCreator;
import ru.dlevin.cross.api.WordPlacement;
import ru.dlevin.cross.api.board.ContainerCoordinate;
import ru.dlevin.cross.api.board.CrosswordBoard;
import ru.dlevin.cross.api.board.WordContainer;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.api.word.WordPattern;
import ru.dlevin.cross.impl.word.WordPatternImpl;

import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;

public class CrosswordCreatorImpl implements CrosswordCreator {
    private static final Logger log = LoggerFactory.getLogger(CrosswordCreatorImpl.class);
    @NotNull
    private final Deque<WordPlacement> placements = new LinkedList<>();
    @NotNull
    private final Deque<WordContainer> containers = new LinkedList<>();
    @NotNull
    private final Deque<Iterator<Word>> iterators = new LinkedList<>();
    @Nullable
    private Iterator<Word> currentIterator;
    @Nullable
    private WordContainer currentContainer;

    /**
     * Use modified backtracing algorithm to find solutions
     */
    @Override
    public void create(@NotNull CrosswordCreationContext context, @NotNull CrosswordCreationListener listener) {
        CrosswordBoard board = context.getBoard();
        containers.addAll(board.getContainers());
        boolean takeNextContainer = true;
        log.debug("Starting search");
        int i = 0;

        while (i < 10000) {
            log.debug("Search iteration: " + ++i);

            if (takeNextContainer) {
                log.debug("Taking next container");
                currentContainer = containers.pollFirst();
                log.debug("Container is taken " + currentContainer);

                if (currentContainer == null) {
                    log.debug("Solution: " + placements);
                } else {
                    WordPattern pattern = getContainerPattern(currentContainer);
                    log.debug("Pattern to find: " + pattern);
                    currentIterator = context.getDictionary().search(pattern);
                }
            }

            log.debug("Searching next word");
            Word word = findNextWord();
            log.debug("Next word is " + word);
            if (word != null) {
                log.debug("Add placement");
                placements.offerFirst(new WordPlacementImpl(word, currentContainer));
                iterators.offerFirst(currentIterator);
                takeNextContainer = true;

            } else {
                log.debug("Remove placement");
                containers.offerFirst(currentContainer);
                WordPlacement placement = placements.pollFirst();
                if (placement != null) {
                    log.debug("Placement remove, try another placement");
                    currentContainer = placement.getContainer();
                    currentIterator = iterators.pollFirst();
                } else { //no more variants
                    log.debug("Nothing to remove, no more variants");
                    break;
                }

                takeNextContainer = false;
            }
        }


    }

    @Nullable
    private Word findNextWord() {
        if (currentIterator != null && currentContainer != null && currentIterator.hasNext()) {
            Word word = currentIterator.next();
            while (word != null && alreadyUsed(word) && currentIterator.hasNext()) {
                word = currentIterator.next();
            }
            return word;
        }
        return null;
    }

    private boolean alreadyUsed(@NotNull Word word) {
        return placements.stream().anyMatch(p -> p.getWord().equals(word));
    }


    @NotNull
    private WordPattern getContainerPattern(@NotNull WordContainer container) {
        char[] patternLetters = new char[container.getLength()];
        Arrays.fill(patternLetters, WordPattern.wildcard);
        for (WordPlacement p : placements) {
            WordContainer placementContainer = p.getContainer();
            ContainerCoordinate intersection = placementContainer.getIntersection(container);

            if (intersection != null) {
                log.debug("Intersection: " + intersection);
                int placementCharIndex = placementContainer.toCharIndex(intersection);
                int containerCharIndex = container.toCharIndex(intersection);

                patternLetters[containerCharIndex] = p.getWord().getText().charAt(placementCharIndex);
            }
        }
        return new WordPatternImpl(new String(patternLetters));
    }
}
