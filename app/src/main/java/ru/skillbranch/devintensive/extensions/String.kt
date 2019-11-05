package ru.skillbranch.devintensive.extensions

fun String.truncate(numberOfSymbols: Int = 16): String {
    if (this.trimEnd().length <= numberOfSymbols)
        return this.trimEnd()
    else
        return this.substring(0..numberOfSymbols - 1).trimEnd() + "..."
}

fun String.stripHtml(): String {
    var resultStr = this.replace("<[^>]*>".toRegex(), "")
    resultStr = resultStr.replace("&nbsp;", "")
    resultStr = resultStr.replace("\\s+".toRegex(), " ")
    return resultStr
}