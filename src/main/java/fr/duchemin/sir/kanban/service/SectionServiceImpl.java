package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Card;
import fr.duchemin.sir.kanban.entity.Section;
import fr.duchemin.sir.kanban.exception.EntityNotFoundException;
import fr.duchemin.sir.kanban.exception.InternalServerException;
import fr.duchemin.sir.kanban.repository.SectionRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SectionServiceImpl implements SectionService {

    private SectionRepository sectionRepository;

    public SectionServiceImpl(SectionRepository sectionRepository) {
        this.sectionRepository = sectionRepository;
    }

    @Override
    public List<Section> getAllSections(Long boardId) {
        List<Section> sections = new ArrayList<>();
        if (null != boardId) {
            this.sectionRepository.findAllByBoardId(boardId).iterator().forEachRemaining(sections::add);
        } else {
            this.sectionRepository.findAll().iterator().forEachRemaining(sections::add);
        }
        return sections;
    }

    @Override
    public Section getSectionById(Long sectionId) {
        Optional<Section> sectionOptional = this.sectionRepository.findById(sectionId);

        if (sectionOptional.isEmpty())
            throw new EntityNotFoundException("Section with id " + sectionId + " not found.");

        return sectionOptional.get();
    }

    @Override
    public void deleteSection(Long sectionId) {
        try {
            this.sectionRepository.deleteById(sectionId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Section with id " + sectionId + " not found.");
        }

        if (this.sectionRepository.existsById(sectionId))
            throw new InternalServerException("Failed : The section hasn't been removed.");
    }

    @Override
    public Section updateSection(Long sectionId, Section section) {
        Optional<Section> sectionOptional = this.sectionRepository.findById(sectionId);

        if (sectionOptional.isEmpty())
            throw new EntityNotFoundException("Section with id " + sectionId + " not found.");

        Section sectionResponse = sectionOptional.get();

        if (null != section.getLabel())
            sectionResponse.setLabel(section.getLabel());

        if (-1 < section.getPosition())
            sectionResponse.setPosition(section.getPosition());

        return this.sectionRepository.save(sectionResponse);
    }

    @Override
    public Section addCardToSection(Long sectionId, Card card) {
        Optional<Section> sectionOptional = this.sectionRepository.findById(sectionId);

        if (sectionOptional.isEmpty())
            throw new EntityNotFoundException("Section with id " + sectionId + " not found.");

        Section section = sectionOptional.get();
        section.addCard(card);

        return this.sectionRepository.save(section);
    }
}
