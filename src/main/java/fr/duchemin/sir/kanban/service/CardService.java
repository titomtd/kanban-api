package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Card;

import java.util.List;

public interface CardService {
    List<Card> getAllCards(Long sectionId);

    Card getCardById(Long cardId);

    Card updateCard(Long cardId, Card card);

    void deleteCard(Long cardId);

    Card changeSectionToCard(Long cardId, Long sectionId);
}
