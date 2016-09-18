package cc.soham.rcexample;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import cc.soham.rcexample.objects.Survey;

public class MainActivity extends AppCompatActivity {
    CoordinatorLayout coordinatorLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        coordinatorLayout = (CoordinatorLayout) findViewById(R.id.coordinatorlayout);

        checkForSurvey();
    }

    /**
     * Uses {@link com.google.firebase.remoteconfig.FirebaseRemoteConfig} to check for the latest
     * {@link Survey} value and then updates the UI accordingly (with a snackbar)
     */
    private void checkForSurvey() {
        RemoteConfigManager.getSurvey(new RemoteConfigManager.OnSurveyDeterminedListener() {
            @Override
            public void onSurveyDetermined(final Survey survey) {
                if (survey != null && survey.isEnabled()) {
                    updateSurveyEnabled(survey);
                }
            }
        });
    }

    /**
     * Creates a {@link Snackbar} based on the dynamic configuration metion in the @param survey
     *
     * @param survey instance of a {@link com.google.firebase.remoteconfig.FirebaseRemoteConfig} variable
     */
    private void updateSurveyEnabled(final Survey survey) {
        Snackbar.make(coordinatorLayout,
                survey.getMessage(),
                survey.isPermanent() ? Snackbar.LENGTH_INDEFINITE : Snackbar.LENGTH_LONG)
                .setAction("Go", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        SurveyActivity.start(MainActivity.this, survey.getSurveyLink());
                    }
                })
                .show();
    }
}
