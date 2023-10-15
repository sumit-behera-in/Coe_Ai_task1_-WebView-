package `in`.apps.coeaibbsr

import android.net.Uri
import android.os.Bundle
import android.webkit.ValueCallback
import android.webkit.WebChromeClient
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    lateinit var  browser:WebView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        supportActionBar?.hide()

        browser = findViewById(R.id.myWebView)
        val url = "https://apps.coeaibbsr.in/"
        browser.settings.domStorageEnabled = true
        browser.settings.javaScriptEnabled = true
        browser.settings.useWideViewPort = true
        browser.settings.loadWithOverviewMode = true
        browser.settings.allowFileAccess = true
        browser.settings.allowContentAccess=true
        browser.canGoBackOrForward(30)

        browser.settings.userAgentString = "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/100.0.4896.127 Safari/537.36"
        browser.settings.supportZoom()
        browser.loadUrl(url)

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

        browser.webViewClient = object : WebViewClient() {
            override fun onPageFinished(view: WebView?, url: String?) {
                super.onPageFinished(view, url)
                view?.evaluateJavascript(
                    """
                        (function() {
                            var button = document.getElementById('input_12');
                            button.addEventListener('click', function() {
                                if (document.getElementById('input')) {
                                    document.getElementById('input').click();
                                } else {
                                    alert('Error!!');
                                }
                            });
                        })();
                    """.trimIndent(), null
                )
            }   }
    }
    override fun onBackPressed() {
        if(browser.canGoBack()){
            browser.goBack()
        }
        else
            super.onBackPressed()
    }
}