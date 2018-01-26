package cz.dialogcompat.library.crouton

import android.app.Activity
import android.content.Context
import android.graphics.Rect
import android.os.Build
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver

import cz.dialogcompat.library.R
import cz.dialogcompat.library.checkPromptTheme
import cz.dialogcompat.library.crouton.callback.LifeCycleCallback
import cz.dialogcompat.library.crouton.model.CroutonItem
import cz.dialogcompat.library.crouton.style.CroutonStyle
import cz.dialogcompat.library.crouton.style.Style
import cz.dialogcompat.library.crouton.widget.CroutonLayout


/**
 * Created by cz on 11/21/16.
 */
class Crouton private constructor(private val context: Context?, decorView: View?, dependView: View?, private val croutonView: View?, private val viewId: Int, croutonItem:CroutonItem?=null) {
    private val item: CroutonItem
    private var decorLayout: ViewGroup? = null

    init {
        if (null != decorView) {
            this.decorLayout = decorView as ViewGroup?
        }
        if (null != dependView) {
            dependToView(dependView)
        }
        //如果外围传入不为空,则代表己初始化,不用默认
        if(null!= croutonItem){
            this.item= croutonItem
        } else {
            this.item = CroutonItem()
            //默认顶部弹出,时间2秒
            this.item.duration = SHORT_TIME
            this.item.style = croutonStyle
        }
    }

    private fun getCroutonLayout(): CroutonLayout?{
        val decorLayout=decorLayout
        var croutonLayout: CroutonLayout? = null
        if(null!=decorLayout){
            val childCount = decorLayout.childCount
            for (i in 0 until childCount) {
                if (R.id.croutonLayout == decorLayout.getChildAt(i).id) {
                    croutonLayout = decorLayout.getChildAt(i) as CroutonLayout
                    break
                }
            }
        }
        return croutonLayout
    }



    /**
     * 设置当前弹出消息依赖view
     * @param dependView
     */
    private fun dependToView(dependView: View) {
        val viewTreeObserver = dependView.viewTreeObserver
        viewTreeObserver.addOnGlobalLayoutListener(object : ViewTreeObserver.OnGlobalLayoutListener {
            override fun onGlobalLayout() {
                if (dependView === dependView.rootView || View.GONE == dependView.visibility) {
                    //除移全局布局监听
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.JELLY_BEAN) {
                        viewTreeObserver.removeGlobalOnLayoutListener(this)
                    } else {
                        viewTreeObserver.removeOnGlobalLayoutListener(this)
                    }
                    //隐藏己弹出的消息
                    val croutonLayout = getCroutonLayout()
                    croutonLayout?.removeAllCrouton()
                }
            }
        })
    }

    fun text(text: String): Crouton {
        this.item.text = text
        return this
    }

    fun text(@StringRes res: Int): Crouton {
        if (null != context) {
            this.item.text = context.getString(res)
        }
        return this
    }

    fun filter(filter: Boolean): Crouton {
        this.item.filter = filter
        return this
    }


    fun style(style: Style): Crouton {
        this.item.style = style
        return this
    }

    fun duration(duration: Int): Crouton {
        this.item.duration = duration
        return this
    }

    fun callback(callback: LifeCycleCallback): Crouton {
        this.item.callback = callback
        return this
    }

    fun backgroundColor(color: Int): Crouton {
        this.item.backgroundColor = color
        return this
    }

    fun show() {
        val decorLayout=decorLayout
        if (null != decorLayout) {
            val context = decorLayout.context
            //检测样式是否配置
            context.checkPromptTheme()
            var croutonLayout = getCroutonLayout()
            if (null == croutonLayout) {
                croutonLayout = CroutonLayout(context)
                croutonLayout.id = R.id.croutonLayout
                decorLayout.addView(croutonLayout, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            }
            var findView: View? = null
            if (null != croutonView) {
                findView = croutonView
            } else if (View.NO_ID != viewId) {
                //这里之所以让控件传入到成员类,是因为要展示时,检测控件是否处于shown状态,可以解决不同fragment id重复问题
                findView = findViewById(decorLayout, viewId)
            }
            if (null != findView) {
                val locationRect = getLocationRect(findView)
                item.outRect = locationRect
                item.parentRect = Rect(locationRect)
                croutonLayout.addCroutonItem(item)
            }
        }
    }

    private fun getLocationRect(view: View): Rect {
        val location = IntArray(2)
        val outRect = Rect()
        view.getLocationInWindow(location)
        view.getWindowVisibleDisplayFrame(outRect)
        outRect.left = location[0]
        outRect.top = location[1]
        outRect.right = location[0] + view.width
        outRect.bottom = location[1] + view.height
        return outRect
    }

    companion object {
        val SHORT_TIME = 2 * 1000
        val NO_COLOR = -1

        val croutonStyle: Style=CroutonStyle()

        fun create(activity: Activity?, @IdRes id: Int, croutonItem:CroutonItem?=null): Crouton {
            var context: Context? = null
            var decorView: View? = null
            if (null != activity) {
                context = activity.applicationContext
                decorView = activity.window.decorView
            }
            return Crouton(context, decorView, null, null, id,croutonItem)
        }

        fun create(fragment: Fragment?, @IdRes id: Int, croutonItem:CroutonItem?=null): Crouton {
            var decorView: View? = null
            var dependView: View? = null
            var context: Context? = null
            if (null != fragment && null != fragment.activity) {
                val activity = fragment.activity
                context = activity.applicationContext
                decorView = activity.window.decorView
                dependView = fragment.view
            }
            return Crouton(context, decorView, dependView, null, id,croutonItem)
        }

        fun create(fragment: Fragment, view: View?, croutonItem:CroutonItem?=null): Crouton {
            var decorView: View? = null
            var dependView: View? = null
            var context: Context? = null
            if (null != view) {
                context = view.context
                dependView = fragment.view
                decorView = view.rootView
            }
            return Crouton(context, decorView, dependView, view, View.NO_ID,croutonItem)
        }

        fun create(view: View?, croutonItem:CroutonItem?=null): Crouton {
            var decorView: View? = null
            var context: Context? = null
            if (null != view) {
                context = view.context
                decorView = view.rootView
            }
            return Crouton(context, decorView, null, view, View.NO_ID,croutonItem)
        }

        /**
         * 查找到未隐藏的view.
         * 这里意图为,如果界面多个fragment,且依赖id相同.
         * 如果使用系统的findViewById则始终为第一个添加到界面的fragment内的id,
         * 所以重写findViewById,检测到非隐藏的view,然后展示,因为无论多少个fragment,几乎进行show/hide时,哪怕id相同,但只会有一个fragment展示
         * @param id
         * @return
         */
        private fun findViewById(decorView: ViewGroup, @IdRes id: Int): View? {
            val childCount = decorView.childCount
            for (i in 0 until childCount) {
                val childView = decorView.getChildAt(i)
                if (childView.isShown) {
                    if (id == childView.id) {
                        return childView
                    } else if (childView is ViewGroup) {
                        return findViewById(childView, id)
                    }
                }
            }
            return null
        }
    }


}
