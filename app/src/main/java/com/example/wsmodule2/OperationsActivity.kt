package com.example.wsmodule2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.LinearLayout
import com.example.wsmodule2.CustomViews.OperationView
import kotlinx.android.synthetic.main.activity_operations.*
import java.util.*


data class CardOperation(var type: String, var date: Date, var money: Double)
class OperationsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_operations)

        for(i in 0..7){
           operation_container.addView(OperationView(this, CardOperation("Перевод на карту", Calendar.getInstance().time, 200.99)))
        }
    }
}
