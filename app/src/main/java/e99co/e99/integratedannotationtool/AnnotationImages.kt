package e99co.e99.integratedannotationtool;

import android.content.res.Resources
import e99co.e99.integratedannotationtool.R

/* Returns initial list of flowers. */
fun annotationImageList(resources: Resources): List<AnnotationImage> {
        return listOf(
                AnnotationImage(
                        id = 1,
                        name = "image name 01.jpg",
                        image = R.drawable.car,
                        description = "number 01 image"
                ),
                AnnotationImage(
                        id = 2,
                        name = "image name 02.jpg",
                        image = R.drawable.dog,
                        description = "number 02 image"
                ),
                AnnotationImage(
                        id = 3,
                        name = "image name 03.jpg",
                        image = R.drawable.person,
                        description = "number 03 image"
                )
        )
}