package ecofrost.app.dynamic.form.zen

import android.content.Context
import android.graphics.Bitmap
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.button.MaterialButton
import ecofrost.app.dynamic.form.R

class ImageViewVH(view: View, fo: FO?) : ViewHolder(view, fo) {

    interface OnImageViewVhClikedLisetner{
        fun onAddImgClicked()
    }

    val mRv : RecyclerView by lazy {
        view.findViewById(R.id.input)
    }

    val mAddImg : MaterialButton by lazy {
        view.findViewById(R.id.add_img)
    }

    lateinit var options: ArrayList<Bitmap>
    lateinit var context: Context

    var listener : OnImageViewVhClikedLisetner? = null

    val adapter : ImageListAdapter by lazy {
        ImageListAdapter(options, context)
    }

    fun build(title: String, l : ArrayList<Bitmap>){
        setLabel(title)
        options = l
        mRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
        mRv.adapter = adapter

        mAddImg.setOnClickListener(View.OnClickListener { v->
            listener!!.onAddImgClicked()
        })
    }

    fun getImagesData() : List<Bitmap>{
        return options
    }

    fun addImgToList(img :Bitmap){
        options.add(img)
        adapter.notifyDataSetChanged()
    }

    fun attachListener(l : OnImageViewVhClikedLisetner){
        listener = l
    }

}