package ecofrost.app.dynamic.form.zen

import android.content.Context
import android.graphics.Color
import android.view.ContextThemeWrapper
import android.view.View
import android.widget.LinearLayout
import com.google.android.material.checkbox.MaterialCheckBox
import ecofrost.app.dynamic.form.R

class CheckBoxVH(view: View, fo: FO?) : ViewHolder(view, fo) {
    val mLayout : LinearLayout by lazy {
        view.findViewById(R.id.checkboxGroup)
    }

    lateinit var values : ArrayList<String>
    lateinit var context: Context

    fun build(title :String, l : ArrayList<String>){
        setLabel(title)
        values = l
        var i = 0
        for( v in values){
            val cb = MaterialCheckBox(ContextThemeWrapper(context, R.style.Base_Theme_MaterialComponents))
            cb.setTextColor(Color.BLACK)
            cb.text = v
            cb.id = i++
            mLayout.addView(cb)
        }

    }

}