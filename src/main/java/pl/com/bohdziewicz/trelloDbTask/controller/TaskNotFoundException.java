package pl.com.bohdziewicz.trelloDbTask.controller;

class TaskNotFoundException extends Exception {

    TaskNotFoundException(String message) {

        super(message);
    }
}
