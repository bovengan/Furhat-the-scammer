package furhatos.app.furhatconvincer.flow.parents

import furhatos.app.furhatconvincer.flow.main.Idle
import furhatos.flow.kotlin.*
import furhatos.gestures.Gestures
import furhatos.skills.emotions.UserGestures
val encourageUser = Button("Give encouragement")

val Parent: State = state {

//    onUserLeave(instant = true) {
//        when {
//            users.count == 0 -> goto(Idle)
//            it == users.current -> furhat.attend(users.other)
//        }
//    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }

    onUserGesture(instant = true, gesture = UserGestures.Smile) {
        furhat.gesture(Gestures.BigSmile(duration = 1.5))
    }
}

val Parent2: State = state {

    onUserEnter(instant = true) {
        furhat.glance(it)

        furhat.attend(it)
    }

    onUserGesture(instant = true, gesture = UserGestures.Smile) {
        furhat.gesture(Gestures.BigSmile(duration = 1.5))
    }

    onButton(encourageUser){
        furhat.say{
            random {
                + "Come on!"
                + "You're doing great!"
                + "Looks awesome!"
                + "You are the best!"
                + "I love this!"
                + "You are sooo good!"
                + "Go, go, go!"
            }
        }
    }
}
