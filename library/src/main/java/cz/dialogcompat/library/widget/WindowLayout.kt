package cz.dialogcompat.library.widget

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.AttributeSet
import android.view.KeyEvent
import android.view.MotionEvent
import android.view.WindowManager
import android.widget.FrameLayout

/**
 * Created by cz on 2018/1/7.
 * 本类只为实现一个功能,透过WindowManager,将一个view添加到顶层,然后劫持返回键,并暴露出去监听,让所有的控件,以及Fragment支持返回键的劫持
 * 功能实现来自于PopupWindow
 */
class WindowLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : FrameLayout(context, attrs, defStyleAttr) {
    //windowManager
    private val windowManager = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    private val listenerItems= mutableListOf<OnBackPressListener>()
    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null,0)

    init {
//        alpha=0f
        setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
    }

    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyDispatcherState == null) {
                return super.dispatchKeyEvent(event)
            }
            if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
                val state = keyDispatcherState
                state?.startTracking(event, this)
                windowManager.removeView(this)
                return true
            } else if (event.action == KeyEvent.ACTION_UP) {
                val state = keyDispatcherState
                return false
                if (state != null && state.isTracking(event) && !event.isCanceled) {
//                    dismissListener?.onDismiss()
                    return true
                }
            }
            return super.dispatchKeyEvent(event)
        } else {
            return super.dispatchKeyEvent(event)
        }
    }

    fun getWindowLayoutParams():WindowManager.LayoutParams {
        val p = WindowManager.LayoutParams()
        p.width=0
        p.height=0
        var curFlags = p.flags
        p.flags = curFlags and (WindowManager.LayoutParams.FLAG_IGNORE_CHEEK_PRESSES or
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE or
                WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE or
                WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH or
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS or
                WindowManager.LayoutParams.FLAG_ALT_FOCUSABLE_IM or
                WindowManager.LayoutParams.FLAG_SPLIT_TOUCH).inv()
        return p
    }

    fun addOnBackPressListener(listener:OnBackPressListener){
        this.listenerItems.add(listener)
    }

    interface OnBackPressListener{
        fun onBackPress()
    }


}