package com.bp;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Ich on 18.07.2016.
 */
public class LectionObject {
    String lectionName; //name~
    int processingStatus; //solved~
    String type;//Lesson-type
    int delaytime; //how much time has to pass if the lection is blocked until it is unlocked again
    long nextfreetime; //the lection is blocked to this point in time
    int reward; //the amount of acorn gained by solving this lection


    String content; //the slides stored as one large String
    //Hashmap to store the split up version of the lectionDescription-String
    public HashMap<String,String> lectionInfoHashMap =new HashMap<>();

    //Hashmap that stores every slide already generated
    public HashMap<String,Slide> slideHashMap =new HashMap<>();
    //BackStack for tracking which slide the back button should access
    public ArrayList<String> slideBackStack=new ArrayList<>();

    //Score for lections with QuizSlides
    public int score=0;

    /**
     * @author Tim
     */
    public LectionObject(String name,String content,String type,int delay, int freetime, int status, int acorn) {
        //filling the lectionInfoHashMap by spliting the lectionDescriptionString
        Log.d("content",content);
        String[] s = content.replace("[", "").split("]");
        for (int i = 0; i < s.length && !s[i].isEmpty(); i++) {
            String key = s[i].substring(0, s[i].indexOf("~"));
            String value = s[i].substring(s[i].indexOf("~") + 1, s[i].length());
            lectionInfoHashMap.put(key, value);
        }

        //generating the first Slide
        String slideDescription=lectionInfoHashMap.get("0");

        Slide firstSlide=Slide.createSlide(slideDescription);
        Bundle slideInfos=new Bundle();
        slideInfos.putString("parameters",slideDescription);
        firstSlide.setArguments(slideInfos);
        slideHashMap.put("0",firstSlide);

        lectionName= name;
        processingStatus = status;
        this.type=type;
        delaytime=delay;    //how much time has to pass if the lection is blocked until it is unlocked again
        nextfreetime=freetime; //the lection is blocked to this point in time
        reward=acorn;   //the amount of acorn gained by solving this lection
        this.content=content;   //the amount of acorn gained by solving this lection

    }

    /**
     *@author Tim
     */
    public String getLectionName(){
        return lectionName;
    }

    /**
     *@author Tim
     */
    public int getProcessingStatus(){
        return processingStatus;
    }




    /**
     * @author Tim
     * @return the slide to return to or "noBack" if the backstack is empty
     */
    public String getBackSlide(){

        if(slideBackStack.isEmpty()){
            return "noBack";
        }else{
            return slideBackStack.get(slideBackStack.size()-1);
        }


    }
    public String getType() {
        return type;
    }
    public int getDelaytime() {
        return delaytime;
    }
    public long getNextfreetime() {
        return nextfreetime;
    }
    public int getReward() {
        return reward;
    }
    public String getContent() {
        return content;
    }
    public void setProcessingStatus(int status){
        processingStatus=status;
    }

}
