package fr.duchemin.sir.kanban.controller;

import fr.duchemin.sir.kanban.dto.AddressDTO;
import fr.duchemin.sir.kanban.dto.CardDTO;
import fr.duchemin.sir.kanban.dto.CardInsertDTO;
import fr.duchemin.sir.kanban.entity.Address;
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
    public ResponseEntity<CardDTO> updateCardById(@PathVariable(value = "id") Long cardId, @RequestBody @Valid CardInsertDTO cardInsertDTO) {
        Card cardRequest = this.modelMapper.map(cardInsertDTO, Card.class);
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

    @PatchMapping("/card/{id}/user")
    public ResponseEntity<CardDTO> setUserToCardById(@PathVariable(value = "id") Long cardId, @RequestBody String userId) {
        Card card = this.cardService.setUserToCard(cardId, Long.parseLong(userId));
        CardDTO cardResponse = this.modelMapper.map(card, CardDTO.class);
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }

    @DeleteMapping("/card/{id}/user")
    public ResponseEntity<CardDTO> removeUserToCardById(@PathVariable(value = "id") Long cardId) {
        Card card = this.cardService.removeUserToCard(cardId);
        CardDTO cardResponse = this.modelMapper.map(card, CardDTO.class);
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }

    @PatchMapping("/card/{id}/address")
    public ResponseEntity<CardDTO> setAddressToCardById(@PathVariable(value = "id") Long cardId, @RequestBody @Valid AddressDTO addressDTO) {
        Address address = this.modelMapper.map(addressDTO, Address.class);
        Card card = this.cardService.setAddressToCard(cardId, address);
        CardDTO cardResponse = this.modelMapper.map(card, CardDTO.class);
        return new ResponseEntity<>(cardResponse, HttpStatus.CREATED);
    }

    @PatchMapping("/card/{id}/tag")
    public ResponseEntity<CardDTO> addTagToCardById(@PathVariable(value = "id") Long cardId, @RequestBody String tagId) {
        Card card = this.cardService.addTagToCard(cardId, Long.parseLong(tagId));
        CardDTO cardResponse = this.modelMapper.map(card, CardDTO.class);
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }

    @DeleteMapping("/card/{id}/tag")
    public ResponseEntity<CardDTO> removeTagToCardById(@PathVariable(value = "id") Long cardId, @RequestBody String tagId) {
        Card card = this.cardService.removeTagToCard(cardId, Long.parseLong(tagId));
        CardDTO cardResponse = this.modelMapper.map(card, CardDTO.class);
        return new ResponseEntity<>(cardResponse, HttpStatus.OK);
    }
}
