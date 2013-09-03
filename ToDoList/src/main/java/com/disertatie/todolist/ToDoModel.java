package com.disertatie.todolist;

import java.util.Date;

/**
 * Created by Bogdan on 03.09.2013.
 */
public class ToDoModel
{
    private int id;
    private String toDoName;
    private String toDoDescription;
    private Date toDoBy;

    public ToDoModel(int _id, String _toDoName, String _toDoDescription, Date _toDoBy)
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
    public Date GetToDoBy()
    {
        return toDoBy;
    }
}
