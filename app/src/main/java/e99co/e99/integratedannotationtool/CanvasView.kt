package e99co.e99.integratedannotationtool

import android.content.ContentValues
import android.content.Context
import android.content.Intent
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import e99co.e99.integratedannotationtool.MainActivity.Companion.annotations
import e99co.e99.integratedannotationtool.MainActivity.Companion.currentImageId
import e99co.e99.integratedannotationtool.MainActivity.Companion.imageChanging
import e99co.e99.integratedannotationtool.MainActivity.Companion.imageTitleList


class CanvasView(context: Context?, attr: AttributeSet?) :
    View(context, attr) {
    private val paint = Paint() // 버퍼

    private var startX = 0
    private var startY = 0
    private var stopX = 0
    private var stopY = 0


    override fun onDraw(canvas: Canvas) {
        MainActivity.selected =-1
        paint.style = Paint.Style.STROKE
        paint.alpha = 50
        paint.strokeWidth = 6f
        //두께

        paint.color = Color.GREEN// 여기에 나중에 AnnotationData 객체에서 label 받아와서 그리기 전에 paint 객체 변경 (아니면 각각 paint 객체 갖는게 나을까??)
        if (!imageChanging) {
            canvas.drawRect(
                startX.toFloat(),
                startY.toFloat(),
                stopX.toFloat(),
                stopY.toFloat(),
                paint
            )
        }
        Log.i("Image Change:", imageChanging.toString())
        for (i in annotations.indices){
            paint.setColor(annotations[i].tagColor)
            canvas.drawRect(annotations[i].startX.toFloat(),annotations[i].startY.toFloat(),annotations[i].stopX.toFloat(),annotations[i].stopY.toFloat(),paint)
            paint.color = Color.GREEN

            MainActivity.annotationlistAdapter.notifyDataSetChanged()
        }

        imageChanging=false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                startX = saturation(event.x.toInt())
                startY = saturation(event.y.toInt())
            }
            MotionEvent.ACTION_MOVE -> {
                stopX = saturation(event.x.toInt())
                stopY = saturation(event.y.toInt())
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                invalidate()
                val annotation=AnnotationData(annotations.size,"not selected",this.startX,this.startY,this.stopX,this.stopY)
                annotations.add(annotation)
                imageTitleList[currentImageId].tags.add(annotation)
            }
        }

        //invalidate();
        return true
    }

    fun saturation(realPos:Int):Int{
        if (realPos>1486){
            return 1486
        }
        if(realPos<0){
            return 0
        }
        return realPos
    }
}
