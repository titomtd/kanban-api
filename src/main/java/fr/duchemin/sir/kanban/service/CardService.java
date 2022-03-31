package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Address;
import fr.duchemin.sir.kanban.entity.Card;

import java.util.List;

public interface CardService {
    List<Card> getAllCards(Long sectionId);

    Card getCardById(Long cardId);

    Card updateCard(Long cardId, Card card);

    void deleteCard(Long cardId);

    Card setUserToCard(Long cardId, Long userId);

    Card removeUserToCard(Long cardId);

    Card setAddressToCard(Long cardId, Address address);

    Card addTagToCard(Long cardId, Long tagId);

    Card removeTagToCard(Long cardId, Long tagId);

    Card changeSectionToCard(Long cardId, Long sectionId);
}
