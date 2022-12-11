package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.nlu.okIntent
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val taskOneCompleted = Button("Task completed!")
val taskOneNotCompleted = Button("Task NOT completed")

val TaskOne: State = state(Parent) {
    onEntry {
        furhat.say("I want you to run around this table as fast as you can")
        furhat.ask("What do you think? Can you do it?")
    }

    onReentry {
        when (reentryCount) {
            1 -> furhat.ask("I didn't quite hear you. So do you want to run around the table? It's gonna be awesome.")
            2 -> furhat.ask("One more time. Do you want to run around the table?")
            else -> {
                furhat.say("Well, lets continue instead!")
                goto(TaskTwo)
            }
        }
    }

    onResponse<Yes> {
        goto(TryingTaskOne)
    }
    onResponse(okIntent) {
        goto(TryingTaskOne)
    }

    onResponse<No> {
        furhat.say("Come on, it is going to be fun, it is not that embarrassing, I am here to keep you company")
        goto(PersuasionPhaseOneTaskOne)
    }

    onNoResponse {
        reentry()
    }

    onResponse {
        reentry()
    }
}

val TryingTaskOne: State = state(Parent) {
    onEntry {
        furhat.say("Awesome, I knew you would do it! Come on, just start whenever!")
    }

    onButton(taskOneCompleted) {
        furhat.say("Good job! You looked like a real athlete doing that. Let's move onto the second task.")
        users.current.userData.tickets++
        users.current.userData.ranAroundTable = true
        goto(TaskTwo)
    }

    onButton(taskOneNotCompleted) {
        furhat.say("Well, what a shame! But I understand that it can be embarrassing, lets move on to the final task instead!")
        furhat.say("I know you will love this one!")
        goto(TaskTwo)
    }
}

val PersuasionPhaseOneTaskOne: State = state(Parent) {
    onEntry {
        furhat.ask("I promise, it will be worth it! Do you want to try it?")
    }

    onReentry {
        furhat.ask("So are you sure? You want to continue to the next task?")
    }

    onResponse<Yes> {
        goto(TryingTaskOne)
    }

    onResponse(okIntent) {
        goto(TryingTaskOne)
    }

    onResponse<No> {
        goto(PersuasionPhaseTwoTaskOne)
    }

    onNoResponse {
        reentry()
    }
}

val PersuasionPhaseTwoTaskOne: State = state(Parent) {
    onEntry {
        val sentence = "Come on, you are only " + users.current.userData.age + " years old, no one expects " +
                "you to be mature, last time I will ask, do you want to run around the table?"
        furhat.ask(sentence)
    }

    onReentry {
        furhat.ask("So are you sure? You want to continue to the next task?")
    }

    onResponse<Yes> {
        goto(TryingTaskOne)
    }

    onResponse(okIntent) {
        goto(TryingTaskOne)
    }

    onResponse<No> {
        furhat.say("Well, what a shame! But I understand that it can be embarrasing, lets move on to the final task instead!")
        furhat.say("I know you will love this one!")
        goto(TaskTwo)
    }

    onNoResponse {
        reentry()
    }
}
