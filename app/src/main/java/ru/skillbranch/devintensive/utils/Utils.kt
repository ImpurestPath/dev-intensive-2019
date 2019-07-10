package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName == null || fullName.isEmpty()) return null to null;
        var zeros = 0;
        for(char in fullName){
            if (char == ' ') zeros++
            else break
        }
        val full = fullName.drop(zeros);
        val parts: List<String>? = full.split(" ");
        var first:String? = parts?.getOrNull(0)
        if (first == "" ) first = null;
        var second:String? = parts?.getOrNull(1)
        if (second == "" ) second = null;
        return Pair(first,second);

    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val first = firstName?.getOrNull(0)
        val second = lastName?.getOrNull(0)
        var total = "null"
        if (first != null) {
            total = "$first".toUpperCase()
            if (second != null) total = "$total$second".toUpperCase()
        } else {
            if (second != null) total = "$second".toUpperCase()
        }
        return total;
    }

    fun transliteration(payload: String, divider: String = " "): String {
        val translit: String =
            payload.replace(Regex("[абвгдеёжзийклмнопрстуфхцчшщъыьэюяАБВГДЕЁЖЗИЙКЛМНОПРСТУФХЦЧШЩЪЫЬЭЮЯ]")) {
                when (it.value) {
                    "а" -> "a"
                    "б" -> "b"
                    "в" -> "v"
                    "г" -> "g"
                    "д" -> "d"
                    "е" -> "e"
                    "ё" -> "e"
                    "ж" -> "zh"
                    "з" -> "z"
                    "и" -> "i"
                    "й" -> "i"
                    "к" -> "k"
                    "л" -> "l"
                    "м" -> "m"
                    "н" -> "n"
                    "о" -> "o"
                    "п" -> "p"
                    "р" -> "r"
                    "с" -> "s"
                    "т" -> "t"
                    "у" -> "u"
                    "ф" -> "f"
                    "х" -> "h"
                    "ц" -> "c"
                    "ч" -> "ch"
                    "ш" -> "sh"
                    "щ" -> "sh'"
                    "ъ" -> ""
                    "ы" -> "i"
                    "ь" -> ""
                    "э" -> "e"
                    "ю" -> "yu"
                    "я" -> "ya"
                    "А" -> "A"
                    "Б" -> "B"
                    "В" -> "V"
                    "Г" -> "G"
                    "Д" -> "D"
                    "Е" -> "E"
                    "Ё" -> "E"
                    "Ж" -> "Zh"
                    "З" -> "Z"
                    "И" -> "I"
                    "Й" -> "I"
                    "К" -> "K"
                    "Л" -> "L"
                    "М" -> "M"
                    "Н" -> "N"
                    "О" -> "O"
                    "П" -> "P"
                    "Р" -> "R"
                    "С" -> "S"
                    "Т" -> "T"
                    "У" -> "U"
                    "Ф" -> "F"
                    "Х" -> "H"
                    "Ц" -> "C"
                    "Ч" -> "Ch"
                    "Ш" -> "Sh"
                    "Щ" -> "Sh'"
                    "Ъ" -> ""
                    "Ы" -> "I"
                    "Ь" -> ""
                    "Э" -> "E"
                    "Ю" -> "Yu"
                    "Я" -> "Ya"
                    else -> it.value
                }
            }
        val parts: List<String> = translit.split(" ")
        var n: Int = 0
        var translUpFirst: String = ""
        while (parts.size - 1 != n) {
            translUpFirst += parts.get(n) + divider
            n++
        }
        translUpFirst += parts.get(n)
        return translUpFirst

    }
}