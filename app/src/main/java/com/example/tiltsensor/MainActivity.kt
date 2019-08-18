package com.example.tiltsensor

import android.content.Context
import android.content.pm.ActivityInfo
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.os.Bundle
import android.util.Log
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity(), SensorEventListener {
    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {

    }//end onAccuracyChanged

    //질문 사항 책 대로 27버전 설정해도 p0로 설정 됨
    override fun onSensorChanged(event : SensorEvent?) {

        //센서값이 변경되면 호출됨
        //values[0] : x축 값 : 위로 기울이면 -10~0, 아래로 기울이면 0~10
        //values[1] : y축 값 : 왼쪽으로 기울이면 -10~0, 오른쪽으로 기울이면 0~10
        //values[2] : z축 값 : 미사용

        event?.let{
            Log.d("MainActivity","onSensorChanged: x :" +
                    " ${event.values[0]}, y : ${event.values[1]}, z : ${event.values[2]}")
         }

        if (event != null) {
            tiltView.onSensorEvent(event)
        }
    }// end onSensorChanged


    private lateinit var  tiltView: TiltView

    private val sensorManager by lazy {
        getSystemService(Context.SENSOR_SERVICE) as SensorManager
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        //화면이 꺼지지 않게 하기
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)
        //화면이 가로 모드로 고정되게 하기
        requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        super.onCreate(savedInstanceState)
        //setContentView(R.layout.activity_main)

        tiltView = TiltView(this)
        setContentView(tiltView)

    }

    override fun onResume() {
        super.onResume()
        sensorManager.registerListener(this,
                                        sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                                        SensorManager.SENSOR_DELAY_NORMAL)
    }

    override fun onPause() {
        super.onPause()
        sensorManager.unregisterListener(this)
    }




}// end class


