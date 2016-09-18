package cc.soham.rcexample;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

/**
 * Displays a web page passed using the static helper method
 */
public class SurveyActivity extends AppCompatActivity {
    private static final String KEY_SURVEY_URL = "survey_url";
    WebView surveyWebView;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_survey);

        surveyWebView = (WebView) findViewById(R.id.activity_survey_webview);
        progressBar = (ProgressBar) findViewById(R.id.activity_survey_progress);

        String url = getIntent().getStringExtra(KEY_SURVEY_URL);
        loadWebViewForNewsObject(url);
    }

    /**
     * Starts this activity and loads the given link in the web page
     *
     * @param context
     * @param link    A url to be opened
     */
    public static void start(final Context context, String link) {
        Intent intent = new Intent(context, SurveyActivity.class);
        intent.putExtra(KEY_SURVEY_URL, link);
        context.startActivity(intent);
    }

    /**
     * Opens the given url in this page
     *
     * @param url
     */
    private void loadWebViewForNewsObject(String url) {
        if (url != null) {
            surveyWebView.getSettings().setJavaScriptEnabled(true);
            surveyWebView.setWebViewClient(new WebViewClient() {
                @Override
                public void onPageStarted(WebView view, String url, Bitmap favicon) {
                    super.onPageStarted(view, url, favicon);
                    progressBar.setVisibility(View.VISIBLE);
                }

                @Override
                public void onPageFinished(WebView view, String url) {
                    super.onPageFinished(view, url);
                    progressBar.setVisibility(View.GONE);
                }
            });
            surveyWebView.loadUrl(url);
        }
    }
}
