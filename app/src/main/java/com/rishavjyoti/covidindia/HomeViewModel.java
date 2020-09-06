package com.example.CovidIndia;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class HomeViewModel extends ViewModel {
    // TODO: Implement the ViewModel
    private MutableLiveData<String> loc = new MutableLiveData<>("India");
    private MutableLiveData<Integer> index = new MutableLiveData<>();
    private MutableLiveData<Integer> i = new MutableLiveData<>(0);
    public void setloc(String string){
        loc.setValue(string);
    }
    public LiveData<String> getloc(){
        return loc;
    }
    public void setIndex(Integer integer) {index.setValue(integer);}
    public LiveData<Integer> getIndex(){
        return index;
    }
    public void setI(Integer integer) {i.setValue(integer);}
    public LiveData<Integer> getI(){
        return i;
    }
}
