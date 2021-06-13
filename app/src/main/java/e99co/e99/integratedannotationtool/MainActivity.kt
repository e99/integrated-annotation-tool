package e99co.e99.integratedannotationtool

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.graphics.alpha
import com.google.android.material.textfield.TextInputEditText
import e99co.e99.integratedannotationtool.MainActivity.Companion.colorHash
import e99co.e99.integratedannotationtool.MainActivity.Companion.currentImageId


const val IMAGE_ID="image id"


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
        var LabelList: ArrayList<Pair<String,Int>> = ArrayList()
        var key:Int=0
        var selected=-1

        lateinit var instance: MainActivity
        fun ApplicationContext() : Context {
            return instance.applicationContext
        }
        lateinit var annotationlistAdapter: AnnotationAdapter
        var currentImageId:Int=-1
        var currentTagId:Int=-1
        var currentLabelId:Int=-1
        var imageTitleList = arrayListOf<AnnotationImage>(
                AnnotationImage(0,"image number 01.jpg",ArrayList<AnnotationData>(),R.drawable.car,"01 image"),
                AnnotationImage(1,"image number 02.jpg",ArrayList<AnnotationData>(),R.drawable.dog,"02 image"),
                AnnotationImage(2,"image number 03.jpg",ArrayList<AnnotationData>(),R.drawable.person,"03 image"),
                AnnotationImage(3,"image number 04.jpg",ArrayList<AnnotationData>(),R.drawable.car,"01 image"),
                AnnotationImage(4,"image number 05.jpg",ArrayList<AnnotationData>(),R.drawable.dog,"02 image"),
                AnnotationImage(5,"image number 03.jpg",ArrayList<AnnotationData>(),R.drawable.person,"03 image"),
                AnnotationImage(6,"image number 06image number 01image number 01.jpg",ArrayList<AnnotationData>(),R.drawable.car,"01 image"),
                AnnotationImage(7,"image number 07.jpg",ArrayList<AnnotationData>(),R.drawable.dog,"02 image"),
                AnnotationImage(8,"image number 08.jpg",ArrayList<AnnotationData>(),R.drawable.person,"03 image")
        )

        val colorList=arrayListOf<Int>(Color.argb(255,248,106,106),Color.argb(255,244,126,8),Color.argb(255,255,255,156),Color.argb(255,156,156,250),Color.argb(255,83,83,246),Color.argb(255,165,83,246))
        val colorHash=HashMap<String,Int>()
        var colorVar=0
        var imageChanging=false
        var tagRemoving=false
        var searchHash=HashMap<String,ArrayList<Pair<Int,Int>>>()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val imm: InputMethodManager = getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        val imagetitleAdapter = ImageTitleAdapter(this, imageTitleList)
        annotationlistAdapter=AnnotationAdapter(this,annotations)
        val annotation_list = findViewById<ListView>(R.id.annotation_list_layout)
        val image_title_list = findViewById<ListView>(R.id.image_title_list)
        addLabelName = findViewById(R.id.textfield)
        val imageCanvas = findViewById<ImageView>(R.id.image_canvas)
        val drawCanvas = findViewById<CanvasView>(R.id.canvas_view)
        val addTagButton = findViewById<ImageView>(R.id.add_tag_button)
        val removeLabelButton = findViewById<ImageView>(R.id.label_remove_button)
        val removeTagButton = findViewById<ImageView>(R.id.tag_remove_button)
        val searchIcon = findViewById<ImageView>(R.id.search_button)


        if (imageTitleList.size != 0){
            imageCanvas.setImageResource(imageTitleList[0].image)
            currentImageId=0
        }
        image_title_list.adapter = imagetitleAdapter
        image_title_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, i, l ->
            currentTagId=i
            val selectedItemText = parent.getItemIdAtPosition(i)
            imageCanvas.setImageResource(imageTitleList[i].image)
            currentImageId= imageTitleList[i].id.toInt()
            annotations.clear()
            if(imageTitleList[currentImageId].tags.isNotEmpty()){
                for (j in imageTitleList[currentImageId].tags.indices) {
                    annotations.add(imageTitleList[currentImageId].tags[j])
                }
            }
            annotationlistAdapter.notifyDataSetChanged()
            imageChanging=true
            drawCanvas.invalidate()
        }
        colorHash.put("not selected", Color.GRAY)
        annotation_list.adapter=annotationlistAdapter
        annotation_list.onItemClickListener =  AdapterView.OnItemClickListener{ parent, view, i, l ->
            key=i
            selected=i
//            currentTagId=i

        }

        val labelListView=findViewById<ListView>(R.id.label_list)

        val labellistAdapter=LabelListAdapter(this, LabelList)

        labelListView.adapter=labellistAdapter
        labelListView.setOnItemClickListener { parent, view, position, id ->
            currentLabelId=position
            val name = LabelList[position].first
            if (selected!=-1){
                var ad=annotations.get(key)
                var addd=AnnotationData(ad.id,name,ad.startX,ad.startY,ad.stopX,ad.stopY,colorHash.get(name)!!)
                annotations.set(key,addd)
                imageTitleList[currentImageId].tags.remove(ad)
                imageTitleList[currentImageId].tags.add(addd)
                var s=searchHash.get(name)
                s!!.add(Pair(currentImageId,ad.id))
                annotationlistAdapter.notifyDataSetChanged()
                imageChanging=true
                drawCanvas.invalidate()
            }
        }

        searchIcon.setOnClickListener{
            selected=-1
            annotationlistAdapter.notifyDataSetChanged()
            Log.i("search hash",searchHash.toString())
        }

        addTagButton.setOnClickListener{
            if (addLabelName.text.isNullOrEmpty()) {
                Toast.makeText(this,"라벨 이름이 비어있습니다.",Toast.LENGTH_SHORT)
            } else {
                var Flag=true
                val name = addLabelName.text.toString()
                for (i in LabelList.indices){
                    if(name== LabelList[i].first){
                        Flag=false
                    }
                }
                if (Flag){
                    searchHash.put(name,ArrayList<Pair<Int,Int>>())
                    LabelList.add(Pair(name,colorList[colorVar%6]))
                    colorHash.put(name,colorList[colorVar%6])
                    labellistAdapter.notifyDataSetChanged()
                    colorVar+=1
                    addLabelName.setText(null)
                    imm.hideSoftInputFromWindow(addLabelName.windowToken,0)
                }
                addLabelName.setText(null)
                imm.hideSoftInputFromWindow(addLabelName.windowToken,0)
            }
        }
        removeLabelButton.setOnClickListener {
            if (currentLabelId!=-1){
                var remove_list= searchHash.get(LabelList[currentLabelId].first)
                Log.i("remove_list",remove_list.toString())
                //searchHash 작업도 추가할 것
                for (i in remove_list!!.indices){
                    var remove_val=imageTitleList[remove_list[i].first].tags[remove_list[i].second]
                    imageTitleList[remove_list[i].first].tags.remove(remove_val)
                }
            }
        }
        removeTagButton.setOnClickListener {
            Log.i("currentTagId", currentTagId.toString())
            if (currentTagId!=-1){
                if(imageTitleList[currentImageId].tags.size!=0){
                    var remove_val=imageTitleList[currentImageId].tags.get(selected)
                    imageTitleList[currentImageId].tags.remove(remove_val)
                    annotations.remove(remove_val)
                    tagRemoving=true
                    annotationlistAdapter.notifyDataSetChanged()
                    drawCanvas.invalidate()

                    //searchHash 작업도 추가할 것
                }
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

class LabelListAdapter (val context: Context, private val labelListAdapter: ArrayList<Pair<String,Int>>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.tag_item, null)
        val labelColorView = view.findViewById<View>(R.id.label_color)
        val tagNameText = view.findViewById<TextView>(R.id.tag_name)
        val item= labelListAdapter[position]
        tagNameText.text=item.first
        labelColorView.setBackgroundColor(item.second)
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
        var alphaColor=colorHash.get(annotation.label)!!
        view.setBackgroundColor(colorHash.get(annotation.label)!!)
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

