package com.osmosoft.authnumberscreen

import android.os.Bundle
import android.text.Editable
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.textfield.TextInputLayout
import ru.tinkoff.decoro.MaskImpl
import ru.tinkoff.decoro.parser.UnderscoreDigitSlotsParser
import ru.tinkoff.decoro.watchers.MaskFormatWatcher

const val PHONE_MASK = "+7 (___) ___ ____"
const val INPUT_ERROR_MESSAGE = "Кажется, вы забыли ввести пару цифр номера"
const val EMPTY_STRING = ""

class MainActivity : AppCompatActivity() {

    private lateinit var closeImageView: ImageView
    private lateinit var phoneInputLayout: TextInputLayout
    private lateinit var phoneEditText: EditText
    private lateinit var continueButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bindAndInitViews()
        setListeners()

    }

    private fun showError(){
        phoneInputLayout.error = INPUT_ERROR_MESSAGE
    }
    private fun hideError(){
        phoneInputLayout.error = EMPTY_STRING
    }

    private fun bindAndInitViews(){
        closeImageView = findViewById(R.id.activity_main_close_image_view)
        phoneInputLayout = findViewById(R.id.activity_main_phone_text_input_layout)
        phoneEditText = findViewById(R.id.phone_edit_text)
        val slots = UnderscoreDigitSlotsParser().parseSlots(PHONE_MASK)
        val phoneMask = MaskImpl.createTerminated(slots)
        val formatWatcher = MaskFormatWatcher(phoneMask)
        formatWatcher.installOn(phoneEditText)

        continueButton = findViewById(R.id.activity_main_continue_button)
    }

    private fun setListeners(){
        closeImageView.setOnClickListener {
            Toast.makeText(this, "Закрыть", Toast.LENGTH_SHORT).show()
        }

        phoneEditText.addTextChangedListener(object : AbstractTextWatcher() {
            override fun afterTextChanged(s: Editable?) {
                if(s?.toString()?.length == PHONE_MASK.length){
                    continueButton.isEnabled = true
                    hideError()
                }else{
                    continueButton.isEnabled = false
                    showError()
                }
            }
        })

        continueButton.setOnClickListener {
            Toast.makeText(this, "Продолжить", Toast.LENGTH_SHORT).show()
        }
    }
}