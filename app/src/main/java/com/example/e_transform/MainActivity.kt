package com.example.e_transform

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.widget.*
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var edtAmount: EditText
    private lateinit var spinnerFrom: Spinner
    private lateinit var spinnerTo: Spinner
    private lateinit var tvResult: TextView
    private lateinit var tvConvertedValue: TextView

    private val exchangeRates = mapOf(
        "USD" to 1.0,
        "VND" to 25575.0,
        "JPY" to 150.0,
        "CNY" to 7.0,
        "EUR" to 0.92
    )


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        edtAmount = findViewById(R.id.edtAmount)
        spinnerFrom = findViewById(R.id.spinnerFrom)
        spinnerTo = findViewById(R.id.spinnerTo)
        tvResult = findViewById(R.id.tvResult)
        tvConvertedValue = findViewById(R.id.tvConvertedValue)

        val currencies = exchangeRates.keys.toList()
        val adapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, currencies)
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        spinnerFrom.adapter = adapter
        spinnerTo.adapter = adapter

        spinnerFrom.setSelection(0)
        spinnerTo.setSelection(1)

        val listener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>?, view: android.view.View?, position: Int, id: Long) {
                convertCurrency()
            }

            override fun onNothingSelected(parent: AdapterView<*>?) {}
        }

        spinnerFrom.onItemSelectedListener = listener
        spinnerTo.onItemSelectedListener = listener

        edtAmount.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(s: Editable?) {
                convertCurrency()
            }

            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {}
        })
        convertCurrency()
    }

    private fun convertCurrency() {
        val amountText = edtAmount.text.toString()
        val amount = amountText.toDoubleOrNull() ?: 0.0

        val fromCurrency = spinnerFrom.selectedItem.toString()
        val toCurrency = spinnerTo.selectedItem.toString()

        val fromRate = exchangeRates[fromCurrency] ?: 1.0
        val toRate = exchangeRates[toCurrency] ?: 1.0
        val conversionRate = toRate / fromRate

        val result = (amount / fromRate) * toRate
        tvResult.text = "Kết quả: %.2f $toCurrency".format(result)
        tvConvertedValue.text = "1 $fromCurrency = %.2f $toCurrency".format(conversionRate)
    }

}
