package cz.dialogcompat.library

import android.content.Context
import android.support.v4.app.Fragment
import android.widget.Toast

/**
 * Created by cz on 2017/12/13.
 * 扩展context/fragment对象,支持对toast的直接操作
 */
inline fun Context.toast(text:String,duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,text,duration).show()
}

inline fun Context.toast(stringRes:Int,duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(this,stringRes,duration).show()
}

inline fun Fragment.toast(text: String,duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(context,text,Toast.LENGTH_SHORT).show()
}

inline fun Fragment.toast(stringRes: Int,duration:Int=Toast.LENGTH_SHORT){
    Toast.makeText(context,stringRes,duration).show()
}