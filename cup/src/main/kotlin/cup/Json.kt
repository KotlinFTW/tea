package cup

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.module.SimpleModule
import com.fasterxml.jackson.jaxrs.json.JacksonJsonProvider
import com.fasterxml.jackson.module.kotlin.KotlinModule
import java.time.LocalDate
import javax.ws.rs.core.MediaType



import com.fasterxml.jackson.core.JsonGenerator
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.databind.DeserializationContext
import com.fasterxml.jackson.databind.JsonNode
import com.fasterxml.jackson.databind.SerializerProvider
import com.fasterxml.jackson.databind.deser.std.StdDeserializer
import com.fasterxml.jackson.databind.node.IntNode
import com.fasterxml.jackson.databind.ser.std.StdSerializer

class LocalDateDeserializer : StdDeserializer<LocalDate>(LocalDate::class.java) {

    override fun deserialize(jp: JsonParser, ctxt: DeserializationContext): LocalDate {
        val node = jp.codec.readTree<JsonNode>(jp)
        val y = (node.get("year") as IntNode).numberValue() as Int
        val m = (node.get("month") as IntNode).numberValue() as Int
        val d = (node.get("dom") as IntNode).numberValue() as Int
        return LocalDate.of(y, m, d)
    }

}

class LocalDateSerializer : StdSerializer<LocalDate>(LocalDate::class.java) {

    override fun serialize(
            value: LocalDate, jgen: JsonGenerator, provider: SerializerProvider) {

        jgen.writeStartObject()
        jgen.writeNumberField("year", value.year)
        jgen.writeNumberField("month", value.monthValue)
        jgen.writeNumberField("dom", value.dayOfMonth)
        jgen.writeEndObject()
    }
}


val json = createJson()

private fun createJson(): ObjectMapper {
    val lotTime = SimpleModule()
    lotTime.addDeserializer(LocalDate::class.java, LocalDateDeserializer())
    lotTime.addSerializer(LocalDate::class.java, LocalDateSerializer())
    return ObjectMapper().registerModule(KotlinModule()).registerModule(lotTime)
}

class RestJacksonProvider : JacksonJsonProvider() {

    override fun locateMapper(type: Class<*>?, mediaType: MediaType?): ObjectMapper = json
}

