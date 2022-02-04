package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Address;
import fr.duchemin.sir.kanban.entity.Card;
import fr.duchemin.sir.kanban.entity.Tag;
import fr.duchemin.sir.kanban.entity.User;
import fr.duchemin.sir.kanban.exception.EntityNotFoundException;
import fr.duchemin.sir.kanban.exception.InternalServerException;
import fr.duchemin.sir.kanban.repository.AddressRepository;
import fr.duchemin.sir.kanban.repository.CardRepository;
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
    private AddressRepository addressRepository;
    private TagRepository tagRepository;

    public CardServiceImpl(
            CardRepository cardRepository,
            UserRepository userRepository,
            AddressRepository addressRepository,
            TagRepository tagRepository
    ) {
        this.cardRepository = cardRepository;
        this.userRepository = userRepository;
        this.addressRepository = addressRepository;
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

        if (null != card.getLabel())
            cardResponse.setLabel(card.getLabel());

        if (null != card.getEndDate())
            cardResponse.setEndDate(card.getEndDate());

        if (0 != card.getEstimatedTime())
            cardResponse.setEstimatedTime(card.getEstimatedTime());

        if (null != card.getUrl())
            cardResponse.setUrl(card.getUrl());

        if (null != card.getNote())
            cardResponse.setNote(card.getNote());

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
    public Card setUserToCard(Long cardId, Long userId) {
        Optional<Card> cardOptional = this.cardRepository.findById(cardId);

        if (cardOptional.isEmpty())
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");

        Optional<User> userOptional = this.userRepository.findById(userId);

        if (userOptional.isEmpty())
            throw new EntityNotFoundException("User with id " + userId + " not found.");

        Card card = cardOptional.get();
        User user = userOptional.get();
        card.setUser(user);

        return this.cardRepository.save(card);
    }

    @Override
    public Card removeUserToCard(Long cardId) {
        Optional<Card> cardOptional = this.cardRepository.findById(cardId);

        if (cardOptional.isEmpty())
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");

        Card card = cardOptional.get();
        card.setUser(null);

        return this.cardRepository.save(card);
    }

    @Override
    public Card setAddressToCard(Long cardId, Address address) {
        Optional<Card> cardOptional = this.cardRepository.findById(cardId);

        if (cardOptional.isEmpty())
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");

        Card card = cardOptional.get();
        Address addressActual = card.getAddress();

        if (null != addressActual)
            this.addressRepository.delete(addressActual);

        card.setAddress(address);

        return this.cardRepository.save(card);
    }

    @Override
    public Card addTagToCard(Long cardId, Long tagId) {
        Optional<Card> cardOptional = this.cardRepository.findById(cardId);

        if (cardOptional.isEmpty())
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");

        Optional<Tag> tagOptional = this.tagRepository.findById(tagId);

        if (tagOptional.isEmpty())
            throw new EntityNotFoundException("Tag with id " + tagId + " not found.");

        Card card = cardOptional.get();
        Tag tag = tagOptional.get();
        card.addTag(tag);

        return this.cardRepository.save(card);
    }

    @Override
    public Card removeTagToCard(Long cardId, Long tagId) {
        Optional<Card> cardOptional = this.cardRepository.findById(cardId);

        if (cardOptional.isEmpty())
            throw new EntityNotFoundException("Card with id " + cardId + " not found.");

        Optional<Tag> tagOptional = this.tagRepository.findById(tagId);

        if (tagOptional.isEmpty())
            throw new EntityNotFoundException("Tag with id " + tagId + " not found.");

        Card card = cardOptional.get();
        Tag tag = tagOptional.get();
        card.removeTag(tag);

        return this.cardRepository.save(card);
    }
}
