package com.sainfe.todowithspot;

public class Item {
    boolean isSelected = false;
    String text;

    public boolean getSelected(){
        return isSelected;
    }

    public void setSelected(boolean selected){
        this.isSelected = selected;
    }

    public Item(String text){
        this.text = text;
    }
}
