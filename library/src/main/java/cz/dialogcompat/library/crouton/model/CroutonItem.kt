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
    var style: Style = Crouton.croutonStyle
    //停留时间
    var duration: Int = Crouton.SHORT_TIME
    //过滤掉相同文字,相同style
    var filter: Boolean = true
    //提示文字
    var text: String? = null
    var backgroundColor: Int = 0
    //生命周期回调事件
    internal var callback: LifeCycleCallback? = null
    //当前展示控件矩阵
    internal var outRect=Rect()
    //映射控件矩阵(可以理解为父控件
    internal var parentRect=Rect()

    init {
        backgroundColor = Crouton.NO_COLOR
    }

    fun setLifeCycleCallback(callback:LifeCycleCallback){
        this.callback=callback
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
