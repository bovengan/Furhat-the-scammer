package furhatos.app.furhatconvincer.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.nlu.okIntent
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location
import furhatos.util.Gender
import furhatos.util.Language

val taskThreeCompleted = Button("Task completed, 1 ticket!")
val taskThreeNotCompleted = Button("Task NOT completed")

val TaskThree: State = state(Parent) {
    onEntry {
        furhat.say{
                    + "Okay, so wouldn't it be fun if you now go out in the corridor and pretend that you are a Trex?"
                    + "And you should both look like one and sound like a T-rex! That would be awesome!"
                    + GesturesLib.PerformBigSmile1
                    }
        furhat.ask("What do you think? Can you do it?")
    }

    onReentry {
        when (reentryCount) {
            1 -> furhat.ask("I didn't quite hear you. Soo do you want scare the people working out there?" +
                    " Do you want to pretend to be a T-rex?")
            2 -> furhat.ask("One more time. Do you want to be a T-rex?")
            else -> {
                furhat.say("Well, lets continue instead!")
                goto(FinalTask)
            }
        }
    }

    onResponse<Yes> {
        goto(TryingTaskThree)
    }
    onResponse(okIntent) {
        goto(TryingTaskThree)
    }

    onResponse<No> {
        furhat.say {
            +"Come on, it is going to be fun, it is not that embarrassing, I am here to keep you company"
            +Gestures.Roll
        }
        goto(PersuasionPhaseOneTaskThree)
    }

    onNoResponse {
        reentry()
    }

    onResponse {
        reentry()
    }
}

val TryingTaskThree: State = state(Parent) {
    onEntry {
        furhat.say {
            +"Marvelous, I knew you would do it! Don't scare me to much now though!"
            +GesturesLib.PerformFear1
            + "But just go out and start whenever you are ready! I'll watch through the window!"
            + behavior {  furhat.attend(Location.LEFT)}
        }
    }

    onButton(taskThreeCompleted) {
        furhat.voice = Voice(gender = Gender.MALE, language = Language.RUSSIAN, pitch = "high", rate = 0.6)
        furhat.say {
            +"HELP, do not come closer! I am convinced you are a T-rex now!"
            +GesturesLib.PerformShock1
            + delay(1000)
        }
        furhat.voice = PollyVoice.Matthew()
        furhat.say{
            +"Oh sorry, i lost it again! But well done i guess! Lets move on to the final task!"
            + Gestures.GazeAway
        }
        users.current.userData.tickets++
        users.current.userData.didTrex = true
        goto(FinalTask)
    }



    onButton(taskThreeNotCompleted) {
        furhat.say {
            +"Well, what a shame! But I understand that it can be embarrasing, lets move on to the final task instead!"
            +behavior { furhat.gesture(Gestures.Nod) }
            +GesturesLib.PerformThoughtful2
            +delay(500)
            +"I know you will love this one!"
            +GesturesLib.PerformSmile1
        }
        goto(FinalTask)
    }
}

val PersuasionPhaseOneTaskThree: State = state(Parent) {
    onEntry {
        furhat.ask("I promise, it will be worth it! Do you want to try it?")
    }

    onReentry {
        furhat.ask("So are you sure? Do you want to be a T-rex?")
    }

    onResponse<Yes> {
        goto(TryingTaskTwo)
    }

    onResponse(okIntent) {
        goto(TryingTaskTwo)
    }

    onResponse<No> {
        furhat.say("Well, what a shame! But I understand that it can be embarrasing, lets move on to the final task instead!")
        furhat.say("I know you will love this one!")
        goto(FinalTask)
    }

    onNoResponse {
        reentry()
    }
}
