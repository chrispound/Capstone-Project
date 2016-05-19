package io.poundcode.gitdo.repositorieslist;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Window;

import io.poundcode.gitdo.R;

public class AddRepositoryDialog extends Dialog {

    public AddRepositoryDialog(Context context) {
        super(context);
    }

    public AddRepositoryDialog(Context context, int themeResId) {
        super(context, themeResId);
    }

    protected AddRepositoryDialog(Context context, boolean cancelable, OnCancelListener cancelListener) {
        super(context, cancelable, cancelListener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.dialog_add_repository);

    }


}
