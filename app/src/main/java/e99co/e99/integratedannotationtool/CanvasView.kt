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
import e99co.e99.integratedannotationtool.MainActivity.Companion.tagRemoving


class CanvasView(context: Context?, attr: AttributeSet?) :
    View(context, attr) {
    private val paint = Paint() // 버퍼

    private var startX = 0
    private var startY = 0
    private var stopX = 0
    private var stopY = 0
    private var moveX = 0
    private var moveY = 0
    private var selected = -1
    private var rectMoving=false

    override fun onDraw(canvas: Canvas) {

        MainActivity.selected =-1
        paint.style = Paint.Style.STROKE
        paint.alpha = 50
        paint.strokeWidth = 6f
        //두께

        paint.color = Color.GREEN// 여기에 나중에 AnnotationData 객체에서 label 받아와서 그리기 전에 paint 객체 변경 (아니면 각각 paint 객체 갖는게 나을까??)
        if (!imageChanging&&!tagRemoving&&!rectMoving) {
            if (startX!=-1 && startY!=-1 && stopX!=-1 && stopY!=-1){
                canvas.drawRect(
                    startX.toFloat(),
                    startY.toFloat(),
                    stopX.toFloat(),
                    stopY.toFloat(),
                    paint
                )
            }
        }

        for (i in annotations.indices){
            paint.setColor(annotations[i].tagColor)
            canvas.drawRect(annotations[i].startX.toFloat(),annotations[i].startY.toFloat(),annotations[i].stopX.toFloat(),annotations[i].stopY.toFloat(),paint)
            paint.color = Color.GREEN
            MainActivity.annotationlistAdapter.notifyDataSetChanged()
        }
        imageChanging=false
        tagRemoving=false
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                var x=saturation(event.x.toInt())
                var y=saturation(event.y.toInt())
                if(checkRectClicked(x,y)!=-1){
                    Log.i("checkRectClicked: ","id - "+checkRectClicked(x,y)+" rect is clicked")
                    selected=checkRectClicked(x,y)
                    moveX=x
                    moveY=y
                    rectMoving=true
                }else{
                    startX = x
                    startY = y
                }
            }
            MotionEvent.ACTION_MOVE -> {
                var x=saturation(event.x.toInt())
                var y=saturation(event.y.toInt())
                if(selected!=-1){
                    var dx=x-moveX
                    var dy=y-moveY
                    annotations[selected].startX+=dx
                    annotations[selected].stopX+=dx
                    annotations[selected].startY+=dy
                    annotations[selected].stopY+=dy
                    moveX=x
                    moveY=y
                }
                else{
                    stopX = saturation(event.x.toInt())
                    stopY = saturation(event.y.toInt())
                }
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                invalidate()
                if(selected!=-1){
                    MainActivity.annotationlistAdapter.notifyDataSetChanged()
                    rectMoving=false
                    selected=-1
                    startX=-1
                    startY=-1
                    stopX=-1
                    stopY=-1
                }
                else{
                    val annotation=AnnotationData(annotations.size,"not selected",this.startX,this.startY,this.stopX,this.stopY)
                    annotations.add(annotation)
                    imageTitleList[currentImageId].tags.add(annotation)
                    selected=-1
                }

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
    fun checkRectClicked(x:Int,y:Int):Int{
        for (i in annotations.indices){
            if(annotations[i].startX<=x && x<=annotations[i].stopX && annotations[i].startY<=y && y<=annotations[i].stopY){
                return i
                // tag의 id 부여 방식을 고민해야 함 선택이 안됨
                // annotations 와 imagelist의 tag를 잘 왔다갔다 할 수 있게 코드 수정할 것
            }
        }
        return -1
    }
}
