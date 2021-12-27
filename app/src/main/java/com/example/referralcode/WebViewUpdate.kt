package com.example.referralcode

import android.annotation.SuppressLint
import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Message
import android.renderscript.Script
import android.webkit.JavascriptInterface
import android.webkit.WebChromeClient
import android.webkit.WebView
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class WebViewUpdate : AppCompatActivity() {

    lateinit var mEditText: EditText
    lateinit var mButtonSend: Button
    lateinit var webViewSample: WebView
    private val mFilePath = "file:///android_asset/Test.html"


    @SuppressLint("JavascriptInterface", "SetJavaScriptEnabled")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_web_view_update)

        mEditText = findViewById(R.id.editInput)
        webViewSample = findViewById(R.id.webViewSample)
        webViewSample.settings.javaScriptEnabled = true
        webViewSample.webChromeClient = WebChromeClient()

        webViewSample.addJavascriptInterface(CaptureClickJavascriptInterface(this,mEditText), "androidButton")

        webViewSample.loadUrl(mFilePath)
        mButtonSend = findViewById(R.id.sendData)
        mButtonSend.setOnClickListener {
            sendDataToWebView()
        }

    }

    private fun sendDataToWebView() {
        webViewSample.evaluateJavascript("javascript: "+ "updateFromNative(\"" + mEditText.text + "\")",null)
    }


    class CaptureClickJavascriptInterface(val context: Context,val editText: EditText) {
        @JavascriptInterface
        fun onCapturedButtonClicked(message: String)
        {
            editText.setText(message)
        }
    }
}