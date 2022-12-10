package furhatos.app.furhatconvincer.nlu

import furhatos.nlu.EnumEntity
import furhatos.nlu.Intent
import furhatos.nlu.SimpleIntent
import furhatos.nlu.common.Time
import furhatos.nlu.common.Number
import furhatos.util.Language


class TellAge(val age : Number? = null) : Intent() {

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



class BuyTicketIntent(val tickets : Number? = null) : Intent() {
    override fun getExamples(lang: Language): List<String> {
        return listOf(
            "@tickets",
            "I want @tickets tickets",
            "@tickets tickets",
            "I would like to buy @tickets tickets"
        )
    }
}
