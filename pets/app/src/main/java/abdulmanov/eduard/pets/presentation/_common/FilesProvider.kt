package abdulmanov.eduard.pets.presentation._common


import android.content.Context
import android.net.Uri
import android.os.Build
import io.reactivex.Single
import java.io.*
import java.util.*
import javax.inject.Inject

class FilesProvider @Inject constructor(private val context: Context) {

    fun getNewFileFromCache(): Single<File> {
        return Single.fromCallable {
            val fileName = Date().time.toString()
            File(context.externalCacheDir, fileName)
        }
    }

    fun getInputStreamForImageInGallery(uri: Uri): Single<InputStream> {
        return Single.fromCallable {

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
                context.contentResolver.openInputStream(uri)
            } else {
                val file = File(uri.toString())
                FileInputStream(file)
            }
        }
    }
}