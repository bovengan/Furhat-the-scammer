package furhatos.app.furhatconvincer.flow

import furhatos.app.furhatconvincer.flow.main.Idle
import furhatos.flow.kotlin.*

val Parent: State = state {

    onUserLeave(instant = true) {
        when {
            users.count == 0 -> goto(Idle)
            it == users.current -> furhat.attend(users.other)
        }
    }

    onUserEnter(instant = true) {
        furhat.glance(it)
    }
    
    onUserGesture(instant = true, gesture = UserGestures.Smile) {
        furhat.gesture(Gestures.BigSmile)
    }
}
