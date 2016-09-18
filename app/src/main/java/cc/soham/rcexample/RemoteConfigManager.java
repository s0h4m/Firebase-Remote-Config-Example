package cc.soham.rcexample;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.gson.Gson;

import cc.soham.rcexample.objects.Survey;

/**
 * Manages retrieval of various {@link FirebaseRemoteConfig} related values
 */
public class RemoteConfigManager {
    private static final String KEY_SURVEY = "survey";

    interface OnSurveyDeterminedListener {
        void onSurveyDetermined(@Nullable Survey survey);
    }

    /**
     * Fetches the latest version of {@link FirebaseRemoteConfig} and performs a callback
     *
     * @param onSurveyDeterminedListener
     */
    public static void getSurvey(final OnSurveyDeterminedListener onSurveyDeterminedListener) {
        final FirebaseRemoteConfig firebaseRemoteConfig = FirebaseRemoteConfig.getInstance();
        firebaseRemoteConfig.setDefaults(R.xml.remote_config_defaults);
        firebaseRemoteConfig
                .fetch()
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        // activates the fetched values
                        firebaseRemoteConfig.activateFetched();
                        // retrieves the surveys and initiates a callback
                        retrieveSurveyAndCallBack(firebaseRemoteConfig, onSurveyDeterminedListener);
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        e.printStackTrace();
                        // retrieves the surveys and initiates a callback
                        retrieveSurveyAndCallBack(firebaseRemoteConfig, onSurveyDeterminedListener);
                    }
                });
    }

    /**
     * Retrieves the value of survey from the given {@link FirebaseRemoteConfig} instance and performs a callback
     *
     * @param firebaseRemoteConfig
     * @param onSurveyDeterminedListener
     */
    private static void retrieveSurveyAndCallBack(FirebaseRemoteConfig firebaseRemoteConfig, OnSurveyDeterminedListener onSurveyDeterminedListener) {
        String surveyStringInJson = firebaseRemoteConfig.getString(KEY_SURVEY);
        Survey survey = new Gson().fromJson(surveyStringInJson, Survey.class);
        onSurveyDeterminedListener.onSurveyDetermined(survey);
    }
}
