package com.java.SGT;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;

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
    private String scoreString;
    private Integer totalscore;
    private String slashscore;
    FirebaseDatabase database;
    DatabaseReference myRef;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.quiz);

        Intent mintent = getIntent();
        namestring = mintent.getExtras().getString("username");
        quizselect = mintent.getExtras().getInt("taskselect");
        topbar = findViewById(R.id.briefing);
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


        questionselect = 0;
        quizselectpath = "q" + quizselect;
        // handle specific cases
        // handle one question quizzes


        quizinit();
    }

    // A function to determine which quiz is pressed
    // and initializes the quiz accordingly
    public void quizinit(){
        // Set up score to be referenced later in the code
        myRef.child(quizselectpath).child("questions").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                long temp = dataSnapshot.getChildrenCount();
                String questioncount = Long.toString(temp);
                String s = "0/" + questioncount;
                score.setText(s);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        // Set the top bar name for the quiz
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
        // Set up the first questions text
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
        // Set up the first question choices
        choiceselectpath = 0;
        myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                if(dataSnapshot.exists()) {
                    String type = dataSnapshot.getValue(String.class);
                    scoreString = score.getText().toString();
                    totalscore = parseforscore(scoreString);
                    choice1selectpath = 0;
                    choice2selectpath = 1;
                    if (type.equals("t")) {
                        // Question is true/false, meaning we only worry about
                        // the first and second choices

                        // Set up the correct paths to get to the choices needed
                        choice3answer.setVisibility(View.INVISIBLE);
                        choice3.setVisibility(View.INVISIBLE);
                        // Set up question choice 1 text
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
                        // Set up question choice 2 text
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
                    }
                    else if(type.equals("m")){
                        // Question is multiple choice, meaning we need all
                        // three choices filled and visible
                        if (choice3answer.getVisibility() == View.INVISIBLE && choice3.getVisibility() == View.INVISIBLE){
                            choice3answer.setVisibility(View.VISIBLE);
                            choice3.setVisibility(View.VISIBLE);
                        }
                        // Set up the correct paths to get to the choices needed
                        choice3selectpath = 2;
                        // Set up question choice 1 text
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
                        // Set up question choice 2 text
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
                        // Set up question choice 3 text
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
                            if (intscore < totalscore) {
                                intscore++;
                                String nscore = intscore.toString() + slashscore;
                                score.setText(nscore);
                                quizprogress();
                            }
                        } else {
                            if (intscore > 0 || intscore == 0) {
                                if (intscore > 0)
                                    intscore--;
                                String nscore = intscore.toString() + slashscore;
                                score.setText(nscore);
                                quizprogress();
                            }
                        }
                        String comp = totalscore.toString() + slashscore;
                        if (score.getText().toString().equals(comp)) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("login", false);
                            intent.putExtra("username", namestring);
                            startActivity(intent);
                            finish();
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
                        if (temp.equals(answer)) {
                            if (intscore < totalscore) {
                                intscore++;
                                String nscore = intscore.toString() + slashscore;
                                score.setText(nscore);
                                quizprogress();
                            }
                        } else {
                            if (intscore > 0 || intscore == 0) {
                                if (intscore > 0)
                                    intscore--;
                                String nscore = intscore.toString() + slashscore;
                                score.setText(nscore);
                                quizprogress();
                            }
                        }
                        String comp = totalscore.toString() + slashscore;
                        if (score.getText().toString().equals(comp)) {
                            Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                            intent.putExtra("login", false);
                            intent.putExtra("username", namestring);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });
            }
        });
        if (choice3answer.getVisibility() == View.VISIBLE && choice3.getVisibility() == View.VISIBLE)
            choice3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    answer = choice3answer.getText().toString();
                    myRef.child(quizselectpath).child("answers").child(questionselect.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            String temp = dataSnapshot.getValue(String.class);
                            if (temp.equals(answer)) {
                                if (intscore < totalscore) {
                                    intscore++;
                                    String nscore = intscore.toString() + slashscore;
                                    score.setText(nscore);
                                    quizprogress();
                                }
                            } else {
                                if (intscore > 0 || intscore == 0) {
                                    if (intscore > 0)
                                        intscore--;
                                    String nscore = intscore.toString() + slashscore;
                                    score.setText(nscore);
                                    quizprogress();
                                }
                            }
                            String comp = totalscore.toString() + slashscore;
                            if (score.getText().toString().equals(comp)) {
                                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                                intent.putExtra("login", false);
                                intent.putExtra("username", namestring);
                                startActivity(intent);
                                finish();
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {

                        }
                    });
                }
            });

    }

    // A function called in quizinit
    // to set up the next questions to answer
    public void quizprogress(){
        String s = score.getText().toString();
        Integer sco = parseforscore(s);
        if (questionselect < sco - 1) {
            questionselect++;
            choiceselectpath++;
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
            // Figure out what type of question is being loaded
            myRef.child(quizselectpath).child("types").child(choiceselectpath.toString()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    if (dataSnapshot.exists()) {
                        String type = dataSnapshot.getValue(String.class);
                        if (type.equals("t")) {
                            // BUG: WHEN SWITCHING FROM A MULTIPLE CHOICE TO A T/F
                            // BOTH CHOICES ARE ONE BEHIND

                            // Question is true/false, meaning we only worry about
                            // the first and second choices

                            // Set up the correct paths to get to the choices needed
                            choice1selectpath = choice1selectpath + 2;
                            choice2selectpath = choice2selectpath + 2;
                            choice3selectpath = choice3selectpath + 2;
                            choice3answer.setVisibility(View.INVISIBLE);
                            choice3.setVisibility(View.INVISIBLE);
                            // Set up question choice 1 text
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
                            // Set up question choice 2 text
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
                        }
                        else if (type.equals("m")) {
                            choice1selectpath += 3;
                            choice2selectpath += 3;
                            choice3selectpath += 3;
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
                        if (type.equals("t")) {
                            // Question is true/false, meaning we only worry about
                            // the first and second choices

                            // Set up the correct paths to get to the choices needed
                            choice1selectpath = 0;
                            choice2selectpath = 1;
                            choice3selectpath = 2;
                            choice3answer.setVisibility(View.INVISIBLE);
                            choice3.setVisibility(View.INVISIBLE);
                            // Set up question choice 1 text
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
                            // Set up question choice 2 text
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
                        }
                        else if (type.equals("m")) {
                            choice1selectpath = 0;
                            choice2selectpath = 1;
                            choice3selectpath = 2;
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

    public Integer parseforscore(String sc){
        Integer count = 0;
        for (int i = 0; i < sc.length(); i++){
            char c = sc.charAt(i);
            if (c == '/'){
             char b = sc.charAt(i+1);
             String s = String.valueOf(b);
             count = Integer.parseInt(s);
             slashscore = String.valueOf(c) + b;
            }
        }
        return count;
    }
}


