package fr.duchemin.sir.kanban.controller;

import fr.duchemin.sir.kanban.dto.TagDTO;
import fr.duchemin.sir.kanban.entity.Tag;
import fr.duchemin.sir.kanban.message.Response;
import fr.duchemin.sir.kanban.message.ResponseMessageType;
import fr.duchemin.sir.kanban.service.TagService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@io.swagger.v3.oas.annotations.tags.Tag(name = "Tag")
@RestController
@RequestMapping(value = "/api")
public class TagController {

    private TagService tagService;
    private ModelMapper modelMapper;

    @Autowired
    public TagController(TagService tagService, ModelMapper modelMapper) {
        this.tagService = tagService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/tags")
    public ResponseEntity<List<TagDTO>> getTags() {
        List<TagDTO> tags = this.tagService.getAllTags()
                .stream()
                .map(tag -> this.modelMapper.map(tag, TagDTO.class))
                .collect(Collectors.toList());
        return new ResponseEntity<>(tags, HttpStatus.OK);
    }

    @GetMapping("/tag/{id}")
    public ResponseEntity<TagDTO> getTagById(@PathVariable(value = "id") Long tagId) {
        Tag tag = this.tagService.getTagById(tagId);
        TagDTO tagResponse = this.modelMapper.map(tag, TagDTO.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    @PostMapping("/tags")
    public ResponseEntity<TagDTO> createTag(@RequestBody @Valid TagDTO tagDTO) {
        Tag tagRequest = this.modelMapper.map(tagDTO, Tag.class);
        Tag tag = this.tagService.createTag(tagRequest);
        TagDTO tagResponse = this.modelMapper.map(tag, TagDTO.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.CREATED);
    }

    @PostMapping("/tag/{id}")
    public ResponseEntity<TagDTO> updateTagById(@PathVariable(value = "id") Long tagId, @RequestBody @Valid TagDTO tagDTO) {
        Tag tagRequest = this.modelMapper.map(tagDTO, Tag.class);
        Tag tag = this.tagService.updateTag(tagId, tagRequest);
        TagDTO tagResponse = this.modelMapper.map(tag, TagDTO.class);
        return new ResponseEntity<>(tagResponse, HttpStatus.OK);
    }

    @DeleteMapping("/tag/{id}")
    public ResponseEntity<Response> removeTagById(@PathVariable(value = "id") Long tagId) {
        this.tagService.deleteTag(tagId);

        Response response = new Response(HttpStatus.OK, ResponseMessageType.SUCCESS.toString());
        response.addDetail("tag", "The tag has been removed.");

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
