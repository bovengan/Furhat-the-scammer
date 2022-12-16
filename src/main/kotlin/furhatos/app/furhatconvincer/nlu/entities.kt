package furhatos.app.furhatconvincer.nlu

import furhatos.nlu.EnumEntity
import furhatos.util.Language

class RepeatInstructionsAnswers : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "repeat",
            "slower",
            "shorter",
            "Japanese"
        )
    }
}

// Hard coded names of our subjects
class SubjectsNames : EnumEntity(stemming = true, speechRecPhrases = true) {
    override fun getEnum(lang: Language): List<String> {
        return listOf(
            "Lisa",
            "Liza",
            "Daniel",
            "Emil",
            "Erik",
            "Adrian",
            "Noel",
            "Christian",
            "CJ",
            "Fabian",
            "Carl Johan",
            "Elliot",
            "Hugo",
            "Sally",
            "Karl",
            "Peter",
            "Adam",
            "Lea",
            "Gustav",
            "Vincent",
            "Sara",
            "Simon",
            "Samuel",
            "Mattias",
            "Lovisa",
            "Anna",
            "Karl",
            "Oskar",
            "Ella",
            "Anne",
            "Emilia",
            "Richard",
            "Olivia",
            "Hania",
            "Kristian",
            "Ture",
            "Tor",
            "Elias",
            "Audrey",
            "Audri",
            "Amanda",
            "Victoria",
            "Janni"
        )
    }
}
