package com.example.hotel.controller.comment;

import com.example.hotel.bl.comment.CommentService;
import com.example.hotel.entity.Comment;
import com.example.hotel.entity.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("/api/comment")
public class CommentController {
    @Autowired
    private CommentService commentService;


    @GetMapping("{hotelId}/getComments")
    public ResponseVO getComments(@PathVariable int hotelId){
        return ResponseVO.buildSuccess(commentService.getComments(hotelId));
    }
    @PostMapping("addComment")
    public ResponseVO addComment(@RequestBody Comment comment){
        return commentService.addComment(comment);
    }

    @GetMapping("{hotelId}/{userId}/enable")
    public ResponseVO enable(@PathVariable int hotelId,@PathVariable int userId){
        return ResponseVO.buildSuccess(commentService.enable(hotelId,userId));
    }
    @PostMapping("{id}/deleteComment")
    public ResponseVO deleteComment(@PathVariable int id){
        return commentService.deleteComment(id);
    }

    @GetMapping("{id}/{rate}/{text}/updateComment")
    public ResponseVO updateComment(@PathVariable int id,@PathVariable double rate,@PathVariable String text){
        return commentService.updateComment(id,rate,text);
    }
}
