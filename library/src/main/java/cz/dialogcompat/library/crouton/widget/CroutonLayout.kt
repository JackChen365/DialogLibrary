package cz.dialogcompat.library.crouton.widget

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Rect
import android.graphics.Region
import android.os.Build
import android.util.AttributeSet
import android.view.View
import android.view.ViewGroup

import java.util.LinkedList

import cz.dialogcompat.library.crouton.model.CroutonItem


/**
 * Created by cz on 11/21/16.
 */
class CroutonLayout : ViewGroup {
    private val croutonItems: LinkedList<CroutonItem> = LinkedList()
    private val displayItems: LinkedList<CroutonItem> = LinkedList()
    private val croutonActionItems: LinkedList<Runnable> = LinkedList()

    constructor(context: Context) : super(context) {
        setWillNotDraw(false)
        isClickable = false
        //4.1开启硬件加速,解决clipRect失效问题
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.JELLY_BEAN) {
            setLayerType(View.LAYER_TYPE_SOFTWARE, null)
        }
    }

    /**
     * 禁止xml解析生成
     */
    @JvmOverloads constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int = 0) : super(context, attrs, defStyleAttr) {
        throw RuntimeException("not a xml widget!")
    }


    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val tag = childView.tag
            if (null != tag && tag is CroutonItem) {
                if (null != tag.outRect) {
                    val layoutParams = childView.layoutParams as ViewGroup.MarginLayoutParams
                    measureChild(childView, View.MeasureSpec.makeMeasureSpec(tag.outRect.width() - layoutParams.leftMargin - layoutParams.rightMargin, View.MeasureSpec.EXACTLY), heightMeasureSpec)
                    tag.outRect.bottom = tag.outRect.top + childView.measuredHeight//重置控件活动矩阵宽高
                }
            }
        }
    }

    override fun onLayout(b: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val tag = childView.tag
            if (null != tag && tag is CroutonItem) {
                if (null != tag.outRect) {
                    val layoutRect = tag.style.getLayoutRect(childView, tag.parentRect, tag.outRect)
                    childView.layout(layoutRect.left, layoutRect.top, layoutRect.right, layoutRect.bottom)
                }
            }

        }
    }


    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        val childCount = childCount
        for (i in 0 until childCount) {
            val childView = getChildAt(i)
            val tag = childView.tag
            if (null != tag && tag is CroutonItem) {
                canvas.clipRect(tag.style.getLayoutRect(childView, tag.parentRect, tag.outRect), Region.Op.DIFFERENCE)
            }
        }
    }

    fun addCroutonItem(item: CroutonItem?) {
        if (displayItems.isEmpty()) {
            //开始展示
            if (null != item) {
                displayItems.add(item)
                runCroutonAction(item)
            }
        } else {
            //取出展示列中,最后一个与当前想添加的比较样式,如果不相同,则直接展示
            val croutonItem = displayItems.peekLast()
            if (item!!.filter && !displayItems.contains(item)) {
                croutonItems.offerLast(item)
                if (croutonItem.style !== item.style) {
                    val pollItem = croutonItems.pollLast()
                    displayItems.add(pollItem)
                    runCroutonAction(item)
                }
            }
        }
    }

    private fun runCroutonAction(item: CroutonItem) {
        val view = item.style.getView(context, this@CroutonLayout, item)
        if (null != view) {
            val croutonAction = CroutonAction(view, item)
            croutonActionItems.offer(croutonAction)
            view.tag = item
            addView(view)
            post(croutonAction)
            item.callback?.onCreated(view, item.text)
        }
    }

    /**
     * 清空所有信息
     */
    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        removeAllCrouton()
    }

    fun removeAllCrouton() {
        removeAllViews()
        if (!displayItems.isEmpty()) {
            displayItems.clear()
        }
        if (!croutonItems.isEmpty()) {
            croutonItems.clear()
        }
        if (!croutonActionItems.isEmpty()) {
            for (action in croutonActionItems) {
                removeCallbacks(action)
            }
            croutonActionItems.clear()
        }
    }


    internal inner class CroutonAction(val view: View?, val item: CroutonItem) : Runnable {

        override fun run() {
            if (null != view && null != item.style) {
                val popInAnimator = item.style.getPopInAnimator(view)
                val popOutAnimator = item.style.getPopOutAnimator(view)
                if (null != popInAnimator) {
                    startAnimator(popInAnimator, popOutAnimator, view, item.duration)
                } else {
                    postDelayed({ startPopoutAnimator(popOutAnimator, view) }, item.duration.toLong())
                }
            }
        }

        private fun startAnimator(popInAnimator: ObjectAnimator, popOutAnimator: ObjectAnimator, view: View, duration: Int) {
            popInAnimator.addListener(object : AnimatorListenerAdapter() {
                override fun onAnimationEnd(animation: Animator) {
                    super.onAnimationEnd(animation)
                    postDelayed({ startPopoutAnimator(popOutAnimator, view) }, duration.toLong())
                }
            })
            popInAnimator.start()
        }

        private fun startPopoutAnimator(animator: ObjectAnimator?, view: View?) {
            if (null != animator) {
                animator.addListener(object : AnimatorListenerAdapter() {
                    override fun onAnimationEnd(animation: Animator) {
                        super.onAnimationEnd(animation)
                        removeCroutonAction(view)
                    }
                })
                animator.start()
            } else {
                removeCroutonAction(view)
            }
        }

        private fun removeCroutonAction(view: View?) {
            removeView(view)
            displayItems.remove(item)
            removeCallbacks(this@CroutonAction)
            croutonActionItems.remove(this@CroutonAction)
            item.callback?.onDismiss(view)
            if (!croutonItems.isEmpty()) {
                val croutonItem = croutonItems.pollFirst()
                displayItems.add(croutonItem)
                runCroutonAction(croutonItem)
            }
        }
    }


    override fun generateLayoutParams(p: ViewGroup.LayoutParams): ViewGroup.LayoutParams {
        return ViewGroup.MarginLayoutParams(p)
    }

    override fun generateLayoutParams(attrs: AttributeSet): ViewGroup.LayoutParams {
        return MarginLayoutParams(context, attrs)
    }
}
