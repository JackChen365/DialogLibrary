package cz.dialogcompat.library.popup

import android.content.Context
import android.view.View
import android.view.ViewGroup
import cz.dialogcompat.library.popup.transition.PopupTransition
import cz.dialogcompat.library.popup.widget.PopupLayout
import cz.dialogcompat.library.widget.WindowLayout

/**
 * Created by cz on 2018/1/6.
 * 弹出桢
 */
open class PopupFrame(val context:Context){
    private lateinit var transition:PopupTransition
    private val windowLayout=WindowLayout(context)
    var view:View?=null

    /**
     * 设置内容体布局
     */
    internal fun setContentView(view:View){
        this.view=view
    }

    /**
     * 创建桢控件
     */
    open fun onCreateFrameView(context:Context,parent: ViewGroup):View?=null

    /**
     * 桢创建
     */
    open fun onFrameCreated(context: Context,parent: View)=Unit

    /**
     * 桢销毁
     */
    open fun onFrameDestroy()=Unit

    /**
     * 桢显示
     */
    open fun onFrameShown(context:Context,parent:PopupLayout){
        windowLayout.attachedToWindow()
        windowLayout.setOnBackPressListener(object :WindowLayout.OnBackPressListener{
            override fun onBackPress(): Boolean {
                parent.dismiss(view?.id?:View.NO_ID)
                return true
            }
        })
    }

    /**
     * 桢关闭时
     */
    open fun onFrameDismiss(context:Context,parent:View){
        windowLayout.detachedFromWindow()
    }

    /**
     * 设置面板转换器
     */
    fun setTransition(transition:PopupTransition){
        this.transition=transition
    }

    fun getTransition()=transition

    /**
     * 当前桢是否显示
     */
    fun isShowing():Boolean{
        val view=view
        var isShowing=false
        if(null!=view){
            isShowing=transition.isShowing(view)
        }
        return isShowing
    }
}