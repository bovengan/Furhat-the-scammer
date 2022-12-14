package furhatos.app.furhatconvincer.flow.main.interview

import furhatos.app.furhatconvincer.flow.parents.Parent
import furhatos.app.furhatconvincer.flow.main.TaskOne
import furhatos.app.furhatconvincer.nlu.RepeatInstructions
import furhatos.app.furhatconvincer.userData
import furhatos.flow.kotlin.*
import furhatos.flow.kotlin.furhat.characters.Characters
import furhatos.flow.kotlin.voice.PollyVoice
import furhatos.flow.kotlin.voice.Voice
import furhatos.nlu.common.No
import furhatos.nlu.common.RequestRepeat
import furhatos.nlu.common.Yes
import furhatos.util.Gender
import furhatos.util.Language

val ExplainTest: State = state(Parent) {
    var answerNo = false
    val sentence = "Okay so what is going to happen now is that I am going to give you 3 tasks I want you to " +
            "perform. You do not have to do them, but if you do, you will get points that will increase your odds " +
            "of winning a gift from my makers. I will be the judge, with a bit of assistance from my makers, " +
            "if you complete the tasks or not."

    onEntry {
        furhat.say(sentence)
        delay(1000)
        furhat.ask("Do you understand?")
    }

    onReentry {
        users.current.userData.explanationReentry ++
        if (answerNo) {
            furhat.ask("Come again, do you want me to repeat it shorter, slower or just repeat it? Or maybe in japanese?")
        } else {
            furhat.say("I'm sorry, I did not understand. I will repeat the instructions.")
            furhat.say(sentence)
            delay(1000)
            furhat.ask("Do you understand?")
        }
    }

    onResponse<Yes> {
        furhat.say("Okay great, here is the first task")
        goto(TaskOne)
    }
    onResponse<No> {
        users.current.userData.explanationUserNotUnderStood ++
        answerNo = true
        furhat.ask("Hmm okay, that might have went a bit fast, do you want me to repeat it shorter, slower or just repeat it? Or I could even try Japanese!")
    }

    onResponse<RequestRepeat> {
        users.current.userData.explanationUserNotUnderStood ++
        furhat.say(sentence)
        furhat.ask("Can we continue?")
    }

    onResponse<RepeatInstructions> {
        if (it.intent.answer.toString() == "shorter") {
            furhat.say("You will get three tasks to complete, for each completed task you get better odds at winning the gift. Here is the first task.")
            goto(TaskOne)
        } else if (it.intent.answer.toString() == "slower") {
            furhat.voice = Voice(name = "Matthew", rate = 0.7)
            furhat.say(sentence)
            furhat.voice = Voice(name = "Matthew", rate = 1.0)
            furhat.ask("Do you understand?")
        } else if (it.intent.answer.toString() == "repeat") {
            furhat.ask(sentence)
        } else if (it.intent.answer.toString() == "Japanese") {
            furhat.voice = Voice(gender = Gender.FEMALE, language = Language.JAPANESE, pitch = "high", rate = 1.5)
            furhat.setCharacter(Characters.Anime_Legacy.AnimePink)
            delay(1000)
            furhat.say(sentence)
            furhat.voice = PollyVoice.Matthew()
            delay(1000)
            furhat.setCharacter(Characters.Adult.Alex)
            delay(500)
            furhat.say("Just kidding. That would have been cool though")
            furhat.ask("But would you like to continue?")
        } else {
            reentry()
        }
    }

    onNoResponse {
        users.current.userData.explanationUserNoResponse ++
        reentry()
    }

    onResponse {
        reentry()
    }
}
