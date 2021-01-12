package com.example.mojepocasi_utb

import androidx.datastore.CorruptionException
import androidx.datastore.Serializer
import com.example.protodatastoretest.DataHistory
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

class MySerializer : Serializer<DataHistory> {
    override fun readFrom(input: InputStream): DataHistory {
        try {
            return DataHistory.parseFrom(input)
        } catch (exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read proto.", exception)
        }
    }

    override fun writeTo(t: DataHistory, output: OutputStream) {
        t.writeTo(output)
    }
}