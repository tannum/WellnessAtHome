package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Debug;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bjl.tannum.wellnessathome.Controller.Adapter.PromotionAdapter;
import com.bjl.tannum.wellnessathome.Model.PromotionInfo;
import com.bjl.tannum.wellnessathome.R;
import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.oguzdev.circularfloatingactionmenu.library.FloatingActionButton;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PromotionActivity extends AppCompatActivity implements PromotionAdapter.ClickListener{

    RecyclerView recyclerView;
    List<PromotionInfo> promotionItemInfos = Collections.emptyList();
    PromotionAdapter promotionAdapter;
    private DatabaseReference mDatabase;
    FirebaseRecyclerAdapter<PromotionInfo,PromotionViewHolder> firebaseRecyclerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_promotion);




        //Mask: Initial recycler view
        recyclerView = (RecyclerView)findViewById(R.id.listPromotion);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotion_blog");


//        //Mask: Initial recycler view
//        promotionItemInfos = new ArrayList<PromotionInfo>();
//        recyclerView = (RecyclerView)findViewById(R.id.listPromotion);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        promotionAdapter = new PromotionAdapter(this,promotionItemInfos);
//        recyclerView.setAdapter(promotionAdapter);


//        //Mask: Make dummy data
//        promotionItemInfos.add(new PromotionInfo(R.drawable.promotion5,"การตรวจสุขภาพประจำปี","เป็นการคัดกรองโรค (medical scrcening) เป็นประจำทุกรอบ 12 เดือน ซึ่งโรคที่คัดกรองต้องเป็นโรคที่คัดกรองได้ หรือสามารถรักษาในระยะเริ่มต้นแล้วได้ผลดี โดยมีแนวคิดในการคัดกรองในกลุ่มที่ไม่มีอาการก่อนที่จะมีอาการ จะช่วยชะลอการเจ็บป่วยหรือเสียชีวิตได้ สำหรับการตรวจสุขภาพเป็นระยะ(periodical health examination) หมายถึงการตรวจสุขภาพที่ถูกกำหนดตามความเสี่ยงต่อการเกิดโรคเรื้อรังและโรคที่เกิดจากการ สัมผัสสิ่งแวดล้อมในการทำงาน ซึ่งการตรวจสุขภาพทั้ง 2 แบบ มีวัตถุประสงค์แตกต่างไปจากการตรวจเพื่อวินิจฉัยโรค ซึ่งเป็นการตรวจในกลุ่มคนที่มีอาการของโรคแล้ว อันที่จริงแล้วการตรวจสุขภาพประจำปี (annual healthcheck up) หรือการตรวจสุขภาพเป็นระยะ (periodical health examination) นั้น มีประโยชน์และมีความสำคัญอย่างยิ่งในการคัดกรองโรค และการหาปัจจัยเสี่ยงของการเกิดโรคหนึ่งๆ เพื่อจะได้เป็นโอการในการรักษาให้หายขาดได้\\n\" +\n" +
//                "                \"หากต้องการใช้สิทธิ์ตรวจสุขภาพประจำปีฟรี กรุณาแจ้งล่วงหน้า 7วัน "));
//        promotionItemInfos.add(new PromotionInfo(R.drawable.promotion4,"ห้องพักฟรี!","ห้องพักฟรี! โดยมีอายุสมาชิกเป็นเวลา 5ปี หรือคิดตามอัตราค่าห้องจริงไม่เกินปีละ 20,000 บาท แต่หากมีค่าใช้จ่ายเกินจาก 20,000 บาท ในปีนั้นๆ โดยคิดตามจำนวนคูปอง คูปอง 1 ใบ มูลค่าใบละ 1,000บาท พักฟรี 1 คืน ค่าใช้จ่ายอื่นๆ จ่ายเพิ่มเติมต่างจ่ายตามจริง คูปอง 1 ใบ แอดมิต 1 วัน รวมไม่เกิน 100,000 บาท อัตราดังกล่าวถือเป็นส่วนลดใช้ได้ปีต่อปี ไม่สามารถนำยอดฟรีของปีถัดไปมารวมในปีที่ใช้อยู่ได้ และไม่สามารถแลกเปลี่ยนเป็นเงินสด เครดิต ส่วนลด หรือบริการอื่นๆ ตามโปรโมโมชั่นของที่พักนั้นๆ ได้ สิทธิ์นี้ขอสงวนไว้สำหรับค่าห้องพักเท่านั้นไม่จำกัดประเภท และระดับห้องพักกรุณาจองห้องพักล่วงหน้า 45 วัน\n" +
//                " \n" +
//                "สถานที่พักฟรีของสมาชิก\n" +
//                "       • โรงแรมสตาร์ไลท์ (เขาใหญ่) การ์เด้นโซน \n" +
//                "       • โรงแรมแกรนด์จอมเทียนพาเลซ์ (พัทยา) แกรนด์คอทเทจวิง\n" +
//                "       • โตเกียวคันทรีอินน์ รีสอร์ท อำเภอเมือง นครราชสีมา \n" +
//                "       • เวลเนสซิตี้ อำเภอบางไทร พระนครศรีอยุธยา "));
//        promotionItemInfos.add(new PromotionInfo(R.drawable.promotion3,"ขายหรือใช้สิทธิให้ผุ้อื่นได้","เหมาะสำหรับมอบเป็นของขวัญ แก่ผู้ที่เรารักและเป็นห่วง รวมถึงญาติพี่น้องและเหล่าเพื่อนๆ ของท่าน โดยสามารถแจ้งสิทธิ์มายังเจ้าหน้าที่ของเรา เพื่อแจ้งข้อมูลสำคัญอ้างอิงเพื่อมอบสิทธิ์ให้ผู้ที่เราต้องการได้ใช้สิทธิ์เหมือนกับที่เราได้ โดยเมื่อมอบสิทธิ์แล้ว ระบบจะทำการหักยอดสิทธิ์ของท่านตามจำนวนที่ ส่งมอบสิทธิ์นั้น พร้อมผู้ที่ได้รับมอบสิทธิ์สามารถใช้สิทธิ์ได้ทันทีที่มีการอนุมัติแล้ว้"));
//        promotionItemInfos.add((new PromotionInfo(R.drawable.promotion2,"ส่วนลด 10% ค่าบริการ อาหาร ยา ค่าหมอ และอื่นๆ","ยังได้รับส่วนลดพิเศษ ค่าอาหาร ค่ายา ค่าหมอ และค่าบริการอื่นๆ เมื่อใช้บริการทางการแพทย์ และบริการอื่นๆ ในเคลือเวลเนสแคร์ ซึ่งสามารถได้รับส่วนลดมูลค่า 3,000บาท ต่อปี ซึ่งสามารถใช้ได้ตลอดอายุบัตรสมาชิก 5ปี")));
//        promotionItemInfos.add(new PromotionInfo(R.drawable.promotion1,"ตรวจสุขภาพประจำปีที่ 4-5 ลดพิเศษ 30%","การตรวจสุขภาพประจำปี ควรทำการตรวจเป็นประจำอย่างน้อยปีละครั้ง ตามสิทธิ์ที่ได้รับจากโปรโมชั่น ตรวจร่างกายฟรีในปีที่ 1 - 3 แล้วเรายังมีโปรโมชั่นพิเศษในการตรวจร่างกายในปีที่ 4 และ ปีที่ 5 โดยสามารถใช้สิทธิ์เพื่อตรวจสุขภาพในราคาพิเศษ มีส่วนลดมากถึง 30% จากราคาการตรวจสุขภาพปกติ  เพียงแจ้งรหัสสมาชิกเพื่อขอรับสิทธิ์ส่วนลดดังกว่าได้ที่โรงพยาบาลผู้ป่วยเรื้อรังขนาดเล็กเวลเนสแคร์ และศูนย์สุขภาพต่างๆในเคลือเวลเนสแคร"));
//
//        promotionAdapter.notifyDataSetChanged();



//        //Mask: Initial Firebase database
//        recyclerView = (RecyclerView)findViewById(R.id.listPromotion);
//        recyclerView.setLayoutManager(new LinearLayoutManager(this));
//        mDatabase = FirebaseDatabase.getInstance().getReference().child("promotion_blog");
//        ValueEventListener postListener = new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                Log.d("debug", dataSnapshot.getValue().toString());
//                PromotionInfo info = dataSnapshot.getValue(PromotionInfo.class);
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//        mDatabase.addValueEventListener(postListener);




        //Mask: Initial floating button
        ImageView icon = new ImageView(this); // Create an icon
        icon.setImageResource(R.mipmap.ic_launcher);

        FloatingActionButton actionButton = new FloatingActionButton.Builder(this)
                .setContentView(icon)
                .build();
        actionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(PromotionActivity.this,PromotionPostActivity.class));
            }
        });


    }


    @Override
    protected void onStart() {
        super.onStart();

        FirebaseRecyclerAdapter<PromotionInfo,PromotionViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<PromotionInfo, PromotionViewHolder>(
                PromotionInfo.class,
                R.layout.promotion_row,
                PromotionViewHolder.class,
                mDatabase
                ) {
            @Override
            protected void populateViewHolder(PromotionViewHolder viewHolder, PromotionInfo model, int position) {
                viewHolder.setTitle(model.getHeader());
                viewHolder.setDesc(model.getContent());
                viewHolder.setImage(getApplicationContext(),model.getImageUrl());
            }
        };
        recyclerView.setAdapter(firebaseRecyclerAdapter);

        firebaseRecyclerAdapter.notifyDataSetChanged();
    }


    public static class PromotionViewHolder extends RecyclerView.ViewHolder{

        View view;

        public PromotionViewHolder(View itemView) {
            super(itemView);
            view = itemView;
        }

        public void setTitle(String title){
            TextView post_title = (TextView)itemView.findViewById(R.id.promotion_header_content);
            post_title.setText(title);
        }
        public void setDesc(String desc){
            TextView post_desc = (TextView)itemView.findViewById(R.id.promotion_content);
            post_desc.setText(desc);
        }
        public void setImage(Context ctx,String image){
            ImageView post_image = (ImageView)itemView.findViewById(R.id.image_promotion);
            Picasso.with(ctx).load(image).into(post_image);

        }
    }


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
    }

    @Override
    public void onBookingPromotionItemClicked(int position, View view) {
        Log.d("debug","position = "  + String.valueOf(position));
    }

}
