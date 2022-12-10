package furhatos.app.furhatconvincer.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.furhatconvincer.flow.Parent
import furhatos.app.furhatconvincer.nlu.okIntent
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val taskTwoCompleted = Button("Task completed, 1 ticket!")
val taskTwoWellCompleted = Button("Task completed, 2 tickets!")
val taskTwoNotCompleted = Button("Task NOT completed")

val TaskTwo: State = state(Parent) {
    onEntry {
        furhat.say("Okay, now I want you to bark as a dog as long and realistic as possible, the longer the bark the better the odds you get, my makers will inform you if you are not barking loud enough")
        furhat.ask("What do you think? Can you do it?")
    }

    onReentry {
        when (reentryCount) {
            1 -> furhat.ask("I didn't quite hear you. Soo do you want to bark as a dog? It's gonna be awesome.")
            2 -> furhat.ask("One more time. Do you want to bark as a dog?")
            else -> {
                furhat.say("Well, lets continue instead!")
                goto(TaskThree)
            }
        }
    }

    onResponse<Yes> {
        goto(TryingTaskTwo)
    }
    onResponse(okIntent) {
        goto(TryingTaskTwo)
    }

    onResponse<No> {
        furhat.say {
            +"Come on, it is going to be fun, it is not that embarrassing, I am here to keep you company"
            +Gestures.Roll
        }
        goto(PersuasionPhaseOneTaskTwo)
    }

    onNoResponse {
        reentry()
    }

    onResponse {
        reentry()
    }
}

val TryingTaskTwo: State = state(Parent) {
    onEntry {
        furhat.say {
            +"Awesome, I knew you would do it! Come on, just start whenever!"
            +GesturesLib.PerformBigSmile1
        }
    }

    onButton(taskTwoCompleted) {
        furhat.say {
            +"Hahaha great, you sounded like a real dog! Now let us move on to the final task"
            +GesturesLib.PerformBigSmile1
        }
        users.current.userData.tickets++
        goto(TaskThree)
    }

    onButton(taskTwoWellCompleted) {
        furhat.say {
            +"Hahaha great, you sounded like a real dog! Now let us move on to the final task"
            +GesturesLib.PerformBigSmile1
        }
        users.current.userData.tickets++
        users.current.userData.tickets++
        goto(TaskThree)
    }

    onButton(taskTwoNotCompleted) {
        furhat.say {
            +"Well, what a shame! But I understand that it can be embarrasing, lets move on to the final task instead!"
            +behavior { furhat.gesture(Gestures.Nod) }
            +GesturesLib.PerformThoughtful2
            +delay(500)
            +"I know you will love this one!"
            +GesturesLib.PerformSmile1
        }
        goto(TaskThree)
    }
}

val PersuasionPhaseOneTaskTwo: State = state(Parent) {
    onEntry {
        furhat.ask("I promise, it will be worth it! Do you want to try it?")
    }

    onReentry {
        furhat.ask("So are you sure? Do you want to bark?")
    }

    onResponse<Yes> {
        furhat.say("Okay, I'll take that as a YES, I wan't to do the task! Leets go!")
        goto(TryingTaskTwo)
    }

    onResponse(okIntent) {
        goto(TryingTaskTwo)
    }

    onResponse<No> {
        furhat.say("Well, what a shame! But I understand that it can be embarrasing, lets move on to the final task instead!")
        furhat.say("I know you will love this one!")
        goto(TaskThree)
    }

    onNoResponse {
        reentry()
    }
}
