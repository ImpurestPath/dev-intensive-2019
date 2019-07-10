package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName:String?):Pair<String?,String?>{
        val parts:List<String>? = fullName?.split(" ");
        return Pair(parts?.getOrNull(0)?:"null",parts?.getOrNull(1)?:"null");
    }
}