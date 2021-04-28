package com.mygdx.game.NakamaController;

import com.heroiclabs.nakama.SocketClient;

public class NakamaGameManager {

    SocketClient socket;
    //https://github.com/heroiclabs/nakama-godot-demo/blob/master/godot/src/Autoload/ServerConnection.gd

    public void estado_update(String estado){

    }

    public void posicion_update(int x, int y){
//        socket.sendMatchData();
//        func send_posicion_update(input: float) -> void:
//        if _socket:
//        var payload := {id = get_user_id(), inp = input}
//        _socket.send_match_state_async(_world_id, OpCodes.UPDATE_INPUT, JSON.print(payload))
    }

    public void recibir_estado_partida(){
//        var code := match_state.op_code
//        var raw := match_state.data
//
//        match code:
//        OpCodes.UPDATE_STATE:
//        var decoded: Dictionary = JSON.parse(raw).result
//
//        var positions: Dictionary = decoded.pos
//        var inputs: Dictionary = decoded.inp
//
//        emit_signal("state_updated", positions, inputs)
//
//        OpCodes.UPDATE_COLOR:
//        var decoded: Dictionary = JSON.parse(raw).result
//
//        var id: String = decoded.id
//        var color := Converter.color_string_to_color(decoded.color)
//
//        emit_signal("color_updated", id, color)
//
//        OpCodes.INITIAL_STATE:
//        var decoded: Dictionary = JSON.parse(raw).result
//
//        var positions: Dictionary = decoded.pos
//        var inputs: Dictionary = decoded.inp
//        var colors: Dictionary = decoded.col
//        var names: Dictionary = decoded.nms
//
//        for key in colors:
//        colors[key] = Converter.color_string_to_color(colors[key])
//
//        emit_signal("initial_state_received", positions, inputs, colors, names)
//
//        OpCodes.DO_SPAWN:
//        var decoded: Dictionary = JSON.parse(raw).result
//
//        var id: String = decoded.id
//        var color := Converter.color_string_to_color(decoded.col)
//        var name: String = decoded.nm
//
//        emit_signal("character_spawned", id, color, name)

    }

    public void direccion_update(String direccion){

    }

    public void personaje_spawn(){

    }

    public void join_world(){

    }
}
