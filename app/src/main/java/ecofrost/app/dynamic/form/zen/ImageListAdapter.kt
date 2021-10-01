package ecofrost.app.dynamic.form.zen

import android.content.Context
import android.graphics.Bitmap
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import ecofrost.app.dynamic.form.R

class ImageListAdapter(var values : List<Bitmap>, val context : Context) : RecyclerView.Adapter<ImageListAdapter.ImageVH>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageVH {
        val inflater = LayoutInflater.from(parent.context)
        return ImageVH(inflater.inflate(R.layout.image_itemview,parent, false))
    }

    override fun onBindViewHolder(holder: ImageVH, position: Int) {
        Glide.with(context)
            .load(values[position])
            .into(holder.mImg)
    }

    override fun getItemCount(): Int {
        return values.size
    }


    class ImageVH(i: View) : RecyclerView.ViewHolder(i) {
        val mImg = i.findViewById<ImageView>(R.id.img)
    }
}