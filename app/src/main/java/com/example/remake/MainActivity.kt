package com.example.remake

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {

    private lateinit var squareView: SquareView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        squareView = SquareView(this)
        setContentView(squareView)
        animateSquare()
    }

    private fun animateSquare() {
        handler.postDelayed({
            squareView.invalidate()
            animateSquare()
        }, 30)
    }

    inner class SquareView(context: Context) : View(context) {

        private val paint = Paint().apply { color = Color.GREEN }
        private var squareX: Float = 0f
        private var squareY: Float = 0f
        private val squareSize: Float = 240f

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)
            canvas.drawRect(squareX, squareY, squareX + squareSize, squareY + squareSize, paint)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    squareX = event.x - squareSize / 2
                    squareY = event.y - squareSize / 2
                    invalidate()
                }
            }
            return true
        }
    }
}
