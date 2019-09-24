package resources

import co.location.locationapp.data.model.Address
import co.location.locationapp.data.model.DeliveryLocation
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okio.Okio


object TestUtil {
    fun getListOfLocation(): List<DeliveryLocation> {
        return listOf(DeliveryLocation("01", "abc", "www.google.com", Address(10.0, 12.0, "address")))
    }

    fun getArrayOfLocation(): String {
        return "[\n" +
                "  {\n" +
                "    \"id\": 10,\n" +
                "    \"description\": \"Deliver documents to Eric\",\n" +
                "    \"imageUrl\": \"https://s3-ap-southeast-1.amazonaws.com/lalamove-mock-api/images/pet-1.jpeg\",\n" +
                "    \"location\": {\n" +
                "      \"lat\": 22.335538,\n" +
                "      \"lng\": 114.176169,\n" +
                "      \"address\": \"Kowloon Tong\"\n" +
                "    }\n" +
                "  }]"
    }

    fun getLocationDataFromFile(fileName: String): List<DeliveryLocation>? {
        val inputStream = javaClass.classLoader?.getResourceAsStream("api-responses/$fileName")
        val source = Okio.buffer(Okio.source(inputStream))
        val data = source.readString(Charsets.UTF_8)
        return parseJsonArray(data)
    }

    private fun parseJsonArray(json: String): List<DeliveryLocation>? {
        val moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
        val type = Types.newParameterizedType(List::class.java, DeliveryLocation::class.java)
        val adapter: JsonAdapter<List<DeliveryLocation>> = moshi.adapter(type)
        return adapter.fromJson(json)
    }
}
