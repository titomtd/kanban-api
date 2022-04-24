package fr.duchemin.sir.kanban.controller;

import fr.duchemin.sir.kanban.dto.CardDTO;
import fr.duchemin.sir.kanban.entity.Card;
import fr.duchemin.sir.kanban.message.Response;
import fr.duchemin.sir.kanban.message.ResponseMessageType;
import fr.duchemin.sir.kanban.service.CardService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Card")
@RestController
@RequestMapping(value = "/api")
public class CardController {

    private CardService cardService;
    private ModelMapper modelMapper;

    @Autowired
    public CardController(CardService cardService, ModelMapper modelMapper) {
        this.cardService = cardService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/cards")
    public ResponseEntity<List<CardDTO>> getCards(@RequestParam(required = false) Long sectionId) {
        List<CardDTO> cards = this.cardService.getAllCards(sectionId)
                .stream()
                .map(card -> this.modelMapper.map(card, CardDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(cards, HttpStatus.OK);
    }

    @GetMapping("/card/{id}")
    public ResponseEntity<CardDTO> getCardById(@PathVariable(value = "id") Long cardId) {
        Card card = this.cardService.getCardById(cardId);
        CardDTO cardResponse = this.modelMapper.map(card, CardDTO.class);
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }

    @PostMapping("/card/{id}")
    public ResponseEntity<CardDTO> updateCardById(@PathVariable(value = "id") Long cardId, @RequestBody @Valid CardDTO cardDTO) {
        Card cardRequest = this.modelMapper.map(cardDTO, Card.class);
        Card card = this.cardService.updateCard(cardId, cardRequest);
        CardDTO cardResponse = this.modelMapper.map(card, CardDTO.class);
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }

    @DeleteMapping("/card/{id}")
    public ResponseEntity<Response> removeCardById(@PathVariable(value = "id") Long cardId) {
        this.cardService.deleteCard(cardId);

        Response response = new Response(HttpStatus.OK, ResponseMessageType.SUCCESS.toString());
        response.addDetail("card", "The card has been removed.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PatchMapping("/card/{id}/section")
    public ResponseEntity<CardDTO> changeSectionToCardById(@PathVariable(value = "id") Long cardId, @RequestBody String sectionId) {
        Card card = this.cardService.changeSectionToCard(cardId, Long.parseLong(sectionId));
        CardDTO cardResponse = this.modelMapper.map(card, CardDTO.class);
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }
}
