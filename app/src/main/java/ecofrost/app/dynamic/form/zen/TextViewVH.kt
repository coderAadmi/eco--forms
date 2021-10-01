package ecofrost.app.dynamic.form.zen

import android.view.View
import android.widget.EditText
import android.widget.TextView
import ecofrost.app.dynamic.form.R

class TextViewVH(view: View, fo: FO?) : ViewHolder(view, fo) {
    val mInput : TextView by lazy {
        view.findViewById(R.id.input)
    }

    fun build(title: String, hint: String?){
        setLabel(title)
        mInput.setText(hint)
    }

    override fun getData() : String{
        return mInput.text.toString()
    }
}