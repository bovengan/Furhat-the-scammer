package furhatos.app.furhatconvincer.flow.main

import furhatos.app.furhatconvincer.flow.Parent
import furhatos.app.furhatconvincer.nlu.TellAge
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.nlu.common.No
import furhatos.nlu.common.TellName
import furhatos.nlu.common.Yes

val Interview : State = state(Parent) {
    onEntry {
        furhat.ask("I am Furhat, what is your name?")
    }

    onResponse<TellName> {
        furhat.say("Nice to meet you " + it.intent.name + ", you are a very handsome human")
        users.current.userData.name = it.intent.name.toString()
        if (users.current.userData.name != "") {
            goto(AskAge)
        }
    }
}

val AskAge : State = state {
    onEntry {
        furhat.ask("How old are you " + users.current.userData.name + "?")
    }

    onReentry {
        furhat.ask("Dude, how old are you " + users.current.userData.name + "?")
    }

    onResponse<TellAge> {
        users.current.userData.age = it.intent.age
        furhat.ask("Cool, so " + users.current.userData.name + " age " + users.current.userData.age + " are you ready to hear about what we are going to do today?")
    }

    onResponse<Yes> {
        goto(Explanation)
    }
}

val Explanation : State = state {
    onEntry {
        furhat.ask("Okay so what is going to happen now is that I am going to give you 3 tasks I want you to perform. You do not have to do them, but if you do, you will get points that will increase your odds of winning a gift from my makers. I will be the judge, with a bit of assistance from my makers, whether you complete the tasks or not. Do you understand?")
    }

    onResponse<Yes> {
        furhat.say("Okay great, here is the first task")
        goto(TaskOne)
    }

}
