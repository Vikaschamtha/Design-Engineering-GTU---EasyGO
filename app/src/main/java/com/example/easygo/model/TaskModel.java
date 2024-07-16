//package com.example.easygo.model;
//
//public class TaskModel {
//
//    String taskId,taskName,taskStatus,userId;
//
//
//    public TaskModel(){
//
//    }
//
//    public TaskModel(String taskId, String taskName, String taskStatus,String userId) {
//        this.taskId = taskId;
//        this.taskName = taskName;
//        this.taskStatus = taskStatus;
//        this.userId = userId;
//    }
//
//    public String getUserId() {
//        return userId;
//    }
//
//    public void setUserId(String userId) {
//        this.userId = userId;
//    }
//
//    public String getTaskId() {
//        return taskId;
//    }
//
//    public void setTaskId(String taskId) {
//        this.taskId = taskId;
//    }
//
//    public String getTaskName() {
//        return taskName;
//    }
//
//    public void setTaskName(String taskName) {
//        this.taskName = taskName;
//    }
//
//    public String getTaskStatus() {
//        return taskStatus;
//    }
//
//    public void setTaskStatus(String taskStatus) {
//        this.taskStatus = taskStatus;
//    }
//}


package com.example.easygo.model;

public class TaskModel {

    String taskId,taskName,taskStatus,userId,taskDate,taskTime,taskDescription;


    public TaskModel(){

    }

    public TaskModel(String taskId, String taskName, String taskStatus,String userId,String taskDate,String taskTime,String taskDescription) {
        this.taskId = taskId;
        this.taskName = taskName;
        this.taskStatus = taskStatus;
        this.userId = userId;
        this.taskDate = taskDate;
        this.taskTime = taskTime;
        this.taskDescription = taskDescription;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getTaskId() {
        return taskId;
    }

    public void setTaskId(String taskId) {
        this.taskId = taskId;
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTaskDate() {
        return taskDate;
    }

    public void setTaskDate(String taskDate) {
        this.taskDate = taskDate;
    }

    public String getTaskTime() {
        return taskTime;
    }

    public void setTaskTime(String taskTime) {
        this.taskTime = taskTime;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) { this.taskDescription = taskDescription;}
}

