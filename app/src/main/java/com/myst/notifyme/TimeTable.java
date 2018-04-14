package com.myst.notifyme;

import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by Myst on 1/24/2017.
 */

public class TimeTable {

    int[] lectureHourId = {R.id.first, R.id.second, R.id.third, R.id.fourth, R.id.fifth, R.id.sixth, R.id.seventh, R.id.eighth};

    int[] lectureTypeId = {R.id.lecture_type1, R.id.lecture_type2, R.id.lecture_type3, R.id.lecture_type4,
                         R.id.lecture_type5, R.id.lecture_type6, R.id.lecture_type7, R.id.lecture_type8};

    int[] timeId = {R.id.time1, R.id.time2, R.id.time3, R.id.time4, R.id.time5, R.id.time6, R.id.time7, R.id.time8};

    int[] classroomId = {R.id.classroom1, R.id.classroom2, R.id.classroom3, R.id.classroom4, R.id.classroom5,
                         R.id.classroom6, R.id.classroom7, R.id.classroom8};

    int[] teacherId = {R.id.teacher1, R.id.teacher2, R.id.teacher3, R.id.teacher4, R.id.teacher5, R.id.teacher6,
                       R.id.teacher7, R.id.teacher8};

    TextView[] lectureHour = new TextView[8];
    EditText[] lectureType = new EditText[8];
    EditText[] time = new EditText[8];
    EditText[] teacher = new EditText[8];
    EditText[] classroom = new EditText[8];

    TimeTable(View view){

        for(int i=0; i<lectureHour.length; i++){
            lectureHour[i] = (TextView) view.findViewById(lectureHourId[i]);
        }

        for(int i=0; i<lectureType.length; i++){
            lectureType[i] = (EditText) view.findViewById(lectureTypeId[i]);
        }

        for(int i=0; i<time.length; i++){
            time[i] = (EditText) view.findViewById(timeId[i]);
        }

        for(int i=0; i<teacher.length; i++){
            teacher[i] = (EditText) view.findViewById(teacherId[i]);
        }

        for(int i=0; i<classroom.length; i++){
            classroom[i] = (EditText) view.findViewById(classroomId[i]);
        }
    }
}
