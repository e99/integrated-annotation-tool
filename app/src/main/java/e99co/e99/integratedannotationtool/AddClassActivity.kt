package e99co.e99.integratedannotationtool

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText


class AddClassActivity : AppCompatActivity() {
    private lateinit var addLabelName: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_class_layout)

        findViewById<Button>(R.id.add_class_button).setOnClickListener {
            addLabel()
        }
        addLabelName = findViewById(R.id.add_class_name)
    }

    /* The onClick action for the done button. Closes the activity and returns the new flower name
    and description as part of the intent. If the name or description are missing, the result is set
    to cancelled. */

    private fun addLabel() {
        val resultIntent = Intent()

        if (addLabelName.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addLabelName.text.toString()
            resultIntent.putExtra("label_text", name)
            setResult(Activity.RESULT_OK, resultIntent)
        }
        finish()
    }
}