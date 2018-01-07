package cz.dialogcompat.library.depend

import android.content.Context
import android.view.View
import cz.dialogcompat.library.depend.transition.PopupTransition
import cz.dialogcompat.library.depend.widget.PopupLayout

/**
 * Created by cz on 2018/1/6.
 * 弹出桢
 */
class PopupFrame(val view:View){
    private lateinit var transition:PopupTransition
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
    open fun onFrameShown(parent:View)=Unit

    /**
     * 桢关闭时
     */
    open fun onFrameClosed(parent:View)=Unit

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
    fun isShowing()=transition.isShowing(view)
}