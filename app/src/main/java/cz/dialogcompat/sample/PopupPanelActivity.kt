package cz.dialogcompat.sample

import android.content.Context
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.WindowManager
import cz.dialogcompat.library.depend.PopupPanel
import cz.dialogcompat.library.widget.WindowLayout
import cz.dialogcompat.sample.annotation.ToolBar
import cz.volunteerunion.ui.ToolBarActivity
import cz.widget.linearlayout.TabLinearLayout

import kotlinx.android.synthetic.main.activity_popup_panel.*

@ToolBar
class PopupPanelActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_popup_panel)
        setTitle(intent.getStringExtra("title"))
        setSupportActionBar(toolbar)

        val windowManager = getSystemService(Context.WINDOW_SERVICE) as WindowManager
        val windowLayout=WindowLayout(this)
        windowManager.addView(windowLayout,windowLayout.getWindowLayoutParams())

        val popupPanel=PopupPanel(layout)
        tabLayout.setOnSelectListener(object :TabLinearLayout.OnSelectTabListener{
            override fun onSelectTab(v: View, position: Int, lastPosition: Int) {
                when(position){
                    0->{
                        if(v.isSelected||position!=lastPosition){
                            popupPanel.show(R.id.frame1)
                        } else {
                            popupPanel.dismiss(R.id.frame1)
                        }
                    }
                    1->{
                        if(v.isSelected||position!=lastPosition){
                            popupPanel.show(R.id.frame2)
                        } else {
                            popupPanel.dismiss(R.id.frame2)
                        }
                    }
                    2->{
                        if(v.isSelected||position!=lastPosition){
                            popupPanel.show(R.id.frame3)
                        } else {
                            popupPanel.dismiss(R.id.frame3)
                        }
                    }
                }
            }
        })

    }

}
