package cz.dialogcompat.library.depend

import android.support.annotation.IdRes
import android.support.annotation.LayoutRes
import android.view.*
import cz.dialogcompat.library.depend.transition.PopupTransition
import cz.dialogcompat.library.depend.transition.TopPopupTransition
import cz.dialogcompat.library.depend.widget.PopupLayout

/**
 * Created by cz on 2017/12/13.
 * 一个快捷面板,指定依赖于屏幕中的一个容器,提供阴影层,以及多层级展示,以及类dialog操作.
 * 为简休屏幕内大量的自定义面板
 * 提供基本4种方位,上下左右,默认向下
 * 提供基本的面板组控件
 * 提供面板之间的互斥关系
 */
class PopupPanel {
    companion object {
        val NONE=0
        val LEFT =1
        val TOP =2
        val RIGHT =3
        val BOTTOM =4
    }
    private val layout:PopupLayout

    constructor(layout:PopupLayout){
        this.layout=layout
    }

    constructor(builder:Builder){
        val layout = builder.layout
        //添加控件
        this.layout =layout
        val context = layout.context
        var frame=builder.frame
        val frameLayout=builder.layout
        if(null!=builder.layout){
            //初始化view
            frame=PopupFrame(context)
            frame.setContentView(frameLayout)
        } else if(-1!=builder.layoutRes){
            val inflateView = LayoutInflater.from(context).inflate(builder.layoutRes, layout, false)
            frame=PopupFrame(context)
            frame.setContentView(inflateView)
        }
        //添加依赖关系
        if(null!=frame){
            val view=frame.view
            when(builder.align){
                LEFT,RIGHT->{
                    val horizontalLayoutParams=PopupLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.MATCH_PARENT)
                    horizontalLayoutParams.layoutAlign=builder.align
                    view?.layoutParams=horizontalLayoutParams
                }
                TOP,BOTTOM->{
                    val verticalLayoutParams=PopupLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT)
                    verticalLayoutParams.layoutAlign=builder.align
                    view?.layoutParams=verticalLayoutParams
                }
            }
            //设置转换器
            frame.setTransition(builder.transition)
            //添加当前桢
            layout.addFrame(frame)
        }
    }

    /**
     * 添加一个弹出桢
     */
    fun addFrame(frame:PopupFrame){
        layout.addFrame(frame)
    }

    /**
     * 当前是否显示
     */
    fun isShowing(@IdRes id:Int): Boolean {
        val frameItem=layout.getFrame(id)
        return frameItem?.isShowing()?:false
    }

    /**
     * 显示所有面板
     */
    fun show()=layout.show()

    /**
     * 显示指定面板
     */
    fun show(@IdRes id:Int)=layout.show(id)

    /**
     * 隐藏显示所有面板
     */
    fun dismiss()=layout.dismiss()

    fun dismiss(@IdRes id:Int)=layout.dismiss(id)

    fun setOnDismissListener(listener: PopupLayout.OnDismissListener){
        this.layout.setOnDismissListener(listener)
    }

    class Builder(val layout:PopupLayout){
        var align= NONE
        var view:View?=null
        var layoutRes:Int=-1
        var frame: PopupFrame?=null
        var transition: PopupTransition= TopPopupTransition()

        fun setFrame(@LayoutRes layout:Int):Builder{
            this.layoutRes=layout
            return this
        }

        fun setFrame(view: View):Builder{
            this.view=view
            return this
        }

        fun setFrame(frame: PopupFrame):Builder{
            this.frame=frame
            return this
        }

        fun setAlign(align: Int):Builder{
            this.align=align
            return this
        }

        fun setTransition(transition:PopupTransition):Builder{
            this.transition=transition
            return this
        }

        fun build(): PopupPanel = PopupPanel(this)
    }

}