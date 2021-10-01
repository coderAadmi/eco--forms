package ecofrost.app.dynamic.form.zen

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.RadioButton
import android.widget.RadioGroup
import ecofrost.app.dynamic.form.R

class RadioGroupVH(view: View, fo: FO?) : ViewHolder(view, fo) {
    val mRg: RadioGroup by lazy {
        view.findViewById(R.id.input)
    }

    lateinit var options: List<String>
    lateinit var context: Context

    var listeners  = ArrayList<OnItemSelectedListener>()

    var selectedValue : String? = null

    fun build(title: String, o: List<String>) {
        setLabel(title)
        options = o
        var i = 0
        for (s in options) {
            val rb = RadioButton(context)
            rb.setText(s)
            rb.id = i
            mRg.addView(rb)
            i++
        }

        mRg.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener { mRg, pos ->
            Log.d("RES_RAD", "RG Selected : " + options[pos])
            selectedValue = options[pos]
            for(li in listeners)
            {
                li.onItemSelected(selectedValue!!)
            }
        })

    }

    override fun getData(): String? {
        return selectedValue
    }

    fun attachListener(l : OnItemSelectedListener){
        Log.d("RES_L","Attached Listener")
        listeners.add(l)
    }
}

