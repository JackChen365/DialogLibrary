package cz.dialogcompat.sample.bottomdialog

import android.content.Context
import android.os.Bundle
import cz.dialogcompat.library.bottom.BottomDialog
import cz.dialogcompat.sample.R

/**
 * Created by cz on 2018/1/25.
 */
class SharedDialog(context: Context) : BottomDialog(context) {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_shared)
    }
}