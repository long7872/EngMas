package com.example.engmas.audio

import android.media.AudioFormat
import android.media.AudioRecord
import android.media.MediaRecorder
import android.util.Log
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream

object WavRecorder {
    private var audioRecord: AudioRecord? = null
    private var recordingThread: Thread? = null
    private var isRecording = false

    private const val sampleRate = 16000
    private const val channelConfig = AudioFormat.CHANNEL_IN_MONO
    private const val audioFormat = AudioFormat.ENCODING_PCM_16BIT

    fun startRecording(outputFile: File) {
        val minBufferSize = AudioRecord.getMinBufferSize(sampleRate, channelConfig, audioFormat)
        val bufferSize = minBufferSize.coerceAtLeast(sampleRate * 2) // ít nhất 1 giây âm thanh

        audioRecord = AudioRecord(
            MediaRecorder.AudioSource.MIC,
            sampleRate,
            channelConfig,
            audioFormat,
            bufferSize
        )

        if (audioRecord?.state != AudioRecord.STATE_INITIALIZED) {
            Log.e("WavRecorder", "AudioRecord initialization failed")
            return
        }

        audioRecord?.startRecording()
        isRecording = true

        recordingThread = Thread {
            val pcmData = ByteArrayOutputStream()
            val buffer = ByteArray(bufferSize)

            Log.d("WavRecorder", "Recording started...")

            while (isRecording) {
                val read = audioRecord?.read(buffer, 0, buffer.size) ?: 0
                if (read > 0) {
                    pcmData.write(buffer, 0, read)
                }
            }

            audioRecord?.stop()
            audioRecord?.release()
            audioRecord = null

            pcmData.flush()
            val totalAudioLen = pcmData.size().toLong()
            val totalDataLen = totalAudioLen + 36
            val byteRate = sampleRate * 2 // 16bit mono = 2 bytes/sample

            val wavFile = FileOutputStream(outputFile)
            wavFile.write(createWavHeader(totalAudioLen, totalDataLen, sampleRate, 1, byteRate))
            wavFile.write(pcmData.toByteArray())
            wavFile.close()

            Log.d("WavRecorder", "Recording saved: ${outputFile.absolutePath}")
        }

        recordingThread?.start()
    }

    fun stopRecording() {
        isRecording = false
        recordingThread?.join() // Đảm bảo thread ghi xong trước khi tiếp tục
        recordingThread = null
    }

    private fun createWavHeader(audioLen: Long, dataLen: Long, sampleRate: Int, channels: Int, byteRate: Int): ByteArray {
        val header = ByteArray(44)
        header[0] = 'R'.code.toByte(); header[1] = 'I'.code.toByte()
        header[2] = 'F'.code.toByte(); header[3] = 'F'.code.toByte()
        writeInt(header, 4, dataLen.toInt())
        header[8] = 'W'.code.toByte(); header[9] = 'A'.code.toByte()
        header[10] = 'V'.code.toByte(); header[11] = 'E'.code.toByte()
        header[12] = 'f'.code.toByte(); header[13] = 'm'.code.toByte()
        header[14] = 't'.code.toByte(); header[15] = ' '.code.toByte()
        writeInt(header, 16, 16) // PCM
        writeShort(header, 20, 1.toShort()) // PCM format
        writeShort(header, 22, channels.toShort())
        writeInt(header, 24, sampleRate)
        writeInt(header, 28, byteRate)
        writeShort(header, 32, (channels * 16 / 8).toShort()) // Block align
        writeShort(header, 34, 16.toShort()) // Bits per sample
        header[36] = 'd'.code.toByte(); header[37] = 'a'.code.toByte()
        header[38] = 't'.code.toByte(); header[39] = 'a'.code.toByte()
        writeInt(header, 40, audioLen.toInt())
        return header
    }

    private fun writeInt(buffer: ByteArray, offset: Int, value: Int) {
        buffer[offset] = (value and 0xff).toByte()
        buffer[offset + 1] = ((value shr 8) and 0xff).toByte()
        buffer[offset + 2] = ((value shr 16) and 0xff).toByte()
        buffer[offset + 3] = ((value shr 24) and 0xff).toByte()
    }

    private fun writeShort(buffer: ByteArray, offset: Int, value: Short) {
        buffer[offset] = (value.toInt() and 0xff).toByte()
        buffer[offset + 1] = ((value.toInt() shr 8) and 0xff).toByte()
    }
}
