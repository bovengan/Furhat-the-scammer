package furhatos.app.furhatconvincer.nlu

import furhatos.nlu.Intent
import furhatos.nlu.SimpleIntent
import furhatos.nlu.common.Number
import furhatos.util.Language

val whyIntent = SimpleIntent("Why?", "Why would I?", "Why should I?", "Why should I do that?", "Why would I do that?", "Why, I don't want to!")

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


//class TellName(val name : String? = null) : Intent() {
//    override fun getExamples(lang:Language): List<String> {
//        return listOf("@name", "My name is @name", "I'm @name")
  //  }
//}
