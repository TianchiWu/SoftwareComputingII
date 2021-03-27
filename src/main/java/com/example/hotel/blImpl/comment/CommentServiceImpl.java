package com.example.hotel.blImpl.comment;

import com.example.hotel.bl.comment.CommentService;
import com.example.hotel.bl.order.OrderService;
import com.example.hotel.entity.Comment;
import com.example.hotel.repository.CommentRepository;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Service
public class CommentServiceImpl implements CommentService {
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    OrderService orderService;
    @Override
    public List<Comment> getComments(int hotelId){
        return commentRepository.findAllByHotelId(hotelId);
    }
    @Override
    public ResponseVO addComment(Comment comment){
        List<Comment> comments=getComments(comment.getHotelId());
        for (Comment value : comments) {
            if (value.getUserEmail().equals(comment.getUserEmail())) {
                return ResponseVO.buildFailure("不能重复进行评论");
            }
        }
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        comment.setCommentTime(curdate);
        commentRepository.save(comment);
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public ResponseVO deleteComment(int id){
        commentRepository.deleteById(id);
        return ResponseVO.buildSuccess(true);
    }

    @Override
    public ResponseVO updateComment(int id,double rate,String text){
        Comment comment=commentRepository.findById(id).get();
        comment.setRate(rate);
        comment.setText(text);
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        Date date = new Date(System.currentTimeMillis());
        String curdate = sf.format(date);
        comment.setCommentTime(curdate);
        commentRepository.save(comment);
        return ResponseVO.buildSuccess(true);
    }
    @Override
    public boolean enable(int hotelId, int userId){

        return orderService.hasUserHotelOrders(hotelId,userId);
    }
    @Override
    public Double getRate(int hotelId){
        List<Comment> comments=commentRepository.findAllByHotelId(hotelId);
        List<Double> target=new ArrayList<>();
        for (Comment comment : comments) {
            target.add(comment.getRate());
        }
        double rate=0.0;
        for (Double aDouble : target) {
            rate += aDouble;
        }
        if(target.size()!=0) {
            rate = rate / target.size();
        }
        String str = String.format("%.2f",rate);
        return Double.parseDouble(str);
    }
}
