package com.example.wsmodule2.Utilities;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

public class GrandToster {
    public static void MakeToast(String s, Context context){
        Toast toast = Toast.makeText(context,
                s,
                Toast.LENGTH_SHORT);
        toast.setGravity(Gravity.CENTER,0, 100);
        toast.show();
    }
}
