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
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt
import kotlin.random.Random

class MainActivity : AppCompatActivity() {

    private lateinit var circularWorldView: CircularWorldView
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        circularWorldView = CircularWorldView(this)
        setContentView(circularWorldView)
        animateSquare()
    }

    private fun animateSquare() {
        handler.postDelayed({
            circularWorldView.invalidate()
            animateSquare()
        }, 30)
    }

    inner class CircularWorldView(context: Context) : View(context) {

        private val paintSquare = Paint().apply { color = Color.GREEN }
        private val paintCircle = Paint().apply { color = Color.BLACK }
        private val circleRadius = 600f // Radius of the circular world

        private var squareX: Float = 0f
        private var squareY: Float = 0f
        private val squareSize: Float = 50f

        init {
            // Inicializar las coordenadas del cuadrado dentro de la circunferencia
            resetSquarePosition()
        }

        private fun resetSquarePosition() {
            // Generar un ángulo aleatorio
            val randomAngle = Random.nextDouble(0.0, 2 * Math.PI)

            // Calcular las coordenadas iniciales del cuadrado dentro de la circunferencia
            squareX = (width / 2 + circleRadius * cos(randomAngle)).toFloat() - squareSize / 2
            squareY = (height / 2 + circleRadius * sin(randomAngle)).toFloat() - squareSize / 2
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            // Dibujar la circunferencia en negro
            canvas.drawCircle(width / 2f, height / 2f, circleRadius, paintCircle)

            // Dibujar el cuadrado en verde
            canvas.drawRect(squareX, squareY, squareX + squareSize, squareY + squareSize, paintSquare)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    // Handle touch events to update the square position based on user input
                    val touchX = event.x - width / 2
                    val touchY = event.y - height / 2

                    // Limit the movement to stay within the circular boundary
                    val distance = sqrt((touchX * touchX + touchY * touchY).toDouble())
                    if (distance <= circleRadius) {
                        squareX = event.x - squareSize / 2
                        squareY = event.y - squareSize / 2
                        invalidate()
                    }
                }
            }
            return true
        }
    }
}



