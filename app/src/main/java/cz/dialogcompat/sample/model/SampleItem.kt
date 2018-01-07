package cz.dialogcompat.sample.model

/**
 * Created by Administrator on 2017/6/8.
 */
data class SampleItem<T>(var id:Int?, var pid:Int=0, var clazz:Class<out T>?, var title:String?, var desc:String?){
    constructor():this(null,0,null,null,null)
}