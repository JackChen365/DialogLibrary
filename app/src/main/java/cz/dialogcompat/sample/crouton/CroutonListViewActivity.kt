package cz.dialogcompat.sample.crouton

import android.os.Bundle
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.ListView

import cz.dialogcompat.library.crouton.Crouton
import cz.dialogcompat.sample.R
import cz.dialogcompat.sample.annotation.ToolBar
import cz.volunteerunion.ui.ToolBarActivity

/**
 * Created by cz on 11/22/16.
 */
@ToolBar
class CroutonListViewActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_listview)
        setTitle(intent.getStringExtra("title"))
        val listView = findViewById(R.id.list_view) as ListView
        val items = arrayOfNulls<String>(50)
        for (i in items.indices) {
            items[i] = "Item:" + (i + 1)
        }
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        listView.onItemClickListener = AdapterView.OnItemClickListener { _, _, _, _ ->
            Crouton.create(this@CroutonListViewActivity, R.id.list_view).text(R.string.list_clicked).show() }
    }
}
