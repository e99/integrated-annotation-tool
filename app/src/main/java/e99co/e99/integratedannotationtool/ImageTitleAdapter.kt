package e99co.e99.integratedannotationtool

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import e99co.e99.integratedannotationtool.R
import e99co.e99.integratedannotationtool.AnnotationImage

//class ImageTitleAdapter(private val onClick: (AnnotationImage) -> Unit) :
//    ListAdapter<AnnotationImage, ImageTitleAdapter.ImageTitleViewHolder>(AnnotationImageDiffCallback) {
//
//    class ImageTitleViewHolder(itemView: View, val onClick: (AnnotationImage) -> Unit) :
//        RecyclerView.ViewHolder(itemView) {
//        private val flowerTextView: TextView = itemView.findViewById(R.id.image_title)
//        private val flowerImageView: ImageView = itemView.findViewById(R.id.thumbnail_image)
//        private var currentImageTitle: AnnotationImage? = null
//
//        init {
//            itemView.setOnClickListener {
//                currentImageTitle?.let {
//                    onClick(it)
//                }
//            }
//        }
//
//        /* Bind flower name and image. */
//        fun bind(annotationImage: AnnotationImage) {
//            currentImageTitle = annotationImage
//
//            flowerTextView.text = annotationImage.name
//            if (annotationImage.image != null) {
//                flowerImageView.setImageResource(annotationImage.image)
//            } else {
//                flowerImageView.setImageResource(R.drawable.ic_baseline_image_not_supported_24)
//            }
//        }
//    }
//
//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageTitleViewHolder {
//        val view = LayoutInflater.from(parent.context)
//            .inflate(R.layout.image_title_item, parent, false)
//        return ImageTitleViewHolder(view, onClick)
//    }
//
//    /* Gets current flower and uses it to bind view. */
//    override fun onBindViewHolder(holder: ImageTitleViewHolder, position: Int) {
//        val annotationimage = getItem(position)
//        holder.bind(annotationimage)
//
//    }
//}



object AnnotationImageDiffCallback : DiffUtil.ItemCallback<AnnotationImage>() {
    override fun areItemsTheSame(oldItem: AnnotationImage, newItem: AnnotationImage): Boolean {
        return oldItem == newItem
    }

    override fun areContentsTheSame(oldItem: AnnotationImage, newItem: AnnotationImage): Boolean {
        return oldItem.id == newItem.id
    }
}