package furhatos.app.furhatconvincer

import furhatos.records.User
import furhatos.nlu.common.Number

class UserData(
    var name: String? = null,
    var age: Number? = null
)

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())