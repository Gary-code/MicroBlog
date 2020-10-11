package com.gary.microblog.service;

import com.gary.microblog.entity.Tag;
import com.gary.microblog.entity.Type;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.validation.constraints.Null;
import java.util.List;

public interface TagService {
    Tag saveTag(Tag tag);

    Tag getTag(Long id);

    Page<Tag> listTag(Pageable pageable);

    Tag updateTag(Tag tag,Long id);

    void deleteTag(Long id);

    Tag getTagByName(String name);

    List<Tag> listTag();
    //逗号隔开的多个id
    List<Tag> ListTag(String ids);

    List<Tag> findTopTag(Integer size);
}
