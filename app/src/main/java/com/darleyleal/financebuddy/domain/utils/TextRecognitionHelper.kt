package com.darleyleal.financebuddy.domain.utils

import android.content.Context
import android.net.Uri
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

object TextRecognitionHelper {
    fun recognizeTextFromUri(context: Context, uri: Uri, onResut: (String) -> Unit) {
        val inputImage = InputImage.fromFilePath(context, uri)
        val recognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

        recognizer.process(inputImage)
            .addOnSuccessListener { visionText ->
                onResut(visionText.text)
            }
            .addOnFailureListener { exception ->
                onResut("Recognition failed: ${exception.localizedMessage}")
            }
    }
}