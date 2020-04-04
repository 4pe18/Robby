package fr.the4pe18.robby.audit;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.Map;

/**
 * @author 4PE18
 */
public class VocalAuditAdapter extends TypeAdapter<VocalAudit> {

    @Override
    public void write(JsonWriter jsonWriter, VocalAudit vocalAudit) throws IOException {
        jsonWriter.beginObject();
        jsonWriter.name("voiceChannel");
            jsonWriter.beginObject();
            jsonWriter.name("id");
            jsonWriter.value(vocalAudit.getChannel().getIdLong());
            jsonWriter.name("name");
            jsonWriter.value(vocalAudit.getChannel().getName());
            jsonWriter.endObject();
        jsonWriter.name("startTime");
        jsonWriter.value(vocalAudit.getStartTime());
        jsonWriter.name("stopTime");
        jsonWriter.value(vocalAudit.getStopTime());
        jsonWriter.name("membersCount");
            jsonWriter.beginObject();
            for (Map.Entry<Long,Integer> e: vocalAudit.getMembersCount().entrySet()) {
                jsonWriter.name(e.getKey().toString());
                jsonWriter.value(e.getValue());
            }
            jsonWriter.endObject();
        jsonWriter.name("audits");
            jsonWriter.beginObject();
            for (VocalAudit.VocalChange e: vocalAudit.getVocalChanges()) {
                jsonWriter.name(e.getTime().toString());
                    jsonWriter.beginObject();
                    jsonWriter.name("isJoin");
                    jsonWriter.value(e.isJoin());
                    jsonWriter.name("member");
                        jsonWriter.beginObject();
                        jsonWriter.name("id");
                        jsonWriter.value(e.getMember().getIdLong());
                        jsonWriter.name("name");
                        jsonWriter.value(e.getMember().getNickname());
                        jsonWriter.endObject();
                    jsonWriter.endObject();
                }
            jsonWriter.endObject();
        jsonWriter.endObject();


    }

    @Override
    public VocalAudit read(JsonReader jsonReader) {
        return null;
    }
}
