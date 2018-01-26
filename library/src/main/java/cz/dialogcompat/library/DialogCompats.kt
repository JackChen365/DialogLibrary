package cz.dialogcompat.library

import android.app.Activity
import android.content.Context
import android.support.annotation.IdRes
import android.support.annotation.StringRes
import android.support.v4.app.Fragment
import android.util.Log
import android.view.View
import android.widget.Toast
import cz.dialogcompat.library.crouton.Crouton
import cz.dialogcompat.library.crouton.model.CroutonItem
import cz.dialogcompat.library.toast.ToastCompat

/**
 * Created by cz on 2017/12/13.
 * 扩展context/fragment对象,支持对toast的直接操作
 */

//----------------------------------------------------------
// toast扩展
//----------------------------------------------------------
inline fun Context.toast(text:String)=ToastCompat.toast(this,text)
inline fun Context.toast(@StringRes stringRes:Int,duration:Int=Toast.LENGTH_SHORT)=ToastCompat.toast(this,stringRes,duration)

inline fun Context.customToast(text:String,duration:Int=Toast.LENGTH_SHORT)=ToastCompat.customToast(this,text,duration)
inline fun Context.customToast(@StringRes stringRes:Int,duration:Int=Toast.LENGTH_SHORT)=ToastCompat.customToast(this,stringRes,duration)

inline fun Context.successToast(text:String)=ToastCompat.successToast(this,text)
inline fun Context.successToast(@StringRes stringRes:Int)=ToastCompat.successToast(this,stringRes)

inline fun Fragment.toast(text: String)=ToastCompat.toast(context,text)
inline fun Fragment.toast(@StringRes stringRes: Int,duration:Int=Toast.LENGTH_SHORT)=ToastCompat.toast(context,stringRes,duration)

inline fun Fragment.customToast(text: String,duration:Int=Toast.LENGTH_SHORT)=ToastCompat.customToast(context,text,Toast.LENGTH_SHORT)
inline fun Fragment.customToast(@StringRes stringRes: Int,duration:Int=Toast.LENGTH_SHORT)=ToastCompat.customToast(context,stringRes,duration)

inline fun Fragment.successToast(text:String)=ToastCompat.successToast(context,text)
inline fun Fragment.successToast(@StringRes stringRes:Int)=ToastCompat.successToast(context,stringRes)


//----------------------------------------------------------
// crouton扩展
//----------------------------------------------------------
inline fun Activity.crouton(@IdRes id:Int,crouton: CroutonItem.()->Unit){
    val item=CroutonItem().apply(crouton)
    Crouton.create(this,id,item).show()
}
inline fun Fragment.crouton(@IdRes id:Int,crouton: CroutonItem.()->Unit){
    val item=CroutonItem().apply(crouton)
    Crouton.create(this,id,item).show()
}
inline fun View.crouton(crouton: CroutonItem.()->Unit){
    val item=CroutonItem().apply(crouton)
    Crouton.create(this,item).show()
}


/**
 * 检测弹出样式是否配置
 */
inline internal fun Context.checkPromptTheme(){
    val a = obtainStyledAttributes(intArrayOf(R.attr.prompt))
    if (!a.hasValue(0)) {
        Log.w("DialogCompat","You have no configuration prompt style with this activity!")
        theme.applyStyle(R.style.PromptStyle,true)
    }
    a.recycle()
}

