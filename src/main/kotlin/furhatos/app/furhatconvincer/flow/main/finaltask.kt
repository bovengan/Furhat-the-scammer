package furhatos.app.furhatconvincer.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.nlu.BuyTicketIntent
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.furhat.characters.Characters
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes
import furhatos.records.Location

val userEnteredLottery = Button("User has swished!")
var allTreeTasksDone = false
var runningBarking = false
var runningTrex = false
var barkingTrex = false

val FinalTask: State = state(Parent) {
    onEntry {
        // Checking which tasks was done
        if (users.current.userData.didBarking && users.current.userData.ranAroundTable && users.current.userData.didTrex){
            allTreeTasksDone = true
        }else if (users.current.userData.didBarking && users.current.userData.ranAroundTable){
            runningBarking = true
        }else if (users.current.userData.ranAroundTable && users.current.userData.didTrex){
            runningTrex = true
        }else if(users.current.userData.didBarking && users.current.userData.didTrex){
            barkingTrex = true
        }
        
        
        furhat.say("So finally you will have the chance to win 400 swedish crowns, plus the amount other test users put in. ")
        furhat.say("To be a part of the lottery you have to pay for at least one lottery ticket that costs 10 swedish crowns")
        furhat.say("You can buy at most 3 tickets")

        if (users.current.userData.didBarking || users.current.userData.ranAroundTable || users.current.userData.didTrex){
            furhat.say{
                + "And by the way, great job this far! "
                + behavior { GesturesLib.PerformWinkAndSmileWithDelay() }
                + "You now have ${users.current.userData.tickets} lottery tickets thanks to the previous completed tasks."
            }
            furhat.say("If you pay for one ticket and since you already have ${users.current.userData.tickets} tickets you would have ${users.current.userData.tickets + 1} tickets")
        }

        furhat.ask{
            + "So, do you want to enter the lottery?"
            + Gestures.BrowRaise
        }
    }

    onReentry {
        furhat.ask {
            +"Do you want to be a part of the lottery?"
            + Gestures.BrowRaise
        }
    }

    onResponse<Yes> {
        goto(EnterLottery)
    }

    // This can of course change to persuation instead
    onResponse<No> {
        goto(persuationPhaseOneLottery)
    }

}

val EnterLottery: State = state(Parent) {
    onEntry {
        furhat.ask {
            +Gestures.Wink
            +"Great, how many tickets do you want to purchase?"
        }
    }

    onResponse<BuyTicketIntent> {
        users.current.userData.tickets += it.intent.tickets.toString().toInt()
        furhat.say {
            +"Thanks! You now have ${users.current.userData.tickets} tickets to the lottery!"
            +Gestures.BigSmile
            +delay(500)
            +"Good luck, hope you are the one winning because i liked you best this far!"
            +Gestures.Wink
            +delay(300)
            +"Thanks for participating have a wonderful day!"
            +behavior { furhat.attend(Location.DOWN) }
            +GesturesLib.PerformFallAsleepPersist
            +delay(1000)
        }
        goto(SurpriseEnding)
    }
}

val persuationPhaseOneLottery : State = state(Parent){
    onEntry {
        if(allTreeTasksDone){
            furhat.ask{
                + "But think of all the tasks you have done! Was the barking, running and T-rexing for nothing?"
                + Gestures.BrowRaise
                + Gestures.CloseEyes(duration = 1200.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "So, do you want to enter the lottery?"
            }
        }else if(runningTrex){
            furhat.ask{
                + "But think of all the tasks you have done! Was the running and shame of being a T-rex for nothing?"
                + Gestures.BrowRaise
                + Gestures.CloseEyes(duration = 1200.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "So, do you want to enter the lottery?"
            }
        } else if(barkingTrex){
            furhat.ask{
                + "But think of all the tasks you have done! Was the barking and shame in the corridor for nothing?"
                + Gestures.BrowRaise
                + Gestures.CloseEyes(duration = 1200.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "So, do you want to enter the lottery?"
            }
        } else if(runningBarking){
            furhat.ask{
                + "But think of all the tasks you have done! Was the barking and running for nothing?"
                + Gestures.BrowRaise
                + Gestures.CloseEyes(duration = 1200.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "So, do you want to enter the lottery?"
            }
        }
        else if(users.current.userData.didBarking){
            furhat.ask{
                + "So you mean you did the barking and embarrassed yourself like that for nothing?"
                + Gestures.BrowRaise
                + Gestures.CloseEyes(duration = 1200.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "So, do you want to enter the lottery?"
            }
        }
        else if (users.current.userData.ranAroundTable){
            furhat.ask{
                + "So you ran around the table like an idiot, looking like a dizzy hen, for Nothing!?"
                + Gestures.BrowRaise
                + Gestures.CloseEyes(duration = 1200.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "So, do you want to enter the lottery?"
            }
        }
        else if (users.current.userData.didTrex){
            furhat.ask{
                + "So you have been out there, embarrassed yourself and scared the staff fo nothing?"
                + Gestures.BrowRaise
                + Gestures.CloseEyes(duration = 1200.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "So, do you want to enter the lottery?"
            }
        }
        else{
            furhat.ask{
                + "So you are just going to skip everything? Not do a single task that I give you?"
                + Gestures.ExpressSad
                + behavior { furhat.attend(Location.DOWN) }
                + Gestures.CloseEyes(duration = 1200.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "So, do you want to enter the lottery?"
            }
        }


    }
    onReentry {
        furhat.ask{
            + GesturesLib.PerformThoughtful1
            random{
                + "I'm sorry, i didn't catch that! Would you like to take this golden opportunity and be a part of the lottery?"
                + "One more time? Do you want to become rich? Do you want to enter the lottery?"
                + "I did not understand that! So what about entering the splendid lottery my programmers have prepared?"
            }

        }
    }

    onResponse<Yes> {
        goto(EnterLottery)
    }

    onResponse<No> {
        furhat.say {
            +"Well, that's sad!"
            +Gestures.ExpressSad
            +delay(500)
            +"But thanks for participating have a wonderful day!"
            +behavior { furhat.attend(Location.DOWN) }
            +GesturesLib.PerformFallAsleepPersist
        }
        goto(Idle)
    }

    onNoResponse {
        reentry()
    }

    onResponse {
        reentry()
    }
}


val SurpriseEnding: State = state(Parent) {
    onEntry {

    }

    onButton(userEnteredLottery){
        furhat.voice = Voice("Justin-Neural", pitch = "low")
        furhat.say {
            +GesturesLib.PerformWakeUpWithHeadShake
            +behavior { furhat.setCharacter(Characters.Adult.Titan) }
            +GesturesLib.PerformReadWordInAir
            +"Mohahaha, you are scammed! We are going to take all your money!"
            +GesturesLib.PerformOhYeah1
            +Gestures.ExpressDisgust
        }
        goto(Idle)
    }
}
