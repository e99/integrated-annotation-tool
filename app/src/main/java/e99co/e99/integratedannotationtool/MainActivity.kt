package e99co.e99.integratedannotationtool

import android.annotation.SuppressLint
import android.app.Activity
import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.SystemClock
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*
import e99co.e99.integratedannotationtool.CanvasView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlin.concurrent.thread
import e99co.e99.integratedannotationtool.AnnotationData
import org.jetbrains.annotations.NotNull
import kotlin.collections.ArrayList


const val IMAGE_ID="image id"
var imageTitleList = arrayListOf<AnnotationImage>(
    AnnotationImage(1,"image number 01.jpg",R.drawable.car,"01 image"),
    AnnotationImage(2,"image number 02.jpg",R.drawable.dog,"02 image"),
    AnnotationImage(3,"image number 03.jpg",R.drawable.person,"03 image"),
    AnnotationImage(1,"image number 01.jpg",R.drawable.car,"01 image"),
    AnnotationImage(2,"image number 02.jpg",R.drawable.dog,"02 image"),
    AnnotationImage(3,"image number 03.jpg",R.drawable.person,"03 image"),
    AnnotationImage(1,"image number 01image number 01image number 01.jpg",R.drawable.car,"01 image"),
    AnnotationImage(2,"image number 02.jpg",R.drawable.dog,"02 image"),
    AnnotationImage(3,"image number 03.jpg",R.drawable.person,"03 image")
)

class MainActivity : AppCompatActivity() {
    //    private val annotationImageListViewModel by viewModels<annotationImageListViewModel> {
//        annotationImageListViewModelFactory(this)
//    }

    companion object { // 동반 객체, 자바의 static 역할.
        var annotations: ArrayList<AnnotationData> = ArrayList()
        var TagList: ArrayList<String> = ArrayList()
        var key:Int=0
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val imagetitleAdapter = ImageTitleAdapter(this, imageTitleList)
        val Canvas1=findViewById<CanvasView>(R.id.canvas_view)
        val annotationlistAdapter=AnnotationAdapter(this,annotations)
        val annotation_list = findViewById<ListView>(R.id.annotation_list_layout)
        val image_title_list = findViewById<ListView>(R.id.image_title_list)

        val imageCanvas = findViewById<ImageView>(R.id.image_canvas)

        val addTagButton = findViewById<ImageView>(R.id.add_tag_button)
        val searchIcon = findViewById<ImageView>(R.id.search_button)

        image_title_list.adapter = imagetitleAdapter
        image_title_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, i, l ->
            val selectedItemText = parent.getItemIdAtPosition(i)
            imageCanvas.setImageResource(imageTitleList[i].image)
        }

        annotation_list.adapter=annotationlistAdapter
        annotation_list.onItemClickListener =  AdapterView.OnItemClickListener{ parent, view, i, l ->
            val intent= Intent(this,AddClassActivity::class.java)
            startActivityForResult(intent,100)
            key=i
        }
        searchIcon.setOnClickListener{

            annotationlistAdapter.notifyDataSetChanged()
        }

        addTagButton.setOnClickListener{

        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)

        /* Inserts flower into viewModel. */
        if (requestCode == 100 && resultCode == Activity.RESULT_OK) {
            intentData?.let { data ->
                val label_name = data.getStringExtra("label_text")
                var ad=annotations.get(key)
                var addd=AnnotationData(ad.id,label_name,ad.startX,ad.startY,ad.stopX,ad.stopY)
                annotations.remove(ad)
                annotations.add(addd)
                Log.i("intent",label_name)
            }
        }
    }
}

/*
        val imageTitleAdapter=ImageTitleAdapter{ annotationImage -> adapterOnClick(annotationImage) }
        val recyclerView: RecyclerView = findViewById(R.id.recycler_title_image)
        recyclerView.adapter=imageTitleAdapter

        val imageTitleObserver = Observer<annotationImage>{ annotationImage ->

        }

        annotationImageListViewModel.annotationImageLiveData.observe(this,{
            it?.let {
                imageTitleAdapter.submitList(it as MutableList<annotationImage>)
            }
        })
    }
    private fun adapterOnClick(annotationImage: AnnotationImage) {
        val intent = Intent(this, annotationImageDetailActivity()::class.java)
        intent.putExtra(IMAGE_ID,annotationImage.id)
        startActivity(intent)
    }

 */


class ImageTitleAdapter (val context: Context, val ImageTitleList: ArrayList<AnnotationImage>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.image_title_item, null)

        val thumbnailPhoto = view.findViewById<ImageView>(R.id.thumbnail_image)
        val imageTitleItem = view.findViewById<TextView>(R.id.image_title)

        val imagetitle = ImageTitleList[position]
        thumbnailPhoto.setImageResource(imagetitle.image)
        imageTitleItem.text = imagetitle.name

        return view
    }

    override fun getItem(position: Int): Any {
        return ImageTitleList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return ImageTitleList.size
    }
}

class AnnotationAdapter (val context: Context, private val annotationList: ArrayList<AnnotationData>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.annotation_item, null)


        val annotationId = view.findViewById<TextView>(R.id.annotation_id)
        val annotationLabel = view.findViewById<TextView>(R.id.annotation_label)
        val annotationStartX = view.findViewById<TextView>(R.id.annotation_startX)
        val annotationStartY = view.findViewById<TextView>(R.id.annotation_startY)
        val annotationStopX = view.findViewById<TextView>(R.id.annotation_stopX)
        val annotationStopY = view.findViewById<TextView>(R.id.annotation_stopY)

        val annotation=annotationList[position]

        annotationId.text= annotation.id.toString()
        annotationLabel.text= annotation.label
        annotationStartX.text= annotation.startX.toString()
        annotationStartY.text= annotation.stopY.toString()
        annotationStopX.text= annotation.stopX.toString()
        annotationStopY.text= annotation.stopY.toString()
        return view
    }

    override fun getItem(position: Int): Any {
        return annotationList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return annotationList.size
    }
}

