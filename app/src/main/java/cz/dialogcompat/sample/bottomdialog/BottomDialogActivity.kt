package cz.dialogcompat.sample.bottomdialog

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import cz.dialogcompat.sample.R
import cz.dialogcompat.sample.annotation.ToolBar
import cz.volunteerunion.ui.ToolBarActivity
import kotlinx.android.synthetic.main.activity_bottom_dialog.*

@ToolBar
class BottomDialogActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_bottom_dialog)
        setTitle(intent.getStringExtra("title"))

        buttonShow1.setOnClickListener {
            SharedDialog(this).show()
        }

        buttonShow2.setOnClickListener {
            EditTextDialogFragment().show(supportFragmentManager)
        }
    }
}
