package homeworks.server

import io.ktor.websocket.WebSocketServerSession

class PlayerData(val id: Int, val session: WebSocketServerSession)
