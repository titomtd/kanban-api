package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.*;
import fr.duchemin.sir.kanban.exception.EntityNotFoundException;
import fr.duchemin.sir.kanban.exception.InternalServerException;
import fr.duchemin.sir.kanban.repository.CardRepository;
import fr.duchemin.sir.kanban.repository.SectionRepository;
import fr.duchemin.sir.kanban.repository.TagRepository;
import fr.duchemin.sir.kanban.repository.UserRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class CardServiceImpl implements CardService {

    private CardRepository cardRepository;
    private UserRepository userRepository;
    private SectionRepository sectionRepository;
    private TagRepository tagRepository;

    public CardServiceImpl(
            CardRepository cardRepository,
            UserRepository userRepository,
            SectionRepository sectionRepository,
            TagRepository tagRepository
    ) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.sectionRepository = sectionRepository;
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Card> getAllCards(Long sectionId) {
        List<Card> cards = new ArrayList<>();
        if (null != sectionId) {
            this.cardRepository.findAllBySectionId(sectionId).iterator().forEachRemaining(cards::add);
        } else {
            this.cardRepository.findAll().iterator().forEachRemaining(cards::add);
        }
        return cards;
    }

    @Override
    public Card getCardById(Long cardId) {
        Optional<Card> cardOptional = this.cardRepository.findById(cardId);

        if (cardOptional.isEmpty())
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");

        return cardOptional.get();
    }

    @Override
    public Card updateCard(Long cardId, Card card) {
        Optional<Card> cardOptional = this.cardRepository.findById(cardId);

        if (cardOptional.isEmpty())
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");

        Card cardResponse = cardOptional.get();

        cardResponse.setLabel(card.getLabel());
        cardResponse.setEndDate(card.getEndDate());

        if (null != card.getUser()) {
            Optional<User> userOptional = this.userRepository.findById(card.getUser().getId());

            if (userOptional.isEmpty())
                throw new EntityNotFoundException("User with id " + card.getUser().getId() + " not found.");

            cardResponse.setUser(userOptional.get());
        } else {
            cardResponse.setUser(null);
        }

        cardResponse.setEstimatedTime(card.getEstimatedTime());
        cardResponse.setUrl(card.getUrl());
        cardResponse.setNote(card.getNote());

        cardResponse.getAddress().setStreet(card.getAddress().getStreet());
        cardResponse.getAddress().setCity(card.getAddress().getCity());
        cardResponse.getAddress().setZipCode(card.getAddress().getZipCode());

        cardResponse.removeAllTags();

        for (Tag tag : card.getTags()) {
            Optional<Tag> tagOptional = this.tagRepository.findById(tag.getId());
            tagOptional.ifPresent(cardResponse::addTag);
        }

        return this.cardRepository.save(cardResponse);
    }

    @Override
    public void deleteCard(Long cardId) {
        try {
            this.cardRepository.deleteById(cardId);
        } catch (EmptyResultDataAccessException e) {
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");
        }

        if (this.cardRepository.existsById(cardId))
            throw new InternalServerException("Failed : The card hasn't been removed.");
    }

    @Override
    public Card changeSectionToCard(Long cardId, Long sectionId) {
        Optional<Card> cardOptional = this.cardRepository.findById(cardId);

        if (cardOptional.isEmpty())
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");

        Optional<Section> sectionOptional = this.sectionRepository.findById(sectionId);

        if (sectionOptional.isEmpty())
            throw new EntityNotFoundException("Section with id " + sectionId + " not found.");

        Card card = cardOptional.get();
        Section section = sectionOptional.get();
        card.setSection(section);

        return this.cardRepository.save(card);
    }
}
