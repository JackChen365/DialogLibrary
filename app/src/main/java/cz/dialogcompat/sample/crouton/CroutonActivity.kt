package cz.dialogcompat.sample.crouton

import android.graphics.Color
import android.os.Bundle
import android.widget.RadioGroup
import cz.dialogcompat.library.crouton
import cz.dialogcompat.library.crouton.Crouton
import cz.dialogcompat.sample.R
import cz.dialogcompat.sample.annotation.ToolBar
import cz.volunteerunion.ui.ToolBarActivity

@ToolBar
class CroutonActivity : ToolBarActivity() {
    private var id = R.id.activityContainer
    private var message: String? = null
    private var show: Boolean = false
    private var count: Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_crouton_layout)
        setTitle(intent.getStringExtra("title"))
        message = getString(R.string.refresh_complete)
        val layout2 = findViewById(R.id.layout2) as RadioGroup
        layout2.setOnCheckedChangeListener { _, i ->
            when (i) {
                R.id.container -> id = R.id.activityContainer
                R.id.frame -> id = R.id.frameLayout
                R.id.text -> id = R.id.textView
            }
        }
        val layout3 = findViewById(R.id.layout3) as RadioGroup
        layout3.setOnCheckedChangeListener { _, i ->
            if (i == R.id.sameMessage) {
                message = getString(R.string.refresh_complete)
            }
        }

        findViewById(R.id.btn_show).setOnClickListener {
            if (R.id.diffMessage == layout3.checkedRadioButtonId) {
                message = getString(R.string.refresh_complete) + count++
            }
            show = !show
            crouton(id){ text=message }
//            Crouton.create(this, id)//确定装载activity-以及参照控件
//                    .filter(true)//是否过滤重复样式的重复弹出,默认不过滤
//                    .backgroundColor(if (show) Color.RED else Crouton.NO_COLOR)
//                    .text(message!!)//展示信息
//                    .show()
        }
    }

}
