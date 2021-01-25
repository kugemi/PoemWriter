package com.kugemi.poemwriter.views.custom_views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Typeface
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import java.util.*
import java.util.regex.Pattern

class CustomEditText : AppCompatEditText {
    private val grayPaint : Paint = Paint()
    private val greenPaint : Paint = Paint()
    private var syllableNumbers : List<Int> = listOf(-1)
    private var language : String = "en"
    private lateinit var pattern : Pattern
    private var xPosition = 0

    constructor(context: Context) : super(context)

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs)

    constructor(context: Context, attrs: AttributeSet?, defStyle: Int) : super(context, attrs, defStyle)

    init {
        grayPaint.style = Paint.Style.FILL
        grayPaint.color = Color.GRAY
        grayPaint.textSize = 50F
        grayPaint.typeface = Typeface.MONOSPACE

        greenPaint.style = Paint.Style.FILL
        greenPaint.color = Color.GREEN
        greenPaint.textSize = 50F
        greenPaint.typeface = Typeface.MONOSPACE
    }

    override fun onDraw(canvas: Canvas?) {
        xPosition = this.width - this.width / 10
        var baseline : Int
        val lineCount = lineCount
        val textFromEdit = Objects.requireNonNull(text).toString()
        val lines = textFromEdit.split("\n".toRegex()).toTypedArray()
        var lineIndex = 0

        for (idx in 0 until lineCount){
            var syllableCount = 0
            var syllableIndex = lineIndex
            while (syllableIndex >= syllableNumbers.size){
                syllableIndex -= syllableNumbers.size
            }


            if (language == "en")
                pattern = Pattern.compile("[aeyiuoAEYIUO]")
            if (language == "ru")
                pattern = Pattern.compile("[уеыаоэяиюёУЕЫАОЭЯИЮЁ]")
            if (lineIndex < lines.size){
                val matcher = pattern.matcher(lines[lineIndex])
                while (matcher.find()){
                    syllableCount++
                }
            }

            baseline = getLineBounds(idx, null)
            if (idx == 0){
                if (syllableCount == syllableNumbers[syllableIndex]){
                    canvas?.drawText(syllableCount.toString(), xPosition.toFloat(), baseline.toFloat(), greenPaint)
                }
                else{
                    canvas?.drawText(syllableCount.toString(), xPosition.toFloat(), baseline.toFloat(), grayPaint)
                }
                lineIndex++
            }
            else if (text.toString()[layout.getLineStart(idx) - 1] == '\n'){
                if (syllableCount == syllableNumbers[syllableIndex]){
                    canvas?.drawText(syllableCount.toString(), xPosition.toFloat(), baseline.toFloat(), greenPaint)
                }
                else{
                    canvas?.drawText(syllableCount.toString(), xPosition.toFloat(), baseline.toFloat(), grayPaint)
                }
                lineIndex++
            }
        }

        super.onDraw(canvas)
    }

    fun setSyllableNumber(syllableInfo : String){
        if (syllableInfo.isNotEmpty()){
            syllableNumbers = syllableInfo.split("-").map {
                it.toInt()
            }
        }
    }

    fun setLanguage(language : String){
        if (language.isNotEmpty()){
            this.language = language
        }
    }
}