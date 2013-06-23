package de.hswt.hrm.report.latex.collectors;

import java.util.Collection;

import de.hswt.hrm.photo.model.Photo;

public class TargetPerformancePhotoStates {

    private Collection<TargetPerformanceActivity> targetPerformanceActivity;
    private Collection<Photo> photos;
    private String targetName;
    private String targetGrade;

    public Collection<TargetPerformanceActivity> getTargetPerformanceActivity() {
        return targetPerformanceActivity;
    }

    public void setTargetPerformanceActivity(
            Collection<TargetPerformanceActivity> targetPerformanceActivity) {
        this.targetPerformanceActivity = targetPerformanceActivity;
    }

    public Collection<Photo> getPhotos() {
        return photos;
    }

    public void setPhotos(Collection<Photo> photos) {
        this.photos = photos;
    }

    public String getTargetName() {
        return targetName;
    }

    public void setTargetName(String targetName) {
        this.targetName = targetName;
    }

    public String getTargetGrade() {
        return targetGrade;
    }

    public void setTargetGrade(String targetGrade) {
        this.targetGrade = targetGrade;
    }

}
