package com.ctf.ctfserver.service.submission.comparator;

import com.ctf.ctfserver.domain.entities.Submission;

import java.util.Comparator;

public class SubmissionDateComparator implements Comparator<Submission> {

    @Override
    public int compare(Submission o1, Submission o2) {
        return o1.getDate().compareTo(o2.getDate());
    }

    public static SubmissionDateComparator getInstance() {
        return new SubmissionDateComparator();
    }

}
