package ru.skillbranch.devintensive.models


import androidx.core.text.isDigitsOnly

class Bender(var status: Status = Status.NORMAL, var question: Question = Question.NAME) {
    fun askQuestion() = when (question) {
        Question.NAME -> Question.NAME.question
        Question.PROFESSION -> Question.PROFESSION.question
        Question.MATERIAL -> Question.MATERIAL.question
        Question.BDAY -> Question.BDAY.question
        Question.SERIAL -> Question.SERIAL.question
        Question.IDLE -> Question.IDLE.question
    }


    fun listenAnswer(answer: String): Pair<String, Triple<Int, Int, Int>> {
        return if (answer.isNullOrEmpty()) {
            "${question.validationString}\n${question.question}" to status.color
        } else if (!question.validateInput(answer)) {
            "${question.validationString}\n${question.question}" to status.color
        } else if (question.answers.contains(answer.toLowerCase())) {
            question = question.nextQuestion()
            if (question != Question.IDLE)
                "Отлично - ты справился\n${question.question}" to status.color
            else
                "Отлично - ты справился\nНа этом все, вопросов больше нет" to status.color
        } else {
            status = status.nextStatus()
            if (status != Status.NORMAL)
                "Это неправильный ответ\n${question.question}" to status.color
            else
                "Это неправильный ответ. Давай все по новой\n${question.question}" to status.color
        }
    }

    enum class Status(val color: Triple<Int, Int, Int>) {
        NORMAL(Triple(255, 255, 255)),
        WARNING(Triple(255, 120, 0)),
        DANGER(Triple(255, 60, 60)),
        CRITICAL(Triple(255, 0, 0));

        fun nextStatus(): Status {
            return if (this.ordinal < values().lastIndex) {
                values()[this.ordinal + 1]
            } else
                values()[0]
        }
    }

    enum class Question(
        val question: String,
        val answers: List<String>,
        val validationString: String
    ) {
        NAME(
            "Как меня зовут?",
            listOf("Бендер", "bender"),
            "Имя должно начинаться с заглавной буквы"
        ) {
            override fun nextQuestion(): Question = PROFESSION
            override fun validateInput(answer: String): Boolean {
                return answer[0].isUpperCase()
            }
        },
        PROFESSION(
            "Назови мою профессию?",
            listOf("сгибальщик", "bender"),
            "Профессия должна начинаться со строчной буквы"
        ) {
            override fun nextQuestion(): Question = MATERIAL
            override fun validateInput(answer: String): Boolean {
                return answer[0].isLowerCase()
            }
        },
        MATERIAL(
            "Из чего я сделан?",
            listOf("металл", "дерево", "metal", "iron", "wood"),
            "Материал не должен содержать цифр"
        ) {
            override fun nextQuestion(): Question = BDAY
            override fun validateInput(answer: String): Boolean {
                return answer.none { it -> it.isDigit() }
            }
        },
        BDAY(
            "Когда меня создали?",
            listOf("2993"),
            "Год моего рождения должен содержать только цифры"
        ) {
            override fun nextQuestion(): Question = SERIAL
            override fun validateInput(answer: String): Boolean {
                return answer.isDigitsOnly()
            }
        },
        SERIAL(
            "Мой серийный номер?",
            listOf("2716057"),
            "Серийный номер содержит только цифры, и их 7"
        ) {
            override fun nextQuestion(): Question = IDLE
            override fun validateInput(answer: String): Boolean {
                return (answer.isDigitsOnly() && answer.length == 7)
            }
        },
        IDLE("На этом все, вопросов больше нет", listOf(), "") {
            override fun nextQuestion(): Question = IDLE
            override fun validateInput(answer: String): Boolean {
                return true
            }
        };

        abstract fun nextQuestion(): Question
        abstract fun validateInput(answer: String): Boolean
    }

}