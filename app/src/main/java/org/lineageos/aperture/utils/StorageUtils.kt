package org.lineageos.aperture.utils

import android.content.ContentResolver
import android.content.ContentValues
import android.provider.MediaStore
import androidx.camera.core.ImageCapture
import androidx.camera.video.MediaStoreOutputOptions
import androidx.camera.view.video.OutputFileOptions
import java.text.SimpleDateFormat
import java.util.Locale

object StorageUtils {
    private const val FILENAME_FORMAT = "yyyy-MM-dd-HH-mm-ss-SSS"
    private const val STORAGE_DESTINATION = "DCIM/Aperture"

    /**
     * Returns a new ImageCapture.OutputFileOptions to use to store a JPEG photo
     */
    fun getPhotoMediaStoreOutputOptions(
        contentResolver: ContentResolver
    ): ImageCapture.OutputFileOptions {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, getCurrentTimeString())
            put(MediaStore.MediaColumns.MIME_TYPE, "image/jpeg")
            put(MediaStore.Images.Media.RELATIVE_PATH, STORAGE_DESTINATION)
        }

        return ImageCapture.OutputFileOptions
            .Builder(
                contentResolver, MediaStore.Images.Media.EXTERNAL_CONTENT_URI,
                contentValues
            )
            .build()
    }

    /**
     * Returns a new MediaStoreOutputOptions to use to store a MP4 video
     */
    @androidx.camera.view.video.ExperimentalVideo
    fun getVideoMediaStoreOutputOptions(contentResolver: ContentResolver): OutputFileOptions {
        val contentValues = ContentValues().apply {
            put(MediaStore.MediaColumns.DISPLAY_NAME, getCurrentTimeString())
            put(MediaStore.MediaColumns.MIME_TYPE, "video/mp4")
            put(MediaStore.Video.Media.RELATIVE_PATH, STORAGE_DESTINATION)
        }

        return OutputFileOptions
            .builder(contentResolver, MediaStore.Video.Media.EXTERNAL_CONTENT_URI, contentValues)
            .build()
    }

    private fun getCurrentTimeString(): String {
        return SimpleDateFormat(FILENAME_FORMAT, Locale.US)
            .format(System.currentTimeMillis())
    }
}
