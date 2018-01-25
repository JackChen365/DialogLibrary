package cz.dialogcompat.library.bottom

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import cz.dialogcompat.library.R

/**
 * 底部弹窗的对象框
 */
open class BottomDialog(context: Context) : AlertDialog(context,R.style.BottomDialog) {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val window = window
        val params = window.attributes
        params.windowAnimations = R.style.BottomDialog_AnimationStyle
        params.width = WindowManager.LayoutParams.MATCH_PARENT
        params.height = WindowManager.LayoutParams.WRAP_CONTENT
        params.gravity = Gravity.BOTTOM
    }

    override fun show() {
        try{
            super.show()
        } catch (e:Exception){
            e.printStackTrace()
        }
    }

    override fun dismiss() {
        try{
            super.dismiss()
        } catch (e:Exception){
            e.printStackTrace()
        }
    }
}