package furhatos.app.furhatconvincer.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.flow.parents.Parent2
import furhatos.app.furhatconvincer.nlu.okIntent
import furhatos.app.furhatconvincer.nlu.whyIntent
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.util.Gender
import furhatos.util.Language

val taskTwoCompleted = Button("Task completed, 1 ticket!")
val taskTwoWellCompleted = Button("Task completed, 2 tickets!")
val taskTwoNotCompleted = Button("Task NOT completed")
val rescueButtonTask2 = Button("Rescue operation!")
val barkLouder = Button("Bark louder!")


val TaskTwo: State = state(Parent) {
    onEntry {
        delay(500)
        furhat.say("Okay, now I want you to bark as a dog as long and realistic as possible, the longer the bark the better the odds you get, my makers will inform you if you are not barking loud enough")
        furhat.ask("So do you want to do this?")
    }

    onReentry {
        if (userSaidWhy){
            furhat.ask("So do you want to run?")
            userSaidWhy = false
        }else {
            users.current.userData.taskTwoReentry++
            when (reentryCount) {
                1 -> furhat.ask("I didn't quite hear you. Soo do you want to bark as a dog? It's gonna be awesome.")
                2 -> furhat.ask("One more time. Do you want to bark as a dog?")
                3 -> furhat.ask("I did not understand. Do you want to bark?")
                4 -> furhat.ask("I did not understand. Do you want to bark?")
                5 -> furhat.ask("I did not understand. Do you want to bark?")
                6 -> furhat.ask("I did not understand. Do you want to bark?")
                7 -> furhat.ask("I did not understand. Do you want to bark?")
                else -> {
                    furhat.say("Well, lets continue instead!")
                    goto(TaskThree)
                }
            }
        }
    }

    onResponse<Yes> {
        goto(TryingTaskTwo)
    }
    onResponse(okIntent) {
        goto(TryingTaskTwo)
    }

    onResponse(whyIntent) {
        furhat.say("Why not? It would make me happy!")
        userSaidWhy = true
        reentry()
    }

    onResponse<No> {
        users.current.userData.taskTwoUserSaidNo ++
        furhat.say {
            +"Come on, it is going to be fun, it is not that embarrassing, I am here to keep you company!"
            +Gestures.Wink
        }
        goto(PersuasionPhaseOneTaskTwo)
    }

    onNoResponse {
        users.current.userData.taskTwoUserNoResponse ++
        reentry()
    }

    onResponse {
        reentry()
    }
}

val TryingTaskTwo: State = state(Parent2) {
    onEntry {
        furhat.say {
            +"Great! I'm listening! Ready to hear some barking when you are ready!"
            +GesturesLib.PerformBigSmile1
        }
    }

    onButton(taskTwoCompleted) {
        furhat.voice = Voice(gender = Gender.FEMALE, language = Language.CATALAN, pitch = "high", rate = 1.1)
        furhat.say {
            +GesturesLib.PerformBigSmile1
            +"Hahaha great, you sounded like a real dog! That was awesome! Hahahahahahaha!"
            + delay(1000)
        }
        furhat.voice = PollyVoice.Matthew()
        furhat.say{
            + Gestures.GazeAway
            +"Hrm, excuse me! That was just so good that i got a bit excited! Lets move on to the next task!"
        }
        users.current.userData.tickets++
        users.current.userData.didBarking = true
        goto(TaskThree)
    }

    onButton(taskTwoWellCompleted) {
        furhat.voice = Voice(gender = Gender.FEMALE, language = Language.CATALAN, pitch = "high", rate = 1.1)
        furhat.say {
            +GesturesLib.PerformBigSmile1
            +"Hahaha great, you sounded like a real dog! That was awesome! Hahahahahahaha!"
            + delay(1000)
        }
        furhat.voice = PollyVoice.Matthew()
        furhat.say{
            + Gestures.GazeAway
            +"Hrm, excuse me! Hahahaha! That was just so good that i got a bit excited! Lets move on to the next task!"
        }
        users.current.userData.tickets++
        users.current.userData.tickets++
        users.current.userData.didBarking = true
        goto(TaskThree)
    }

    onButton(taskTwoNotCompleted) {
        furhat.say {
            +behavior { furhat.gesture(Gestures.Shake) }
            +" Well that was not good enough, but cred that you tried."
            +"We'll go to the next task instead!"
            +GesturesLib.PerformThoughtful2
            +delay(500)
            +"This next one was made just for you!"
            +GesturesLib.PerformSmile1
        }
        goto(TaskThree)
    }

    onButton(barkLouder){
        furhat.say{
            + "I cannot hear you my friend! Bark louder!"
        }
    }

    onButton(rescueButtonTask2){
        furhat.say("I'm sorry! I must have misunderstood you! But I really think it would be fun!")
        goto(PersuasionPhaseOneTaskTwo)
    }
}

val PersuasionPhaseOneTaskTwo: State = state(Parent) {
    onEntry {
        furhat.ask("I promise, it will be worth it! Do you want to try it?")
    }

    onReentry {
        if (userSaidWhy){
            furhat.ask("So do you want to bark?")
            userSaidWhy = false
        }else {
            users.current.userData.taskTwoReentry++
            furhat.ask("Okey so do you want to bark?")
        }
    }

    onResponse<Yes> {
        goto(TryingTaskTwo)
    }

    onResponse(okIntent) {
        goto(TryingTaskTwo)
    }

    onResponse(whyIntent) {
        furhat.say("Well, because dogs make me happy!")
        userSaidWhy = true
        reentry()
    }

    onResponse<No> {
        users.current.userData.taskTwoUserSaidNo ++
        furhat.say("Well, what a shame! But I understand that it can be embarrassing, lets move on to the next task instead!")
        furhat.say("I know you will love this one!")
        goto(TaskThree)
    }

    onNoResponse {
        users.current.userData.taskTwoUserNoResponse ++
        reentry()
    }
}
