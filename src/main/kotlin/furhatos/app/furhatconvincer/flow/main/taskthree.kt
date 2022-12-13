package furhatos.app.furhatconvincer.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.flow.parents.Parent2
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
val betterTrex = Button("Better impression please!")
val goFurtherAway = Button("Go further away!")

val TaskThree: State = state(Parent) {
    onEntry {
        furhat.say{
                    + Gestures.Smile(duration = 1.5)
                    + "Okay, so wouldn't it be fun if you now go out in the corridor and pretend that you are the dinosaur T-rex?"
                    + "And you should both look like one and sound like a T-rex! That would be awesome!"
                    }
        furhat.ask("So is this something for you?")
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
            +"Really? I think T-rexes are soo cool! I would love it of you did it!"
            +Gestures.Wink
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

val TryingTaskThree: State = state(Parent2) {
    onEntry {
        furhat.say {
            +GesturesLib.PerformBigSmile1
            +"Marvelous, I knew you would do it! Don't scare me to much now though!"
            + "But just go out and start whenever you are ready! I'll watch from here!"
            + behavior {  furhat.attend(Location.LEFT)}
        }
    }

    onButton(taskThreeCompleted) {
        furhat.attend(users.current)
        furhat.voice = Voice(gender = Gender.MALE, language = Language.DANISH, pitch = "high", rate = 1.0)
        furhat.say {
            +GesturesLib.PerformShock1
            +"HELP, do not come closer! I am convinced you are a T-rex now!"
            + delay(1000)
        }
        furhat.voice = PollyVoice.Matthew()
        furhat.say{
            + Gestures.GazeAway(duration = 1.7)
            +"Oh sorry, i lost it again! But well done i guess! Lets move on to the final task!"

        }
        users.current.userData.tickets++
        users.current.userData.didTrex = true
        goto(FinalTask)
    }



    onButton(taskThreeNotCompleted) {
        furhat.say {
            +behavior { furhat.gesture(Gestures.Nod) }
            +GesturesLib.PerformThoughtful2
            +"Well, well, well, well. Valid effort but not nearly good enough!"
            + "That's an F for this task, but let's move on to the final!"
            +delay(500)
            +GesturesLib.PerformSmile1
        }
        goto(FinalTask)
    }

    onButton(betterTrex){
        furhat.say("A bit more effort please! That is not good enough!")
    }
    onButton(goFurtherAway){
        furhat.say("Go further away please! You need to scare more people!")
    }
}

val PersuasionPhaseOneTaskThree: State = state(Parent) {
    onEntry {
        furhat.say("This can be epic! Think about it!")
        if(users.current.userData.ranAroundTable && users.current.userData.didBarking){
            furhat.say{
                +"You already barked "
                + behavior { furhat.attend(Location.DOWN_LEFT) }
                + behavior { furhat.attend(Location.DOWN_RIGHT) }
                +"and ran around this table"
                + behavior { furhat.attend(users.current)}
                + "This is not any worse than that, right?"
            }
        }else if(users.current.userData.didBarking){
            furhat.say{
                +"You already barked like a mad dog!"
                +Gestures.Wink
                + "The damage is already done, this is not worse, right?"
            }
        }else if(users.current.userData.ranAroundTable){
            furhat.say{
                +"You have already been up and running!"
                + Gestures.BrowRaise(duration = 2.0)
                +"Think of this as an extension of that!"
            }
        }

        furhat.ask("So do you want to do the T-rex?")

    }

    onReentry {
        furhat.ask("So are you sure? Do you want to be a T-rex?")
    }

    onResponse<Yes> {
        goto(TryingTaskThree)
    }

    onResponse(okIntent) {
        goto(TryingTaskThree)
    }

    onResponse<No> {
        furhat.say("Okay, okay! You are a scared i guess!")
        furhat.say("Lets move on to the final task instead!")
        goto(FinalTask)
    }

    onNoResponse {
        reentry()
    }
}
