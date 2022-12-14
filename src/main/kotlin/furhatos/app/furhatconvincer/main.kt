package furhatos.app.furhatconvincer

//import furhatos.app.furhatconvincer.flow.*
//import furhatos.skills.Skill
//import furhatos.flow.kotlin.*

import furhatos.app.furhatconvincer.flow.main.Idle
import furhatos.app.furhatconvincer.setting.distanceToEngage
import furhatos.app.furhatconvincer.setting.maxNumberOfUsers
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.furhat.characters.Characters
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.skills.emotions.UserGestures

class FurhatconvincerSkill : Skill() {
    override fun start() {
        Flow().run(Init)
    }
}

fun main(args: Array<String>) {
    Skill.main(args)
}

val Init : State = state() {
    init {
        /** Set our default interaction parameters */
        users.setSimpleEngagementPolicy(distanceToEngage, maxNumberOfUsers)
        furhat.voice = Voice("Matthew")
        furhat.setCharacter(Characters.Adult.Alex)
        /** start the interaction */
        goto(Idle)
    }
}
