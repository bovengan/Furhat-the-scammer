package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.Parent
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.Yes

val TaskThree : State = state(Parent) {
    onEntry {
        furhat.say("Okay, great job, now you have 3 lottery tickets thanks to the previous completed tasks.")
        furhat.say("For the final task you will have the chance to win 400 swedish crowns, plus the amount other test users put in. ")
        furhat.say("To be a part of the lottery you have to pay for at least one lottery ticket that costs 10 swedish crowns")
        furhat.say("If you pay for one ticket and since you already have 3 tickets you will 4 tickets")
        furhat.say("You can buy at most 10 tickets")
        furhat.ask("Do you want to enter the lottery?")
    }

    onReentry {
        furhat.ask("Did you make it?")
    }

    onResponse<Yes>{
        furhat.say("Great, how many tickets do you want to purchase?")
    }

}
