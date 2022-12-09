package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.Greeting
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val Greeting : State = state(Parent) {
    onEntry {
        furhat.ask("Well hello there")
        goto(Interview)
    }
    onResponse<Greeting> {
        goto(Interview)
    }
}
