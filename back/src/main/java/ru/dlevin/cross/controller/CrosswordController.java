package ru.dlevin.cross.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.core.MessageSendingOperations;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Controller;
import ru.dlevin.cross.api.CrosswordCreationContext;
import ru.dlevin.cross.api.WordPlacement;
import ru.dlevin.cross.api.board.ContainerCoordinate;
import ru.dlevin.cross.api.board.ContainerOrientation;
import ru.dlevin.cross.api.board.CrosswordBoard;
import ru.dlevin.cross.api.board.WordContainer;
import ru.dlevin.cross.api.word.Word;
import ru.dlevin.cross.api.word.dict.WordDictionary;
import ru.dlevin.cross.dto.ContainerDto;
import ru.dlevin.cross.dto.IterativeOperationStatus;
import ru.dlevin.cross.dto.IterativeOperationStatusDto;
import ru.dlevin.cross.impl.CrosswordCreationContextImpl;
import ru.dlevin.cross.impl.CrosswordCreatorImpl;
import ru.dlevin.cross.impl.EmptyCrosswordCreationListener;
import ru.dlevin.cross.impl.board.CrosswordBoardBuilderImpl;
import ru.dlevin.cross.impl.word.dict.ResourceWordDictionaryFactory;
import ru.dlevin.cross.utils.JacksonUtils;
import ru.dlevin.cross.utils.ObjectUtils;
import ru.dlevin.cross.utils.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class CrosswordController {

    private static final int progressStep = 5000;

    private final WordDictionary dictionary = new ResourceWordDictionaryFactory(() -> StreamUtils.getResourceStream("dictionary.txt"), Charset.forName("UTF-8")).create();

    private final MessageSendingOperations<String> messagingTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public CrosswordController(MessageSendingOperations<String> messagingTemplate,
                               ObjectMapper objectMapper) {
        this.messagingTemplate = messagingTemplate;
        this.objectMapper = objectMapper;
    }

    @MessageMapping(value = "/crosswords/create")
    public void create(Message<byte[]> message, StompHeaderAccessor headers) throws IOException {
        List<ContainerDto> containers = ObjectUtils.notNull(JacksonUtils.readValue(objectMapper, message.getPayload(), new TypeReference<List<ContainerDto>>() {
        }));

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

        creator.create(context, new EmptyCrosswordCreationListener() {
            private long iterations = 0;

            @Override
            public void onIteration(@NotNull CrosswordCreationContext context) {
                iterations++;
                if (iterations % progressStep == 0) {
                    messagingTemplate.convertAndSend("/crosswords/solutions/" + headers.getSessionId(), new IterativeOperationStatusDto(IterativeOperationStatus.inProgress, iterations));
                }
            }

            @Override
            public void onSolutionFound(@NotNull List<WordPlacement> placements) {
                List<ContainerDto> solution = placements.stream().map(ps -> {
                    WordContainer container = ps.getContainer();
                    ContainerCoordinate start = container.getStartCoordinate();
                    Word word = ps.getWord();
                    return new ContainerDto(start.getLeft(), start.getTop(), container.getOrientation() == ContainerOrientation.vertical, word.getLength(), word.getText());
                }).collect(Collectors.toList());

                messagingTemplate.convertAndSend("/crosswords/solutions/" + headers.getSessionId(), solution);
            }

            @Override
            public void onFinish(@NotNull CrosswordCreationContext context) {
                messagingTemplate.convertAndSend("/crosswords/solutions/" + headers.getSessionId(), new IterativeOperationStatusDto(IterativeOperationStatus.finished, iterations));
            }
        });
    }
}
