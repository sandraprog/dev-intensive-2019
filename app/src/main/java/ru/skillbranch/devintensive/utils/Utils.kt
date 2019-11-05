package ru.skillbranch.devintensive.utils

object Utils {
    fun parseFullName(fullName: String?): Pair<String?, String?> {
        if (fullName.isNullOrBlank())
            return null to null
        else {
            val parts: List<String>? = fullName?.split(" ")
            val firstName = parts?.getOrNull(0)
            val lastName = parts?.getOrNull(1)
            return firstName to lastName
        }
    }

    fun transliteration(payload: String, divider: String = " "): String {
        var resultStr = ""
        for (ch in payload) {
            resultStr += when (ch.toString()) {
                " " -> divider
                else -> if (ch.isUpperCase())
                            mapping.get(ch.toString().toLowerCase())?.capitalize()?: ch
                        else
                            mapping.get(ch.toString()) ?: ch
            }
        }
        return resultStr
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val initials = "${if (firstName.isNullOrBlank()) "" else firstName[0].toUpperCase()}" +
                "${if (lastName.isNullOrBlank()) "" else lastName[0].toUpperCase()}"
        if (initials.isEmpty())
            return null
        else
            return initials
    }

    val mapping: HashMap<String, String> = hashMapOf(
        "а" to "a",

        "б" to "b",

        "в" to "v",

        "г" to "g",

        "д" to "d",

        "е" to "e",

        "ё" to "e",

        "ж" to "zh",

        "з" to "z",

        "и" to "i",

        "й" to "i",

        "к" to "k",

        "л" to "l",

        "м" to "m",

        "н" to "n",

        "о" to "o",

        "п" to "p",

        "р" to "r",

        "с" to "s",

        "т" to "t",

        "у" to "u",

        "ф" to "f",

        "х" to "h",

        "ц" to "c",

        "ч" to "ch",

        "ш" to "sh",

        "щ" to "sh'",

        "ъ" to "",

        "ы" to "i",

        "ь" to "",

        "э" to "e",

        "ю" to "yu",

        "я" to "ya"

    )
}