package ecofrost.app.dynamic.form.zen

import android.view.View
import android.widget.EditText
import ecofrost.app.dynamic.form.R

class TextVH(view: View, fo: FO?) : ViewHolder(view, fo) {
    val mInput : EditText by lazy {
        view.findViewById(R.id.input)
    }

    fun build(title: String, hint: String?){
        setLabel(title)
        mInput.hint = hint
    }

    override fun getData() : String{
        return mInput.text.toString()
    }
}