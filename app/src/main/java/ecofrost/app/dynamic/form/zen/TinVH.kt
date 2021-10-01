package ecofrost.app.dynamic.form.zen

import android.util.Log
import android.view.View
import com.google.android.material.textfield.TextInputEditText
import com.google.android.material.textfield.TextInputLayout
import ecofrost.app.dynamic.form.R

class TinVH(view: View, fo: FO?) : ViewHolder(view, fo) {

    interface OnTinVHClickedListener{
        fun onEndIconclicked()
    }

    val mText : TextInputEditText by lazy {
        view.findViewById(R.id.date_input)
    }

    val mTexIn : TextInputLayout by lazy {
        view.findViewById(R.id.input)
    }

    var listener: OnTinVHClickedListener? = null

    fun build(title: String, hint : String?){
        setLabel(title)
        mText.hint = hint
        mTexIn.setEndIconOnClickListener(View.OnClickListener { v->
            Log.d("RES_TIN","Clicked")
            listener?.onEndIconclicked()
        })
    }

    fun attachListener(l : OnTinVHClickedListener){
        listener = l
    }

}