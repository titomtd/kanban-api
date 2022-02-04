package fr.duchemin.sir.kanban.controller;

import fr.duchemin.sir.kanban.dto.BoardDTO;
import fr.duchemin.sir.kanban.dto.BoardInsertDTO;
import fr.duchemin.sir.kanban.dto.SectionInsertDTO;
import fr.duchemin.sir.kanban.entity.Board;
import fr.duchemin.sir.kanban.entity.Section;
import fr.duchemin.sir.kanban.message.Response;
import fr.duchemin.sir.kanban.message.ResponseMessageType;
import fr.duchemin.sir.kanban.service.BoardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Board")
@RestController
@RequestMapping(value = "/api")
public class BoardController {

    private BoardService boardService;
    private ModelMapper modelMapper;

    @Autowired
    public BoardController(BoardService boardService, ModelMapper modelMapper) {
        this.boardService = boardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/boards")
    public ResponseEntity<List<BoardDTO>> getBoards() {
        List<BoardDTO> boards = this.boardService.getAllBoards()
                .stream()
                .map(board -> this.modelMapper.map(board, BoardDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(boards, HttpStatus.OK);
    }

    @GetMapping("/board/{id}")
    public ResponseEntity<BoardDTO> getBoardById(@PathVariable(value = "id") Long boardId) {
        Board board = this.boardService.getBoardById(boardId);
        BoardDTO boardResponse = this.modelMapper.map(board, BoardDTO.class);
        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    @PostMapping("/boards")
    public ResponseEntity<BoardDTO> createBoard(@RequestBody @Valid BoardInsertDTO boardInsertDTO) {
        Board boardRequest = this.modelMapper.map(boardInsertDTO, Board.class);
        Board board = this.boardService.createBoard(boardRequest);
        BoardDTO boardResponse = this.modelMapper.map(board, BoardDTO.class);
        return new ResponseEntity<>(boardResponse, HttpStatus.CREATED);
    }

    @DeleteMapping("/board/{id}")
    public ResponseEntity<Response> removeBoardById(@PathVariable(value = "id") Long boardId) {
        this.boardService.deleteBoard(boardId);

        Response response = new Response(HttpStatus.OK, ResponseMessageType.SUCCESS.toString());
        response.addDetail("board", "The board has been removed.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/board/{id}")
    public ResponseEntity<BoardDTO> updateBoardById(@PathVariable(value = "id") Long boardId, @RequestBody @Valid BoardInsertDTO boardInsertDTO) {
        Board boardRequest = this.modelMapper.map(boardInsertDTO, Board.class);
        Board board = this.boardService.updateBoard(boardId, boardRequest);
        BoardDTO boardResponse = this.modelMapper.map(board, BoardDTO.class);
        return new ResponseEntity<>(boardResponse, HttpStatus.OK);
    }

    @PatchMapping("/board/{id}/section")
    public ResponseEntity<BoardDTO> addSectionToBoardById(@PathVariable(value = "id") Long boardId, @RequestBody @Valid SectionInsertDTO sectionInsertDTO) {
        Section sectionRequest = this.modelMapper.map(sectionInsertDTO, Section.class);
        Board board = this.boardService.addSectionToBoard(boardId, sectionRequest);
        BoardDTO boardResponse = this.modelMapper.map(board, BoardDTO.class);
        return new ResponseEntity<>(boardResponse, HttpStatus.CREATED);
    }
}
