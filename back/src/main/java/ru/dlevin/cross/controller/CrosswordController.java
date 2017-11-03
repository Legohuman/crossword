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
import ru.dlevin.cross.dto.PlacementDto;
import ru.dlevin.cross.dto.PlacementsDto;
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

    @RequestMapping(path = "/", method = { RequestMethod.POST })
    @NotNull
    public List<List<PlacementDto>> create(@RequestBody PlacementsDto containersDto) {
        List<PlacementDto> containers = containersDto.getPlacements();
        CrosswordCreatorImpl creator = new CrosswordCreatorImpl();
        CrosswordBoardBuilderImpl builder = new CrosswordBoardBuilderImpl();
        containers.forEach(c -> {
            if (c.v) {
                builder.addVerticalContainer(c.x, c.y, c.l);
            } else {
                builder.addHorizontalContainer(c.x, c.y, c.l);
            }
        });
        CrosswordBoard board = builder.build();

        CrosswordCreationContextImpl context = new CrosswordCreationContextImpl(board, dictionary);
        List<List<PlacementDto>> solutions = new ArrayList<>();
        creator.create(context, new EmptyCrosswordCreationListener() {
            @Override
            public void onSolutionFound(@NotNull List<WordPlacement> placements) {
                List<PlacementDto> solution = placements.stream().map(ps -> {
                    WordContainer container = ps.getContainer();
                    ContainerCoordinate start = container.getStartCoordinate();
                    Word word = ps.getWord();
                    return new PlacementDto(start.getLeft(), start.getTop(), container.getOrientation() == ContainerOrientation.vertical, word.getLength(), word.getText());
                }).collect(Collectors.toList());
                solutions.add(solution);
            }
        });

        return solutions;
    }

}
