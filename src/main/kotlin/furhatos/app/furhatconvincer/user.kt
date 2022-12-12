package furhatos.app.furhatconvincer

import furhatos.records.User
import furhatos.nlu.common.Number

class UserData(
    var name: String? = null,
    var age: Number? = null,
    var tickets: Int = 0,
    var didBarking: Boolean = false,
    var ranAroundTable: Boolean = false,
    var didTrex: Boolean = false
)

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())
