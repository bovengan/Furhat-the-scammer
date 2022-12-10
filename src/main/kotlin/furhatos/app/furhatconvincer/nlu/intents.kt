package furhatos.app.furhatconvincer.nlu

import furhatos.nlu.Intent
import furhatos.nlu.SimpleIntent
import furhatos.nlu.common.Number
import furhatos.util.Language

val okIntent = SimpleIntent("Okey", "Ok", "Okey, I can do it!", "Okey, I'll do it!", "Well, I'll do it!")

class JustName(val name: SubjectsNames? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf("@name")
    }
}

class TellAge(val age: Number? = null) : Intent() {

    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@age",
            "I am @age",
            "I'm @age",
            "I'm @age years",
            "I'm @age years old"
        )
    }
}


class BuyTicketIntent(val tickets: Number? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@tickets",
            "I want @tickets tickets",
            "@tickets tickets",
            "I would like to buy @tickets tickets"
        )
    }
}

class RepeatInstructions(val answer: RepeatInstructionsAnswers? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "repeat it @answer",
            "@answer",
            "@answer it",
            "just @answer",
            "just @answer it",
            "in @answer"
        )
    }
}