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
import ru.dlevin.cross.utils.Functions;
import ru.dlevin.cross.utils.MapUtils;

import java.util.*;

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
        containers.addAll(sortForSearch(board.getContainers()));
        boolean takeNextContainer = true;
        log.debug("Starting search");
        int i = 0, sc = 0;
        listener.onStart(context);

        while (i < 500000 && sc < 10) {
            log.debug("Search iteration: " + ++i);
            listener.onIteration(context);

            if (takeNextContainer) {
                log.debug("Taking next container");
                currentContainer = containers.pollFirst();
                log.debug("Container is taken " + currentContainer);

                if (currentContainer == null) {
                    log.debug("Solution: " + placements);
                    listener.onSolutionFound(new ArrayList<>(placements));
                    sc++;
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
        listener.onFinish(context);
    }

    @NotNull
    private List<WordContainer> sortForSearch(@NotNull Collection<WordContainer> containers) {
        if (containers.isEmpty()) return Collections.emptyList();
        if (containers.size() == 1) return Collections.singletonList(containers.iterator().next());

        SortedMap<WordContainer, Integer> containerToIntersectionCount = new TreeMap<>();
        ArrayList<WordContainer> containersList = new ArrayList<>(containers);
        for (int i = 0; i < containersList.size(); i++) {
            for (int j = i + 1; j < containersList.size(); j++) {
                WordContainer container1 = containersList.get(i);
                WordContainer container2 = containersList.get(j);
                ContainerCoordinate intersection = container1.getIntersection(container2);
                if (intersection != null) {
                    containerToIntersectionCount.compute(container1, Functions.incrementOrOne);
                    containerToIntersectionCount.compute(container2, Functions.incrementOrOne);
                } else {
                    containerToIntersectionCount.compute(container1, Functions.sameOrZero);
                    containerToIntersectionCount.compute(container2, Functions.sameOrZero);
                }
            }
        }
        //sort containers by intersection count and by their length
        return MapUtils.sortedKeys(containerToIntersectionCount, (e1, e2) -> {
            int result = e2.getValue().compareTo(e1.getValue());
            if (result == 0) {
                result = e2.getKey().getLength() - e1.getKey().getLength();
            }
            return result;
        });
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
