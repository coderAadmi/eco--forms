package ecofrost.app.dynamic.form

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Environment
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import com.google.android.material.button.MaterialButton
import ecofrost.app.dynamic.form.zen.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File

class MainActivity : AppCompatActivity() {

    lateinit var viewList : List<ViewHolder>

    lateinit var resultLauncher : ActivityResultLauncher<Intent>

    var iVH : ImageViewVH? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val inflater = layoutInflater

        val form = findViewById<ViewGroup>(R.id.main_form)


        val data = arrayListOf<String>("Option 1", "Option 2", "Option 3", "Option 4")


        RetrofitClient.api.getForms().enqueue(object : Callback<List<FO>>{
            override fun onResponse(call: Call<List<FO>>, response: Response<List<FO>>) {
                if(response.isSuccessful)
                {
                    Log.d("RES_P","Success")

                    setUI(response.body()!!,inflater, form!!)
                }
            }

            override fun onFailure(call: Call<List<FO>>, t: Throwable) {

            }

        })

        resultLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){
                result->
            if (result.resultCode == Activity.RESULT_OK) {
                // There are no request codes
                val data: Intent? = result.data
                val photo = data?.extras?.get("data") as Bitmap
                Log.d("RES_CAM","IMG : "+photo.height + " : "+photo.width)
                iVH?.addImgToList(photo)
                savePhoto( photo)
            }
        }

        val btn : MaterialButton = findViewById(R.id.save)
        btn.setOnClickListener(View.OnClickListener { v->
            val t = Thread(Runnable {
                var i = 1
                for(vh in viewList){
                    when(vh.type){
                        1->{
                            val th : TextViewVH= vh as TextViewVH
                            Log.d("FORM_RES",""+i+" : "+th.getData())
                        }
                        2->{
                            val th : TextVH= vh as TextVH
                            Log.d("FORM_RES",""+i+" : "+th.getData())
                        }
                        3->{
                            val th : TextVH= vh as TextVH
                            Log.d("FORM_RES",""+i+" : "+th.getData())
                        }
                        4->{
                            val rh: SpinnerVH = vh as SpinnerVH
                            Log.d("FORM_RES",""+i+" : "+rh.getData())
                        }
                        5->{
                            val rh: RadioGroupVH = vh as RadioGroupVH
                            Log.d("FORM_RES",""+i+" : "+rh.getData())
                        }
                    }
                    i++
                }
            })

            t.start()
        })
    }

    private fun savePhoto(photo: Bitmap) {
        File(applicationContext.filesDir, ""+System.currentTimeMillis()).writeBitmap(photo, Bitmap.CompressFormat.PNG, 85)
    }

    private fun File.writeBitmap(bitmap: Bitmap, format: Bitmap.CompressFormat, quality: Int) {
        outputStream().use { out ->
            bitmap.compress(format, quality, out)
            out.flush()
        }
    }


    fun getData(i : String) : ArrayList<String>{
        var lis = ArrayList<String>()
        for(x in 1..12){
            lis.add(""+i+ " type option "+ x)
        }
        return  lis
    }


    fun setUI(l : List<FO>, inflater: LayoutInflater, form: ViewGroup){
        val viewLis = ArrayList<ViewHolder>()
        for( f in l){
            Log.d("RES_P",f.toString())
            when(f.type)
            {
                1->{
                    val v = inflater.inflate(R.layout.form_text_view,null)
                    val tv = TextViewVH(v, null)
                    tv.type = f.type
                    tv.build(f.title, f.value)
                    viewLis.add(tv)
                    form.addView(v)
                }
                2->{
                    val v = inflater.inflate(R.layout.form_text,null)
                    val tv = TextVH(v, null)
                    tv.type = f.type
                    tv.build(f.title, f.placeholder)
                    viewLis.add(tv)
                    form.addView(v)
                }
                3->{
                    val v = inflater.inflate(R.layout.form_phone,null)
                    val tv = TextVH(v, null)
                    tv.type = f.type
                    tv.build(f.title, f.placeholder)
                    viewLis.add(tv)
                    form.addView(v)
                }
                4-> {
                    val v = inflater.inflate(R.layout.form_spinner, null)
                    val tv = SpinnerVH(v, null)
                    tv.context = this
                    tv.type = f.type
                    tv.build(f.title, getData(""))
                    viewLis.add(tv)
                    if (f.dependsUpon != -1) {
                        val fx = viewLis.get(f.dependsUpon-1)
                        when (fx.type) {
                            4 -> {
                                val xv = fx as SpinnerVH
                                xv.attachListener( object : OnItemSelectedListener {
                                    override fun onItemSelected(value: String) {
                                        Toast.makeText(applicationContext, "Listening from "+f.id, Toast.LENGTH_SHORT).show()
                                        Log.d("RES_P","Listening from "+f.id+" : "+f.title)
                                        tv.mSpinner.setText("", false)
                                        tv.build(f.title,getData(value))
                                    }
                                })
                            }
                            5 -> {
                                val xv = fx as SpinnerVH
                                xv.attachListener( object : OnItemSelectedListener {
                                    override fun onItemSelected(value: String) {
                                        Toast.makeText(applicationContext, "Listening from "+f.id, Toast.LENGTH_SHORT).show()
                                        Log.d("RES_P","Listening from "+f.id+" : "+f.title)
                                        tv.mSpinner.setText("", false)
                                        tv.build(f.title,getData(value))
                                    }
                                })
                            }
                        }
                    }
                    form.addView(v)
                }
                5->{
                    val v = inflater.inflate(R.layout.form_rg,null)
                    val tv = RadioGroupVH(v, null)
                    tv.context = this
                    tv.type = f.type
                    tv.build(f.title, getData(""))
                    viewLis.add(tv)
                    if (f.dependsUpon != -1) {
                        val fx = viewLis.get(f.dependsUpon-1)
                        when (fx.type) {
                            4 -> {
                                val xv = fx as SpinnerVH
                                xv.attachListener(object : OnItemSelectedListener {
                                    override fun onItemSelected(value: String) {
                                        Toast.makeText(applicationContext, "Listening from "+f.id, Toast.LENGTH_SHORT).show()
                                        Log.d("RES_P","Listening from "+f.id+" : "+f.title)
                                        tv.mRg.removeAllViews()
                                        tv.build(f.title,getData(value))
                                    }
                                })
                            }
                            5 -> {
                                val xv = fx as SpinnerVH
                                xv.attachListener(object : OnItemSelectedListener {
                                    override fun onItemSelected(value: String) {
                                        Toast.makeText(applicationContext, "Listening from "+f.id, Toast.LENGTH_SHORT).show()
                                        Log.d("RES_P","Listening from "+f.id+" : "+f.title)
                                        tv.mRg.removeAllViews()
                                        tv.build(f.title,getData(value))
                                    }
                                })
                            }
                        }
                    }
                    form.addView(v)
                }

                6->{
                    val v = inflater.inflate(R.layout.form_images,null)
                    val tv = ImageViewVH(v, null)
                    tv.context = this
                    tv.type = f.type
                    tv.build(f.title, arrayListOf())
                    viewLis.add(tv)
                    tv.attachListener(object : ImageViewVH.OnImageViewVhClikedLisetner{
                        override fun onAddImgClicked() {
                            val intent = Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE)
                            iVH = tv
                            resultLauncher.launch(intent)
                        }

                    })
                    form.addView(v)
                }

                7->{
                    val v = inflater.inflate(R.layout.form_barcode_input,null)
                    val tv = TinVH(v, null)
                    tv.type = f.type
                    tv.build(f.title,f.placeholder)
                    viewLis.add(tv)
                    tv.attachListener(object : TinVH.OnTinVHClickedListener{
                        override fun onEndIconclicked() {
                            val pickerDialog = PickerDialog()
                            pickerDialog.show(supportFragmentManager, "")
                        }

                    })
                    form.addView(v)
                }

                8->{
                    val v = inflater.inflate(R.layout.form_checkbox_view,null)
                    val tv = CheckBoxVH(v, null)
                    tv.type = f.type
                    tv.context= applicationContext
                    tv.build(f.title,getData("Check "))
                    viewLis.add(tv)
                    form.addView(v)
                }
            }
        }
        viewList = viewLis
    }

    override fun onLowMemory() {
        super.onLowMemory()
        //save content of Form for future use
    }

}

