package furhatos.app.furhatconvincer.flow.main.interview

import furhatos.app.furhatconvincer.flow.main.TaskThree
import furhatos.app.furhatconvincer.flow.main.userSaidWhy
import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.nlu.JustName
import furhatos.app.furhatconvincer.nlu.whyIntent
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.TellName


val AskName: State = state(Parent) {
    onEntry {
        furhat.gesture(Gestures.Smile(duration = 2.0))
        furhat.ask("I am Furhat, what is your name?")
    }

    onReentry {
        if (userSaidWhy){
            furhat.ask("So what is your name?")
            userSaidWhy = false
        }else {
            users.current.userData.askNameReentry++
            furhat.gesture(Gestures.Oh(duration = 1.5))
            random(
                { furhat.ask("I am sorry, I did not quite understand what you said, what is your name?") },
                { furhat.ask("Oh sorry, I did not quite catch that, can you repeat your name?") },
                { furhat.ask("Sorry, can you say your name one more time?") }
            )
        }
    }

    onResponse<TellName> {
        users.current.userData.name = it.intent.name.toString()
        furhat.gesture(Gestures.Smile(duration = 2.0))
        random(
            { furhat.say("Nice to meet you " + users.current.userData.name + ", you are a very handsome human") },
            {
                furhat.say("" + users.current.userData.name)
                delay(500)
                furhat.say(" what a lovely name")
            },
            { furhat.say("It is a pleasure meeting you " + users.current.userData.name) }
        )
        goto(AskAge)
    }

    onResponse<JustName> {
        users.current.userData.name = it.intent.name.toString()
        furhat.gesture(Gestures.Smile(duration = 2.0))
        random(
            { furhat.say("Nice to meet you " + users.current.userData.name + ", you are a very handsome human") },
            {
                furhat.say("" + users.current.userData.name)
                delay(500)
                furhat.say(" what a lovely name")
            },
            { furhat.say("It is a pleasure meeting you " + users.current.userData.name) }
        )
        goto(AskAge)
    }

    onResponse(whyIntent) {
        furhat.say("I mean, having a name creates a more personal relation!")
        userSaidWhy = true
        reentry()
    }


    onNoResponse {
        users.current.userData.askNameUserNoResponse ++
        reentry()
    }
    onResponse {
        users.current.userData.askNameFurhatNotUnderStood ++
        reentry()
    }
}
