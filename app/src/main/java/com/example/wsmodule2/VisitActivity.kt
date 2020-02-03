package com.example.wsmodule2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import android.widget.RelativeLayout
import com.example.wsmodule2.CustomViews.VisitView
import java.util.*

data class Visit(var date: Date)
class VisitActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_visit)

        var container: LinearLayout = findViewById(R.id.visit_container)

        for(i in 0..7){
            var visitPanel: RelativeLayout = VisitView(this, Visit(Calendar.getInstance().time))
            visitPanel.setBackgroundResource(R.drawable.profilebuton_selector)
            container.addView(visitPanel)
        }

    }
}
