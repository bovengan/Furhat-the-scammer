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
            "Erik",
            "Adrian",
            "Christian",
            "CJ",
            "Carl Johan",
            "Elliot"
        )
    }
}