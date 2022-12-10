ackage furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.Parent
import furhatos.app.furhatconvincer.nlu.TellAge
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.TellName
import furhatos.nlu.common.Yes

val AskName : State = state(Parent) {
    onEntry {
        furhat.ask("I am Furhat, what is your name?")
    }

    onReentry {
        random(
                {   furhat.ask("I am sorry, I did not quite understand what you said, what is your name?") },
                {   furhat.ask("Oh sorry, I did not quite catch that, can you repeat your name?") },
                {   furhat.ask("Sorry, can you say your name one more time?") }
            )
    }

    onResponse<TellName> {
        users.current.userData.name = it.intent.name.toString()
        random(
            {   furhat.say("Nice to meet you " + users.current.userData.name + ", you are a very handsome human")},
            {   furhat.say("" + users.current.userData.name)
                delay(500)
                furhat.say(" what a lovely name")},
            {furhat.say("It is a pleasure meeting you "+ users.current.userData.name)}
        )
        goto(AskAge)
    }
    onResponseFailed {
        reentry()
    }
    onNoResponse {
        reentry()
    }
}

val AskAge : State = state {
    var ageConfirmed = false
    onEntry {
        furhat.ask("How old are you " + users.current.userData.name + "?")
    }

    onReentry {
        if (ageConfirmed) {
            delay(2000)
            furhat.ask("Okay, are you ready now?")
        } else {
            random(
                    { furhat.ask("I am sorry, I did not quite understand what you said, how old are you?") },
                    { furhat.ask("Oh sorry, I did not quite catch that, can you repeat your age?") },
                    { furhat.ask("Sorry, can you say your age one more time?") }
                )
        }
    }

    onResponse<TellAge> {
        users.current.userData.age = it.intent.age
        ageConfirmed = true
        furhat.ask("Cool, so " + users.current.userData.name + " age " + users.current.userData.age + " are you ready to hear about what we are going to do today?")
    }

    onResponse<Yes> {
        if (ageConfirmed) {
            goto(Explanation)
        } else {
            reentry()
        }
    }

    onResponse<No> {
        reentry()
    }
}

val Explanation : State = state {
    onEntry {
        val sentence = "Okay so what is going to happen now is that I am going to give you 3 tasks I want you to " +
                "perform. You do not have to do them, but if you do, you will get points that will increase your odds " +
                "of winning a gift from my makers. I will be the judge, with a bit of assistance from my makers, " +
                "if you complete the tasks or not. Do you understand?"
        furhat.ask(sentence)
    }

    onResponse<Yes> {
        furhat.say("Okay great, here is the first task")
        goto(TaskOne)
    }

}
