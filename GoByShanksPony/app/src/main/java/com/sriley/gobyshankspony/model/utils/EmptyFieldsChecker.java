package com.sriley.gobyshankspony.model.utils;


import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;

public class EmptyFieldsChecker {

    public static boolean areAllFieldsPopulated(LinearLayout container){
        for (int i=0;i<container.getChildCount();i++){
            if(container.getChildAt(i) instanceof LinearLayout){
             boolean fieldsPopulated= containerChildrenFieldsPopulated(container);
                if(!fieldsPopulated)
                    return false;
            }
        }
        return true;
    }



    private static boolean containerChildrenFieldsPopulated(LinearLayout container){
        for (int i=0;i<container.getChildCount();i++){
            View childView=container.getChildAt(i);
            if(childView instanceof LinearLayout){
               if(!containerChildrenFieldsPopulated((LinearLayout) childView))
                   return false;
            }
            else if(childView instanceof EditText){
                boolean isPopulated=isEditTextPopulated((EditText) childView);
                if(!isPopulated)
                    return false;
            }
        }

        return true;
    }



    private static boolean isEditTextPopulated(EditText editText){
        String text=editText.getText()+"";
        if(text.equals(""))
            return false;
        else
            return true;
    }
}
