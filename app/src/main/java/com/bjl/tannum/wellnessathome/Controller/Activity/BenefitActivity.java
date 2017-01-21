package com.bjl.tannum.wellnessathome.Controller.Activity;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.bjl.tannum.wellnessathome.Controller.Adapter.BenefitsAdapter;
import com.bjl.tannum.wellnessathome.Controller.Fragment.NavigationDrawerFragment;
import com.bjl.tannum.wellnessathome.Controller.Library.GPSTracker;
import com.bjl.tannum.wellnessathome.Model.BenefitInfo;
import com.bjl.tannum.wellnessathome.R;

import java.util.ArrayList;
import java.util.List;

public class BenefitActivity extends AppCompatActivity implements BenefitsAdapter.ClickListener {


    List<BenefitInfo> benefitInfos;
    private RecyclerView recyclerView;
    private BenefitsAdapter benefitsAdapter;
    private Toolbar toolbar;
    final  int MY_PERMISSIONS_ACCESS_LOCATION = 0;
    final  int MY_PERMISSIONS_CALL_PHONE = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_benefit);

        //Mask: setup tool bar
        toolbar = (Toolbar)findViewById(R.id.app_bar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        //Mask: setup navigation drawer
        NavigationDrawerFragment drawderFragment = (NavigationDrawerFragment)getSupportFragmentManager().findFragmentById(R.id.fragment_navigation_drawer);
        drawderFragment.setUp(R.id.fragment_navigation_drawer,(DrawerLayout)findViewById(R.id.drawer_layout),toolbar);

        //Mask: Make Dummy information.
        benefitInfos = new ArrayList<BenefitInfo>();
        benefitInfos.add(new BenefitInfo("การตรวจสุขภาพประจำปี (annual health examination) เป็นการคัดกรองโรค (medical scrcening) เป็นประจำทุกรอบ 12 เดือน ซึ่งโรคที่คัดกรองต้องเป็นโรคที่คัดกรองได้ หรือสามารถรักษาในระยะเริ่มต้นแล้วได้ผลดี โดยมีแนวคิดในการคัดกรองในกลุ่มที่ไม่มีอาการก่อนที่จะมีอาการ จะช่วยชะลอการเจ็บป่วยหรือเสียชีวิตได้ สำหรับการตรวจสุขภาพเป็นระยะ(periodical health examination) หมายถึงการตรวจสุขภาพที่ถูกกำหนดตามความเสี่ยงต่อการเกิดโรคเรื้อรังและโรคที่เกิดจากการ สัมผัสสิ่งแวดล้อมในการทำงาน ซึ่งการตรวจสุขภาพทั้ง 2 แบบ มีวัตถุประสงค์แตกต่างไปจากการตรวจเพื่อวินิจฉัยโรค ซึ่งเป็นการตรวจในกลุ่มคนที่มีอาการของโรคแล้ว อันที่จริงแล้วการตรวจสุขภาพประจำปี (annual healthcheck up) หรือการตรวจสุขภาพเป็นระยะ (periodical health examination) นั้น มีประโยชน์และมีความสำคัญอย่างยิ่งในการคัดกรองโรค และการหาปัจจัยเสี่ยงของการเกิดโรคหนึ่งๆ เพื่อจะได้เป็นโอการในการรักษาให้หายขาดได้\n" +
                "หากต้องการใช้สิทธิ์ตรวจสุขภาพประจำปีฟรี กรุณาแจ้งล่วงหน้า 7วัน ","ตรวจสุขภาพประจำปี ฟรี!",R.drawable.promotion5,100,50));
        benefitInfos.add(new BenefitInfo("ห้องพักฟรี! โดยมีอายุสมาชิกเป็นเวลา 5ปี หรือคิดตามอัตราค่าห้องจริงไม่เกินปีละ 20,000 บาท แต่หากมีค่าใช้จ่ายเกินจาก 20,000 บาท ในปีนั้นๆ โดยคิดตามจำนวนคูปอง คูปอง 1 ใบ มูลค่าใบละ 1,000บาท พักฟรี 1 คืน ค่าใช้จ่ายอื่นๆ จ่ายเพิ่มเติมต่างจ่ายตามจริง คูปอง 1 ใบ แอดมิต 1 วัน รวมไม่เกิน 100,000 บาท อัตราดังกล่าวถือเป็นส่วนลดใช้ได้ปีต่อปี ไม่สามารถนำยอดฟรีของปีถัดไปมารวมในปีที่ใช้อยู่ได้ และไม่สามารถแลกเปลี่ยนเป็นเงินสด เครดิต ส่วนลด หรือบริการอื่นๆ ตามโปรโมโมชั่นของที่พักนั้นๆ ได้ สิทธิ์นี้ขอสงวนไว้สำหรับค่าห้องพักเท่านั้นไม่จำกัดประเภท และระดับห้องพักกรุณาจองห้องพักล่วงหน้า 45 วัน\n" +
                " \n" +
                "สถานที่พักฟรีของสมาชิก\n" +
                "       • โรงแรมสตาร์ไลท์ (เขาใหญ่) การ์เด้นโซน \n" +
                "       • โรงแรมแกรนด์จอมเทียนพาเลซ์ (พัทยา) แกรนด์คอทเทจวิง\n" +
                "       • โตเกียวคันทรีอินน์ รีสอร์ท อำเภอเมือง นครราชสีมา \n" +
                "       • เวลเนสซิตี้ อำเภอบางไทร พระนครศรีอยุธยา","ค่าห้องพักฟรี! 5ปี",R.drawable.promotion4,100,50));
        benefitInfos.add(new BenefitInfo("เหมาะสำหรับมอบเป็นของขวัญ แก่ผู้ที่เรารักและเป็นห่วง รวมถึงญาติพี่น้องและเหล่าเพื่อนๆ ของท่าน โดยสามารถแจ้งสิทธิ์มายังเจ้าหน้าที่ของเรา เพื่อแจ้งข้อมูลสำคัญอ้างอิงเพื่อมอบสิทธิ์ให้ผู้ที่เราต้องการได้ใช้สิทธิ์เหมือนกับที่เราได้ โดยเมื่อมอบสิทธิ์แล้ว ระบบจะทำการหักยอดสิทธิ์ของท่านตามจำนวนที่ ส่งมอบสิทธิ์นั้น พร้อมผู้ที่ได้รับมอบสิทธิ์สามารถใช้สิทธิ์ได้ทันทีที่มีการอนุมัติแล้ว","ขายหรือใช้สิทธิให้ผู้อื่นได้",R.drawable.promotion3,100,50));
        benefitInfos.add(new BenefitInfo("การตรวจสุขภาพประจำปี ควรทำการตรวจเป็นประจำอย่างน้อยปีละครั้ง ตามสิทธิ์ที่ได้รับจากโปรโมชั่น ตรวจร่างกายฟรีในปีที่ 1 - 3 แล้วเรายังมีโปรโมชั่นพิเศษในการตรวจร่างกายในปีที่ 4 และ ปีที่ 5 โดยสามารถใช้สิทธิ์เพื่อตรวจสุขภาพในราคาพิเศษ มีส่วนลดมากถึง 30% จากราคาการตรวจสุขภาพปกติ  เพียงแจ้งรหัสสมาชิกเพื่อขอรับสิทธิ์ส่วนลดดังกว่าได้ที่โรงพยาบาลผู้ป่วยเรื้อรังขนาดเล็กเวลเนสแคร์ และศูนย์สุขภาพต่างๆในเคลือเวลเนสแคร์","ตรวจสุขภาพประจำปีที่ 4-5 ลดพิเศษ 30%",R.drawable.promotion2,100,50));
        benefitInfos.add(new BenefitInfo("ยังได้รับส่วนลดพิเศษ ค่าอาหาร ค่ายา ค่าหมอ และค่าบริการอื่นๆ เมื่อใช้บริการทางการแพทย์ และบริการอื่นๆ ในเคลือเวลเนสแคร์ ซึ่งสามารถได้รับส่วนลดมูลค่า 3,000บาท ต่อปี ซึ่งสามารถใช้ได้ตลอดอายุบัตรสมาชิก 5ปี","ส่วนลด 10% ค่าบริการ อาหาร ยา ค่าหมอ และอื่นๆ",R.drawable.promotion1,100,50));


        //Mask: Initial RecyclerView
        recyclerView = (RecyclerView)findViewById(R.id.benefit_list);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        benefitsAdapter = new BenefitsAdapter(this,benefitInfos);
        benefitsAdapter.SetOnBenefitItemClickListener(this);
        recyclerView.setAdapter(benefitsAdapter);


        //Mask: Check Permission
        CheckLocationPermission();
        CheckCallPermission();

    }

    private void CheckLocationPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.ACCESS_FINE_LOCATION},
                    MY_PERMISSIONS_ACCESS_LOCATION);
        }
    }
    private void CheckCallPermission() {

        int permissionCheck = ContextCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE);

        if(permissionCheck != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},
                    MY_PERMISSIONS_ACCESS_LOCATION);
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        //Log.d("debug","************* On Create Menu");
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    private void dialContactPhone(final String phoneNumber) {
        startActivity(new Intent(Intent.ACTION_CALL, Uri.fromParts("tel", phoneNumber, null)));
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int item_id = item.getItemId();
        Log.d("debug","item id = " + String.valueOf(item_id));
        switch (item_id){
            case R.id.action_home:
                break;
            case R.id.action_emergency:
                dialContactPhone("035249500");
                break;
            case R.id.action_appointment:
                startActivity(new Intent(BenefitActivity.this,AppointmentActivity.class));
                break;
            case R.id.action_promotion:
                startActivity(new Intent(BenefitActivity.this,PromotionActivity.class));
                break;
            case R.id.action_location:
                startActivity(new Intent(BenefitActivity.this,LocationActivity.class));
                break;
            case R.id.action_logout:
                startActivity(new Intent(this,LoginActivity.class));
                finish();
                break;
            case R.id.action_wellness_home:
                Intent intent_home = new Intent(this,HomepageActivity.class);
                intent_home.putExtra("URL","home");
                startActivity(intent_home);
                break;
            case R.id.action_wellness_city:
                Intent intent_city = new Intent(this,HomepageActivity.class);
                intent_city.putExtra("URL","city");
                startActivity(intent_city);
                break;
            case R.id.action_wellness_resort:
                Intent intent_resort = new Intent(this,HomepageActivity.class);
                intent_resort.putExtra("URL","resort");
                startActivity(intent_resort);
                break;
            case R.id.action_wellness_sahakron:
                Intent intent_sahakron = new Intent(this,HomepageActivity.class);
                intent_sahakron.putExtra("URL","sahakron");
                startActivity(intent_sahakron);
                break;

            default:
                break;
        }

        return super.onOptionsItemSelected(item);
    }
    @Override
    public void onBenefitItemClicked(int position, View view) {
        Log.d("debug","position = " + String.valueOf(position));
        startActivity(new Intent(this,PromotionActivity.class));
    }
}
