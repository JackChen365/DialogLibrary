package cz.dialogcompat.sample.bottomdialog

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import cz.dialogcompat.library.bottom.BottomDialog
import cz.dialogcompat.library.bottom.BottomDialogFragment
import cz.dialogcompat.sample.R
import kotlinx.android.synthetic.main.dialog_edit_text.*

/**
 * Created by cz on 2018/1/25.
 */
class EditTextDialogFragment : BottomDialogFragment() {

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater?.inflate(R.layout.dialog_edit_text, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        editText.post{
            val imm = activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(editText, 0)
        }
    }
}