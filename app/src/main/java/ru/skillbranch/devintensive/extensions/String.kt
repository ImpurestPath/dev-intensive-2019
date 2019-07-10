package ru.skillbranch.devintensive.extensions

fun String.truncate(lenght: Int = 16): String {
    var trimString = this.trim()

    if (lenght == trimString.length) {
        return trimString
    }

    if (trimString.length <= lenght) {
        return trimString
    }

    trimString = trimString.substring(0, lenght)

    if ((trimString.last()).isWhitespace()) {
        trimString = trimString.dropLast(1)
        trimString = "$trimString..."
        return trimString
    }

    return "$trimString..."
}

fun String.stripHtml(): String {
    var inputHtml: String = Regex("""<.*?>""").replace(this, "")

    inputHtml = inputHtml.replace("""&amp;""", "")
    inputHtml = inputHtml.replace("""&gt;""", "")
    inputHtml = inputHtml.replace("""&lt;""", "")
    inputHtml = inputHtml.replace("""&#39;""", "")
    inputHtml = inputHtml.replace("""&quot;""", "")

    if (inputHtml.contains("\n")) {
        inputHtml = Regex("""\\s+""").replace(inputHtml, " ")
        inputHtml = Regex(" {2}").replace(inputHtml, " ")
    } else {
        inputHtml = Regex("\\s+").replace(inputHtml, " ")
    }
    return inputHtml
}