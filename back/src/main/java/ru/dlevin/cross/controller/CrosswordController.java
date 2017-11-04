package ru.dlevin.cross.controller;

import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import ru.dlevin.cross.api.WordPlacement;
import ru.dlevin.cross.api.board.ContainerCoordinate;
import ru.dlevin.cross.api.board.ContainerOrientation;
import ru.dlevin.cross.api.board.CrosswordBoard;
import ru.dlevin.cross.api.board.WordContainer;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.api.word.dict.WordDictionary;
import ru.dlevin.cross.dto.ContainerDto;
import ru.dlevin.cross.impl.CrosswordCreationContextImpl;
import ru.dlevin.cross.impl.CrosswordCreatorImpl;
import ru.dlevin.cross.impl.EmptyCrosswordCreationListener;
import ru.dlevin.cross.impl.board.CrosswordBoardBuilderImpl;
import ru.dlevin.cross.impl.word.dict.ResourceWordDictionaryFactory;
import ru.dlevin.cross.utils.StreamUtils;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/data/crosswords")
public class CrosswordController {

    private final WordDictionary dictionary = new ResourceWordDictionaryFactory(() -> StreamUtils.getResourceStream("dictionary.txt"), Charset.forName("UTF-8")).create();

    @Autowired
    public CrosswordController() {

    }

    @RequestMapping(path = "/", method = {RequestMethod.POST})
    @NotNull
    public List<List<ContainerDto>> create(@RequestBody List<ContainerDto> containers) {
        CrosswordCreatorImpl creator = new CrosswordCreatorImpl();
        CrosswordBoardBuilderImpl builder = new CrosswordBoardBuilderImpl();
        containers.forEach(c -> {
            if (c.isVertical()) {
                builder.addVerticalContainer(c.getX(), c.getY(), c.getLength());
            } else {
                builder.addHorizontalContainer(c.getX(), c.getY(), c.getLength());
            }
        });
        CrosswordBoard board = builder.build();

        CrosswordCreationContextImpl context = new CrosswordCreationContextImpl(board, dictionary);
        List<List<ContainerDto>> solutions = new ArrayList<>();
        creator.create(context, new EmptyCrosswordCreationListener() {
            @Override
            public void onSolutionFound(@NotNull List<WordPlacement> placements) {
                List<ContainerDto> solution = placements.stream().map(ps -> {
                    WordContainer container = ps.getContainer();
                    ContainerCoordinate start = container.getStartCoordinate();
                    Word word = ps.getWord();
                    return new ContainerDto(start.getLeft(), start.getTop(), container.getOrientation() == ContainerOrientation.vertical, word.getLength(), word.getText());
                }).collect(Collectors.toList());
                solutions.add(solution);
            }
        });

        return solutions;
    }

}
