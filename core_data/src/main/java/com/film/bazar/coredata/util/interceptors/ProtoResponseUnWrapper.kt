import com.film.app.core.exception.ServiceFailureException
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.Type

class ProtoResponseUnWrapper : Converter.Factory() {

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        /*val bodyConverter = retrofit.nextResponseBodyConverter<BaseResponse>(
            this,
            BaseResponse::class.java,
            annotations
        )*/

        return Converter { value: ResponseBody ->
            /*val baseResponse = bodyConverter.convert(value)
            when (baseResponse?.dataOrErrorCase) {
               *//* BaseResponse.DataOrErrorCase.DATA -> {
                    val data: com.google.protobuf.Any = baseResponse.data
                    try {
                        val method = (type as Class<*>).getDeclaredMethod(
                            "parseFrom",
                            com.google.protobuf.ByteString::class.java
                        )
                        method.invoke(null, data.value)
                    } catch (ignored: Exception) {
                        throw ServiceFailureException(ignored.message ?: "Parse Error")
                    }
                }
                BaseResponse.DataOrErrorCase.ERROR -> {
                    val error = baseResponse.error
                    throw ServiceFailureException(error.errorCode, error.errorMessage)
                }*//*
                else -> throw ServiceFailureException("Parse Error")
            }*/
        }
    }
}
