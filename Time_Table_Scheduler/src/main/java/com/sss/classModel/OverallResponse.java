package com.sss.classModel;

public class OverallResponse {
    public OverallResponse() {
        this.facultyResponse = new FacultyResponse();
        this.courseResponse = new CourseResponse();
    }

    public FacultyResponse facultyResponse;
    public CourseResponse courseResponse;
}
