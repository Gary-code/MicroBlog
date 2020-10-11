/**
 * Authore by Gary on 2020/07/28.
 **/

package com.gary.microblog.service;

import com.gary.microblog.dao.TagRepository;
import com.gary.microblog.entity.Tag;
import com.gary.microblog.exception.NotFoundException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service
public class TagServiceImpl implements TagService {


    @Autowired
    TagRepository tagRepository;

    @Transactional
    @Override
    public Tag saveTag(Tag tag) {
        return tagRepository.save(tag);
    }

    @Transactional
    @Override
    public Tag getTag(Long id) {
        return tagRepository.findById(id).orElse(null);
    }

    @Transactional
    @Override
    public Page<Tag> listTag(Pageable pageable) {
        return tagRepository.findAll(pageable);
    }

    @Transactional
    @Override
    public Tag updateTag(Tag tag, Long id) {
        Tag t=tagRepository.findById(id).orElse(null);
        if(t==null){
            throw new NotFoundException("不存在该标签");
        }
        BeanUtils.copyProperties(tag, t);
        return tagRepository.save(t);
    }

    @Transactional
    @Override
    public void deleteTag(Long id) {
        tagRepository.deleteById(id);
    }

    @Transactional
    @Override
    public Tag getTagByName(String name) {
        return tagRepository.findByName(name);
    }

    @Override
    public List<Tag> listTag() {
        return tagRepository.findAll();
    }

    private List<Long> convertToList(String ids){
        List<Long> list=new ArrayList<>();
        if(!"".equals(ids)&& ids!=null){
            String [] idarray =ids.split(",");
            for(int i=0;i<idarray.length;++i){
                list.add(new Long(idarray[i]));
            }
        }
        return list;
    }

    @Override
    public List<Tag> ListTag(String ids) {
        return tagRepository.findAllById(convertToList((ids)));
    }

    @Override
    public List<Tag> findTopTag(Integer size) {
        Sort sort=Sort.by(Sort.Direction.DESC, "blogs.size");
        Pageable pageable= PageRequest.of(0, size, sort);
        return tagRepository.findTop(pageable);
    }
}
