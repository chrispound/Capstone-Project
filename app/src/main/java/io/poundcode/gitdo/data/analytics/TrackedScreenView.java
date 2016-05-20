package io.poundcode.gitdo.data.analytics;

public interface TrackedScreenView {
    String getScreenName();

    void fireAnalytics();
}
