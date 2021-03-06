package com.xored.javafx.packeteditor.data.combined;

import com.google.gson.JsonElement;
import com.xored.javafx.packeteditor.data.FEInstructionParameter;
import com.xored.javafx.packeteditor.data.user.FEInstruction;
import com.xored.javafx.packeteditor.data.user.UserField;
import com.xored.javafx.packeteditor.metatdata.FieldMetadata;
import com.xored.javafx.packeteditor.scapy.FieldData;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static com.xored.javafx.packeteditor.metatdata.FieldMetadata.*;

public class CombinedField {
    UserField userField;

    FieldData scapyField;
    FieldMetadata meta;
    CombinedProtocol parent;

    public String getId() { return meta.getId(); }
    /**
     * use metadata to get id, name, type, ...
     */
    public FieldMetadata getMeta() {
        return meta;
    }


    public String getDisplayValue() {
        String res = getScapyDisplayValue();
        if (res == null) res = getUserStringValue();
        return res;
    }
    public JsonElement getValue() { return getScapyValue(); }

    public boolean hasUserValue() { return userField != null && userField.getValue() != null; }
    public JsonElement getUserValue() {
        return userField != null ? userField.getValue() : null;
    }
    public String getUserStringValue() {
        return userField != null ? userField.getStringValue() : null;
    }

    /**
     * scapy model is optional. it won't be created if user model contains errors
     */
    public JsonElement getScapyValue() {
        return scapyField != null ? scapyField.getValue() : null;
    }

    public String getScapyDisplayValue() {
        return scapyField != null ? scapyField.getHumanValue() : null;
    }

    public CombinedProtocol getProtocol() {
        return parent;
    }

    public FieldData getScapyFieldData() {
        return scapyField;
    }

    public List<FEInstructionParameter> getFEInstructionParameters() {
        FEInstruction fieldInstruction = parent.getUserProtocol().getFieldInstruction(getId());
        if (fieldInstruction == null) {
            return Collections.<FEInstructionParameter>emptyList();
        }
        
        return fieldInstruction.getParameters().entrySet().stream()
                .map(parameterEntry -> new FEInstructionParameter(this, parent.getMeta().getInstructionParameterMeta(parameterEntry.getKey())))
                .collect(Collectors.toList());
    }

    public FieldType getType() {
        return meta.getType();
    }
}
