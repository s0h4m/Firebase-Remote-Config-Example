package cc.soham.rcexample.objects;

/**
 * An object that represents the "survey" feature in a "Feature Toggle"
 * We use Firebase Remote Config to retrieve and initialise an instance of this
 */
public class Survey {
    boolean enabled;
    String surveyLink;
    String message;
    boolean permanent;

    public boolean isEnabled() {
        return enabled;
    }

    public String getSurveyLink() {
        return surveyLink;
    }

    public String getMessage() {
        return message;
    }

    public boolean isPermanent() {
        return permanent;
    }
}
