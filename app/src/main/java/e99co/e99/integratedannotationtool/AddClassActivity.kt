package e99co.e99.integratedannotationtool

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.Button
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputEditText
import e99co.e99.integratedannotationtool.MainActivity.Companion.TagList
import java.util.ArrayList


class AddClassActivity : AppCompatActivity() {
    private lateinit var addLabelName: TextInputEditText

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_class_layout)

        findViewById<Button>(R.id.add_class_button).setOnClickListener {
            addLabel()
        }
        val labelListView=findViewById<ListView>(R.id.tag_list)
        addLabelName = findViewById(R.id.add_class_name)
        val taglistAdapter=labelListAdapter(this, TagList)

        labelListView.adapter=taglistAdapter
        labelListView.setOnItemClickListener { parent, view, position, id ->

            val resultIntent = Intent()
            
            val name = TagList[position]
            resultIntent.putExtra("label_text", name)
            setResult(Activity.RESULT_OK, resultIntent)

            finish()
        }
    }

    private fun addLabel() {
        val resultIntent = Intent()

        if (addLabelName.text.isNullOrEmpty()) {
            setResult(Activity.RESULT_CANCELED, resultIntent)
        } else {
            val name = addLabelName.text.toString()
            resultIntent.putExtra("label_text", name)
            setResult(Activity.RESULT_OK, resultIntent)
            TagList.add(name)
        }
        finish()
    }
}

class labelListAdapter (val context: Context, private val labelListAdapter: ArrayList<String>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        val view: View = LayoutInflater.from(context).inflate(R.layout.tag_item, null)

        val tagNameText = view.findViewById<TextView>(R.id.tag_name)
        val name= labelListAdapter[position]
        tagNameText.text=name
        return view

    }

    override fun getItem(position: Int): Any {
        return labelListAdapter[position]
    }

    override fun getItemId(position: Int): Long {
        return 0
    }

    override fun getCount(): Int {
        return labelListAdapter.size
    }
}