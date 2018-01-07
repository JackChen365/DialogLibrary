package cz.dialogcompat.library.progress


import android.graphics.PorterDuff
import android.os.Build
import android.os.Bundle
import android.support.annotation.StringRes
import android.support.v4.app.DialogFragment
import android.support.v4.app.FragmentManager
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ProgressBar
import android.widget.TextView

import cz.dialogcompat.library.R

/**
 * Created by cz on 17/12/13.
 * 一个旋转进度对话框
 */
class ProgressDialogFragment : DialogFragment() {
    private val TAG= ProgressDialogFragment::class.java.simpleName

    companion object {
        fun newInstance(title: String): ProgressDialogFragment {
            val dialogFragment = ProgressDialogFragment()
            val argument = Bundle()
            argument.putString("title", title)
            dialogFragment.arguments = argument
            dialogFragment.setStyle(DialogFragment.STYLE_NO_TITLE, R.style.ProgressDialog)
            return dialogFragment
        }
    }
    private var titleView: TextView? = null
    private var title: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        title = arguments?.getString("title")
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.dialog_progress, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val progressBar = view.findViewById(R.id.progressBar) as ProgressBar
//            progressBar.indeterminateDrawable.setColorFilter(Res.getColor(R.color.colorPrimary), PorterDuff.Mode.SRC_IN)
        }
        titleView = view.findViewById(R.id.progressMessage) as TextView
        titleView?.text = title
    }


    fun setProgressInfo(info: String) {
        this.titleView!!.text = info
    }

    fun setProgressInfo(@StringRes info: Int) {
        this.title = context.getString(info)
    }

    fun show(manager: FragmentManager) {
        super.show(manager, TAG)
    }

    override fun show(manager: FragmentManager, tag: String) {
        try{
            super.show(manager, tag)
        } catch (e:Exception){
            Log.w(TAG,"ProgressDialogFragment show error!\n${e.message}")
        }
    }

    override fun dismiss() {
        try{
            super.dismiss()
        } catch (e:Exception){
            Log.w(TAG,"ProgressDialogFragment dismiss error!\n${e.message}")
        }
    }
}
