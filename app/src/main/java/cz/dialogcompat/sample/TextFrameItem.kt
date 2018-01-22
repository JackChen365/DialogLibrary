package cz.dialogcompat.sample

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import cz.dialogcompat.library.depend.PopupFrame
import cz.dialogcompat.library.depend.widget.PopupLayout
import cz.widget.frame.FrameItem

/**
 * Created by cz on 2018/1/22.
 */
class TextFrameItem(context: Context) : PopupFrame(context){

    override fun onCreateFrameView(context: Context,parent:ViewGroup): View? {
        return LayoutInflater.from(context).inflate(R.layout.frame3_layout,parent,false)
    }

    override fun onFrameShown(context:Context,parent: PopupLayout) {
        super.onFrameShown(context,parent)
        Toast.makeText(context,"打开",Toast.LENGTH_SHORT).show()
    }

    override fun onFrameDismiss(context:Context,parent: View) {
        super.onFrameDismiss(context,parent)
        Toast.makeText(context,"关闭",Toast.LENGTH_SHORT).show()
    }
}