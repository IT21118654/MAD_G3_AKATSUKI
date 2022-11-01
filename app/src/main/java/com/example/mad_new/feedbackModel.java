package com.example.mad_new;

public class feedbackModel {
    String id;
    String name;
    String feedback;

    public feedbackModel(){
    }

    public feedbackModel( String feedback,String name,String id) {
        this.name = name;
        this.feedback = feedback;
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }


}
