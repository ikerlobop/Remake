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
    private var squareX: Float = 0f
    private var squareY: Float = 0f
    private val squareSize: Float = 200f
    private val squareSpeed: Float = 5f
    private val handler = Handler(Looper.getMainLooper())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        squareView = SquareView(this)
        setContentView(squareView)

        // Inicia la animación
        animateSquare()
    }

    private fun animateSquare() {
        handler.post(object : Runnable {
            override fun run() {
                // Actualiza la posición del cuadrado
                updateSquarePosition()

                // Vuelve a dibujar la vista
                squareView.invalidate()

                // Repite la animación después de un breve retraso
                handler.postDelayed(this, 30)
            }
        })
    }

    private fun updateSquarePosition() {
        // Actualiza la posición del cuadrado en función de la velocidad
        squareX += squareSpeed

        // Si el cuadrado sale de la pantalla, reinícialo
        if (squareX > squareView.width) {
            squareX = -squareSize
        }
    }

    inner class SquareView(context: Context) : View(context) {

        private val paint = Paint()

        init {
            // Configura el color del cuadrado
            paint.color = Color.BLUE
        }

        override fun onDraw(canvas: Canvas) {
            super.onDraw(canvas)

            // Dibuja el cuadrado en la posición actual
            canvas.drawRect(squareX, squareY, squareX + squareSize, squareY + squareSize, paint)
        }

        override fun onTouchEvent(event: MotionEvent): Boolean {
            // Manejar eventos táctiles para mover el cuadrado
            when (event.action) {
                MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                    // Actualizar la posición del cuadrado según la posición del dedo
                    squareX = event.x - squareSize / 2
                    squareY = event.y - squareSize / 2
                    invalidate()
                }
            }
            return true
        }
    }
}
