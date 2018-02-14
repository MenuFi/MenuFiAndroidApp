package com.ckmcknight.android.menufi.model.containers;

import org.json.JSONObject;

public interface JsonCreator<T> {

    T createFromJsonObject(JSONObject object);
}
