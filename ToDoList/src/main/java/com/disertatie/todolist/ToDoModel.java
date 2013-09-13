package com.disertatie.todolist;

import java.util.Date;

/**
 * Created by Dan Petran on 03.09.2013.
 */
public class ToDoModel
{
    private int id;
    private String toDoName;
    private String toDoDescription;
    private String toDoBy;
    private Boolean taskNotified = false;

    public ToDoModel()
    {

    }

    public ToDoModel(int _id, String _toDoName, String _toDoDescription, String _toDoBy)
    {
        id = _id;
        toDoName = _toDoName;
        toDoDescription = _toDoDescription;
        toDoBy = _toDoBy;
    }

    //Getter For id
    public int GetId()
    {
        return id;
    }

    //Getter for ToDoName
    public String GetToDoName()
    {
        return toDoName;
    }

    //Getter and Setter for ToDoDescription
    public String GetToDoDescription()
    {
        return toDoDescription;
    }

    //Getter and Setter for toDoBy
    public String GetToDoBy()
    {
        return toDoBy;
    }

    public void SetTaskNotified()
    {
        taskNotified = true;
    }

    public Boolean GetTaskNotified()
    {
        return taskNotified;
    }
}
