package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class QuizActivity extends Activity {
    private Button button;
    private TextView topbar;
    private TextView question;
    private TextView score;
    private Integer intscore = 0;
    private Button choice1;
    private Button choice2;
    private Button choice3;
    private String answer;
    private TextView choice1answer;
    private TextView choice2answer;
    private TextView choice3answer;
    private String namestring;
    private int quizselect;
    private Integer questionselect;
    private Integer choiceselectpath;
    private Integer choice1selectpath;
    private Integer choice2selectpath;
    private Integer choice3selectpath;
    private String quizselectpath;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        Intent mintent = getIntent();
        namestring = mintent.getExtras().getString("username");
        quizselect = mintent.getExtras().getInt("taskselect");
        topbar = findViewById(R.id.briefing);
        button = findViewById(R.id.quizbutton);
        choice1 = findViewById(R.id.choice1);
        choice1answer = findViewById(R.id.choice1answer);
        //choice1answer.setText();
        choice2 = findViewById(R.id.choice2);
        choice2answer = findViewById(R.id.choice2answer);
        //choice2answer.setText();
        choice3 = findViewById(R.id.choice3);
        choice3answer = findViewById(R.id.choice3answer);
        //choice3answer.setText();
        question = findViewById(R.id.question);
        score = findViewById(R.id.score);
        //question.setText();
        database = FirebaseDatabase.getInstance();
        myRef = database.getReference("quiz");
        //if (question.getText().toString().equals(customs[0]))
            //answer = customs[1];

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                intent.putExtra("login", false);
                intent.putExtra("username", namestring);
                startActivity(intent);
            }
        });

        questionselect = 0;
        quizselectpath = "q" + quizselect;
        // handle specific cases
        // handle one question quizzes

        // handle two question quizzes
        if (quizselect == 1 || quizselect == 7){
            String nscore = "0/2";
            score.setText(nscore);

            myRef.child(quizselectpath).child("topic").addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String temp = dataSnapshot.getValue(String.class);
                    topbar.setText(temp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    String temp = dataSnapshot.getValue(String.class);
                    question.setText(temp);
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            choiceselectpath = 0;
            myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if(dataSnapshot.exists()) {
                        String type = dataSnapshot.getValue(String.class);
                        choice1selectpath = 0;
                        choice2selectpath = 1;
                        choice3selectpath = 2;
                        if (type.equals("m")) {
                            myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String temp = dataSnapshot.getValue(String.class);
                                    choice1answer.setText(temp);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String temp = dataSnapshot.getValue(String.class);
                                    choice2answer.setText(temp);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                            myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                @Override
                                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                    String temp = dataSnapshot.getValue(String.class);
                                    choice3answer.setText(temp);
                                }

                                @Override
                                public void onCancelled(@NonNull DatabaseError databaseError) {

                                }
                            });
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            choice1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answer = choice1answer.getText().toString();
                    myRef.child(quizselectpath).child("answers").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String temp = dataSnapshot.getValue(String.class);
                            if (temp.equals(answer)) {
                                if (intscore < 2) {
                                    intscore++;
                                    String nscore = intscore.toString() + "/2";
                                    score.setText(nscore);
                                    if (questionselect == 0) {
                                        questionselect = 1;
                                        choiceselectpath = 1;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 3;
                                                    choice2selectpath = 4;
                                                    choice3selectpath = 5;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    } else {
                                        questionselect = 0;
                                        choiceselectpath = 0;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 0;
                                                    choice2selectpath = 1;
                                                    choice3selectpath = 2;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            } else {
                                if (intscore > 0 || intscore == 0) {
                                    if (intscore > 0)
                                        intscore--;
                                    String nscore = intscore.toString() + "/2";
                                    score.setText(nscore);
                                    if (questionselect == 0) {
                                        questionselect = 1;
                                        choiceselectpath = 1;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 3;
                                                    choice2selectpath = 4;
                                                    choice3selectpath = 5;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    } else {
                                        questionselect = 0;
                                        choiceselectpath = 0;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 0;
                                                    choice2selectpath = 1;
                                                    choice3selectpath = 2;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }
                            if (score.getText().toString().equals("2/2")) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("login", false);
                                intent.putExtra("username", namestring);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

            choice2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answer = choice2answer.getText().toString();
                    myRef.child(quizselectpath).child("answers").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String temp = dataSnapshot.getValue(String.class);
                            if (temp.equals(answer)){
                                if (intscore < 2) {
                                    intscore++;
                                    String nscore = intscore.toString() + "/2";
                                    score.setText(nscore);
                                    if(questionselect == 0) {
                                        questionselect = 1;
                                        choiceselectpath = 1;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 3;
                                                    choice2selectpath = 4;
                                                    choice3selectpath = 5;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else{
                                        questionselect = 0;
                                        choiceselectpath = 0;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 0;
                                                    choice2selectpath = 1;
                                                    choice3selectpath = 2;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }
                            else {
                                if (intscore > 0 || intscore == 0) {
                                    if (intscore > 0)
                                        intscore--;
                                    String nscore = intscore.toString() + "/2";
                                    score.setText(nscore);
                                    if (questionselect == 0) {
                                        questionselect = 1;
                                        choiceselectpath = 1;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 3;
                                                    choice2selectpath = 4;
                                                    choice3selectpath = 5;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    } else {
                                        questionselect = 0;
                                        choiceselectpath = 0;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 0;
                                                    choice2selectpath = 1;
                                                    choice3selectpath = 2;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }
                            if (score.getText().toString().equals("2/2")) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("login", false);
                                intent.putExtra("username", namestring);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });
            choice3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answer = choice3answer.getText().toString();
                    myRef.child(quizselectpath).child("answers").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String temp = dataSnapshot.getValue(String.class);
                            if (temp.equals(answer)){
                                if (intscore < 2) {
                                    intscore++;
                                    String nscore = intscore.toString() + "/2";
                                    score.setText(nscore);
                                    if(questionselect == 0) {
                                        questionselect = 1;
                                        choiceselectpath = 1;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 3;
                                                    choice2selectpath = 4;
                                                    choice3selectpath = 5;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                    }
                                    else{
                                        questionselect = 0;
                                        choiceselectpath = 0;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if(dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 0;
                                                    choice2selectpath = 1;
                                                    choice3selectpath = 2;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }
                            else {
                                if (intscore > 0 || intscore == 0) {
                                    if (intscore > 0)
                                        intscore--;
                                    String nscore = intscore.toString() + "/2";
                                    score.setText(nscore);
                                    if (questionselect == 0) {
                                        questionselect = 1;
                                        choiceselectpath = 1;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });

                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 3;
                                                    choice2selectpath = 4;
                                                    choice3selectpath = 5;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    } else {
                                        questionselect = 0;
                                        choiceselectpath = 0;
                                        myRef.child(quizselectpath).child("questions").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                String temp = dataSnapshot.getValue(String.class);
                                                question.setText(temp);
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                            @Override
                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                if (dataSnapshot.exists()) {
                                                    String type = dataSnapshot.getValue(String.class);
                                                    choice1selectpath = 0;
                                                    choice2selectpath = 1;
                                                    choice3selectpath = 2;
                                                    if (type.equals("m")) {
                                                        myRef.child(quizselectpath).child("choices").child(choice1selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice1answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice2selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice2answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                        myRef.child(quizselectpath).child("choices").child(choice3selectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                                                            @Override
                                                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                                String temp = dataSnapshot.getValue(String.class);
                                                                choice3answer.setText(temp);
                                                            }

                                                            @Override
                                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                                            }
                                                        });
                                                    }
                                                }
                                            }

                                            @Override
                                            public void onCancelled(@NonNull DatabaseError databaseError) {

                                            }
                                        });
                                    }
                                }
                            }
                            if (score.getText().toString().equals("2/2")) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("login", false);
                                intent.putExtra("username", namestring);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

        }


    }
}



