package cz.dialogcompat.sample

import android.app.Activity
import android.content.Intent
import android.graphics.Typeface
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.PopupWindow
import android.widget.TextView
import android.widget.Toast
import cz.dialogcompat.sample.model.SampleItem

import cz.volunteerunion.ui.ToolBarActivity
import kotlinx.android.synthetic.main.activity_main.*
import org.jetbrains.anko.*
import org.jetbrains.anko.sdk25.coroutines.onClick

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val id=intent.getIntExtra("id",0)
        val title = intent.getStringExtra("title")
        if(null==title) {
            toolBar.setTitle(R.string.app_name)
            setSupportActionBar(toolBar)
        } else {
            toolBar.title = title
            toolBar.subtitle=intent.getStringExtra("desc")
            setSupportActionBar(toolBar)
            supportActionBar?.setDisplayHomeAsUpEnabled(true)
            toolBar.setNavigationOnClickListener{ finish() }
        }
        recyclerView.layoutManager= LinearLayoutManager(this)
        val items = FuncTemplate[id]
        items?.let { recyclerView.adapter=Adapter(it) }

        PopupWindow()
    }

    class Adapter(val items:List<SampleItem<Activity>>): RecyclerView.Adapter<RecyclerView.ViewHolder>() {

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
            val view=with(parent.context) {
                linearLayout {
                    orientation= LinearLayout.VERTICAL
                    lparams(width = matchParent, height = wrapContent)
                    leftPadding=dip(12)
                    rightPadding=dip(12)
                    backgroundResource=R.drawable.white_item_selector
                    textView {
                        id = android.R.id.text1
                        textSize = 16f
                        typeface = Typeface.DEFAULT_BOLD
                        topPadding = dip(4)
                        bottomPadding=dip(4)
                    }
                    textView {
                        id = android.R.id.text2
                        textSize = 14f
                        topPadding = dip(4)
                        bottomPadding=dip(4)
                        lines=2
                    }
                }
            }
            return object: RecyclerView.ViewHolder(view){}
        }

        override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
            val item=items[position]
            holder.itemView.find<TextView>(android.R.id.text1).text=item.title
            holder.itemView.find<TextView>(android.R.id.text2).text=item.desc
            holder.itemView.onClick {
                val context=it?.context?:return@onClick
                if(item.id in FuncTemplate){
                    it.context.startActivity(Intent(context,MainActivity::class.java).apply {
                        putExtra("id",item.id)
                        putExtra("title",item.title)
                        putExtra("desc",item.desc)
                    })//子分组
                } else if(null!=item.clazz){
                    it.context.startActivity(Intent(context,item.clazz).apply { putExtra("title",item.title) })//子条目
                } else{
                    Toast.makeText(context,"未配置子分组,也未配置跳转界面信息!", Toast.LENGTH_SHORT).show()
                }
            }
        }

        override fun getItemCount(): Int=items.size


    }
}

val DEBUG=true
internal inline fun <reified T> T.debugLog(value:String){
    if(DEBUG){
        val item=this as Any
        Log.e(item::class.java.simpleName,value)
    }
}
