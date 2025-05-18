package com.example.engmas.network

import io.socket.client.IO
import io.socket.client.Socket
import io.socket.emitter.Emitter
import java.net.URISyntaxException

object SocketManager {

    private const val SOCKET_URL = "http://10.0.2.2:3000"

    private var socket: Socket? = null

    fun connect() {
        if (socket == null) {
            try {
                socket = IO.socket(SOCKET_URL)
            } catch (e: URISyntaxException) {
                e.printStackTrace()
            }
        }

        socket?.connect()
    }

    fun disconnect() {
        socket?.disconnect()
    }

    fun leave() {
        emit("leave", "")
    }

    fun emit(event: String, data: Any) {
        socket?.emit(event, data)
    }

    fun on(event: String, listener: Emitter.Listener) {
        socket?.on(event, listener)
    }

    fun off(event: String) {
        socket?.off(event)
    }

    fun isConnected(): Boolean {
        return socket?.connected() == true
    }

}