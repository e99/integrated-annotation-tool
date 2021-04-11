package e99co.e99.integratedannotationtool

import android.annotation.SuppressLint
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import java.util.*

const val IMAGE_ID="image id"
var imageTitleList = arrayListOf<AnnotationImage>(
        AnnotationImage(1,"image number 01.jpg",R.drawable.car,"01 image"),
        AnnotationImage(2,"image number 02.jpg",R.drawable.dog,"02 image"),
        AnnotationImage(3,"image number 03.jpg",R.drawable.person,"03 image")
)

class MainActivity : AppCompatActivity() {
    //    private val annotationImageListViewModel by viewModels<annotationImageListViewModel> {
//        annotationImageListViewModelFactory(this)
//    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        /*
        val graphicView=findViewById(R.id.image_canvas)
        setContentView(MyGraphicView(this))
        */

        val imagetitleAdapter = ImageTitleAdapter(this, imageTitleList)
        val image_title_list = findViewById<ListView>(R.id.image_title_list)
        val imageCanvas = findViewById<ImageView>(R.id.image_canvas)
        image_title_list.adapter = imagetitleAdapter
        image_title_list.onItemClickListener = AdapterView.OnItemClickListener { parent, view, i, l ->
            val selectedItemText = parent.getItemIdAtPosition(i)
            imageCanvas.setImageResource(imageTitleList[i].image)
        }
    }
/*
    override fun onCreateOptionsMenu(menu: Menu?): Boolean { // 자바코드에서 메뉴를 구성하는 것. 지난 시간에는
        // layout에서 menu 디렉토리를 생성해서 만들었는데 코드로 만들어보자!!
        super.onCreateOptionsMenu(menu)

        menu?.add(0, 1, 0, "선 그리기")
        menu?.add(0, 2, 0, "원 그리기")
        menu?.add(0, 3, 0, "사각형 그리기")

        val sMenu = menu?.addSubMenu("색상변경==>")
        sMenu?.add(0, 4, 0, "빨강색")
        sMenu?.add(0, 5, 0, "파랑색")
        sMenu?.add(0, 6, 0, "초록색")
        sMenu?.add(0, 7, 0, "선 굵게")
        sMenu?.add(0, 8, 0, "선 가늘게")

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        when (item?.itemId) {
            1 -> {
                curShapee = LINE
                return true
            }

            2 -> {
                curShapee = CIRCLE
                return true
            }

            3 -> {
                curShapee = SQ
                return true
            }

            4 -> {
                color = 1
                return true
            }

            5 -> {
                color = 2
                return true
            }

            6 -> {
                color = 3
                return true
            }

            7 -> {
                size += 5
                return true
            }

            8 -> {
                size -= 5
                return true
            }

        }

        return super.onOptionsItemSelected(item)
    }

    companion object { // 동반 객체, 자바의 static 역할.
        internal val LINE = 1 // 선
        internal val CIRCLE = 2 // 원
        internal val SQ = 3 // 사각형
        internal var curShapee = LINE // curShape = 1
        internal var color = 1 // 색상 빨강 파랑 녹색
        internal var size = 5 // 선 굵기 기본값
        // internal은 자바의 default 역할.
        // 생성된 클라스와 패키지에서 사용 가능.
        // 다른 패키지 불가.

        internal var myShapes: MutableList<MyShape> = ArrayList()
        // 도형들의 데이터 누적.
    }

    private class MyGraphicView(context: Context) : View(context) {
        var startX = -1
        var startY = -1
        var stopX = -1
        var stopY = -1

        @SuppressLint("WrongCall")
        override fun onTouchEvent(event: MotionEvent?): Boolean {
            when (event?.action) {
                MotionEvent.ACTION_DOWN -> { //touch 시작, 화면에 손가락 올림.
                    startX = event.x.toInt()
                    startY = event.y.toInt()
                }
                MotionEvent.ACTION_MOVE -> {
                    // 화면에서 이동할 때, 화면에서 손가락을 띄였을 때.
                    stopX = event.x.toInt()
                    stopY = event.y.toInt()
                    this.invalidate() // 명령 완료, 그리기 호출.
                }

                // move랑 up을 나눠서 처리하는 이유는, 도형의 잔상이 남지 않도록 하기 위해서.

                MotionEvent.ACTION_UP -> {
                    val shape = MyShape() // 도형 데이터 1건을 저장시킬 객체 생성.
                    shape.shapeType = curShapee
                    shape.startX = startX
                    shape.startY = startY
                    shape.stopX = stopX
                    shape.stopY = stopY
                    shape.color = color
                    shape.size = size
                    myShapes.add(shape) // ArrayList에 저장. 도형 누적.

                    this.invalidate()
                }

            }

            return true
        }

        override fun onDraw(canvas: Canvas?) {
            super.onDraw(canvas)

            val paint = Paint() // paint라는 객체를 생성하고
            paint.style = Paint.Style.STROKE // 채워지지 않는 도형 형성
            // paint.strokeWidth = size.toFloat()

            for (i in myShapes.indices) {
                val shape2 = myShapes[i]
                paint.setStrokeWidth(shape2.size.toFloat())
                // 각 도형별(1번째) 사이즈 가져와서 펜 설정.

                if (shape2.color === 1) {
                    paint.color = Color.RED
                } else if (shape2.color === 2) {
                    paint.color = Color.BLUE
                } else {
                    paint.color = Color.GREEN
                }

                when (shape2.shapeType) {
                    LINE ->
                        canvas?.drawLine(shape2.startX.toFloat(), shape2.startY.toFloat(), shape2.stopX.toFloat(), shape2.stopY.toFloat(), paint)

// 피타고라스 정리 이용, 원의 반지름 구하기.
                    CIRCLE -> {
                        val radius = Math.sqrt(
                                Math.pow(
                                        (shape2.stopX - shape2.startX).toDouble(),
                                        2.0) + Math.pow((shape2.stopY - shape2.startY).toDouble(), 2.0)
                        )
                        // 결과적으로 2좌표의 거리를 산출.

                        canvas?.drawCircle(shape2.startX.toFloat(), shape2.startY.toFloat(), radius.toFloat(), paint)


                    }

                    SQ -> {
                        canvas?.drawRect(shape2.startX.toFloat(), shape2.startY.toFloat(), shape2.stopX.toFloat(), shape2.stopY.toFloat(), paint)
                    }


                }

            }

            if (color == 1) {
                paint.color = Color.RED
            } else if (color == 2) {
                paint.color = Color.BLUE
            } else {
                paint.color = Color.GREEN
            }

            when (curShapee) {
                LINE ->
                    canvas?.drawLine(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), paint)

                // 피타고라스 정리 이용, 원의 반지름 구하기.
                CIRCLE -> {
                    val radius = Math.sqrt(
                            Math.pow(
                                    (stopX - startX).toDouble(),
                                    2.0) + Math.pow((stopY - startY).toDouble(), 2.0)
                    )
                    // 결과적으로 2좌표의 거리를 산출.

                    canvas?.drawCircle(startX.toFloat(), startY.toFloat(), radius.toFloat(), paint)


                }

                SQ -> {
                    canvas?.drawRect(startX.toFloat(), startY.toFloat(), stopX.toFloat(), stopY.toFloat(), paint)
                }


            }
        }

    }
    */
}

//        val imageTitleAdapter=ImageTitleAdapter{ annotationImage -> adapterOnClick(annotationImage) }
//        val recyclerView: RecyclerView = findViewById(R.id.recycler_title_image)
//        recyclerView.adapter=imageTitleAdapter
//
////        val imageTitleObserver = Observer<annotationImage>{ annotationImage ->
////
////        }
////
////        annotationImageListViewModel.annotationImageLiveData.observe(this,{
////            it?.let {
////                imageTitleAdapter.submitList(it as MutableList<annotationImage>)
////            }
////        })
//    }
//    private fun adapterOnClick(annotationImage: AnnotationImage) {
////        val intent = Intent(this, annotationImageDetailActivity()::class.java)
////        intent.putExtra(IMAGE_ID,annotationImage.id)
////        startActivity(intent)
//    }



public class MyShape{
    var shapeType = 0
    var startX = 0
    var startY:Int = 0
    var stopX:Int = 0
    var stopY:Int = 0
    var color = 0
    var size = 0
}

class ImageTitleAdapter (val context: Context, val dogList: ArrayList<AnnotationImage>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        /* LayoutInflater는 item을 Adapter에서 사용할 View로 부풀려주는(inflate) 역할을 한다. */
        val view: View = LayoutInflater.from(context).inflate(R.layout.image_title_item, null)

        /* 위에서 생성된 view를 res-layout-main_lv_item.xml 파일의 각 View와 연결하는 과정이다. */
        val thumbnailPhoto = view.findViewById<ImageView>(R.id.thumbnail_image)
        val imageTitleItem = view.findViewById<TextView>(R.id.image_title)

        /* ArrayList<Dog>의 변수 dog의 이미지와 데이터를 ImageView와 TextView에 담는다. */
        val imagetitle = imageTitleList[position]
        //val resourceId = context.resources.getIdentifier(imagetitle.image, "drawable", context.packageName)
        thumbnailPhoto.setImageResource(imagetitle.image)
        imageTitleItem.text = imagetitle.name

        return view
    }

    override fun getItem(position: Int): Any {
        return imageTitleList[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return imageTitleList.size
    }
}
