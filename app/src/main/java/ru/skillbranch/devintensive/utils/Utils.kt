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
        return ""
    }

    fun toInitials(firstName: String?, lastName: String?): String? {
        val initials = "${if (firstName.isNullOrBlank()) "" else firstName[0].toUpperCase()}" +
                "${if (lastName.isNullOrBlank()) "" else lastName[0].toUpperCase()}"
        if (initials.isEmpty())
            return null
        else
            return initials
    }
}