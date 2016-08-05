package com.widevision.multipilco;

import android.app.Activity;
import android.app.Dialog;
import android.content.ClipData;
import android.content.ClipDescription;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Handler;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.DragEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;


import com.widevision.multipilco.util.Constant;
import com.widevision.multipilco.util.NumberBean;
import com.widevision.multipilco.util.PreferenceConnector;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;

public class GameActivity extends Activity implements View.OnClickListener {

    ImageView first_number, second_number, third_number, forth_number, fifth_number, sixth_number, ans_image2,
            ans_image3, ans_image4, ans_image5, ans_image6, ans_letter5, ans_letter4, ans_letter3, ans_letter2, ans_letter1,
            question_1, next, exit, reload;
    ArrayList<NumberBean> numberBeans;
    ArrayList<Integer> numbers;
    private String[] secuence = {"Ascending", "Descending"};
    TextView txt1, txt2;
    Animation animation;
    Animation shake;
    Handler myHandler = new Handler();
    RelativeLayout ans_layout6, ans_layout5, ans_layout4, ans_layout3, ans_layout2, question_layout2, question_layout1, question_layout3, question_layout4, question_layout5, question_layout6;
    TextView text;
    String sequesnce = null;
    int drag = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON, WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_game);
        init();
    }

    private void init() {
        first_number = (ImageView) findViewById(R.id.first_number);
        next = (ImageView) findViewById(R.id.next);
        reload = (ImageView) findViewById(R.id.reload);
        exit = (ImageView) findViewById(R.id.exit);
        question_1 = (ImageView) findViewById(R.id.question_1);
        ans_image2 = (ImageView) findViewById(R.id.ans_image2);
        ans_image3 = (ImageView) findViewById(R.id.ans_image3);
        ans_image4 = (ImageView) findViewById(R.id.ans_image4);
        ans_image5 = (ImageView) findViewById(R.id.ans_image5);
        ans_image6 = (ImageView) findViewById(R.id.ans_image6);
        ans_letter5 = (ImageView) findViewById(R.id.ans_letter5);
        ans_letter4 = (ImageView) findViewById(R.id.ans_letter4);
        ans_letter3 = (ImageView) findViewById(R.id.ans_letter3);
        ans_letter2 = (ImageView) findViewById(R.id.ans_letter2);
        ans_letter1 = (ImageView) findViewById(R.id.ans_letter1);
        ans_layout6 = (RelativeLayout) findViewById(R.id.ans_layout6);
        ans_layout5 = (RelativeLayout) findViewById(R.id.ans_layout5);
        ans_layout4 = (RelativeLayout) findViewById(R.id.ans_layout4);
        ans_layout3 = (RelativeLayout) findViewById(R.id.ans_layout3);
        ans_layout2 = (RelativeLayout) findViewById(R.id.ans_layout2);
        question_layout2 = (RelativeLayout) findViewById(R.id.question_layout2);
        question_layout1 = (RelativeLayout) findViewById(R.id.question_layout1);
        question_layout3 = (RelativeLayout) findViewById(R.id.question_layout3);
        question_layout4 = (RelativeLayout) findViewById(R.id.question_layout4);
        question_layout5 = (RelativeLayout) findViewById(R.id.question_layout5);
        question_layout6 = (RelativeLayout) findViewById(R.id.question_layout6);

        text = (TextView) findViewById(R.id.text);
        numbers = new ArrayList<>();
        numberBeans = new ArrayList<>();

        if (PreferenceConnector.readString(GameActivity.this, PreferenceConnector.RELOAD, "").equals("RELOAD")) {
            numbers.add(PreferenceConnector.readInteger(GameActivity.this, PreferenceConnector.NO1, 0));
            numbers.add(PreferenceConnector.readInteger(GameActivity.this, PreferenceConnector.NO2, 0));
            numbers.add(PreferenceConnector.readInteger(GameActivity.this, PreferenceConnector.NO3, 0));
            numbers.add(PreferenceConnector.readInteger(GameActivity.this, PreferenceConnector.NO4, 0));
            numbers.add(PreferenceConnector.readInteger(GameActivity.this, PreferenceConnector.NO5, 0));
            numbers.add(PreferenceConnector.readInteger(GameActivity.this, PreferenceConnector.NO6, 0));
            sequesnce = PreferenceConnector.readString(GameActivity.this, PreferenceConnector.SEQUENCE, "");
        } else {
            numbers.add(random());
            random_ans();
            sequesnce = secuence[question()];
            if (sequesnce.equals("Descending")) {
                Collections.sort(numbers, Collections.reverseOrder());

            } else {
                Collections.sort(numbers);
            }


        }
        text.setText("Arrenge in " + sequesnce + " Order");
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO1, numbers.get(0));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO2, numbers.get(1));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO3, numbers.get(2));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO4, numbers.get(3));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO5, numbers.get(4));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO6, numbers.get(5));
        PreferenceConnector.writeString(GameActivity.this, PreferenceConnector.SEQUENCE, sequesnce);

        if (numbers.get(0) == 0) {
            question_1.setImageResource(Constant.numbers[numbers.get(0)]);
        } else {
            question_1.setImageResource(Constant.numbers[numbers.get(0) - 1]);
        }
        for (int i = 1; i < 6; i++) {
            NumberBean numberBean = new NumberBean(Constant.balls[i - 1], "" + i, Constant.numbers[numbers.get(i) - 1]);
            numberBeans.add(numberBean);
        }

        Collections.shuffle(numberBeans);

        ans_letter1.setImageResource(numberBeans.get(0).getNumber());
        ans_letter2.setImageResource(numberBeans.get(1).getNumber());
        ans_letter3.setImageResource(numberBeans.get(2).getNumber());
        ans_letter4.setImageResource(numberBeans.get(3).getNumber());
        ans_letter5.setImageResource(numberBeans.get(4).getNumber());

        ans_image2.setImageResource(numberBeans.get(0).getImage_id());
        ans_image3.setImageResource(numberBeans.get(1).getImage_id());
        ans_image4.setImageResource(numberBeans.get(2).getImage_id());
        ans_image5.setImageResource(numberBeans.get(3).getImage_id());
        ans_image6.setImageResource(numberBeans.get(4).getImage_id());

        ans_layout2.setTag(numberBeans.get(0).getTag());
        ans_layout3.setTag(numberBeans.get(1).getTag());
        ans_layout4.setTag(numberBeans.get(2).getTag());
        ans_layout5.setTag(numberBeans.get(3).getTag());
        ans_layout6.setTag(numberBeans.get(4).getTag());


        question_layout2.setTag("1");
        question_layout3.setTag("2");
        question_layout4.setTag("3");
        question_layout5.setTag("4");
        question_layout6.setTag("5");

        ans_layout2.setOnTouchListener(new MyClickListener());
        ans_layout3.setOnTouchListener(new MyClickListener());
        ans_layout4.setOnTouchListener(new MyClickListener());
        ans_layout5.setOnTouchListener(new MyClickListener());
        ans_layout6.setOnTouchListener(new MyClickListener());

        question_layout2.setOnDragListener(new MyDragListener());
        question_layout3.setOnDragListener(new MyDragListener());
        question_layout4.setOnDragListener(new MyDragListener());
        question_layout6.setOnDragListener(new MyDragListener());
        question_layout5.setOnDragListener(new MyDragListener());


        ans_layout2.setOnDragListener(new MyDragListener());
        ans_layout3.setOnDragListener(new MyDragListener());
        ans_layout4.setOnDragListener(new MyDragListener());
        ans_layout5.setOnDragListener(new MyDragListener());
        ans_layout6.setOnDragListener(new MyDragListener());

        next.setOnClickListener(this);
        reload.setOnClickListener(this);
        exit.setOnClickListener(this);


    }


    //genarate random question
    private int random() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(8 - 1 + 1) + 1;
    }

    //genarate before or after question
    private int question() {
        SecureRandom secureRandom = new SecureRandom();
        return secureRandom.nextInt(1 - 0 + 1) + 0;
    }

    //genarate 7 random answer
    private void random_ans() {
        SecureRandom secureRandom = new SecureRandom();
        for (int i = 1; i < 6; i++) {
            int ans = secureRandom.nextInt(50 - numbers.get(0) + 1) + numbers.get(0);
            if (!numbers.contains(ans)) {
                numbers.add(ans);
            } else {
                i--;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.exit:
                onBackPressed();
                break;
            case R.id.next:
                Intent intent = new Intent(GameActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.reload:

                reload_game();
                break;

        }
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(GameActivity.this, SplashActivity.class);
        startActivity(intent);
        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
        finish();
    }

    //For Show popup dialog timer
    Runnable ShowPopup = new Runnable() {
        public void run() {
            if (drag == 5) {
                show_dialog();
            }

        }

    };

    //Show popup
    public void show_dialog() {
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        dialog.setContentView(R.layout.popup_dialog);
        dialog.setCancelable(false);
        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        WindowManager.LayoutParams lp = dialog.getWindow().getAttributes();
        dialog.getWindow().setAttributes(lp);
        dialog.getWindow().setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        LinearLayout popuplayout = (LinearLayout) dialog.findViewById(R.id.popuplayout);
        ImageView next = (ImageView) dialog.findViewById(R.id.next);
        ImageView reload = (ImageView) dialog.findViewById(R.id.reload);
        ImageView exit = (ImageView) dialog.findViewById(R.id.exit);
        dialog.show();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PreferenceConnector.writeString(GameActivity.this, PreferenceConnector.RELOAD, "");

                dialog.dismiss();
                Intent intent = new Intent(GameActivity.this, GameActivity.class);
                startActivity(intent);
                finish();
            }
        });

        reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reload_game();
                dialog.dismiss();
            }
        });

        exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                reload_game();
                dialog.dismiss();
            }
        });
    }


    private void reload_game() {
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO1, numbers.get(0));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO2, numbers.get(1));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO3, numbers.get(2));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO4, numbers.get(3));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO5, numbers.get(4));
        PreferenceConnector.writeInteger(GameActivity.this, PreferenceConnector.NO6, numbers.get(5));
        PreferenceConnector.writeString(GameActivity.this, PreferenceConnector.SEQUENCE, sequesnce);
        PreferenceConnector.writeString(GameActivity.this, PreferenceConnector.RELOAD, "RELOAD");
        Intent intent = new Intent(GameActivity.this, GameActivity.class);
        startActivity(intent);
        finish();

    }

    private final class MyClickListener implements View.OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent motionEvent) {
            ClipData.Item item = new ClipData.Item((CharSequence) view.getTag());
            String[] mimeTypes = {ClipDescription.MIMETYPE_TEXT_PLAIN};
            ClipData data = new ClipData(view.getTag().toString(), mimeTypes, item);
            View.DragShadowBuilder shadowBuilder = new View.DragShadowBuilder(view);
            view.startDrag(data, shadowBuilder, view, 0);
            Vibrator vb = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
            vb.vibrate(500);
            view.setVisibility(View.INVISIBLE);
            return true;
        }
    }

    class MyDragListener implements View.OnDragListener {
        @Override
        public boolean onDrag(final View v, final DragEvent event) {
            switch (event.getAction()) {
                case DragEvent.ACTION_DRAG_STARTED:
                    break;
                case DragEvent.ACTION_DRAG_ENTERED:
                    break;
                case DragEvent.ACTION_DRAG_EXITED:
                    break;
                case DragEvent.ACTION_DROP:
                    final View view = (View) event.getLocalState();
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            if (v == findViewById(R.id.question_layout1)) {

                                ViewGroup viewgroup = (ViewGroup) view.getParent();
                                LinearLayout containView = (LinearLayout) v;
                                if (containView.getTag().equals(view.getTag())) {
                                    viewgroup.removeView(view);
                                    containView.removeAllViews();
                                    containView.addView(view);
                                    RelativeLayout img = (RelativeLayout) view;

                                    view.setVisibility(View.VISIBLE);
                                    drag = drag + 1;
                                    myHandler.postDelayed(ShowPopup, 1000);
                                } else {
                                    Animation animation1 = AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake);
                                    question_layout1.startAnimation(animation1);
                                    view.setVisibility(View.VISIBLE);
                                }
                            } else if (v == findViewById(R.id.question_layout2)) {

                                ViewGroup viewgroup = (ViewGroup) view.getParent();
                                RelativeLayout containView = (RelativeLayout) v;
                                if (containView.getTag().equals(view.getTag())) {
                                    viewgroup.removeView(view);
                                    containView.removeAllViews();
                                    containView.addView(view);
                                    drag = drag + 1;
                                    myHandler.postDelayed(ShowPopup, 1000);
                                } else {
                                    view.setVisibility(View.VISIBLE);
                                    Animation animation1 = AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake);
                                    question_layout2.startAnimation(animation1);
                                }
                            } else if (v == findViewById(R.id.question_layout3)) {

                                ViewGroup viewgroup = (ViewGroup) view.getParent();
                                RelativeLayout containView = (RelativeLayout) v;
                                if (containView.getTag().equals(view.getTag())) {
                                    viewgroup.removeView(view);
                                    containView.removeAllViews();
                                    containView.addView(view);
                                    view.setVisibility(View.VISIBLE);
                                    drag = drag + 1;
                                    myHandler.postDelayed(ShowPopup, 1000);
                                } else {
                                    view.setVisibility(View.VISIBLE);
                                    Animation animation1 = AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake);
                                    question_layout3.startAnimation(animation1);
                                }
                            } else if (v == findViewById(R.id.question_layout4)) {

                                ViewGroup viewgroup = (ViewGroup) view.getParent();
                                RelativeLayout containView = (RelativeLayout) v;
                                if (containView.getTag().equals(view.getTag())) {
                                    viewgroup.removeView(view);
                                    containView.removeAllViews();
                                    containView.addView(view);
                                    view.setVisibility(View.VISIBLE);
                                    drag = drag + 1;
                                    myHandler.postDelayed(ShowPopup, 1000);
                                } else {
                                    view.setVisibility(View.VISIBLE);
                                    Animation animation1 = AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake);
                                    question_layout4.startAnimation(animation1);
                                }
                            } else if (v == findViewById(R.id.question_layout5)) {

                                ViewGroup viewgroup = (ViewGroup) view.getParent();
                                RelativeLayout containView = (RelativeLayout) v;
                                if (containView.getTag().equals(view.getTag())) {
                                    viewgroup.removeView(view);
                                    containView.removeAllViews();
                                    containView.addView(view);
                                    drag = drag + 1;
                                    view.setVisibility(View.VISIBLE);
                                    myHandler.postDelayed(ShowPopup, 1000);
                                } else {
                                    view.setVisibility(View.VISIBLE);
                                    Animation animation1 = AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake);
                                    question_layout5.startAnimation(animation1);
                                }
                            } else if (v == findViewById(R.id.question_layout6)) {

                                ViewGroup viewgroup = (ViewGroup) view.getParent();
                                RelativeLayout containView = (RelativeLayout) v;
                                if (containView.getTag().equals(view.getTag())) {
                                    viewgroup.removeView(view);
                                    containView.removeAllViews();
                                    containView.addView(view);
                                    drag = drag + 1;
                                    view.setVisibility(View.VISIBLE);
                                    myHandler.postDelayed(ShowPopup, 1000);
                                } else {
                                    view.setVisibility(View.VISIBLE);
                                    Animation animation1 = AnimationUtils.loadAnimation(GameActivity.this, R.anim.shake);
                                    question_layout6.startAnimation(animation1);
                                }
                            } else {

                                view.setVisibility(View.VISIBLE);
                                Context context = getApplicationContext();

                            }


                        }
                    });


                    break;
                case DragEvent.ACTION_DRAG_ENDED:
                    final View view1 = (View) event.getLocalState();
                    v.post(new Runnable() {
                        @Override
                        public void run() {
                            view1.setVisibility(View.VISIBLE);
                        }
                    });

                default:
                    break;
            }
            return true;
        }
    }


}
