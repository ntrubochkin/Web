package com.weblaba.mt.stuff;

public class WelcomeMessage {
    public enum MessageToUser {
        REGISTRATION_SUCCESSFUL("Registration successful."),
        BAD_PASSWORD_LENGTH("Too short password."),
        NICKNAME_EXISTS("An account with the same name already exists."),
        INCORRECT_DATA("Incorrect login or password"),
        EMAIL_EXISTS("An account already exists for this email address."),
        WRONG_EMAIL("Wrong email Address."),
        DEFAULT("Bruh."),
        IDK("Something went wrong. Try again later.");

        public String msg;

        MessageToUser(String msg) {
            this.msg = msg;
        }

        public String getMessage() {
            return msg;
        }
    }

    private static WelcomeMessage instance;

    private String message;
    private String page;

    private WelcomeMessage() { }

    public static WelcomeMessage getInstance() {
        if(instance == null) {
            instance = new WelcomeMessage();
        }
        return instance;
    }

    public String getMessage() {
        return message;
    }

    public String getPage() {
        return page;
    }

    public void setMessage(MessageToUser msg, String page) {
        message = msg.getMessage();
        this.page = page;
    }
}