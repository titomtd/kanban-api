package fr.duchemin.sir.kanban.service;

import fr.duchemin.sir.kanban.entity.Tag;

import java.util.List;

public interface TagService {

    List<Tag> getAllTags();

    Tag getTagById(Long tagId);

    Tag createTag(Tag tag);

    Tag updateTag(Long tagId, Tag tag);

    void deleteTag(Long tagId);
}
