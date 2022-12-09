package furhatos.app.furhatconvincer

import furhatos.app.furhatconvincer.flow.main.Idle
import furhatos.app.furhatconvincer.setting.distanceToEngage
import furhatos.app.furhatconvincer.setting.maxNumberOfUsers
import furhatos.skills.Skill
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.Voice

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
        /** start the interaction */
        goto(Idle)
    }
}
