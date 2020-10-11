/**
 * Authored by Gary on 2020/09/01.
 **/

package com.gary.microblog.service;

import com.gary.microblog.dao.CommentRepository;
import com.gary.microblog.entity.Comment;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    private CommentRepository commentRepository;
    @Override

    public List<Comment> listCommentByBlogId(Long blogId) {
        Sort sort= Sort.by(Sort.Direction.ASC, "createTime");
        List<Comment> comments=commentRepository.findByBlogIdAndParentCommentNull(blogId, sort);
        return eachComment(comments);
    }

    @Transactional
    @Override
    public Comment saveComment(Comment comment) {
        Long parentCommentId=comment.getParentComment().getId();
        if(parentCommentId!=-1){
            comment.setParentComment(commentRepository.findById(parentCommentId).orElse(null));
        }
        else{
            comment.setParentComment(null);
        }
        comment.setCreateTime(new Date());
        return commentRepository.save(comment);
    }

    /**
     * 循环每个顶级的节点
     * @param  comments
     * @return
     */
    private List<Comment> eachComment(List<Comment> comments){
        List<Comment> commentList=new ArrayList<>();
        for(Comment comment :comments){
            Comment c = new Comment();
            BeanUtils.copyProperties(comment, c);
            commentList.add(comment);
        }
        //合并评论的各层最高的级
        combineChildren(commentList);
        return commentList;
    }

    /**
     *
     * @param commentList root为根节点 blog不为空的对象集合
     */
    private void combineChildren(List<Comment> commentList) {
        for(Comment comment : commentList){
            List<Comment> replys1=comment.getReplyComments();
            for(Comment reply1: replys1){
                //迭代循环，再出子代存放在tempReplys中
                recursively(reply1);
            }
            //修改顶节点的reply几何为迭代处理后的集合
            comment.setReplyComments(tempReplys);
            //清除临时存放区
            tempReplys=new ArrayList<>();
        }
    }
    private List<Comment> tempReplys=new ArrayList();

    /**
     * 迭代
     * @param comment
     */
    private void recursively(Comment comment) {
        tempReplys.add(comment);//顶节点添加进来
        if(comment.getReplyComments().size()>0){
            List<Comment> replys=comment.getReplyComments();
            for(Comment reply: replys){
                tempReplys.add(reply);
                if (reply.getReplyComments().size()>0){
                    recursively(reply);
                }
            }
        }
    }
}
