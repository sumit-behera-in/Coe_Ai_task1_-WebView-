package `in`.apps.coeaibbsr

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.strictmode.WebViewMethodCalledOnWrongThreadViolation
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity
import java.io.FileInputStream

class MainActivity : AppCompatActivity() {
    lateinit var  browser:WebView
    var f_string : ValueCallback<Array<Uri>>? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()


        browser = findViewById(R.id.myWebView)
        val url = "https://www.remove.bg/upload"
        browser.settings.domStorageEnabled = true
        browser.settings.javaScriptEnabled = true
        browser.settings.useWideViewPort = true
        browser.settings.loadWithOverviewMode = true
        browser.settings.allowFileAccess = true
        browser.settings.allowContentAccess=true
        browser.settings.allowFileAccessFromFileURLs = true
        browser.settings.allowUniversalAccessFromFileURLs =  true
        browser.settings.useWideViewPort = true
        browser.settings.allowContentAccess = true
        browser.settings.databaseEnabled = true
        browser.canGoBackOrForward(30)

        browser.settings.userAgentString = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36"
        browser.settings.supportZoom()

        //        load changing urls
        browser.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(
                view: WebView?,
                request: WebResourceRequest?
            ): Boolean {
                val url = request?.url.toString()
                view?.loadUrl(url)
                return true
            }

        }

        browser.loadUrl(url)

        browser.webChromeClient = object : WebChromeClient(){
            override fun onShowFileChooser(
                webView: WebView?,
                filePathCallback: ValueCallback<Array<Uri>>?,
                fileChooserParams: FileChooserParams?
            ): Boolean {
                 super.onShowFileChooser(webView, filePathCallback, fileChooserParams)
                val intent = fileChooserParams?.createIntent()
                f_string = filePathCallback
                if (intent != null) {
                    startActivityForResult(intent,101)
                }
                return true
            }
        }

    }
    override fun onBackPressed() {
        if(browser.canGoBack()){
            browser.goBack()
        }
        else
            super.onBackPressed()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if(requestCode==101){
            f_string?.onReceiveValue(WebChromeClient.FileChooserParams.parseResult(resultCode,data))
            f_string = null
        }
    }
}