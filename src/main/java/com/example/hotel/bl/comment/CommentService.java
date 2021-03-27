package com.example.hotel.bl.comment;
import com.example.hotel.entity.Comment;
import com.example.hotel.entity.ResponseVO;
import java.util.List;
public interface CommentService {

    /**
     * 增加评论
     * @param comment
     * @return
     */
    ResponseVO addComment(Comment comment);

    /**
     * 删除评论
     * @param id
     * @return
     */
    ResponseVO deleteComment(int id);

    /**
     * 修改评论
     * @param id
     * @param rate
     * @param text
     * @return
     */
    ResponseVO updateComment(int id,double rate,String text);

    /**
     * 获得某一酒店的所有评论
     * @param hotelId
     * @return
     */
    List<Comment> getComments(int hotelId);

    /**
     * 判断能否进行评论
     * @param hotelId
     * @param userId
     * @return
     */
    boolean enable(int hotelId, int userId);

    /**
     * 获取某酒店的评分
     * @param hotelId
     * @return
     */
    Double getRate(int hotelId);
}
