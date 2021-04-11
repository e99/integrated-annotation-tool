package e99co.e99.integratedannotationtool

import androidx.annotation.DrawableRes

data class AnnotationImage(
    val id: Long,
    val name: String,
    @DrawableRes
    val image: Int,
    val description: String
)