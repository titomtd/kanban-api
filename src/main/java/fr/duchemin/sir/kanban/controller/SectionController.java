package fr.duchemin.sir.kanban.controller;

import fr.duchemin.sir.kanban.dto.CardInsertDTO;
import fr.duchemin.sir.kanban.dto.SectionDTO;
import fr.duchemin.sir.kanban.dto.SectionInsertDTO;
import fr.duchemin.sir.kanban.entity.Card;
import fr.duchemin.sir.kanban.entity.Section;
import fr.duchemin.sir.kanban.message.Response;
import fr.duchemin.sir.kanban.message.ResponseMessageType;
import fr.duchemin.sir.kanban.service.SectionService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@Tag(name = "Section")
@RestController
@RequestMapping(value = "/api")
public class SectionController {

    private SectionService sectionService;
    private ModelMapper modelMapper;

    @Autowired
    public SectionController(SectionService sectionService, ModelMapper modelMapper) {
        this.sectionService = sectionService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/sections")
    public ResponseEntity<List<SectionDTO>> getSections(@RequestParam(required = false) Long boardId) {
        List<SectionDTO> sections = this.sectionService.getAllSections(boardId)
                .stream()
                .map(section -> this.modelMapper.map(section, SectionDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(sections, HttpStatus.OK);
    }

    @GetMapping("/section/{id}")
    public ResponseEntity<SectionDTO> getSectionById(@PathVariable(value = "id") Long sectionId) {
        Section section = this.sectionService.getSectionById(sectionId);
        SectionDTO sectionResponse = this.modelMapper.map(section, SectionDTO.class);
        return new ResponseEntity<>(sectionResponse, HttpStatus.OK);
    }

    @DeleteMapping("/section/{id}")
    public ResponseEntity<Response> removeSectionById(@PathVariable(value = "id") Long sectionId) {
        this.sectionService.deleteSection(sectionId);

        Response response = new Response(HttpStatus.OK, ResponseMessageType.SUCCESS.toString());
        response.addDetail("section", "The section has been removed.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @PostMapping("/section/{id}")
    public ResponseEntity<SectionDTO> updateSectionById(@PathVariable(value = "id") Long sectionId, @RequestBody @Valid SectionInsertDTO sectionInsertDTO) {
        Section sectionRequest = this.modelMapper.map(sectionInsertDTO, Section.class);
        Section section = this.sectionService.updateSection(sectionId, sectionRequest);
        SectionDTO sectionResponse = this.modelMapper.map(section, SectionDTO.class);
        return new ResponseEntity<>(sectionResponse, HttpStatus.OK);
    }

    @PatchMapping("/section/{id}/card")
    public ResponseEntity<SectionDTO> addCardToSectionById(@PathVariable(value = "id") Long sectionId, @RequestBody @Valid CardInsertDTO cardInsertDTO) {
        Card cardRequest = this.modelMapper.map(cardInsertDTO, Card.class);
        Section section = this.sectionService.addCardToSection(sectionId, cardRequest);
        SectionDTO sectionResponse = this.modelMapper.map(section, SectionDTO.class);
        return new ResponseEntity<>(sectionResponse, HttpStatus.CREATED);
    }
}
