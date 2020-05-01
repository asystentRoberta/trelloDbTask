package pl.com.bohdziewicz.trelloDbTask.utils;

public enum MailTypes {
    EMAIL_SCHEDULER("Tasks: Once a day email"),
    EMAIL_NEW_TRELLO_CARD("Tasks: new Trello card"),
    EMAIL_NoOneKnow("Please set subject yur self");
    private final String subjectOfMail;

    MailTypes(String subjectOfMail) {

        this.subjectOfMail = subjectOfMail;
    }

    public String getSubjectOfMail() {

        return subjectOfMail;
    }
}
