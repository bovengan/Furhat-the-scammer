package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Greeting

val Greeting : State = state(Parent) {
    onEntry {
        random(
            {   furhat.ask("Hi there") },
            {   furhat.ask("Hello there") }
        )
    }
    onResponse<Greeting> {
        goto(AskName)
    }
    onResponseFailed {
        goto(AskName)
    }
    onNoResponse {
        goto(AskName)
    }
}
