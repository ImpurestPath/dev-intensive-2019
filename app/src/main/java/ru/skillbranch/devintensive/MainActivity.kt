package ru.skillbranch.devintensive

import android.graphics.Color
import android.graphics.PorterDuff
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.KeyEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import ru.skillbranch.devintensive.models.Bender
import android.view.inputmethod.EditorInfo
import android.widget.Toast
import ru.skillbranch.devintensive.extensions.hideKeyboard


class MainActivity : AppCompatActivity(), View.OnClickListener, TextView.OnEditorActionListener {


    lateinit var benderImage: ImageView
    lateinit var textTxt: TextView
    lateinit var messageEt: EditText
    lateinit var sendBtn: ImageView
    lateinit var benderObj: Bender


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //benderImage = findViewById(R.id.iv_bender)
        benderImage = iv_bender
        textTxt = tv_text
        messageEt = et_message
        sendBtn = iv_send

        val status = savedInstanceState?.getString("STATUS")
        val question = savedInstanceState?.getString("QUESTION")
        val message = savedInstanceState?.getString("MESSAGE")
        benderObj = Bender(Bender.Status.valueOf(status ?: "NORMAL"),
                Bender.Question.valueOf(question ?: "NAME"))
        val (r, g, b) = benderObj.status.color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        sendBtn.setOnClickListener(this)
        textTxt.text = benderObj.askQuestion()
        messageEt.setText(message)
    }
    fun checkAnswer(){
        val (phrase, color) = benderObj.listenAnswer(messageEt.text.toString().toLowerCase())
        messageEt.setText("")
        val (r, g, b) = color
        benderImage.setColorFilter(Color.rgb(r, g, b), PorterDuff.Mode.MULTIPLY)
        textTxt.text = phrase
    }
    override fun onClick(p0: View?) {
        if (p0?.id == R.id.iv_send) {
            checkAnswer()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        outState.putString("STATUS", benderObj.status.name)
        outState.putString("QUESTION", benderObj.question.name)
        outState.putString("MESSAGE", messageEt.text.toString())
    }
    override fun onEditorAction(v: TextView, actionId: Int, event: KeyEvent): Boolean {
        return if (actionId == EditorInfo.IME_ACTION_DONE) {
            checkAnswer()
            hideKeyboard()
            true
        } else false
    }
}
