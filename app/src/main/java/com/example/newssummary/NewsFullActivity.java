package com.example.newssummary;

import androidx.appcompat.app.AppCompatActivity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

public class NewsFullActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);
        String url=getIntent().getStringExtra("url");
        webView =findViewById(R.id.web_view);
        WebSettings webSettings =webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

    }

   @Override
    public void onBackPressed() {
        if(webView.canGoBack())
            webView.goBack();
        else
        super.onBackPressed();
    }

    // Summary generator
    public class multi extends AsyncTask<String, String, String> {

        @Override
        protected String doInBackground(String... strings) {
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(NewsFullActivity.this));
            }
            Python py = Python.getInstance();
            PyObject pyobj = py.getModule("Summary");
            PyObject obj = pyobj.callAttr("generate_summary", url);
            String result = obj.toString();
            if (result.isEmpty()) {
                result = "Sorry! Failed to generate a summary";
            }
            return result;
            Log.i("Summary", result);
        }


    }

}