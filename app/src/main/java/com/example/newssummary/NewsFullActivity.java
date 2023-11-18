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
        String url = getIntent().getStringExtra("url");
        webView = findViewById(R.id.web_view);
        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.setWebViewClient(new WebViewClient());
        webView.loadUrl(url);

        // Call your AsyncTask with the URL

     new multi().execute(url);
        //Log.i("Summary", "URL loaded in WebView: " + url);
    }

    @Override
    public void onBackPressed() {
        if (webView.canGoBack())
            webView.goBack();
        else
            super.onBackPressed(); //no problem in function also
    }

   //  Summary generator
    public class multi extends AsyncTask<String, String, String> {

       @Override
       protected String doInBackground(String... strings) {
           try {
               Log.i("Summary", "AsyncTask is executing.");//executed
               String url = strings[0];
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
               Log.i("Summary", result);
               return result;
           } catch (Exception e) {
               Log.e("Summary", "Exception in doInBackground: " + e.getMessage(), e);
               return "Error: " + e.getMessage();
           }
       }

       @Override
        protected void onPostExecute(String result) {
            // Handle the result as needed, e.g., update UI or display the summary
            Log.i("Summary", "Summary from AsyncTask: " + result);
        }
    }
}


