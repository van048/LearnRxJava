package cn.ben.learnrxjava;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import rx.Observable;
import rx.functions.Action1;
import rx.functions.Func1;

public class MapActivity extends AppCompatActivity {

    private static final String TAG = MapActivity.class.getSimpleName();
    private List<String> nameList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);

        nameList = new ArrayList<>();

        Student student1, student2, student3;
        student1 = new Student("a");
        student2 = new Student("b");
        student3 = new Student("c");
        Observable.just(student1, student2, student3)
                //使用map进行转换，参数1：转换前的类型，参数2：转换后的类型
                .map(new Func1<Student, String>() {
                    @Override
                    public String call(Student i) {
                        return i.getName();//返回name
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        nameList.add(s);
                    }
                });
        for (String name : nameList) Log.i(TAG, name);

        //多次使用map，想用几个用几个
        Observable.just("Hello", "World")
                .map(new Func1<String, Integer>() {//将String类型转化为Integer类型的哈希码
                    @Override
                    public Integer call(String s) {
                        return s.hashCode();
                    }
                })
                .map(new Func1<Integer, String>() {//将转化后得到的Integer类型的哈希码再转化为String类型
                    @Override
                    public String call(Integer integer) {
                        return integer + "";
                    }
                })
                .subscribe(new Action1<String>() {
                    @Override
                    public void call(String s) {
                        Log.i(TAG, s);
                    }
                });

        List<Student> students = new ArrayList<>();
        student1.addCourse("hello", "hi", "bye");
        student2.addCourse("excuse", "me");
        student3.addCourse("good");
        students.add(student1);
        students.add(student2);
        students.add(student3);

        Observable.from(students)
                .flatMap(new Func1<Student, Observable<Course>>() {
                    @Override
                    public Observable<Course> call(Student student) {
                        return Observable.from(student.getCoursesList());
                    }
                })
                .subscribe(new Action1<Course>() {
                    @Override
                    public void call(Course course) {
                        Log.i(TAG, course.getName());
                    }
                });
    }

    /**
     * 学生类
     */
    class Student {
        private final String mName;//姓名
        private final List<Course> mCoursesList;//所修的课程

        Student(String name) {
            mName = name;
            mCoursesList = new ArrayList<>();
        }

        public String getName() {
            return mName;
        }

        List<Course> getCoursesList() {
            return mCoursesList;
        }

        void addCourse(String... courseNameList) {
            for (String courseName : courseNameList)
                mCoursesList.add(new Course(courseName));
        }
    }

    /**
     * 课程类
     */
    class Course {
        private final String mName;//课程名

        Course(String courseName) {
            mName = courseName;
        }

        public String getName() {
            return mName;
        }
    }
}
