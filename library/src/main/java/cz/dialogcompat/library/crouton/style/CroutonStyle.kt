package cz.dialogcompat.library.crouton.style

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import cz.dialogcompat.library.R
import cz.dialogcompat.library.crouton.Crouton
import cz.dialogcompat.library.crouton.model.CroutonItem

/**
 * Created by cz on 11/22/16.
 */

class CroutonStyle : Style() {

    override fun getLayoutRect(view: View, parentRect: Rect, outRect: Rect): Rect {
        val layoutParams = view.layoutParams as ViewGroup.MarginLayoutParams
        return Rect(parentRect.left + layoutParams.leftMargin, parentRect.top - outRect.height(), parentRect.right - layoutParams.rightMargin, parentRect.top)
    }

    override fun getPopInAnimator(view: View): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(view, "translationY", view.measuredHeight*1f)
        objectAnimator.duration = 300
        return objectAnimator
    }

    override fun getPopOutAnimator(view: View): ObjectAnimator {
        val objectAnimator = ObjectAnimator.ofFloat(view, "translationY", 0f)
        objectAnimator.duration = 200
        return objectAnimator
    }

    override fun getView(context: Context, parent: ViewGroup, item: CroutonItem): View {
        val inflateView = LayoutInflater.from(context).inflate(R.layout.prompt_crouton_layout, parent, false)
        if (Crouton.NO_COLOR != item.backgroundColor) {
            inflateView.setBackgroundColor(item.backgroundColor)
        }
        val textView = inflateView as TextView
        textView.text = item.text
        return inflateView
    }


}
