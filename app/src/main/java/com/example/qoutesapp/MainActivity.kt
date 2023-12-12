package com.example.qoutesapp

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.View.OnClickListener
import android.widget.RelativeLayout
import android.widget.TextView
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import com.example.qoutesapp.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity(), OnClickListener {

    private lateinit var mainViewModel: MainViewModel

    private lateinit var mainActivityBinding: ActivityMainBinding
    private val quoteText: TextView
        get() = mainActivityBinding.quote

    private val quoteAuthor: TextView
        get() = mainActivityBinding.quoteAuthor

    private val nextBtn: RelativeLayout
        get() = mainActivityBinding.nextBtn

    private val previousBtn: RelativeLayout
        get() = mainActivityBinding.previousBtn

    private val shareBtn: FloatingActionButton
        get() = mainActivityBinding.shareBtn

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mainActivityBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(mainActivityBinding.root)
        init()
    }

    private fun init() {
        mainViewModel =
            ViewModelProvider(this, MainViewModelFactory(application))[MainViewModel::class.java]
        setQuote(mainViewModel.getQuote())
        nextBtn.setOnClickListener(this)
        previousBtn.setOnClickListener(this)
        shareBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        when (v?.id) {
            R.id.nextBtn -> setQuote(mainViewModel.nextQuote())
            R.id.previousBtn -> setQuote(mainViewModel.previousQuote())
            R.id.shareBtn -> {
                val intent = Intent(Intent.ACTION_SEND)
                intent.type = "text/plain"
                intent.putExtra(Intent.EXTRA_TEXT, mainViewModel.getQuote().text)
                startActivity(intent)
            }
        }
    }

    private fun setQuote(quote: Quote) {
        quoteText.text = quote.text
        quoteAuthor.text = quote.author
    }
}