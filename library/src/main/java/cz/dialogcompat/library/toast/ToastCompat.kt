package cz.dialogcompat.library.toast

import android.content.Context
import android.os.Handler
import android.os.Looper
import android.support.annotation.StringRes
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import cz.dialogcompat.library.R
import cz.dialogcompat.library.widget.SuccessTickView

/**
 * @author cz
 */
object ToastCompat {
    val SHORT_DURATION_TIMEOUT: Long = 1000
    val LONG_DURATION_TIMEOUT: Long = 5000

    private val handler: Handler = Handler(Looper.getMainLooper())
    private val toastValues = mutableListOf<String>()

    fun toast(context:Context,@StringRes resId: Int, vararg params: Any) {
        toast(context,context.getString(resId, params))
    }

    fun toast(context:Context,text: String) {
        frequently(text){
            if (Looper.getMainLooper() == Looper.myLooper()) {
                android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT).show()
            } else {
                Looper.prepare()
                android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT).show()
                Looper.loop()
            }
        }
    }



    /**
     * 显示自定义弹出窗
     *
     * @param res
     */
    fun customToast(context:Context,@StringRes res: Int, duration: Int = android.widget.Toast.LENGTH_SHORT) {
        customToast(context,context.getString(res), duration)
    }

    /**
     * 显示自定义弹出窗
     *
     * @param res
     */
    fun customToast(context:Context,@StringRes res: Int, vararg params: Any) {
        customToast(context,context.getString(res,params))
    }


    /**
     * 显示自定义弹出窗
     * @param text
     * @param duration  @see #LENGTH_SHORT @see #LENGTH_LONG
     */
    fun customToast(context:Context,text: String, duration: Int = android.widget.Toast.LENGTH_SHORT) {
        frequently(text){
            val layoutInflate = LayoutInflater.from(context)
            val toastView = layoutInflate.inflate(R.layout.prompt_toast_custom, null)
            val localTextView = toastView.findViewById(R.id.toastContent) as TextView
            localTextView.text = text
            val toast = android.widget.Toast(context)
            toast.duration = duration
            toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0)
            toast.view = toastView
            toast.show()
        }
    }


    /**
     * 显示自定义弹出窗
     *
     * @param res
     */
    fun showSuccessToast(context:Context,@StringRes res: Int) {
        showSuccessToast(context,context.getString(res))
    }

    /**
     * 显示自定义弹出窗
     *
     * @param msg
     */
    fun showSuccessToast(context:Context,text: String) {
        frequently(text) {
            val toast = android.widget.Toast.makeText(context, text, android.widget.Toast.LENGTH_SHORT)
            toast.setGravity(Gravity.CENTER, Gravity.CENTER_HORIZONTAL, Gravity.CENTER_VERTICAL)
            val view = View.inflate(context, R.layout.prompt_success_item, null)
            val tickView = view.findViewById(R.id.successTick) as SuccessTickView
            tickView.startTickAnim()
            val textView = view.findViewById(R.id.successText) as TextView
            textView.text = text
            toast.view = view
            toast.show()
        }
    }


    /**
     * 阻塞相同文本的toast信息
     */
    private fun frequently(text: String,action:()->Unit) {
        if (!toastValues.contains(text)) {
            toastValues.add(text)//添加正在执行吐司对象
            action()//执行任务
            //短吐司2秒,后移除对象.
            handler.postDelayed({ toastValues.remove(text) }, SHORT_DURATION_TIMEOUT)
        }
    }


}