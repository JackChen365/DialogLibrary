package cz.dialogcompat.library.depend.transition

import android.animation.Animator
import android.view.View

/**
 * Created by cz on 2017/12/13.
 */
interface PopupTransition {
    /**
     * 进入动画体
     */
    fun enterAnimator(target: View):Animator
    /**
     * 退出动画体
     */
    fun exitAnimator(target:View):Animator

    /**
     * 当前是否显示
     */
    fun isShowing(target:View):Boolean
}