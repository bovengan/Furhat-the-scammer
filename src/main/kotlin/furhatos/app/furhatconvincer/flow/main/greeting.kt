package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.flow.main.interview.AskName
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.furhat.characters.Characters
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.Greeting

val Greeting: State = state(Parent) {
    onEntry {
        println("Are we here?")
        furhat.setCharacter(Characters.Adult.Alex)
        furhat.ledStrip.solid(java.awt.Color(0,0,0))
        furhat.voice = Voice("Matthew")
        furhat.gesture(Gestures.Smile(duration = 2.0))

        random(
            { furhat.ask("Hi there") },
            { furhat.ask("Hello there") }
        )
        println("All the way to the end")
    }
    onResponse<Greeting> {
        println("And then here?")
        goto(AskName)
    }
    onResponseFailed {
        goto(AskName)
    }
    onNoResponse {
        goto(AskName)
    }
}
