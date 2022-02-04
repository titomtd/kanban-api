package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Board;
import fr.duchemin.sir.kanban.entity.Section;

import java.util.List;

public interface BoardService {
    List<Board> getAllBoards();

    Board getBoardById(Long boardId);

    Board createBoard(Board board);

    Board updateBoard(Long boardId, Board board);

    void deleteBoard(Long boardId);

    Board addSectionToBoard(Long boardId, Section section);
}
