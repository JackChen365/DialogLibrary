package cz.dialogcompat.sample

import android.app.Activity
import cz.dialogcompat.sample.model.SampleItem

/**
 * Created by Administrator on 2017/6/8.
 */
class FuncTemplate {
    companion object {
        val items = mutableListOf<SampleItem<Activity>>()
        val groupItems = mutableMapOf<Int, List<SampleItem<Activity>>>()
        fun item(closure: SampleItem<Activity>.() -> Unit) {
            items.add(SampleItem<Activity>().apply(closure))
        }

        //分组模板
        fun group(closure: () -> Unit) {
            closure.invoke()
            groupItems += items.groupBy { it.pid }
        }

        operator fun get(id: Int?) = groupItems[id]

        operator fun contains(id: Int?) = groupItems.any { it.key == id }

        init {
            group {
                item {
                    id = 1
                    title = "Toast定制"
                    desc = "一些自定制toast"
//                    clazz=FlipImgActivity::class.java
                }
                item {
                    id = 2
                    title = "对话框"
                    desc = "一样定制的对话框"
//                    clazz=FlipImgActivity::class.java
                    item {
                        pid = 2
                        title = "底部弹出框"
                        desc = "测试让指定绘制片断翻转的动画"
//                    clazz=FlipImgActivity::class.java
                    }
                }
                item {
                    id = 3
                    title = "加载弹出框"
                    desc = "测试让指定绘制片断翻转的动画"
                    clazz=ProgressActivity::class.java
                }
                item {
                    id = 4
                    title = "PopupPanel"
                    desc = "一种附加式的弹出框"
                    clazz = PopupPanelActivity::class.java
                }
                item {
                    id = 5
                    title = "Crouton弹出扩展"
                    desc = "测试让指定绘制片断翻转的动画"
//                    clazz=FlipImgActivity::class.java
                }
            }

        }
    }
}