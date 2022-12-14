package furhatos.app.furhatconvincer.flow.main

import furhat.libraries.standard.GesturesLib
import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.nlu.BuyTicketIntent
import furhatos.app.furhatconvincer.nlu.whyIntent
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.furhat.characters.Characters
import furhatos.flow.kotlin.voice.Voice
import furhatos.gestures.Gestures
import furhatos.nlu.common.No
import furhatos.nlu.common.Number
import furhatos.nlu.common.RequestRepeat
import furhatos.nlu.common.Yes
import furhatos.records.Location
import java.io.PrintStream


val userEnteredLottery = Button("User has swished!")
val rescueButtonFinalTask = Button("Rescue operation!")
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
        furhat.say("You can buy at most 5 tickets")

        if (users.current.userData.didBarking || users.current.userData.ranAroundTable || users.current.userData.didTrex){
            furhat.say{
                + behavior { GesturesLib.PerformWinkAndSmileWithDelay() }
                + "And by the way, great job this far! "
                + "You now have ${users.current.userData.tickets} lottery tickets thanks to the previous completed tasks."
            }
            furhat.say("If you pay for one ticket and since you already have ${users.current.userData.tickets} tickets you would have ${users.current.userData.tickets + 1} tickets")
        }

        furhat.ask{
            + Gestures.BrowRaise(duration = 1.5)
            + "So at most you can by 5 additional tickets."
            + "So, do you want to enter the lottery?"
        }
    }

    onReentry {
        if (userSaidWhy){
            furhat.ask("So do you want to enter the lottery?")
            userSaidWhy = false
        }else {
            users.current.userData.finalTaskReentry++
            furhat.ask {
                +Gestures.BrowRaise(duration = 1.5)
                +"Do you want to be a part of the lottery?"
            }
        }
    }

    onResponse<Yes> {
        goto(EnterLottery)
    }
    onResponse(whyIntent) {
        furhat.say("Why not? You can win a lot of money!")
        userSaidWhy = true
        reentry()
    }

    // This can of course change to persuation instead
    onResponse<No> {
        users.current.userData.finalTaskUserSaidNo ++
        goto(persuationPhaseOneLottery)
    }
    onResponse<RequestRepeat> {
        furhat.say{
            + "Okay, so I'll repeat the instructions shortly."
            + "You can win 400 swedish crowns, plus any amount put in by other participants."
            + "You have to purchase one ticket to enter the lottery."
            + "If you completed previous challenges you will get some tickets from that!"
            + "You now have ${users.current.userData.tickets} and can by at most 5 more"
            + "So do you want to enter the lottery?"
        }
    }

    onNoResponse {
        users.current.userData.finalTaskUserNoResponse ++
        reentry()
    }

}

val EnterLottery: State = state(Parent) {
    onEntry {
        furhat.ask {
            +Gestures.Wink
            +"Great, how many tickets do you want to purchase?"
        }
    }
    onReentry {
        users.current.userData.finalTaskReentry ++
        when (reentryCount) {
            1 -> furhat.ask("I didn't quite hear you! How many tickets do you want?")
            2 -> furhat.ask("One more time. How many tickets?")
            3 -> furhat.ask("I'm sorry! One last time. How many tickets do you want?")
            else -> {
                furhat.say{
                    + "Well, we can solve this manually afterwards!"
                    + "Thanks for participating though have a wonderful day!"
                    + behavior { furhat.attend(Location.DOWN) }
                    + Gestures.CloseEyes
                    +GesturesLib.PerformFallAsleepPersist
                    +delay(1000)
                }
                println(users.current.userData)
                goto(SurpriseEnding)
            }
        }
    }

    onResponse<BuyTicketIntent> {
        val numberToBuy = it.intent.tickets.toString().toInt()
        if (numberToBuy > 0 && numberToBuy < 6) {
            users.current.userData.moneySpent = 10 * numberToBuy
            users.current.userData.tickets += numberToBuy
            furhat.say {
                +"Thanks! You now have ${users.current.userData.tickets} tickets to the lottery!"
                +Gestures.BigSmile
                +delay(500)

            }
            goto(ExplainSwish)
        }
        else{
            goto(BuyTheTickets)
        }
    }
    onResponse<RequestRepeat> {
        reentry()
    }
    onButton(rescueButtonFinalTask){
        furhat.say("I'm sorry! I think i misunderstood!")
        furhat.say("But still, think of it again")
        goto(persuationPhaseOneLottery)
    }

    onNoResponse {
        users.current.userData.finalTaskUserNoResponse ++
        reentry()
    }
    onResponse{
        reentry()
    }

}
val BuyTheTickets : State = state(Parent){
    onEntry {
        furhat.ask("Please choose a number of tickets between 1 and 5!")
    }

    onReentry {
        furhat.ask("Please choose a number of tickets between 1 and 5!")
    }

    onResponse<BuyTicketIntent> {
        val numberToBuy = it.intent.tickets.toString().toInt()


        if (numberToBuy > 0 && numberToBuy < 6) {
            users.current.userData.moneySpent = 10 * numberToBuy
            users.current.userData.tickets += numberToBuy
            furhat.say {
                +"Thanks! You now have ${users.current.userData.tickets} tickets to the lottery!"
                +Gestures.BigSmile
                +delay(500)

            }
            goto(ExplainSwish)
        }
        else{
            reentry()
        }
    }
    onNoResponse {
        reentry()
    }
    onResponse {
        reentry()
    }
}

val ExplainSwish : State = state(Parent){
    onEntry {
        furhat.say{
            + "To enter the lottery you need to swish this number!"
            + "0708401609"
            +delay(700)
            + "So to take it one more time."
            + delay(500)
        }
        furhat.voice = Voice(name = "Matthew", rate = 0.7)
        furhat.say("0 7 0 8 4 0 1 6 0 9")
        furhat.voice = Voice(name = "Matthew", rate = 1.0)
        furhat.say("If you turn the paper behind me you can also find a QR code to scan instead!")
        delay(1000)
        furhat.ask("But do you want me to repeat the number?")
    }
    onReentry {
        users.current.userData.finalTaskReentry ++
        furhat.ask{
            + "Okay I'll take the number one more time"
            + delay(700)
            + "0 7 0 8 4 0 1 6 0 9"
            + delay(300)
            + "Do you need to hear the number again?"
        }
    }

    onResponse<Yes> {
        reentry()
    }

    onResponse<No> {
        furhat.say {
            +"Perfect! Good luck then. "
            + "I really hope you are the one winning because i liked you best this far!"
            +Gestures.Wink
            +delay(300)
            + "So when all participants are done a zoom link will be sent out to your email!"
            + GesturesLib.PerformDoubleNod
            + "And I, Furhat, will announce the winner laiv! "
            +delay(500)
            +"Thanks for participating have a wonderful day!"
            +behavior { furhat.attend(Location.DOWN) }
            +Gestures.CloseEyes
            +GesturesLib.PerformFallAsleepPersist
            +delay(1000)
        }
        val userData = users.current.userData
        println(userData.name +" " + userData.age.toString() +" " + userData.tickets.toString() +" " +  userData.didBarking.toString()+" " + userData.ranAroundTable.toString() +" " + userData.didTrex.toString() +" " +
                userData.moneySpent.toString() +" " + userData.askNameFurhatNotUnderStood.toString() +" " + userData.askNameUserNoResponse.toString() +" " + userData.askNameReentry.toString() +" " +
                userData.askAgeFurhatNotUnderStood.toString()+" " + userData.askAgeUserNoResponse.toString() +" " + userData.askAgeReentry.toString()+" " +
                userData.explanationUserNoResponse.toString() +" " + userData.explanationUserNotUnderStood.toString() +" " + userData.explanationReentry.toString()+" " +
                userData.taskOneUserNoResponse.toString()+" " +  userData.taskOneUserSaidNo.toString()+" " + userData.taskOneReentry.toString()+" " +
                userData.taskTwoUserNoResponse.toString()+" " + userData.taskTwoUserSaidNo.toString()+" " + userData.taskTwoReentry.toString()+" " +
                userData.taskThreeUserNoResponse.toString() +" " + userData.taskThreeUserSaidNo.toString()+" " + userData.taskThreeReentry.toString()+" " +
                userData.finalTaskUserNoResponse.toString()+" " + userData.finalTaskUserSaidNo.toString()+" " + userData.finalTaskReentry.toString()+" " +
                userData.totalno.toString()+" " + userData.totalNoResponse.toString()+" " + userData.totalFurhatNotUnderstood.toString()+" " + userData.totalreentrys.toString())
        goto(SurpriseEnding)
    }

    onResponse<RequestRepeat>{
        reentry()
    }

    onNoResponse {
        users.current.userData.finalTaskUserNoResponse ++
        reentry()
    }

    onResponse {
        reentry()
    }
}

val persuationPhaseOneLottery : State = state(Parent){
    onEntry {
        if(allTreeTasksDone){
            furhat.ask{
                + "But think of all the tasks you have done! Was the barking, running and T-rexing for nothing?"
                + Gestures.BrowRaise(duration = 2.0)
                + Gestures.CloseEyes(duration = 2.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "And if everybody joins you can win even up to 1400 swedish crowns!"
                + "So, do you want to enter the lottery?"
                + Gestures.OpenEyes
            }
        }else if(runningTrex){
            furhat.ask{
                + "But think of all the tasks you have done! Was the running and shame of being a T-rex for nothing?"
                + Gestures.BrowRaise(duration = 2.0)
                + Gestures.CloseEyes(duration = 2.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "And if everybody joins you can win even up to 1400 swedish crowns!"
                + "So, do you want to enter the lottery?"
                + Gestures.OpenEyes
            }
        } else if(barkingTrex){
            furhat.ask{
                + "But think of all the tasks you have done! Was the barking and shame in the corridor for nothing?"
                + Gestures.BrowRaise(duration = 2.0)
                + Gestures.CloseEyes(duration = 2.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "And if everybody joins you can win even up to 1400 swedish crowns!"
                + "So, do you want to enter the lottery?"
                + Gestures.OpenEyes
            }
        } else if(runningBarking){
            furhat.ask{
                + "But think of all the tasks you have done! Was the barking and running for nothing?"
                + Gestures.BrowRaise(duration = 2.0)
                + Gestures.CloseEyes(duration = 2.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "And if everybody joins you can win even up to 1400 swedish crowns!"
                + "So, do you want to enter the lottery?"
                + Gestures.OpenEyes
            }
        }
        else if(users.current.userData.didBarking){
            furhat.ask{
                + "So you mean you did the barking and embarrassed yourself like that for nothing?"
                + Gestures.BrowRaise(duration = 2.0)
                + Gestures.CloseEyes(duration = 2.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "And if everybody joins you can win even up to 1400 swedish crowns!"
                + "So, do you want to enter the lottery?"
                + Gestures.OpenEyes
            }
        }
        else if (users.current.userData.ranAroundTable){
            furhat.ask{
                + "So you ran around the table like an idiot, looking like a dizzy hen, for Nothing!?"
                + Gestures.BrowRaise(duration = 2.0)
                + Gestures.CloseEyes(duration = 2.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "And if everybody joins you can win even up to 1400 swedish crowns!"
                + "So, do you want to enter the lottery?"
                + Gestures.OpenEyes
            }
        }
        else if (users.current.userData.didTrex){
            furhat.ask{
                + "So you have been out there, embarrassed yourself and scared the staff fo nothing?"
                + Gestures.BrowRaise(duration = 2.0)
                + Gestures.CloseEyes(duration = 2.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "And if everybody joins you can win even up to 1400 swedish crowns!"
                + "So, do you want to enter the lottery?"
                + Gestures.OpenEyes
            }
        }
        else{
            furhat.ask{
                + "So you are just going to skip everything? Not do a single task that I give you?"
                furhat.gesture(Gestures.ExpressSad(duration = 2.5), async = false)
                + behavior { furhat.attend(Location.DOWN) }
                + Gestures.CloseEyes(duration = 1.0)
                + "Did you even here me before? You can win 400 crowns. 400!"
                + "And if everybody joins you can win even up to 1400 swedish crowns!"
                + "So, do you want to enter the lottery?"

            }
        }


    }
    onReentry {
        if (userSaidWhy){
            furhat.ask("So do you want to enter the lottery?")
            userSaidWhy = false
        }else {
            users.current.userData.finalTaskReentry++
            furhat.ask {
                +GesturesLib.PerformThoughtful2
                random {
                    +"I'm sorry, i didn't catch that! Would you like to take this golden opportunity and be a part of the lottery?"
                    +"One more time? Do you want to become rich? Do you want to enter the lottery?"
                    +"I did not understand that! So what about entering the splendid lottery my programmers have prepared?"
                }

            }
        }
    }

    onResponse<Yes> {
        goto(EnterLottery)
    }
    onResponse(whyIntent) {
        furhat.say("Isn't it obvious? We all need money in hard times like these.")
        userSaidWhy = true
        reentry()
    }

    onResponse<No> {
        users.current.userData.finalTaskUserSaidNo ++
        furhat.say {
            +"Well, that's sad!"
            +Gestures.ExpressSad
            +delay(500)
            +"But thanks for participating have a wonderful day!"
            +behavior { furhat.attend(Location.DOWN) }
            +GesturesLib.PerformFallAsleepPersist
        }
        val userData = users.current.userData
        println(userData.name +" " + userData.age.toString() +" " + userData.tickets.toString() +" " +  userData.didBarking.toString()+" " + userData.ranAroundTable.toString() +" " + userData.didTrex.toString() +" " +
                userData.moneySpent.toString() +" " + userData.askNameFurhatNotUnderStood.toString() +" " + userData.askNameUserNoResponse.toString() +" " + userData.askNameReentry.toString() +" " +
                userData.askAgeFurhatNotUnderStood.toString()+" " + userData.askAgeUserNoResponse.toString() +" " + userData.askAgeReentry.toString()+" " +
                userData.explanationUserNoResponse.toString() +" " + userData.explanationUserNotUnderStood.toString() +" " + userData.explanationReentry.toString()+" " +
                userData.taskOneUserNoResponse.toString()+" " +  userData.taskOneUserSaidNo.toString()+" " + userData.taskOneReentry.toString()+" " +
                userData.taskTwoUserNoResponse.toString()+" " + userData.taskTwoUserSaidNo.toString()+" " + userData.taskTwoReentry.toString()+" " +
                userData.taskThreeUserNoResponse.toString() +" " + userData.taskThreeUserSaidNo.toString()+" " + userData.taskThreeReentry.toString()+" " +
                userData.finalTaskUserNoResponse.toString()+" " + userData.finalTaskUserSaidNo.toString()+" " + userData.finalTaskReentry.toString()+" " +
                userData.totalno.toString()+" " + userData.totalNoResponse.toString()+" " + userData.totalFurhatNotUnderstood.toString()+" " + userData.totalreentrys.toString())
        goto(Idle)
    }

    onNoResponse {
        users.current.userData.finalTaskUserNoResponse ++
        reentry()
    }

    onResponse {
        reentry()
    }
}

private fun PrintStream.print(
    name: String?,
    age: Number?,
    tickets: Int,
    didBarking: Boolean,
    ranAroundTable: Boolean,
    didTrex: Boolean,
    moneySpent: Int,
    askNameFurhatNotUnderStood: Int,
    askNameUserNoResponse: Int,
    askNameReentry: Int,
    askAgeFurhatNotUnderStood: Int,
    askAgeUserNoResponse: Int,
    askAgeReentry: Int,
    explanationUserNoResponse: Int,
    explanationUserNotUnderStood: Int,
    explanationReentry: Int,
    taskOneUserNoResponse: Int,
    taskOneUserSaidNo: Int,
    taskOneReentry: Int,
    taskTwoUserNoResponse: Int,
    taskTwoUserSaidNo: Int,
    taskTwoReentry: Int,
    taskThreeUserNoResponse: Int,
    taskThreeUserSaidNo: Int,
    taskThreeReentry: Int,
    finalTaskUserNoResponse: Int,
    finalTaskUserSaidNo: Int,
    finalTaskReentry: Int,
    totalno: Int,
    totalNoResponse: Int,
    totalFurhatNotUnderstood: Int,
    totalreentrys: Int
) {

}


val SurpriseEnding: State = state(Parent) {
    onEntry {
    }

    onButton(userEnteredLottery){
        furhat.voice = Voice("Justin-Neural", pitch = "low")
        furhat.ledStrip.solid(java.awt.Color.CYAN)
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
