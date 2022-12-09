package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val TaskTwo : State = state(Parent) {
    onEntry {
        furhat.say("Okay, now I want you to bark as dog as long as possible, the longer the bark the better the odds you get, my makers will inform you if you are not barking loud enough")
        furhat.ask("Did you make it?")
    }

    onReentry {
        furhat.ask("Did you make it?")
    }

    onResponse<Yes>{
        furhat.say("Hahaha great, you sounded like a real dog! Now let us move on to the final task")
        goto(TaskThree)
    }

    onResponse<No>{
        furhat.say("Come on, it is going to be fun, it is not that embarrassing, I am here to keep you company")
        reentry()
    }
}
