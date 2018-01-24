package cz.dialogcompat.library.crouton.callback;

import android.view.View;

/**
 * Created by cz on 11/21/16.
 */

public interface LifeCycleCallback {

    void onCreated(View view, String text);

    void onDismiss(View view);
}
