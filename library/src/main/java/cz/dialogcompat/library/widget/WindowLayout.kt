package cz.dialogcompat.library.widget

import android.content.Context
import android.util.AttributeSet
import android.view.KeyEvent
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
    private var listener:OnBackPressListener?=null
    private var isAttachToWindow=false
    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null,0)

    /**
     * 装载入窗体
     */
    fun attachedToWindow(){
        if(!isAttachToWindow){
            isAttachToWindow=true
            val layoutParams = WindowManager.LayoutParams()
            //使浮层没有大小
            layoutParams.width=0
            layoutParams.height=0
            //设置不可触摸,使事件往其他Window传递
            layoutParams.flags = WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE
            windowManager.addView(this,layoutParams)
        }
    }

    /**
     * 装载入窗体
     */
    fun detachedFromWindow(){
        if(isAttachToWindow){
            isAttachToWindow=false
            windowManager.removeView(this)
        }
    }


    /**
     * 本方法在常规控件体内,是不生效的.只有被放入WindowManager之后,才会生效
     */
    override fun dispatchKeyEvent(event: KeyEvent): Boolean {
        if (event.keyCode == KeyEvent.KEYCODE_BACK) {
            if (keyDispatcherState == null) {
                return super.dispatchKeyEvent(event)
            }
            if (event.action == KeyEvent.ACTION_DOWN && event.repeatCount == 0) {
                val state = keyDispatcherState
                state?.startTracking(event, this)
                return true
            } else if (event.action == KeyEvent.ACTION_UP) {
                val state = keyDispatcherState
                if (state != null && state.isTracking(event) && !event.isCanceled) {
                    if(listener?.onBackPress() == true){
                        //移除当前window窗体
                        detachedFromWindow()
                    }
                    return true
                }
            }
            return super.dispatchKeyEvent(event)
        } else {
            return super.dispatchKeyEvent(event)
        }
    }

    fun setOnBackPressListener(listener:OnBackPressListener){
        this.listener=listener
    }

    interface OnBackPressListener{
        fun onBackPress():Boolean
    }

}