package com.xored.javafx.packeteditor.scapy;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ScapyUtils {
    public static JsonObject layer(String type) {
        JsonObject res = new JsonObject();
        res.add("id", new JsonPrimitive(type));
        return res;
    }

    // Ether()/IP()/TCP()
    public static JsonArray tcpIpTemplate() {
        JsonArray payload = new JsonArray();
        payload.add(layer("Ether"));
        payload.add(layer("IP"));
        payload.add(layer("TCP"));
        return payload;
    }

    /** generates payload for reconstruct_pkt */
    public static List<ReconstructProtocol> createReconstructPktPayload(List<String> fieldPath, ReconstructField fieldEdit) {
        List<ReconstructProtocol> res = fieldPath.stream().map(
                protocolId->ReconstructProtocol.pass(protocolId)
        ).collect(Collectors.toList());

        if (!fieldPath.isEmpty()) {
            res.get(res.size() - 1).fields = Arrays.asList(fieldEdit);
        }
        return res;
    }

}

