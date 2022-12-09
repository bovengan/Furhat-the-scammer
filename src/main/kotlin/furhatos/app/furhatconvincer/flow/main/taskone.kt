package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val TaskOne : State = state(Parent) {
    onEntry {
        furhat.say("I want you to run around this table as fast as you can")
        furhat.ask("Did you make it?")
    }

    onReentry {
        furhat.ask("Did you make it?")
    }

    onResponse<Yes>{
        furhat.say("You did great! Hallelujah that must have felt good. Okay now it is time for task number 2")
        goto(TaskTwo)
    }

    onResponse<No>{
        furhat.say("Come on, it is going to be fun, no one will notice you, or maybe just a few, but who cares, do it for me!")
        reentry()
    }
}
