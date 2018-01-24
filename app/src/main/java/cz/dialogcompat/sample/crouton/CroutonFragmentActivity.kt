package cz.dialogcompat.sample.crouton

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v7.app.AppCompatActivity
import android.view.View

import cz.dialogcompat.sample.R
import cz.dialogcompat.sample.annotation.ToolBar
import cz.dialogcompat.sample.crouton.fragment.Sample1Fragment
import cz.dialogcompat.sample.crouton.fragment.Sample2Fragment
import cz.volunteerunion.ui.ToolBarActivity

/**
 * Created by cz on 11/23/16.
 */
@ToolBar
class CroutonFragmentActivity : ToolBarActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_fragment)
        setTitle(intent.getStringExtra("title"))
        val fragments = arrayOf(Sample1Fragment(), Sample2Fragment())
        supportFragmentManager.beginTransaction().add(R.id.frame_layout, fragments[0]).commit()
        findViewById(R.id.tab1).setOnClickListener { showFragment(fragments[0], fragments[1]) }

        findViewById(R.id.tab2).setOnClickListener { showFragment(fragments[1], fragments[0]) }

        findViewById(R.id.tab3).setOnClickListener {
            if (fragments[0].isAdded) {
                supportFragmentManager.beginTransaction().remove(fragments[0]).commit()
            }
        }

    }

    /**
     * 显示指定fragment,并隐藏其他fragment
     *
     * @param showFragment
     * @param hideFragment
     */
    private fun showFragment(showFragment: Fragment, hideFragment: Fragment) {
        val fragmentManager = supportFragmentManager
        if (showFragment.isAdded) {
            fragmentManager.beginTransaction().show(showFragment).commitAllowingStateLoss()
        } else {
            fragmentManager.beginTransaction().add(R.id.frame_layout, showFragment).commitAllowingStateLoss()
        }
        fragmentManager.beginTransaction().hide(hideFragment).commitAllowingStateLoss()
    }
}
