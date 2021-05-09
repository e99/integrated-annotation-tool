package e99co.e99.integratedannotationtool

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


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
    private lateinit var addLabelName: TextInputEditText
    init{
        instance = this
    }

    companion object { // 동반 객체, 자바의 static 역할.
        var annotations: ArrayList<AnnotationData> = ArrayList()
        var LabelList: ArrayList<String> = ArrayList()
        var key:Int=0
        var selected=-1

        lateinit var instance: MainActivity
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
        lateinit var annotationlistAdapter: AnnotationAdapter
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val imagetitleAdapter = ImageTitleAdapter(this, imageTitleList)
        val Canvas1=findViewById<CanvasView>(R.id.canvas_view)
        annotationlistAdapter=AnnotationAdapter(this,annotations)
        val annotation_list = findViewById<ListView>(R.id.annotation_list_layout)
        val image_title_list = findViewById<ListView>(R.id.image_title_list)
        addLabelName = findViewById(R.id.textfield)
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
            key=i
            selected=i
        }

        val labelListView=findViewById<ListView>(R.id.label_list)

        val labellistAdapter=LabelListAdapter(this, LabelList)

        labelListView.adapter=labellistAdapter
        labelListView.setOnItemClickListener { parent, view, position, id ->
            val name = LabelList[position]
            if (selected!=-1){
                var ad=annotations.get(key)
                var addd=AnnotationData(ad.id,name,ad.startX,ad.startY,ad.stopX,ad.stopY)
                annotations.set(key,addd)
                annotationlistAdapter.notifyDataSetChanged()
            }

        }
        searchIcon.setOnClickListener{
            selected=-1
            annotationlistAdapter.notifyDataSetChanged()
        }

        addTagButton.setOnClickListener{
            if (addLabelName.text.isNullOrEmpty()) {
                Toast.makeText(this,"라벨 이름이 비어있습니다.",Toast.LENGTH_SHORT)
            } else {
                val name = addLabelName.text.toString()
                LabelList.add(name)
                labellistAdapter.notifyDataSetChanged()
                addLabelName.setText(null)
                imm.hideSoftInputFromWindow(addLabelName.windowToken,0)
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentData: Intent?) {
        super.onActivityResult(requestCode, resultCode, intentData)
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

class LabelListAdapter (val context: Context, private val labelListAdapter: ArrayList<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.tag_item, null)

        val tagNameText = view.findViewById<TextView>(R.id.tag_name)
        val name= labelListAdapter[position]
        tagNameText.text=name
        return view

    }

    override fun getItem(position: Int): Any {
        return labelListAdapter[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return labelListAdapter.size
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

