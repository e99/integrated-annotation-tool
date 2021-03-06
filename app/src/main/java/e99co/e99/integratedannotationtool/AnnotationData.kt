package e99co.e99.integratedannotationtool

import android.graphics.Color

class AnnotationData(
    var id :Int=0,
    var label:String="",
    var startX:Int=0,
    var startY:Int=0,
    var stopX:Int=0,
    var stopY:Int=0,
    var tagColor:Int= Color.GRAY
)

/*

(var dataid :Int,
                                     var datalabel:String,
                                     var datastartX:Int,
                                     var datastartY:Int,
                                     var datastopX:Int,
                                     var datastopY:Int,
)
 */