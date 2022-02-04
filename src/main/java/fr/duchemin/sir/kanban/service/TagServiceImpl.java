package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Tag;
import fr.duchemin.sir.kanban.exception.EntityNotFoundException;
import fr.duchemin.sir.kanban.exception.InternalServerException;
import fr.duchemin.sir.kanban.repository.TagRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TagServiceImpl implements TagService {

    private TagRepository tagRepository;

    public TagServiceImpl(TagRepository tagRepository) {
        this.tagRepository = tagRepository;
    }

    @Override
    public List<Tag> getAllTags() {
        List<Tag> tags = new ArrayList<>();
        this.tagRepository.findAll().iterator().forEachRemaining(tags::add);
        return tags;
    }

    @Override
    public Tag getTagById(Long tagId) {
        Optional<Tag> tagOptional = this.tagRepository.findById(tagId);

        if (tagOptional.isEmpty())
            throw new EntityNotFoundException("Tag with id " + tagId + " not found.");

        return tagOptional.get();
    }

    @Override
    public Tag createTag(Tag tag) {
        return this.tagRepository.save(tag);
    }

    @Override
    public Tag updateTag(Long tagId, Tag tag) {
        Optional<Tag> tagOptional = this.tagRepository.findById(tagId);

        if (tagOptional.isEmpty())
            throw new EntityNotFoundException("Tag with id " + tagId + " not found.");

        Tag tagResponse = tagOptional.get();

        if (null != tag.getLabel())
            tagResponse.setLabel(tag.getLabel());

        return this.tagRepository.save(tagResponse);
    }

    @Override
    public void deleteTag(Long tagId) {
        try {
            this.tagRepository.deleteById(tagId);
        } catch (EmptyResultDataAccessException exception) {
            throw new EntityNotFoundException("Tag with id " + tagId + " not found.");
        }

        if (this.tagRepository.existsById(tagId))
            throw new InternalServerException("Failed : The tag hasn't been removed.");
    }
}
