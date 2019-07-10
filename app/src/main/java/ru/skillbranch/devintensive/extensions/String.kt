package ru.skillbranch.devintensive.extensions

fun String.stripHtml(): String {
    val escape = "&[^;]+;".toRegex()
    val space = " {2,}".toRegex()
    val tag = "<[^>]+>".toRegex()
    return this.replace(escape, "").replace(space, " ").replace(tag, "")
}

fun String.truncate(num: Int = 16): String {
    val outString = this.trim()
    if (num > outString.length - 1)
        return outString
    return when (outString[num - 1]) {
        ' ' -> "${outString.substring(0 until num - 1)}..."
        else -> "${outString.substring(0 until num)}..."
    }
}