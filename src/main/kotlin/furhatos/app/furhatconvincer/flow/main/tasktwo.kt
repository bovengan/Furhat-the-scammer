package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.SimpleIntent
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val completedTask = Button("Task completed!")
val taskNotCompleted = Button("Task NOT completed")

val okIntent = SimpleIntent("Okey", "Ok", "Okey, I can do it!", "Okey, I'll do it!", "Well, I'll do it!")

val TaskTwo : State = state(Parent) {
    onEntry {
        furhat.say("Okay, now I want you to bark as a dog as long and realistic as possible, the longer the bark the better the odds you get, my makers will inform you if you are not barking loud enough")
        furhat.ask("What do you think? Can you do it?")
    }

    onReentry {
        when(reentryCount){
            1 -> furhat.ask("I didn't quite hear you. Soo do you want to bark as a dog? It's gonna be awesome.")
            2 -> furhat.ask("One more time. Do you want to bark as a dog?")
            else -> {
                furhat.say("Well, lets continue instead!")
                goto(TaskThree)
            }
        }
    }

    onResponse<Yes>{
        goto(tryingTask)
    }
    onResponse(okIntent) {
        goto(tryingTask)
    }

    onResponse<No>{
        furhat.say("Come on, it is going to be fun, it is not that embarrassing, I am here to keep you company")
        goto(persuationPhase)
    }

    onNoResponse {
        reentry()
    }

    onResponse {
        reentry()
    }
}

val tryingTask : State = state(Parent) {
    onEntry {
        furhat.say("Awesome, I knew you would do it! Come on, just start whenever!")
    }

    onButton(completedTask){
        furhat.say("Hahaha great, you sounded like a real dog! Now let us move on to the final task")
        goto(TaskThree)
    }

    onButton(taskNotCompleted) {
        furhat.say("Well, what a shame! But I understand that it can be embarrasing, lets move on to the final task instead!")
        furhat.say("I know you will love this one!")
        goto(TaskThree)
    }
}

val persuationPhase : State = state(Parent) {
    onEntry {
        furhat.ask("I promise, it will be worth it! Are you sure you don't want to try it?")
    }

    onReentry {
        furhat.ask("So are you sure? You want to continue to the next task?")
    }

    onResponse<Yes> {
        furhat.say("Well, what a shame! But I understand that it can be embarrasing, lets move on to the final task instead!")
        furhat.say("I know you will love this one!")
        goto(TaskThree)
    }

    onResponse(okIntent) {
        goto(tryingTask)
    }

    onResponse<No> {
        furhat.say("Okay, I'll take that as a YES, I wan't to do the task! Leets go!")
        goto(tryingTask)
    }

    onNoResponse {
        reentry()
    }
}
