package ecofrost.app.dynamic.form.zen

import android.view.View
import android.widget.TextView
import ecofrost.app.dynamic.form.R

open class ViewHolder(val view: View, val fo : FO?) {

    open val label: TextView by lazy{
        view.findViewById(R.id.label)
    }

    open var type : Int = -1

    fun setLabel(txt : String){
        label.text = txt
    }

    open fun getData() : String?{
        return ""
    }

}