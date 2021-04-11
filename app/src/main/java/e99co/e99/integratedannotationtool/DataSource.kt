package e99co.e99.integratedannotationtool

import android.content.res.Resources
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

/* Handles operations on flowersLiveData and holds details about it. */
class DataSource(resources: Resources) {
//    private val initialAnnotationImageList = flowerList(resources)
//    private val annotationImageLiveData = MutableLiveData(initialAnnotationImageList)
//
//    /* Adds flower to liveData and posts value. */
//    fun addannotationImage(annotationimage: AnnotationImage) {
//        val currentList = annotationImageLiveData.value
//        if (currentList == null) {
//            annotationImageLiveData.postValue(listOf(annotationimage))
//        } else {
//            val updatedList = currentList.toMutableList()
//            updatedList.add(0, annotationimage)
//            annotationImageLiveData.postValue(updatedList)
//        }
//    }
//
//    /* Removes flower from liveData and posts value. */
//    fun removeannotationImage(annotationimage: AnnotationImage) {
//        val currentList = annotationImageLiveData.value
//        if (currentList != null) {
//            val updatedList = currentList.toMutableList()
//            updatedList.remove(annotationimage)
//            annotationImageLiveData.postValue(updatedList)
//        }
//    }
//
//    /* Returns flower given an ID. */
//    fun getannotationImageForId(id: Long): AnnotationImage? {
//        annotationImageLiveData.value?.let { annotationimage->
//            return annotationimage.firstOrNull{ it.id == id}
//        }
//        return null
//    }
//
//    fun getannotationImageList(): LiveData<List<AnnotationImage>> {
//        return annotationImageLiveData
//    }
//
//    /* Returns a random flower asset for flowers that are added. */
//    fun getRandomannotationImageAsset(): Int? {
//        val randomNumber = (initialAnnotationImageList.indices).random()
//        return initialAnnotationImageList[randomNumber].image
//    }
//
//    companion object {
//        private var INSTANCE: DataSource? = null
//
//        fun getDataSource(resources: Resources): DataSource {
//            return synchronized(DataSource::class) {
//                val newInstance = INSTANCE ?: DataSource(resources)
//                INSTANCE = newInstance
//                newInstance
//            }
//        }
//    }
}