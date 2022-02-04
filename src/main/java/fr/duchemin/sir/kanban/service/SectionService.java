package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Card;
import fr.duchemin.sir.kanban.entity.Section;

import java.util.List;

public interface SectionService {
    List<Section> getAllSections(Long boardId);

    Section getSectionById(Long sectionId);

    void deleteSection(Long sectionId);

    Section updateSection(Long sectionId, Section section);

    Section addCardToSection(Long sectionId, Card card);
}
