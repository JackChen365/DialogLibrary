package cz.dialogcompat.library.depend.widget

import android.content.Context
import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.SparseArray
import android.view.*
import android.widget.RelativeLayout
import cz.dialogcompat.library.R
import cz.dialogcompat.library.depend.PopupFrame
import cz.dialogcompat.library.depend.PopupPanel
import cz.dialogcompat.library.depend.transition.BottomPopupTransition
import cz.dialogcompat.library.depend.transition.LeftPopupTransition
import cz.dialogcompat.library.depend.transition.RightPopupTransition
import cz.dialogcompat.library.depend.transition.TopPopupTransition

/**
 * Created by cz on 2018/1/6.
 */
class PopupLayout(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : ConstraintLayout(context, attrs, defStyleAttr) {
    //弹窗消息监听
    private var dismissListener: PopupPanel.OnDismissListener?=null
    //桢列表集
    private var frameItems= SparseArray<PopupFrame>()
    //当前内容体
    private lateinit var contentView:View
    //蒙板层
    private val space:View = View(context)

    private var cancelOutSide=false
    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null,0)

    init {
        space.alpha=1f
        space.isClickable=false
        space.setBackgroundColor(0xFF0000)
        space.setOnClickListener {
            //点击隐藏桢

        }
    }

    override fun generateDefaultLayoutParams(): LayoutParams {
        return LayoutParams(super.generateDefaultLayoutParams() as ConstraintLayout.LayoutParams)
    }

    override fun generateLayoutParams(attrs: AttributeSet?): LayoutParams {
        val layoutParams=LayoutParams(super.generateLayoutParams(attrs))
        context.obtainStyledAttributes(attrs, R.styleable.PopupDecorView).apply {
            layoutParams.layoutAlign=getInt(R.styleable.PopupDecorView_layout_align,PopupPanel.NONE)
            recycle()
        }
        return layoutParams
    }

    override fun generateLayoutParams(p: ViewGroup.LayoutParams?): ViewGroup.LayoutParams {
        return LayoutParams(p)
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        //提取出contentView,有且只能有一个
        val contentViews=(0 until childCount).
                map { getChildAt(it) }.
                filter { view->
                    val layoutParams=view.layoutParams
                    layoutParams is LayoutParams&&PopupPanel.NONE==layoutParams.layoutAlign
                }
        if(contentViews.isEmpty()){
            //判定依赖内容控件体是否为空
            throw NullPointerException("Content View is null!")
        } else if(1<contentViews.size){
            throw NullPointerException("Content View < 1!")
        } else {
            contentView=contentViews.first()
        }
        //组织相对于contentView的依赖关系
        for(i in 0 until childCount){
            val childView=getChildAt(i)
            val layoutParams=childView.layoutParams
            if(childView!=contentView&&layoutParams is LayoutParams){
                //添加当前view桢
                val frameItem=PopupFrame(childView)
                addFrameInner(frameItem,contentView)
            }
        }
        //添加空间
        val index=indexOfChild(contentView)
        addView(space,ConstraintLayout.LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT))
    }

    fun addFrame(frame:PopupFrame){
        //添加桢view
        addView(frame.view)
        //添加到桢集合
        addFrameInner(frame,contentView)
    }

    /**
     * 根据id移除管理桢
     */
    fun removeFrame(@IdRes id:Int){
        val findFrame=frameItems.get(id)
        if(null!=findFrame){
            removeFrame(findFrame)
        }
    }

    /**
     * 移除管理桢
     */
    fun removeFrame(frame:PopupFrame){
        //回调事件
        frame.onFrameDestroy()
        //移除控件
        removeView(frame.view)
        //移除桢
        frameItems.remove(frame.view.id)
    }

    private fun addFrameInner(frameItem: PopupFrame, contentView: View) {
        val childView=frameItem.view
        val layoutParams=childView.layoutParams
        if(layoutParams is LayoutParams){
            frameItems.put(childView.id, frameItem)
            //桢创建事件回传
            frameItem.onFrameCreated(context,this)
            when (layoutParams.layoutAlign) {
                PopupPanel.LEFT -> {
                    frameItem.setTransition(LeftPopupTransition())
                    layoutParams.rightToLeft=contentView.id
                }
                PopupPanel.RIGHT -> {
                    frameItem.setTransition(RightPopupTransition())
                    layoutParams.leftToRight=contentView.id
                }
                PopupPanel.BOTTOM -> {
                    frameItem.setTransition(BottomPopupTransition())
                    layoutParams.topToBottom=contentView.id
                }
                else -> {
                    frameItem.setTransition(TopPopupTransition())
                    layoutParams.bottomToTop=contentView.id
                }
            }
            layoutParams.validate()
        }
    }

    fun getFrame(@IdRes id:Int):PopupFrame?=frameItems.get(id)

    fun getFrames()=frameItems

    fun setCanceledOnTouchOutside(cancel:Boolean){
        this.cancelOutSide=cancel
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        val rootView=rootView
    }

    fun setOnDismissListener(listener: PopupPanel.OnDismissListener){
        this.dismissListener=listener
    }

    class LayoutParams(source: ConstraintLayout.LayoutParams?) : ConstraintLayout.LayoutParams(source) {
        var layoutAlign=PopupPanel.TOP

        constructor(width: Int, height: Int):this(ConstraintLayout.LayoutParams(width,height))
    }

}