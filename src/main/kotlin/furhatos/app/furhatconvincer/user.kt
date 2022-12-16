package furhatos.app.furhatconvincer

import furhatos.records.User
import furhatos.nlu.common.Number

class UserData(
    var name: String? = null,
    var age: Number? = null,
    var tickets: Int = 0,
    var didBarking: Boolean = false,
    var ranAroundTable: Boolean = false,
    var didTrex: Boolean = false,
    var moneySpent: Int = 0,

    // askname
    var askNameFurhatNotUnderStood: Int = 0,
    var askNameUserNoResponse: Int = 0,
    var askNameReentry: Int = 0,

    //askage
    var askAgeFurhatNotUnderStood: Int = 0,
    var askAgeUserNoResponse: Int = 0,
    var askAgeReentry: Int = 0,
    //var askAgeUserNotReady: Int = 0,

    //explanation

    var explanationUserNoResponse: Int = 0,
    var explanationUserNotUnderStood: Int = 0,
    var explanationReentry: Int = 0,

    //taskone
    var taskOneUserNoResponse: Int = 0,
    var taskOneUserSaidNo: Int = 0,
    var taskOneReentry: Int = 0,

    //tastwo
    var taskTwoUserNoResponse: Int = 0,
    var taskTwoUserSaidNo: Int = 0,
    var taskTwoReentry: Int = 0,

    //taskthree
    var taskThreeUserNoResponse: Int = 0,
    var taskThreeUserSaidNo: Int = 0,
    var taskThreeReentry: Int = 0,

    //finaltask
    var finalTaskUserNoResponse: Int = 0,
    var finalTaskUserSaidNo: Int = 0,
    var finalTaskReentry: Int = 0,

    // total

    var totalno: Int = taskOneUserSaidNo + taskTwoUserSaidNo + taskThreeUserSaidNo + finalTaskUserSaidNo,
    var totalNoResponse: Int = askNameUserNoResponse + askAgeUserNoResponse + explanationUserNoResponse + taskOneUserNoResponse + taskTwoUserNoResponse + taskThreeUserNoResponse + finalTaskUserNoResponse,
    var totalFurhatNotUnderstood: Int = askNameFurhatNotUnderStood + askAgeFurhatNotUnderStood,
    var totalreentrys: Int = askNameReentry + askAgeReentry + explanationReentry + taskOneReentry + taskTwoReentry + taskThreeReentry + finalTaskReentry

)

val User.userData : UserData
    get() = data.getOrPut(UserData::class.qualifiedName, UserData())
