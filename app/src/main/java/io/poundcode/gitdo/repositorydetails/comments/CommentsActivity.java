package io.poundcode.gitdo.repositorydetails.comments;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import io.poundcode.gitdo.R;

public class CommentsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        getFragmentManager()
            .beginTransaction()
            .add(R.id.container, new CommentsFragment())
            .commit();
    }
}
