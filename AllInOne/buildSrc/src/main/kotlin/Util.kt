import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Created by toannguyen
 * May 31, 2019 at 11:10
 */

fun Date.format(template: String = "yyyy-MM-dd'T'HH:mm:ss'Z'"): String =
  SimpleDateFormat(template, Locale.US).format(this)

fun Date.gmtFormat() = format("EEE',' dd MMM yyyy HH':'mm':'ss 'GMT'")