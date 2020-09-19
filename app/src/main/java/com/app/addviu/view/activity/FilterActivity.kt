package com.app.addviu.view.activity

import android.R.id.message
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import android.widget.RadioGroup
import com.app.addviu.R
import com.app.addviu.data.helper.FILTER
import com.app.addviu.view.BaseActivity
import kotlinx.android.synthetic.main.activity_filter.*


class FilterActivity : BaseActivity() {

    var upload = ""
    var type = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_filter)

        setRadioGroups()
        setUplod(sharedPrefsHelper?.get("upload", "")!!)
        setTyp(sharedPrefsHelper?.get("type", "")!!)
        btnApply.setOnClickListener {
            sharedPrefsHelper?.put("upload", upload)
            sharedPrefsHelper?.put("type", type)
            val intent = Intent()
            intent.putExtra("upload", upload)
            intent.putExtra("type", type)
            setResult(RESULT_OK, intent)
            finish()
            overridePendingTransition(R.anim.slide_out, R.anim.slide_in)
        }
        clearAll.setOnClickListener {
            clearAll()
        }
    }

    fun setRadioGroups() {
        radGroup1.setOnCheckedChangeListener({ group, checkedId ->
            when (checkedId) {
                R.id.radToday -> {
                    upload = "t"
                }
                R.id.radWeek -> {
                    upload = "w"
                }
                R.id.radMonth -> {
                    upload = "m"
                }
                R.id.radYear -> {
                    upload = "y"
                }
            }
        })

        radGroup2.setOnCheckedChangeListener({ group, checkedId ->
            when (checkedId) {
                R.id.radLtf -> {
                    type = "ltf"
                }
                R.id.radmtf -> {
                    type = "mtf"
                }
                R.id.radNewVid -> {
                    type = "nv"
                }
                R.id.radTopLike -> {
                    type = "tl"
                }
                R.id.radMoreView -> {
                    type = "mv"
                }
                R.id.radVid -> {
                    type = "v"
                }
                R.id.radChan -> {
                    type = "c"
                }
                R.id.radPlay -> {
                    type = "p"
                }
            }
        })
    }

    override fun setFullScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            window.decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR
        }
    }

    fun setUplod(upld: String) {
        when (upld) {
            "t" -> {
                radToday.isChecked = true
            }
            "w" -> {
                radWeek.isChecked = true
            }
            "m" -> {
                radMonth.isChecked = true
            }
            "y" -> {
                radYear.isChecked = true
            }
        }
    }

    fun setTyp(typ: String) {
        when (typ) {
            "ltf" -> {
                radLtf.isChecked = true
            }
            "mtf" -> {
                radmtf.isChecked = true
            }
            "nv" -> {
                radNewVid.isChecked = true
            }
            "tl" -> {
                radTopLike.isChecked = true
            }
            "mv" -> {
                radMoreView.isChecked = true
            }
            "v" -> {
                radVid.isChecked = true
            }
            "c" -> {
                radChan.isChecked = true
            }
            "p" -> {
                radPlay.isChecked = true
            }
        }
    }

    fun clearAll(){
        radToday.isChecked = false
        radWeek.isChecked = false
        radMonth.isChecked = false
        radYear.isChecked = false
        radLtf.isChecked = false
        radmtf.isChecked = false
        radNewVid.isChecked = false
        radTopLike.isChecked = false
        radMoreView.isChecked = false
        radVid.isChecked = false
        radChan.isChecked = false
        radPlay.isChecked = false
        upload = ""
        type = ""
        sharedPrefsHelper?.put("upload", upload)
        sharedPrefsHelper?.put("type", type)

    }

    override fun setStatusBarGradiant() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.statusBarColor = resources.getColor(android.R.color.white)

        }
    }
}