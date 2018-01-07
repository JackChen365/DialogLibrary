package cz.dialogcompat.library.depend.transition

import android.animation.Animator
import android.animation.ObjectAnimator
import android.view.View
import android.view.animation.LinearInterpolator

/**
 * Created by cz on 2017/12/13.
 */
class LeftPopupTransition: PopupTransition {

    override fun enterAnimator(target: View): Animator {
        val animator= ObjectAnimator.ofFloat(target, "translationX", target.width * 1f)
        animator.interpolator= LinearInterpolator()
        return animator
    }

    override fun exitAnimator(target: View): Animator {
        val animator= ObjectAnimator.ofFloat(target, "translationX", 0f)
        animator.interpolator= LinearInterpolator()
        return animator
    }

    override fun isShowing(target: View)=0f!=target.translationX
}