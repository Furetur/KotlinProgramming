package homeworks.tictactoe.controllers

import homeworks.tictactoe.GameClient
import io.ktor.util.KtorExperimentalAPI
import javafx.beans.property.SimpleStringProperty
import tornadofx.setValue
import tornadofx.getValue
import tornadofx.stringBinding
import tornadofx.Controller

@KtorExperimentalAPI
class LobbyController : Controller() {
    val client = GameClient()
    private val statusProperty = SimpleStringProperty("connecting")
    var status: String by statusProperty
    val statusText = stringBinding(statusProperty) {
        when (status) {
            "connecting" -> "Connecting to the server"
            "waiting" -> "Waiting for other players"
            "game started" -> "Game started"
            else -> "Please wait"
        }
    }

    init {
        client.onGameStart = { status = "game started" }
        client.onConnectionError = { status = "connection error" }
        client.onConnect = { status = "waiting" }
        client.onDisconnect = { status = "disconnected" }
        client.onGameStart = { status = "game started" }
    }

    fun start() {
        client.start()
    }

    fun close() {
        client.close()
    }

    fun onGameStart(listener: () -> Unit) {
        statusProperty.addListener { _, _, newValue ->
            if (newValue == "game started") {
                listener()
            }
        }
    }

    fun onConnectionError(listener: () -> Unit) {
        statusProperty.addListener { _, _, newValue ->
            if (newValue == "connection error" || newValue == "disconnected") {
                listener()
            }
        }
    }
}
