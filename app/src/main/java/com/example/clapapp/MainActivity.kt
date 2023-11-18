package com.example.clapapp

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Button
import android.widget.SeekBar
import android.widget.TextView
import com.google.android.material.floatingactionbutton.FloatingActionButton

class MainActivity : AppCompatActivity() {
    private lateinit var seekBar: SeekBar
//    private lateinit var mediaPlayer: MediaPlayer
    private lateinit var handler: Handler
    private lateinit var runnable: Runnable
    private var mediaPlayer2:MediaPlayer? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        seekBar = findViewById(R.id.sbLaughing)
        handler = Handler(Looper.getMainLooper())
//        mediaPlayer = MediaPlayer.create(this, R.raw.claps)

//        val button = findViewById<Button>(R.id.btnClap)
//        button.setOnClickListener {
//            mediaPlayer.start()
//        }
//        val button2 = findViewById<Button>(R.id.btnLaugh)
//        button2.setOnClickListener {
//            mediaPlayer2.start()
//        }
        val pause = findViewById<FloatingActionButton>(R.id.fbPause)
        val play = findViewById<FloatingActionButton>(R.id.fbPlay)
        val stop = findViewById<FloatingActionButton>(R.id.fbStop)
        play.setOnClickListener {
            if(mediaPlayer2==null){
                mediaPlayer2 = MediaPlayer.create(this, R.raw.laugh)
                intializeSeekBar()
            }
            mediaPlayer2?.start()
        }
        pause.setOnClickListener {
            mediaPlayer2?.pause()

        }
        stop.setOnClickListener {
            mediaPlayer2?.stop()
            mediaPlayer2?.reset()
            mediaPlayer2?.release()
            mediaPlayer2 = null
            handler.removeCallbacks(runnable)
            seekBar.progress = 0

        }



    }

    private fun intializeSeekBar(){
        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if(fromUser) mediaPlayer2?.seekTo(progress)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {

            }

            override fun onStopTrackingTouch(p0: SeekBar?) {

            }
        })
        val tvPlayed = findViewById<TextView>(R.id.tvPlayed)
        val tvDue = findViewById<TextView>(R.id.tvViewed)
        seekBar.max = mediaPlayer2!!.duration
        runnable = Runnable {
            seekBar.progress = mediaPlayer2!!.currentPosition

            val playedTime = mediaPlayer2!!.currentPosition/1000
            tvPlayed.text = "$playedTime sec"
            val duration = mediaPlayer2!!.duration/1000
            val dueTime = duration-playedTime
            tvDue.text = "$dueTime sec"
            handler.postDelayed(runnable,1000)

        }
        handler.postDelayed(runnable,1000)

    }
}