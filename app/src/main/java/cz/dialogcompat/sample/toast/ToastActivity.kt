package cz.dialogcompat.sample.toast

import android.content.Context
import android.graphics.PixelFormat
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.view.WindowManager
import android.widget.TextView
import cz.dialogcompat.library.successToast
import cz.dialogcompat.library.toast
import cz.dialogcompat.library.toast.ToastCompat
import cz.dialogcompat.sample.R
import cz.dialogcompat.sample.annotation.ToolBar
import cz.volunteerunion.ui.ToolBarActivity

import kotlinx.android.synthetic.main.activity_toast.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@ToolBar
class ToastActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_toast)
        setTitle(intent.getStringExtra("title"))

        buttonShow.setOnClickListener {
            ToastCompat.customToast(this,"自定义Toast")
            //测试以toast作为全局布局
//            val layoutInflate = LayoutInflater.from(this)
//            val toastView = layoutInflate.inflate(cz.dialogcompat.library.R.layout.prompt_toast_custom, null)
//            val localTextView = toastView.findViewById(cz.dialogcompat.library.R.id.toastContent) as TextView
//            localTextView.text = "测试弹窗"
//            localTextView.onClick {
//                toast("点击toast")
//            }
//            val params = WindowManager.LayoutParams()
//            params.height = WindowManager.LayoutParams.WRAP_CONTENT
//            params.width = WindowManager.LayoutParams.WRAP_CONTENT
//            params.format = PixelFormat.TRANSLUCENT
//            params.type = WindowManager.LayoutParams.TYPE_TOAST
//            params.title = "Toast"
//            params.flags = (WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
//                    or WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
//            val windowManager=getSystemService(Context.WINDOW_SERVICE) as WindowManager
//            windowManager.addView(toastView,params)
        }

        buttonSuccess.setOnClickListener { successToast("成功!") }
    }

}
