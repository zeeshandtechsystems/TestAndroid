package com.example.sampleprojectsetup.utilities.enums
/**
 * Develop By Messagemuse
 */
enum class ContactRequestType(val value : Int){
    ADD(1),UPDATE(2),DELETE(3)
}


enum class NotificationType(val value : String){
    SENTYOUCARD("SentYouCard"),
    CARDSENT("CardSent"),
    PACKAGESUBSCRIBED("PackageSubscribed"),
    SENTYOUREMINDER("SentYouReminder"),
    CALENDEREVENTREMINDER("CalenderEventReminder"),
    SCHEDULECARD("ScheduledWillBeSent"),
    SCHEDULECARDSENT("ScheduledCardSent")
}


