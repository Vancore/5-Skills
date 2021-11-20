import java.util.Date

/**
 * Created by toannguyen
 * May 31, 2019 at 11:04
 */
object Env {
  val buildTime = Date()

  val branchName: String by lazy {
    val process = ProcessBuilder("git", "rev-parse", "--abbrev-ref", "HEAD")
        .start()
    process.waitFor()
    return@lazy if (process.exitValue() != 0) {
      process.errorStream.bufferedReader()
          .readText()
    } else {
      process.inputStream.bufferedReader()
          .readText()
          .trim()
    }
  }

  val numberOfCommits by lazy {
    val process = ProcessBuilder("git", "rev-list", "--count", "HEAD")
        .start()
    process.waitFor()
    if (process.exitValue() != 0) {
      throw Exception(process.errorStream.bufferedReader().readText())
    }
    return@lazy process.inputStream.bufferedReader()
        .readText()
        .trim()
        .toInt()
  }

  val commitHash by lazy {
    val process = ProcessBuilder("git", "rev-parse", "--verify", "--short", "HEAD")
        .start()
    process.waitFor()
    return@lazy if (process.exitValue() != 0) {
      process.errorStream.bufferedReader()
          .readText()
    } else {
      process.inputStream.bufferedReader()
          .readText()
          .trim()
    }
  }

}