package cz.dialogcompat.sample

import android.app.Activity
import cz.dialogcompat.sample.bottomdialog.BottomDialogActivity
import cz.dialogcompat.sample.crouton.CroutonActivity
import cz.dialogcompat.sample.crouton.CroutonFragmentActivity
import cz.dialogcompat.sample.crouton.CroutonListViewActivity
import cz.dialogcompat.sample.model.SampleItem
import cz.dialogcompat.sample.popup.PopupPanelActivity
import cz.dialogcompat.sample.progress.ProgressActivity
import cz.dialogcompat.sample.toast.ToastActivity

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
                    clazz= ToastActivity::class.java
                }
                item {
                    id = 2
                    title = "对话框"
                    desc = "一样定制的对话框"
                    item {
                        pid = 2
                        title = "底部弹出框"
                        desc = "测试让指定绘制片断翻转的动画"
                        clazz= BottomDialogActivity::class.java
                    }
                }
                item {
                    id = 3
                    title = "加载弹出框"
                    desc = "测试让指定绘制片断翻转的动画"
                    clazz= ProgressActivity::class.java
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
                    item {
                        pid = 5
                        title = "Crouton常规演示1"
                        desc = "测试常规功能"
                        clazz = CroutonActivity::class.java
                    }
                    item {
                        pid = 5
                        title = "Crouton常规演示2"
                        desc = "测试列表点击弹出"
                        clazz = CroutonListViewActivity::class.java
                    }
                    item {
                        pid = 5
                        title = "Crouton常规演示3"
                        desc = "测试Fragment切换时,Crouton依赖情况"
                        clazz = CroutonFragmentActivity::class.java
                    }
                }
            }

        }
    }
}