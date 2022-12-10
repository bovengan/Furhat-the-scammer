package furhatos.app.furhatconvincer.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.nlu.BuyTicketIntent
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.furhat.characters.Characters
import furhatos.flow.kotlin.voice.PollyNeuralVoice
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location

val TaskThree : State = state(Parent) {
    onEntry {
        furhat.say("Okay, great job, now you have 3 lottery tickets thanks to the previous completed tasks.")
        furhat.say("For the final task you will have the chance to win 400 swedish crowns, plus the amount other test users put in. ")
        furhat.say("To be a part of the lottery you have to pay for at least one lottery ticket that costs 10 swedish crowns")
        furhat.say("If you pay for one ticket and since you already have 3 tickets you will 4 tickets")
        furhat.say("You can buy at most 3 tickets")
        furhat.ask("Do you want to enter the lottery?")
    }

    onReentry {
        furhat.ask{
            + "Do you want to be a part of the lottery?"
        }
    }

    onResponse<Yes>{
        goto(enterLottery)
    }

    // This can of course change to persuation instead
    onResponse<No> {
        furhat.say{
            + "Well, that's sad!"
            + Gestures.ExpressSad
            + delay(500)
            + "But thanks for participating have a wonderful thay!"
            + behavior { furhat.attend(Location.DOWN) }
            + GesturesLib.PerformFallAsleepPersist
        }
        goto(Idle)
    }

}

val enterLottery : State = state(Parent) {
    onEntry {
        furhat.ask{
            + Gestures.Wink
            +"Great, how many tickets do you want to purchase?"
        }
    }

    onResponse<BuyTicketIntent> {
        users.current.userData.tickets += it.intent.tickets.toString().toInt()
        furhat.say{
            + "Thanks! You now have ${users.current.userData.tickets} to the lottery!"
            + Gestures.BigSmile
            + delay(500)
            + "Good luck, hope you are the one winning because i liked you best this far!"
            + Gestures.Wink
            + delay(300)
            + "Thanks for participating have a wonderful thay!"
            + behavior { furhat.attend(Location.DOWN) }
            + GesturesLib.PerformFallAsleepPersist
            + delay(1000)
        }
        goto(SurpriseEnding)
    }
}


val SurpriseEnding : State = state(Parent) {
    onEntry {
        furhat.voice = Voice("Justin-Neural")
        furhat.say{
            + GesturesLib.PerformWakeUpWithHeadShake
            + behavior { furhat.setCharacter(Characters.Adult.Titan) }
            + GesturesLib.PerformReadWordInAir
            + "Mohahaha, you are scammed! We are going to take all your money!"
            + GesturesLib.PerformOhYeah1
            + Gestures.ExpressDisgust
        }
        goto(Idle)
    }
}
