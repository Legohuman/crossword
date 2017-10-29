package ru.dlevin.cross.impl;

import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import ru.dlevin.cross.api.CrosswordCreationContext;
import ru.dlevin.cross.api.CrosswordCreationListener;
import ru.dlevin.cross.api.CrosswordCreator;
import ru.dlevin.cross.api.WordPlacement;
import ru.dlevin.cross.api.board.ContainerCoordinate;
import ru.dlevin.cross.api.board.ContainerOrientation;
import ru.dlevin.cross.api.board.CrosswordBoard;
import ru.dlevin.cross.api.board.WordContainer;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.api.word.WordPattern;
import ru.dlevin.cross.app.CrosswordCreatorApp;
import ru.dlevin.cross.impl.word.WordPatternImpl;

import java.util.Arrays;
import java.util.Deque;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.stream.Collectors;

public class CrosswordCreatorImpl implements CrosswordCreator {
    private static final Logger log = LoggerFactory.getLogger(CrosswordCreatorApp.class);

    private final Deque<WordPlacement> placements = new LinkedList<>();

    @Override
    public void create(@NotNull CrosswordCreationContext context, @NotNull CrosswordCreationListener listener) {
        CrosswordBoard board = context.getBoard();
        Deque<WordContainer> containers = new LinkedList<>(board.getContainers());
        while (!containers.isEmpty()) {
            WordContainer container = containers.pollFirst();
            WordPattern pattern = getContainerPattern(container);
            Iterator<Word> iterator = context.getDictionary().search(pattern);
            if (iterator.hasNext()) {
                placements.offerLast(new WordPlacementImpl(iterator.next(), container));
            }
        }
        log.info("Found placements:\n" + placements.stream().map(String::valueOf).collect(Collectors.joining("\n")));
    }

    private WordPattern getContainerPattern(WordContainer container) {
        char[] patternLetters = new char[container.getLength()];
        Arrays.fill(patternLetters, WordPattern.wildcard);
        for (WordPlacement p : placements) {
            ContainerCoordinate intersection = container.getIntersection(p.getContainer());

            if (intersection != null) {
                log.debug("Intersection: " + intersection);
                int intersectionCharIndex;
                if (container.getOrientation() == ContainerOrientation.horizontal) {
                    intersectionCharIndex = intersection.relativeTo(container.getStartCoordinate()).getLeft();
                } else {
                    intersectionCharIndex = intersection.relativeTo(container.getStartCoordinate()).getTop();
                }
                patternLetters[intersectionCharIndex] = p.getWord().getText().charAt(intersectionCharIndex);
            }
        }
        return new WordPatternImpl(new String(patternLetters));
    }
}
