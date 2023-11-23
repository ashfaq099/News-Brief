package com.example.newssummary;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import androidx.appcompat.app.AppCompatActivity;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class NewsFullActivity extends AppCompatActivity {
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_full);
        String url = getIntent().getStringExtra("url");
        webView = findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        // Call Python function to generate summary
        if (!Python.isStarted()) {
            Python.start(new AndroidPlatform(this));
        }

        try {
           String summary = new GenerateSummaryTask().execute(url).get();
            Log.d("URL", url);

            // Now you can use the summary as needed in your Android application
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed();
    }

    private static class GenerateSummaryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {

                Python python = Python.getInstance();
                PyObject pyObject = python.getModule("Summary");
                PyObject result = pyObject.callAttr("generate_summary", params[0]);

                // Log the summary for testing purposes
                String summary = result.toString();
                Log.d("Summary", summary);

                return summary;
            }

        }
    }

