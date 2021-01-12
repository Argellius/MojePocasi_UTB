package com.example.mojepocasi_utb

import android.content.Context
import android.util.Log
import androidx.datastore.DataStore
import androidx.datastore.createDataStore
import com.example.protodatastoretest.DataHistory
import kotlinx.coroutines.flow.catch
import java.io.IOException

class ProtoRepository(context: Context) {
    private val dataStore: DataStore<DataHistory> = context.createDataStore(
        "my_data",
        serializer = com.example.mojepocasi_utb.MySerializer()
    )

    val readProto = dataStore.data
        .catch { exception->
            if(exception is IOException){
                Log.d("Error", exception.message.toString())
                emit(DataHistory.getDefaultInstance())
            }else{
                throw exception
            }
        }

    suspend fun updateValue(PosledniVyhledavani: String, VyhledavaniHistorie: String){
        dataStore.updateData { preference->
            preference.toBuilder().setPosledniVyhledavani(PosledniVyhledavani).setVyhledavaniHistorie(VyhledavaniHistorie).build()
        }
    }

}

