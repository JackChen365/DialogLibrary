package cz.dialogcompat.library.crouton.model

import android.graphics.Rect

import cz.dialogcompat.library.crouton.Crouton
import cz.dialogcompat.library.crouton.callback.LifeCycleCallback
import cz.dialogcompat.library.crouton.style.CroutonStyle
import cz.dialogcompat.library.crouton.style.Style


/**
 * Created by czz on 2016/11/21.
 */

class CroutonItem {
    //显示样式
    var style: Style = CroutonStyle()
    //停留时间
    var duration: Int = 0
    //过滤掉相同文字,相同style
    var filter: Boolean = false
    //提示文字
    var text: String? = null
    var backgroundColor: Int = 0
    //按钮提示文字
    var actionText: String? = null
    //当前展示控件矩阵
    var outRect=Rect()
    //映射控件矩阵(可以理解为父控件
    var parentRect=Rect()
    //生命周期回调事件
    var callback: LifeCycleCallback? = null

    init {
        backgroundColor = Crouton.NO_COLOR
    }

    override fun equals(o: Any?): Boolean {
        if (this === o) return true
        if (o == null || javaClass != o.javaClass) return false
        val that = o as CroutonItem?
        return equalsObject(style, that?.style) && equalsObject(text, that?.text)
    }

    private fun equalsObject(a: Any?, b: Any?): Boolean {
        return if (a == null) b == null else a == b
    }

}
