package ecofrost.app.dynamic.form.zen

import android.content.Context
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import ecofrost.app.dynamic.form.R

class SpinnerVH(view: View, fo: FO?) : ViewHolder(view, fo) {

    val mSpinner : AutoCompleteTextView by lazy {
        view.findViewById(R.id.input)
    }

    lateinit var values : List<String>
    lateinit var context: Context
    var selectedValue : String? = null

    var listeners  = ArrayList<OnItemSelectedListener>()

    fun build( title: String, l : List<String>){
        setLabel(title)
        values = l
        val ad: ArrayAdapter<String> = ArrayAdapter<String>(
            context ,android.R.layout.simple_spinner_item,
            values)
        mSpinner.setAdapter(ad)
        mSpinner.onItemClickListener = AdapterView.OnItemClickListener { parent, view, position, id ->
            Log.d("RES_SP"," selected : "+position)
            selectedValue = values[position]
            for(li in listeners)
            {
                li.onItemSelected(selectedValue!!)
            }
        }
    }

    override fun getData(): String? {
        return selectedValue
    }

    fun attachListener(l : OnItemSelectedListener){
        Log.d("RES_L","Attached Listener")
        listeners.add(l)
    }
}