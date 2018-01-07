package cz.dialogcompat.sample

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import cz.dialogcompat.library.progress.ProgressDialogFragment
import cz.dialogcompat.sample.annotation.ToolBar
import cz.volunteerunion.ui.ToolBarActivity

import kotlinx.android.synthetic.main.activity_progress.*
import org.jetbrains.anko.sdk25.coroutines.onClick

@ToolBar
class ProgressActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress)
        setTitle(intent.getStringExtra("title"))
        val progressDialog=ProgressDialogFragment.newInstance("加载中...")
        buttonShow.onClick {
            progressDialog.show(supportFragmentManager)
        }

        buttonDismiss.onClick {
            progressDialog.dismiss()
        }
    }

}
