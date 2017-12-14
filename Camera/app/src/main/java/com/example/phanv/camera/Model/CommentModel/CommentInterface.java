package com.example.phanv.camera.Model.CommentModel;

import java.util.List;

/**
 * Created by phanv on 14-Dec-17.
 */

public interface CommentInterface {
    void addCommentSucces(int result);
    void getListCommentSuccess(List<Comment> list);
    void getStartComment(Float count,Float avg);
}
