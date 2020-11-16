package com.example.yu_map.Activity.FriendTimeTable;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.widget.TextViewCompat;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.yu_map.Activity.LoginActivity;
import com.example.yu_map.R;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

public class FriendTimeTableActivity extends AppCompatActivity {

    private String FriendName = TimeTableFriendListAdapter.Friendname;

    private static TextView Monday[] = new TextView[13];
    private static  TextView Tuesday[] = new TextView[13];
    private static  TextView Wednesday[] = new TextView[13];
    private static  TextView Thursday[] = new TextView[13];
    private static  TextView Friday[] = new TextView[13];
    private static TextView ShowFriendName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_friend_time_table);

        Monday[1] = findViewById(R.id.Friendmonday1);
        Monday[2] = findViewById(R.id.Friendmonday2);
        Monday[3] = findViewById(R.id.Friendmonday3);
        Monday[4] = findViewById(R.id.Friendmonday4);
        Monday[5] = findViewById(R.id.Friendmonday5);
        Monday[6] = findViewById(R.id.Friendmonday6);
        Monday[7] = findViewById(R.id.Friendmonday7);
        Monday[8] = findViewById(R.id.Friendmonday8);
        Monday[9] = findViewById(R.id.Friendmonday9);
        Monday[10] = findViewById(R.id.Friendmonday10);
        Monday[11] = findViewById(R.id.Friendmonday11);
        Monday[12] = findViewById(R.id.Friendmonday12);

        Tuesday[1] = findViewById(R.id.Friendtuesday1);
        Tuesday[2] = findViewById(R.id.Friendtuesday2);
        Tuesday[3] = findViewById(R.id.Friendtuesday3);
        Tuesday[4] = findViewById(R.id.Friendtuesday4);
        Tuesday[5] = findViewById(R.id.Friendtuesday5);
        Tuesday[6] = findViewById(R.id.Friendtuesday6);
        Tuesday[7] = findViewById(R.id.Friendtuesday7);
        Tuesday[8] = findViewById(R.id.Friendtuesday8);
        Tuesday[9] = findViewById(R.id.Friendtuesday9);
        Tuesday[10] = findViewById(R.id.Friendtuesday10);
        Tuesday[11] = findViewById(R.id.Friendtuesday11);
        Tuesday[12] = findViewById(R.id.Friendtuesday12);

        Wednesday[1] = findViewById(R.id.Friendwednesday1);
        Wednesday[2] = findViewById(R.id.Friendwednesday2);
        Wednesday[3] = findViewById(R.id.Friendwednesday3);
        Wednesday[4] = findViewById(R.id.Friendwednesday4);
        Wednesday[5] = findViewById(R.id.Friendwednesday5);
        Wednesday[6] = findViewById(R.id.Friendwednesday6);
        Wednesday[7] = findViewById(R.id.Friendwednesday7);
        Wednesday[8] = findViewById(R.id.Friendwednesday8);
        Wednesday[9] = findViewById(R.id.Friendwednesday9);
        Wednesday[10] = findViewById(R.id.Friendwednesday10);
        Wednesday[11] = findViewById(R.id.Friendwednesday11);
        Wednesday[12] = findViewById(R.id.Friendwednesday12);

        Thursday[1] = findViewById(R.id.Friendthursday1);
        Thursday[2] = findViewById(R.id.Friendthursday2);
        Thursday[3] = findViewById(R.id.Friendthursday3);
        Thursday[4] = findViewById(R.id.Friendthursday4);
        Thursday[5] = findViewById(R.id.Friendthursday5);
        Thursday[6] = findViewById(R.id.Friendthursday6);
        Thursday[7] = findViewById(R.id.Friendthursday7);
        Thursday[8] = findViewById(R.id.Friendthursday8);
        Thursday[9] = findViewById(R.id.Friendthursday9);
        Thursday[10] = findViewById(R.id.Friendthursday10);
        Thursday[11] = findViewById(R.id.Friendthursday11);
        Thursday[12] = findViewById(R.id.Friendthursday12);

        Friday[1] = findViewById(R.id.Friendfriday1);
        Friday[2] = findViewById(R.id.Friendfriday2);
        Friday[3] = findViewById(R.id.Friendfriday3);
        Friday[4] = findViewById(R.id.Friendfriday4);
        Friday[5] = findViewById(R.id.Friendfriday5);
        Friday[6] = findViewById(R.id.Friendfriday6);
        Friday[7] = findViewById(R.id.Friendfriday7);
        Friday[8] = findViewById(R.id.Friendfriday8);
        Friday[9] = findViewById(R.id.Friendfriday9);
        Friday[10] = findViewById(R.id.Friendfriday10);
        Friday[11] = findViewById(R.id.Friendfriday11);
        Friday[12] = findViewById(R.id.Friendfriday12);

        ShowFriendName = findViewById(R.id.ShowFriendName);

        ShowFriendName.setText(FriendName + "님의 시간표입니다");
        ShowTimeTable();
    }

    private void ShowTimeTable(){
        FirebaseDatabase db = FirebaseDatabase.getInstance();
        DatabaseReference dbr = db.getReference().child("TimeTable").child(FriendName);

        dbr.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot ds : snapshot.getChildren()) {
                    String Lecture = ds.getKey();
                    String FirstDays = snapshot.child(Lecture).child("FirstDays").getValue().toString();
                    String FirstDaysStartTimeTemp = snapshot.child(Lecture).child("FirstDaysStartTime").getValue().toString();
                    int FirstDaysStartTime = Integer.parseInt(FirstDaysStartTimeTemp.substring(0, 2)) - 8;
                    String FirstDaysFinishTimeTemp = snapshot.child(Lecture).child("FirstDaysFinishTime").getValue().toString();
                    int FirstDaysFinishTime = Integer.parseInt(FirstDaysFinishTimeTemp.substring(0, 2)) - 8;
                    String SecondDays = snapshot.child(Lecture).child("SecondDays").getValue().toString();
                    String SecondDaysStartTimeTemp = snapshot.child(Lecture).child("SecondDaysStartTime").getValue().toString();
                    int SecondDaysStartTime = Integer.parseInt(SecondDaysStartTimeTemp.substring(0, 2)) - 8;
                    String SecondDaysFinishTimeTemp = snapshot.child(Lecture).child("SecondDaysFinishTime").getValue().toString();
                    int SecondDaysFinishTime = Integer.parseInt(SecondDaysFinishTimeTemp.substring(0, 2)) - 8;
                    int Firsttemp = FirstDaysFinishTime - FirstDaysStartTime;
                    int Secondemp = SecondDaysFinishTime - SecondDaysStartTime;


                    if (FirstDays.equals("Monday")) {
                        if (Firsttemp == 0) {
                            Monday[FirstDaysStartTime].setWidth(15);
                            Monday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Monday[FirstDaysStartTime].setText(Lecture);

                        } else {
                            Monday[FirstDaysStartTime].setWidth(15);
                            Monday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Monday[FirstDaysStartTime].setText(Lecture);


                            if (Firsttemp > 1) {
                                for (int i = 0; i < Firsttemp; i++) {
                                    Monday[FirstDaysStartTime + i + 1].setWidth(15);
                                    Monday[FirstDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[FirstDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Monday[FirstDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Monday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Monday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Monday[FirstDaysStartTime + Firsttemp].setText(Lecture);

                            }
                        }
                    } else if (SecondDays.equals("Monday")) {
                        if (Secondemp == 0) {
                            Monday[SecondDaysStartTime].setWidth(15);
                            Monday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Monday[SecondDaysStartTime].setText(Lecture);

                        } else {
                            Monday[SecondDaysStartTime].setWidth(15);
                            Monday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Monday[SecondDaysStartTime].setText(Lecture);


                            if (Secondemp > 1) {
                                for (int i = 0; i < Secondemp; i++) {
                                    Monday[SecondDaysStartTime + i + 1].setWidth(15);
                                    Monday[SecondDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[SecondDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Monday[SecondDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Monday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Monday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Monday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Monday[SecondDaysStartTime + Secondemp].setText(Lecture);

                            }
                        }
                    }//월요일끝


                    if (FirstDays.equals("Tuesday")) {
                        if (Firsttemp == 0) {


                            Tuesday[FirstDaysStartTime].setWidth(15);
                            Tuesday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Tuesday[FirstDaysStartTime].setText(Lecture);

                        } else {
                            Tuesday[FirstDaysStartTime].setWidth(15);
                            Tuesday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Tuesday[FirstDaysStartTime].setText(Lecture);


                            if (Firsttemp > 1) {
                                for (int i = 0; i < Firsttemp; i++) {
                                    Tuesday[FirstDaysStartTime + i + 1].setWidth(15);
                                    Tuesday[FirstDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[FirstDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Tuesday[FirstDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Tuesday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Tuesday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Tuesday[FirstDaysStartTime + Firsttemp].setText(Lecture);

                            }
                        }
                    } else if (SecondDays.equals("Tuesday")) {
                        if (Secondemp == 0) {
                            Tuesday[SecondDaysStartTime].setWidth(Tuesday[SecondDaysStartTime].getWidth());
                            Tuesday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Tuesday[SecondDaysStartTime].setText(Lecture);

                        } else {
                            Tuesday[SecondDaysStartTime].setWidth(Tuesday[SecondDaysStartTime].getWidth());
                            Tuesday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Tuesday[SecondDaysStartTime].setText(Lecture);

                            if (Secondemp > 1) {
                                for (int i = 0; i < Secondemp; i++) {
                                    Tuesday[SecondDaysStartTime + i + 1].setWidth(15);
                                    Tuesday[SecondDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[SecondDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Tuesday[SecondDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Tuesday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Tuesday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Tuesday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Tuesday[SecondDaysStartTime + Secondemp].setText(Lecture);

                            }
                        }
                    } //화요일끝


                    if (FirstDays.equals("Wednesday")) {
                        if (Firsttemp == 0) {
                            Wednesday[FirstDaysStartTime].setWidth(15);
                            Wednesday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Wednesday[FirstDaysStartTime].setText(Lecture);

                        } else {
                            Wednesday[FirstDaysStartTime].setWidth(15);
                            Wednesday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Wednesday[FirstDaysStartTime].setText(Lecture);


                            if (Firsttemp > 1) {
                                for (int i = 0; i < Firsttemp; i++) {
                                    Wednesday[FirstDaysStartTime + i + 1].setWidth(15);
                                    Wednesday[FirstDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[FirstDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Wednesday[FirstDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Wednesday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Wednesday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Wednesday[FirstDaysStartTime + Firsttemp].setText(Lecture);

                            }
                        }
                    } else if (SecondDays.equals("Wednesday")) {
                        if (Secondemp == 0) {
                            Wednesday[SecondDaysStartTime].setWidth(Wednesday[SecondDaysStartTime].getWidth());
                            Wednesday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Wednesday[SecondDaysStartTime].setText(Lecture);

                        } else {
                            Wednesday[SecondDaysStartTime].setWidth(Wednesday[SecondDaysStartTime].getWidth());
                            Wednesday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Wednesday[SecondDaysStartTime].setText(Lecture);


                            if (Secondemp > 1) {
                                for (int i = 0; i < Secondemp; i++) {
                                    Wednesday[SecondDaysStartTime + i + 1].setWidth(15);
                                    Wednesday[SecondDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[SecondDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Wednesday[SecondDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Wednesday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Wednesday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Wednesday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Wednesday[SecondDaysStartTime + Secondemp].setText(Lecture);

                            }
                        }
                    }//수요일끝

                    if (FirstDays.equals("Thursday")) {
                        if (Firsttemp == 0) {
                            Thursday[FirstDaysStartTime].setWidth(15);
                            Thursday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Thursday[FirstDaysStartTime].setText(Lecture);

                        } else {
                            Thursday[FirstDaysStartTime].setWidth(15);
                            Thursday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Thursday[FirstDaysStartTime].setText(Lecture);


                            if (Firsttemp > 1) {
                                for (int i = 0; i < Firsttemp; i++) {
                                    Thursday[FirstDaysStartTime + i + 1].setWidth(15);
                                    Thursday[FirstDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[FirstDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Thursday[FirstDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Thursday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Thursday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Thursday[FirstDaysStartTime + Firsttemp].setText(Lecture);

                            }
                        }
                    } else if (SecondDays.equals("Thursday")) {
                        if (Secondemp == 0) {
                            Thursday[SecondDaysStartTime].setWidth(15);
                            Thursday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Thursday[SecondDaysStartTime].setText(Lecture);

                        } else {
                            Thursday[SecondDaysStartTime].setWidth(15);
                            Thursday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Thursday[SecondDaysStartTime].setText(Lecture);


                            if (Secondemp > 1) {
                                for (int i = 0; i < Secondemp; i++) {
                                    Thursday[SecondDaysStartTime + i + 1].setWidth(15);
                                    Thursday[SecondDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[SecondDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Thursday[SecondDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Thursday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Thursday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Thursday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Thursday[SecondDaysStartTime + Secondemp].setText(Lecture);

                            }
                        }
                    }//목요일끝

                    if (FirstDays.equals("Friday")) {
                        if (Firsttemp == 0) {
                            Friday[FirstDaysStartTime].setWidth(15);
                            Friday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Friday[FirstDaysStartTime].setText(Lecture);

                        } else {
                            Friday[FirstDaysStartTime].setWidth(15);
                            Friday[FirstDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[FirstDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Friday[FirstDaysStartTime].setText(Lecture);

                            if (Firsttemp > 1) {
                                for (int i = 0; i < Firsttemp; i++) {
                                    Friday[FirstDaysStartTime + i + 1].setWidth(15);
                                    Friday[FirstDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[FirstDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Friday[FirstDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Friday[FirstDaysStartTime + Firsttemp].setWidth(15);
                                Friday[FirstDaysStartTime + Firsttemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[FirstDaysStartTime + Firsttemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Friday[FirstDaysStartTime + Firsttemp].setText(Lecture);

                            }
                        }
                    } else if (SecondDays.equals("Friday")) {
                        if (Secondemp == 0) {
                            Friday[SecondDaysStartTime].setWidth(15);
                            Friday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Friday[SecondDaysStartTime].setText(Lecture);

                        } else {
                            Friday[SecondDaysStartTime].setWidth(15);
                            Friday[SecondDaysStartTime].setHeight(15);
                            TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[SecondDaysStartTime], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                            Friday[SecondDaysStartTime].setText(Lecture);

                            if (Secondemp > 1) {
                                for (int i = 0; i < Secondemp; i++) {
                                    Friday[SecondDaysStartTime + i + 1].setWidth(15);
                                    Friday[SecondDaysStartTime + i + 1].setHeight(15);
                                    TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[SecondDaysStartTime + i + 1], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                    Friday[SecondDaysStartTime + i + 1].setText(Lecture);

                                }
                            } else {
                                Friday[SecondDaysStartTime + Secondemp].setWidth(15);
                                Friday[SecondDaysStartTime + Secondemp].setHeight(15);
                                TextViewCompat.setAutoSizeTextTypeUniformWithConfiguration(Friday[SecondDaysStartTime + Secondemp], 30, 40, 2, TextViewCompat.AUTO_SIZE_TEXT_TYPE_NONE);
                                Friday[SecondDaysStartTime + Secondemp].setText(Lecture);

                            }
                            ;
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}