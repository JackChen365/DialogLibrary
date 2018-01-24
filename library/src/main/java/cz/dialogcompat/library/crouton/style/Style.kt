package cz.dialogcompat.library.crouton.style

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup

import cz.dialogcompat.library.crouton.model.CroutonItem


/**
 * Created by cz on 11/22/16.
 */
abstract class Style {

    abstract fun getLayoutRect(view: View, parentRect: Rect, outRect: Rect): Rect
    /**
     * 获得进入动画
     * @return
     */
    abstract fun getPopInAnimator(view: View): ObjectAnimator

    /**
     * 获取弹出动画
     * @return
     */
    abstract fun getPopOutAnimator(view: View): ObjectAnimator

    /**
     * 产生当前样式弹出view组
     * @param context
     * @return
     */
    abstract fun getView(context: Context, parent: ViewGroup, item: CroutonItem): View
}
