package com.film.bazar.coredata.converterfactory

import com.google.protobuf.Message
import com.google.protobuf.util.JsonFormat
import okhttp3.RequestBody
import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import timber.log.Timber
import java.lang.reflect.Type

class ProtoJsonConverterFactory : Converter.Factory() {
    private val printer = JsonFormat.printer()
        .preservingProtoFieldNames()
        .omittingInsignificantWhitespace()

    override fun responseBodyConverter(
        type: Type,
        annotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<ResponseBody, *>? {
        val bodyConverter = retrofit.nextResponseBodyConverter<Message>(this, type, annotations)
        return Converter { value: ResponseBody ->
            val message = bodyConverter.convert(value)
            Timber.tag("OkHttp").d(printer.print(message))
            message
        }
    }

    override fun requestBodyConverter(
        type: Type,
        parameterAnnotations: Array<Annotation>,
        methodAnnotations: Array<Annotation>,
        retrofit: Retrofit
    ): Converter<*, RequestBody>? {
        val bodyConverter = retrofit.nextRequestBodyConverter<Message>(
            this,
            type,
            parameterAnnotations,
            methodAnnotations
        )
        return Converter { value: Message ->
            Timber.tag("OkHttp").d(printer.print(value))
            bodyConverter.convert(value)
        }
    }
}
