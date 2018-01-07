package cz.dialogcompat.library.bottom

import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import cz.dialogcompat.library.R

/**
 * 底部弹窗的对象框
 */
open class BottomDialog : DialogFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.BottomDialog)
        val window = dialog.window
        window.requestFeature(Window.FEATURE_NO_TITLE)
        val params = window.attributes
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.BOTTOM
        window.attributes = params
    }

    fun show(fragmentManager: FragmentManager) {
        if(!fragmentManager.isDestroyed){
            super.show(fragmentManager, this::class.java.simpleName)
        }
    }
}