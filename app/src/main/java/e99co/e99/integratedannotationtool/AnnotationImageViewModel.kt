package e99co.e99.integratedannotationtool

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import e99co.e99.integratedannotationtool.DataSource
import e99co.e99.integratedannotationtool.AnnotationImage
import kotlin.random.Random

class annotationImageListViewModel(val dataSource: DataSource) : ViewModel() {

//    val annotationImageLiveData = dataSource.getannotationImageList()
//
//    /* If the name and description are present, create new Flower and add it to the datasource */
//    fun insertAnnotationImage(flowerName: String?, flowerDescription: String?) {
//        if (flowerName == null || flowerDescription == null) {
//            return
//        }
//
//        val image = dataSource.getRandomannotationImageAsset()
//        val newFlower = AnnotationImage(
//            Random.nextLong(),
//            flowerName,
//            image,
//            flowerDescription
//        )
//
//        dataSource.addannotationImage(newFlower)
//    }
}

//class annotationImageListViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
//
////    override fun <T : ViewModel> create(modelClass: Class<T>): T {
////        if (modelClass.isAssignableFrom(annotationImageListViewModel::class.java)) {
////            @Suppress("UNCHECKED_CAST")
////            return annotationImageListViewModel(
////                dataSource = DataSource.getDataSource(context.resources)
////            ) as T
////        }
////        throw IllegalArgumentException("Unknown ViewModel class")
////    }
//}