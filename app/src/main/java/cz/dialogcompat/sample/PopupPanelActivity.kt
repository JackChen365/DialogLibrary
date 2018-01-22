package cz.dialogcompat.sample

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

        val popupPanel=PopupPanel(layout)
        popupPanel.addFrame(TextFrameItem(this))
        tabLayout.setOnSelectListener(object :TabLinearLayout.OnSelectTabListener{
            override fun onSelectTab(v: View, position: Int, lastPosition: Int) {
                when(position){
                    0->{
                        if(!popupPanel.isShowing(R.id.frame1)){
                            popupPanel.show(R.id.frame1)
                        } else {
                            popupPanel.dismiss(R.id.frame1)
                        }
                    }
                    1->{
                        if(!popupPanel.isShowing(R.id.frame2)){
                            popupPanel.show(R.id.frame2)
                        } else {
                            popupPanel.dismiss(R.id.frame2)
                        }
                    }
                    2->{
                        if(!popupPanel.isShowing(R.id.frame3)){
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
