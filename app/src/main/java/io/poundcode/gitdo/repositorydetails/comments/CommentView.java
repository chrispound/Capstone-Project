package io.poundcode.gitdo.repositorydetails.comments;

import java.util.List;

import io.poundcode.androidgithubapiwrapper.comment.GitHubComment;

public interface CommentView {
    void updateAndDisplayComments(List<GitHubComment> commentList);
}
