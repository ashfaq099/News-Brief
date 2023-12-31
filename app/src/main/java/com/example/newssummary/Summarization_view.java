package com.example.newssummary;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.chaquo.python.PyObject;
import com.chaquo.python.Python;
import com.chaquo.python.android.AndroidPlatform;
import com.example.newssummary.Models.NewsHeadlines;
import com.squareup.picasso.Picasso;

public class Summarization_view extends AppCompatActivity {
    NewsHeadlines headlines;
    TextView txt_title, txt_author, txt_time, txt_content, sentiment_txt;
    Button read_more;
    ImageView img_news, sentiment_icon, fav;
    String url;
    String res, imgUrl;
    ProgressDialog dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_summarization_view);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        txt_title = findViewById(R.id.text_detail_title);
        txt_author = findViewById(R.id.text_detail_author);
        txt_time = findViewById(R.id.text_detail_time);
        txt_content = findViewById(R.id.text_detail_content);
        read_more = findViewById(R.id.read_more);
        img_news = findViewById(R.id.img_detail_news);
        headlines = (NewsHeadlines) getIntent().getSerializableExtra("data");
        txt_title.setText(headlines.getTitle());
        //txt_author.setText(headlines.getAuthor());
        txt_time.setText(headlines.getPublishedAt());
        imgUrl = headlines.getUrlToImage();
        url = headlines.getUrl();
        new multi().execute();

        //python implementation
        read_more.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Summarization_view.this, NewsFullActivity.class);
                intent.putExtra("url", url);
                startActivity(intent);
            }
        });

    }
    public class multi extends AsyncTask<String, String, String> {
        // Omit the 'private' modifier for package-private access
        String summary;
        String wordCountSummary;
        String wordCountNews;
        String percentage;

        @Override
        protected void onPreExecute() {
            dialog = new ProgressDialog(Summarization_view.this);
            dialog.setTitle("Loading ....");
            dialog.show();
            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
        }

        @Override
        protected String doInBackground(String... strings) {
            if (!Python.isStarted()) {
                Python.start(new AndroidPlatform(Summarization_view.this));
            }
            Python py = Python.getInstance();
            PyObject pyobj = py.getModule("Summary");
            PyObject obj = pyobj.callAttr("generate_summary", url);
            PyObject[] arr = obj.toJava(PyObject[].class);

            summary = arr[0].toString();
            wordCountSummary = arr[1].toString();
            wordCountNews = arr[2].toString();
            percentage = arr[3].toString();
            if (summary.isEmpty()) {
                try {
                    summary = headlines.getDescription();
                } catch (Exception e) {
                    summary = "Sorry! failed to generate a summary";
                }
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            dialog.dismiss();

            // Use Picasso to load the image
            Picasso.get().load(imgUrl)
                    .error(R.drawable.placeholder_error)
                    .placeholder(R.drawable.placeholder_loading)
                    .into(img_news);

            // Display summary, word count in summary, word count in news, and percentage
            txt_content.setText("Summary: \n" + summary +
                    "\n\nWord Count in Summary: " + wordCountSummary +
                    "\nWord Count in News: " + wordCountNews +
                    "\nPercentage: " + percentage + "%");
        }
    }




}



