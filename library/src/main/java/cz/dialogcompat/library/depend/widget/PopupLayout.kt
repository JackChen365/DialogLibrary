package cz.dialogcompat.library.depend.widget

import android.content.Context
import android.support.annotation.IdRes
import android.support.constraint.ConstraintLayout
import android.util.AttributeSet
import android.util.SparseArray
import android.view.*
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
    //桢列表集
    private var frameItems= SparseArray<PopupFrame>()
    //当前内容体
    private lateinit var contentView:View
    //上一桢id
    private var lastFrameId:Int=View.NO_ID
    //隐藏回调事件
    private var listener: PopupLayout.OnDismissListener?=null
    //蒙板层
    private val space:View = View(context)
    constructor(context: Context, attrs: AttributeSet?):this(context,attrs,0)
    constructor(context: Context):this(context,null,0)

    init {
        space.alpha=0f
        space.isClickable=false
        space.setBackgroundResource(R.color.alpha_black)
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
                //添加当前view桢,并使其拦截点击
                childView.isClickable=true
                val frameItem=PopupFrame(context)
                frameItem.setContentView(childView)
                addFrameInner(frameItem,contentView)
            }
        }
        //添加阴影空间层
        addView(space,indexOfChild(contentView)+1,LayoutParams(ConstraintLayout.LayoutParams.MATCH_PARENT,ConstraintLayout.LayoutParams.MATCH_PARENT))
    }

    fun addFrame(frame:PopupFrame){
        //确认frameView
        ensureFrameView(frame)
        //添加桢view
        addView(frame.view)
        //添加到桢集合
        addFrameInner(frame,contentView)
    }

    /**
     * 确认frameView
     */
    private fun ensureFrameView(frame: PopupFrame) {
        if (null == frame.view) {
            val frameView = frame.onCreateFrameView(context, this)
            if (null == frameView) {
                throw NullPointerException("Frame view is null!")
            } else {
                //记录view
                frame.view = frameView
            }
        }
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
        val frameView=frame.view?:return
        //回调事件
        frame.onFrameDestroy()
        //移除控件
        removeView(frameView)
        //移除桢
        frameItems.remove(frameView.id)
    }

    private fun shadow(){
        space.isClickable = true
        space.animate().alpha(1f)
    }

    private fun hideShadow(){
        space.isClickable=false
        space.animate().alpha(0f)
    }

    /**
     * 显示所有面板
     */
    fun show(){
        val frameItems=frameItems
        if(null!=frameItems){
            for(i in 0 until frameItems.size()){
                show(frameItems.keyAt(i))
            }
        }
    }

    /**
     * 显示指定面板
     */
    fun show(@IdRes id:Int){
        val frameItem=getFrame(id)
        if(null!=frameItem){
            //隐藏上一桢
            dismissLastFrame(id)
            val frameView=frameItem.view
            val transition = frameItem.getTransition()
            if(null!=frameView&&null!=transition&&!transition.isShowing(frameView)){
                //显示阴影层
                shadow()
                //点击隐藏
                space.setOnClickListener { dismiss(id) }
                //执行进入动画
                transition.enterAnimator(frameView).start()
                //回调桢面板显示
                frameItem.onFrameShown(context,this)
            }
        }
    }

    /**
     * 隐藏上一桢
     */
    private fun dismissLastFrame(id: Int) {
        val last = lastFrameId
        //记录当前桢id
        lastFrameId = id
        //隐藏上一桢
        if (View.NO_ID != last && id != last) {
            dismiss(last)
        }
    }

    /**
     * 隐藏显示所有面板
     */
    fun dismiss(){
        //隐藏显示所有面板
        val frameItems = frameItems
        for(i in 0 until frameItems.size()){
            dismiss(frameItems.keyAt(i))
        }
    }

    fun dismiss(@IdRes id:Int){
        //隐藏指定id的面板桢
        val frameItem=getFrame(id)
        if(null!=frameItem){
            val frameView=frameItem.view
            val transition = frameItem.getTransition()
            if(null!=frameView&&null!=transition){
                //显示阴影层
                hideShadow()
                //执行弹出动画
                transition.exitAnimator(frameView).start()
                //回调桢面板显示
                frameItem.onFrameDismiss(context,this)
                //回调事件
                listener?.onDismiss()
            }
        }
    }

    private fun addFrameInner(frameItem: PopupFrame, contentView: View) {
        val childView=frameItem.view
        val layoutParams=childView?.layoutParams
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

    class LayoutParams(source: ConstraintLayout.LayoutParams?) : ConstraintLayout.LayoutParams(source) {
        var layoutAlign=PopupPanel.TOP
        constructor(width: Int, height: Int):this(ConstraintLayout.LayoutParams(width,height))
    }

    fun setOnDismissListener(listener: PopupLayout.OnDismissListener){
        this.listener=listener
    }

    interface OnDismissListener {
        fun onDismiss()
    }

}