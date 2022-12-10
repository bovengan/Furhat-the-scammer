package furhatos.app.furhatconvincer.flow.main.interview

import furhat.libraries.standard.GesturesLib
import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.nlu.TellAge
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val AskAge: State = state(Parent) {
    var ageConfirmed = false
    onEntry {
        furhat.ask("How old are you ?")
    }

    onReentry {
        if (ageConfirmed) {
            delay(2000)
            furhat.ask("Okay, are you ready now?")
        } else {
            furhat.gesture(Gestures.Oh)
            random(
                { furhat.ask("I am sorry, I did not quite understand what you said, how old are you?") },
                { furhat.ask("Oh sorry, I did not quite catch that, can you repeat your age?") },
                { furhat.ask("Sorry, can you say your age one more time?") }
            )
        }
    }

    onResponse<TellAge> {
        val userAge = it.intent.age.toString().toInt()
        if (userAge in 0..3) {
            GesturesLib.PerformShock1
            furhat.say("You must be the smartest baby I've ever encountered...")
            delay(500)
            furhat.ask("Now come on, what is your real age?")
        } else if (userAge in 4..17) {
            GesturesLib.PerformShock1
            furhat.say(
                "Hmmm I have a very hard time believing since the age requirement for interacting with" +
                        " me is 18... Don't know why that is though..."
            )
            Gestures.Wink
            delay(500)
            furhat.ask("Now come on, what is your real age?")
        } else if (userAge in 81..129) {
            GesturesLib.PerformShock1
            furhat.say("Wow you are really old! That is awesome, you do not look a day older than 25")
        } else if (userAge > 129) {
            GesturesLib.PerformShock1
            furhat.say("That is insane, almost unbelievable... Which it is, you little prankster...")
            delay(500)
            furhat.ask("Now come on, what is your real age?")
        } else {
            GesturesLib.PerformBigSmile1
            furhat.say("Nice, being $userAge must be great")
        }
        users.current.userData.age = it.intent.age
        val furhatAge = 47 // Gabriel's age
        val difference = furhatAge - userAge
        ageConfirmed = true
        if (difference < 0) {
            furhat.say(
                "This means that you are $difference years older than me because I am just as old" +
                        " as my inventor, I'm 47 years old"
            )
        } else if (difference > 0) {
            furhat.say(
                "This means that you are $difference years younger than me because I am just as old" +
                        " as my inventor, I'm 47 years old"
            )
        } else {
            furhat.say("This means that you, me and my inventor are the same age")
        }
        furhat.ask("But now let's move onto more important things. Are you ready to hear about what we are going to do today?")
    }

    onResponse<Yes> {
        if (ageConfirmed) {
            goto(ExplainTest)
        } else {
            reentry()
        }
    }

    onResponse<No> {
        reentry()
    }

    onResponseFailed {
        reentry()
    }

    onNoResponse {
        reentry()
    }
}
