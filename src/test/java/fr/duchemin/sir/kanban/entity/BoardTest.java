package fr.duchemin.sir.kanban.entity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertEquals;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BoardTest {

    private Board board;

    @BeforeEach
    void setUp() {
        this.board = new Board();
    }

    @Test
    void testAddSection() {
        Section section = new Section();
        section.setLabel("Section");
        section.setPosition(4);
        this.board.addSection(section);

        assertEquals(section, this.board.getSections().get(3));
        assertEquals(4, this.board.getSections().size());
    }
}
