package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Board;
import fr.duchemin.sir.kanban.entity.Section;
import fr.duchemin.sir.kanban.exception.EntityNotFoundException;
import fr.duchemin.sir.kanban.exception.InternalServerException;
import fr.duchemin.sir.kanban.repository.BoardRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BoardServiceImpl implements BoardService {

    private final BoardRepository boardRepository;

    public BoardServiceImpl(BoardRepository boardRepository) {
        this.boardRepository = boardRepository;
    }

    @Override
    public List<Board> getAllBoards() {
        List<Board> boards = new ArrayList<>();
        this.boardRepository.findAll().iterator().forEachRemaining(boards::add);
        return boards;
    }

    @Override
    public Board getBoardById(Long boardId) {
        Optional<Board> boardOptional = this.boardRepository.findById(boardId);

        if (boardOptional.isEmpty())
            throw new EntityNotFoundException("Board with id " + boardId + " not found.");

        return boardOptional.get();
    }

    @Override
    public Board createBoard(Board board) {
        return this.boardRepository.save(board);
    }

    @Override
    public Board updateBoard(Long boardId, Board board) {
        Optional<Board> boardOptional = this.boardRepository.findById(boardId);

        if (boardOptional.isEmpty())
            throw new EntityNotFoundException("Board with id " + boardId + " not found.");

        Board boardResponse = boardOptional.get();

        if (null != board.getLabel())
            boardResponse.setLabel(board.getLabel());

        return boardResponse;
    }

    @Override
    public void deleteBoard(Long boardId) {
        try {
            this.boardRepository.deleteById(boardId);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("Board with id " + boardId + " not found.");
        }

        if (this.boardRepository.existsById(boardId))
            throw new InternalServerException("Failed : The board hasn't been removed.");
    }

    @Override
    public Board addSectionToBoard(Long boardId, Section section) {
        Optional<Board> boardOptional = this.boardRepository.findById(boardId);

        if (boardOptional.isEmpty())
            throw new EntityNotFoundException("Board with id " + boardId + " not found.");

        Board boardResponse = boardOptional.get();
        boardResponse.addSection(section);

        return this.boardRepository.save(boardResponse);
    }
}
