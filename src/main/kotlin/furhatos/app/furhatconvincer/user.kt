package furhatos.app.furhatconvincer

import furhatos.nlu.common.Number
import furhatos.records.User

class UserData(
    var name: String? = null,
    var age: Number? = null,
    var tickets: Int = 0
)

val User.userData: UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())